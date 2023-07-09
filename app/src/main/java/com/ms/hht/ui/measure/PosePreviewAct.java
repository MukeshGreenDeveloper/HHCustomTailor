package com.ms.hht.ui.measure;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.ms_android_sdk.CallBack;
import com.example.ms_android_sdk.Mirrorsize_Function;
import com.google.gson.Gson;
import com.ms.hht.R;
import com.ms.hht.data.request.GetMSMeasurementRequest;
import com.ms.hht.data.request.SetMeasurementRequest;
import com.ms.hht.data.response.GETMSMeasurementResponse;
import com.ms.hht.data.response.SETmeasurementResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityPosePreviewBinding;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.CommonFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PosePreviewAct extends AppCompatActivity {
    public static String imageUrl1;
    public static String imageUrl2;
    String ApiStatus = "";
    String ImagePath1;
    String ImagePath2;
    String[] PoseQuestions = {"Is your front pose correct, as shown in the picture? ", "Check if your arms are raised at 45° (approx.) and feet apart, as shown in the picture!", "Check if you tied your hairs above your neck.", "Did you turn at 90°? ", "Is your feet joined and palms touching your thigh?"};
    String apiMessage = "";
    CountDownTimer delaytimer;
    Boolean delaytimerstatus = false;
    Dialog dialog;
    String gender;
    GETMSMeasurementResponse getMSMeasurementResponse;
    //    public List<Object> measurementItem = new ArrayList();
    int possition = 0;
    ActivityPosePreviewBinding previewBinding;
    Mirrorsize_Function mirrorsize_function;
    private final DisposableData response = new DisposableData() {
        public void onSuccess(String str, Object obj) throws Exception {
            if (str.equalsIgnoreCase("get Measurement result")) {
                getMSMeasurementResponse = (GETMSMeasurementResponse) obj;
                if (getMSMeasurementResponse == null) {
                    PosePreviewAct posePreviewAct = PosePreviewAct.this;
                    posePreviewAct.apiMessage = posePreviewAct.getResources().getString(R.string.try_again);
                } else if (getMSMeasurementResponse.getCode().intValue() == 1) {
                    mirrorsize_function = new Mirrorsize_Function();
                    mirrorsize_function.MS_GetMeasurement(PosePreviewAct.this, Constants.MIRROR_ID,
                            Constants.APPAREL_NAME, Constants.BRAND_NAME, gender,
                            sessionManager.getMerchantEmail(), getMSMeasurementResponse.getUserId(), new CallBack() {
                                @Override
                                public void onSuccess(String response) {
                                    Log.d("keyss...", "Success=>" + response);
                                    getMSMeasurementResponse = new Gson().fromJson(response, GETMSMeasurementResponse.class);

                                        if (getMSMeasurementResponse == null) {
                                            PosePreviewAct posePreviewAct3 = PosePreviewAct.this;
                                            posePreviewAct3.apiMessage = posePreviewAct3.getResources().getString(R.string.try_again);
                                        } else if (getMSMeasurementResponse.getCode().intValue() == 1) {
                                            ApiStatus = "complete";
                                        } else {
                                            PosePreviewAct posePreviewAct4 = PosePreviewAct.this;
                                            posePreviewAct4.apiMessage = posePreviewAct4.setMeasurementResponse.getMessage();
                                        }
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    Log.d("keyss...", "onError=>" + error.networkResponse);
                                    error.printStackTrace();
                                    apiMessage = error.getMessage();
                                }
                            });
//                    new GetMSMeasurementRequest(this.sessionManager.getUserDetails()
//                            .get(SessionManager.PROCESS_ID),
//                            String.valueOf((int)ComUserProfileData.getHeight()),
//                            replaceAll, String.valueOf((int)ComUserProfileData.getWeight()),
//                            String.valueOf((int)ComUserProfileData.getAngle()),
//                            String.valueOf(ComUserProfileData.getAge()), this.gender, imageUrl1,
//                            imageUrl2, this.sessionManager.getFitType(),
//                            this.sessionManager.getMerchantEmail(), this.sessionManager.getUserType(),
//                            this.sessionManager.getProductName(), "0", "", Constants.MIRROR_ID)
//                    setMeasurement();//************************
                } else {
                    PosePreviewAct posePreviewAct2 = PosePreviewAct.this;
                    posePreviewAct2.apiMessage = posePreviewAct2.getMSMeasurementResponse.getMessage();
                }
            } else if (str.equalsIgnoreCase("set Measurement result")) {
                setMeasurementResponse = (SETmeasurementResponse) obj;
                if (setMeasurementResponse == null) {
                    PosePreviewAct posePreviewAct3 = PosePreviewAct.this;
                    posePreviewAct3.apiMessage = posePreviewAct3.getResources().getString(R.string.try_again);
                } else if (setMeasurementResponse.getCode().intValue() == 1) {
                    ApiStatus = "complete";
                } else {
                    PosePreviewAct posePreviewAct4 = PosePreviewAct.this;
                    posePreviewAct4.apiMessage = posePreviewAct4.setMeasurementResponse.getMessage();
                }
            }
        }

        public void onError(String str) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(PosePreviewAct.this, str, false);
        }
    };
    SessionManager sessionManager;
    SETmeasurementResponse setMeasurementResponse;
    MediaPlayer song;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityPosePreviewBinding inflate = ActivityPosePreviewBinding.inflate(getLayoutInflater());
        this.previewBinding = inflate;
        setContentView((View) inflate.getRoot());
        this.song = MediaPlayer.create(this, R.raw.audio6);
        this.sessionManager = new SessionManager(this);
        this.gender = ComUserProfileData.getmeasurementGender();
        Dialog dialog2 = new Dialog(this);
        this.dialog = dialog2;
        dialog2.setCancelable(false);
        this.ImagePath1 = getIntent().getStringExtra("frontFilePath");
        this.ImagePath2 = getIntent().getStringExtra("sideFilePath");
        final Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Glide.with((FragmentActivity) this).load(this.ImagePath1).into(this.previewBinding.yourPose);
        getMeasurement();
        this.previewBinding.noButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showRetakeDialog();
            }
        });
        this.previewBinding.yesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                possition++;
                if (possition == 3) {
                    loadAnimation.reset();
                    previewBinding.poseHeadText.setText("SIDE");
                    previewBinding.checkPosetxt123.clearAnimation();
                    previewBinding.checkPosetxt123.startAnimation(loadAnimation);
                    previewBinding.checkPosetxt123.setText(PoseQuestions[possition]);
                    if (gender.equalsIgnoreCase("female")) {
                        previewBinding.correctPoseImage.setImageResource(R.drawable.femalefronttightfit);
                    } else {
                        previewBinding.correctPoseImage.setImageResource(R.drawable.maletightfitside);
                    }
                    Glide.with((FragmentActivity) PosePreviewAct.this).load(ImagePath2).into(previewBinding.yourPose);
                } else if (possition < PoseQuestions.length) {
                    loadAnimation.reset();
                    previewBinding.checkPosetxt123.clearAnimation();
                    previewBinding.checkPosetxt123.startAnimation(loadAnimation);
                    previewBinding.checkPosetxt123.setText(PoseQuestions[possition]);
                } else if (possition == PoseQuestions.length) {
                    Log.d("LOGnew", "limit reached");
                    CommFunc.ShowProgressbar(PosePreviewAct.this);
                    song.start();
                    if (!delaytimerstatus.booleanValue()) {
                        delaytimerstatus = true;
                        delaytimer = new CountDownTimer(120000, 1000) {
                            public void onFinish() {
                            }

                            public void onTick(long j) {
                                checkData();
                            }
                        }.start();
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkData() {
        Log.d("LOGnew", "Check Data function running");
        if (this.ApiStatus.equalsIgnoreCase("complete")) {
            CommFunc.DismissDialog();
            this.song.stop();
            switchNextActivity();
        }
        if (!this.apiMessage.isEmpty()) {
            CommFunc.DismissDialog();
            this.delaytimer.cancel();
            this.song.stop();
            CommFunc.ShowStatusPop(this, this.apiMessage, false);
        }
    }

    private void getMeasurement() {
        String str;
        System.out.println("call get api func");
        String str2 = Build.MANUFACTURER;
        String str3 = Build.MODEL;
        if (str3.toLowerCase().startsWith(str2.toLowerCase())) {
            str = CommonFunc.capitalize(str3);
        } else {
            str = (CommonFunc.capitalize(str2) + str3).trim();
        }
        String replaceAll = str.replaceAll(" ", "");
        if (InternetConnection.isConnected(this)) {
            APICallList.getMSMeasurement(new GetMSMeasurementRequest(this.sessionManager.getUserDetails()
                            .get(SessionManager.PROCESS_ID),
                            String.valueOf((int) ComUserProfileData.getHeight()),
                            replaceAll, String.valueOf((int) ComUserProfileData.getWeight()),
                            String.valueOf((int) ComUserProfileData.getAngle()),
                            String.valueOf(ComUserProfileData.getAge()), this.gender, imageUrl1,
                            imageUrl2, this.sessionManager.getFitType(),
                            this.sessionManager.getMerchantEmail(), this.sessionManager.getUserType(),
                            this.sessionManager.getProductName(), "0", "", Constants.MIRROR_ID),
                    "get Measurement result", this.response, this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    /* access modifiers changed from: private */
    public void setMeasurement(GETMSMeasurementResponse getMSMeasurementResponse) {
        String str;
//        for (int i = 0; i < getMSMeasurementResponse.getMeasurementData().size() - 1; i++) {
//            LinkedHashMap linkedHashMap = new LinkedHashMap();
//            if (this.gender.equalsIgnoreCase("male")) {
//                str = "https://commonms.s3.ap-south-1.amazonaws.com/male_small_icon/" + this.getMSMeasurementResponse.getData().get(i).getPointName();
//            } else {
//                str = "https://commonms.s3.ap-south-1.amazonaws.com/female_small_icon/" + this.getMSMeasurementResponse.getData().get(i).getPointName();
//            }
//            linkedHashMap.put("part", this.getMSMeasurementResponse.getData().get(i).getDisplayName());
//            linkedHashMap.put("value", this.getMSMeasurementResponse.getData().get(i).getValueIninch());
//            linkedHashMap.put("valueIncm", this.getMSMeasurementResponse.getData().get(i).getValueIncm());
//            linkedHashMap.put("pointName", str);
//            linkedHashMap.put("description", this.getMSMeasurementResponse.getData().get(i).getDescription());
//            this.measurementItem.add(linkedHashMap);
//        }
//        if (InternetConnection.isConnected(this)) {
//            SetMeasurementRequest.General general = new SetMeasurementRequest.General(ComUserProfileData.getWeightWithUnit(), ComUserProfileData.getHeightWithUnit(), Integer.valueOf(ComUserProfileData.getAge()), this.gender, ComUserProfileData.getShoeSize(), ComUserProfileData.getPreferredFit());
//            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
//            linkedHashMap2.put("measurement", this.measurementItem);
//            linkedHashMap2.put("comment", "");
//            linkedHashMap2.put("general", general);
//            APICallList.setUserMeasurement(linkedHashMap2, "set Measurement result", this.response, this);
//            mirrorsize_function.MS_Get_Size_Recommendation(this, Constants.MIRROR_ID,
//                    Constants.APPAREL_NAME, Constants.BRAND_NAME, gender, sessionManager.getMerchantEmail(),
//                    getMSMeasurementResponse.getUserId(), new CallBack() {
//                        @Override
//                        public void onSuccess(String response) {
//                            Log.d("keyssss2...","Success=>"+response);
//                        }
//
//                        @Override
//                        public void onError(VolleyError error) {
//                            error.printStackTrace();
//                            CommFunc.ShowStatusPop(PosePreviewAct.this, error.getMessage(), false);
//                        }
//                    });
//            return;
//        }
//        CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
    }

    private void switchNextActivity() {
        Intent intent = new Intent(this, MeasurementResult.class);
        MeasurementResult.MeasurementHistoryActivityComingFrom = "cart";
        MeasurementResult.MEASUREMENT = this.getMSMeasurementResponse.getMeasurementData();
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /* access modifiers changed from: private */
    public void showRetakeDialog() {
        this.dialog.setContentView(R.layout.delete_popup);
        ((ImageView) this.dialog.findViewById(R.id.successPop_Header_image)).setImageResource(R.drawable.oopsimage);
        ((TextView) this.dialog.findViewById(R.id.successPop_error_description))
                .setText("We recommend you to retake for accurate measurement. RETAKE?");
        ((Button) this.dialog.findViewById(R.id.pop_no_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRetakeDialog(view);
            }
        });
        ((Button) this.dialog.findViewById(R.id.successPop_done_button)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showRetakeDialog(view);
                    }
                });
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$showRetakeDialog$0$com-ms-hht-ui-measure-PosePreviewAct  reason: not valid java name */
//    public /* synthetic */ void showRetakeDialog(View view) {
//        this.dialog.dismiss();
//    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$showRetakeDialog$1$com-ms-hht-ui-measure-PosePreviewAct  reason: not valid java name */
    public /* synthetic */ void showRetakeDialog(View view) {
        this.dialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), CameraAct.class);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        File file = new File(this.ImagePath1);
        File file2 = new File(this.ImagePath2);
        file.delete();
        file2.delete();
        if (this.song.isPlaying()) {
            this.song.stop();
            this.song.release();
        }
        if (this.delaytimerstatus.booleanValue()) {
            this.delaytimer.cancel();
        }
    }
}
