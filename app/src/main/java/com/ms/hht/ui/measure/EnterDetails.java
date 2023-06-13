package com.ms.hht.ui.measure;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.ms.hht.R;
import com.ms.hht.data.SignUpResponse;
import com.ms.hht.data.request.SignupRequest;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActEnterDetailsBinding;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

public class EnterDetails extends AppCompatActivity implements View.OnClickListener {

    ActEnterDetailsBinding detailsBinding;
    String Isheight = "cm";
    String IsKG = "kg";
    String ShoeSizeUnit = "US";
    String PrefferedType = "";
    String genderForMeas = "Male";
    int HeightData, WeightData;
    SessionManager sessionManager;
    SignUpResponse signUpResponse;
    String userToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = ActEnterDetailsBinding.inflate(getLayoutInflater());
        setContentView(detailsBinding.getRoot());
        sessionManager = new SessionManager(EnterDetails.this);
        detailsBinding.backBtn.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        detailsBinding.nextBtn.setOnClickListener(this);
        detailsBinding.cmBtn.setOnClickListener(this);
        detailsBinding.inchBtn.setOnClickListener(this);
        detailsBinding.kgBtn.setOnClickListener(this);
        detailsBinding.lbsBtn.setOnClickListener(this);
        detailsBinding.usBtn.setOnClickListener(this);
        detailsBinding.euBtn.setOnClickListener(this);
        detailsBinding.taperedBtn.setOnClickListener(this);
        detailsBinding.regularBtn.setOnClickListener(this);
        detailsBinding.LooseBtn.setOnClickListener(this);
        detailsBinding.maleBtnTapped.setOnClickListener(this);
        detailsBinding.femaleBtnTapped.setOnClickListener(this);
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if(appLinkData!=null) { // Intent received from click on URL or scan URL
            Log.d("keyss..", "Received Data here==>activion=" + appLinkAction);
            Log.d("keyss..", "Received Data here==>appLinkData=" + appLinkData);
            try {
                FirebaseApp.initializeApp(getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    FirebaseDynamicLinks.getInstance()
                            .getDynamicLink(appLinkIntent)
                            .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                                @Override
                                public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                                    // Get deep link from result (may be null if no link is found)
                                    Uri deepLink = null;
                                    String token = null;
                                    if (pendingDynamicLinkData != null) {
                                        deepLink = pendingDynamicLinkData.getLink();
                                        token = deepLink.getQueryParameter("token");
                                        userLoginByToken(token);

                                    }
                                    Log.w("keysss....Success", "Receied Link data=>" + "__token==>" + token);
                                }
                            })
                            .addOnFailureListener(this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("keysss....Error", "getDynamicLink:onFailure", e);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    public int HeightValidation() {
        double ft, inch;
        if (Isheight.equalsIgnoreCase("feet")) {
            if (detailsBinding.fteditText.getText().toString().isEmpty()) {
                return 0;
            } else if (detailsBinding.inchEditText.getText().toString().isEmpty()) {
                String ftValue = detailsBinding.fteditText.getText().toString();
                ft = Double.parseDouble(ftValue) * 12 * 2.54 * 10;
                return (int) Math.round(ft);
            } else {
                String ftValue = detailsBinding.fteditText.getText().toString();
                ft = Double.parseDouble(ftValue) * 12 * 2.54 * 10;
                String inValue = detailsBinding.inchEditText.getText().toString();
                inch = Double.parseDouble(inValue) * 2.54 * 10;
                double height = ft + inch;
                return (int) height;
            }
        }
        else {
            if (detailsBinding.cmeditText.getText().toString().isEmpty()) {
                return 0;
            } else {
                return Integer.parseInt(detailsBinding.cmeditText.getText().toString()) * 10;
            }
        }

    }

    private int weightValidation() {
        if (IsKG.equalsIgnoreCase("kg")) {

            if (detailsBinding.weightEditText.getText().toString().isEmpty()) {
                return 0;
            } else {
                return Integer.parseInt(detailsBinding.weightEditText.getText().toString());
            }
        } else {

            if (detailsBinding.weightEditText.getText().toString().isEmpty()) {
                return 0;
            } else {
                double weight = Double.parseDouble(detailsBinding.weightEditText.getText().toString()) / 2.20;
                return (int) weight;
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.usBtn:
                detailsBinding.usBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                detailsBinding.euBtn.setTextColor(Color.parseColor("#173043"));
                detailsBinding.euBtn.setBackgroundResource(R.drawable.white_background);
                detailsBinding.shoeSizeEditText.setText("");
                ShoeSizeUnit = "US";
                break;

            case R.id.euBtn:
                detailsBinding.euBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                detailsBinding.usBtn.setTextColor(Color.parseColor("#173043"));
                detailsBinding.usBtn.setBackgroundResource(R.drawable.white_background);
                detailsBinding.shoeSizeEditText.setText("");
                ShoeSizeUnit = "EU";
                break;
            case R.id.kgBtn:
                detailsBinding.kgBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                detailsBinding.lbsBtn.setBackgroundResource(R.drawable.white_background);
                detailsBinding.lbsBtn.setTextColor(Color.parseColor("#173043"));
                IsKG = "kg";
                break;

            case R.id.lbsBtn:
                detailsBinding.lbsBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                detailsBinding.kgBtn.setBackgroundResource(R.drawable.white_background);
                detailsBinding.kgBtn.setTextColor(Color.parseColor("#173043"));
                IsKG = "lbs";
                break;

            case R.id.inchBtn:
                detailsBinding.FtnInchLayout.setVisibility(View.VISIBLE);
                detailsBinding.cmeditText.setVisibility(View.GONE);
                detailsBinding.cmBtn.setBackgroundResource(R.drawable.white_background);
                detailsBinding.cmBtn.setTextColor(Color.parseColor("#173043"));
                detailsBinding.inchBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                Isheight = "feet";
                break;

            case R.id.cmBtn:
                detailsBinding.cmeditText.setVisibility(View.VISIBLE);
                detailsBinding.FtnInchLayout.setVisibility(View.GONE);
                detailsBinding.cmeditText.setHint(R.string.height);
                detailsBinding.cmBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                detailsBinding.inchBtn.setBackgroundResource(R.drawable.white_background);
                detailsBinding.inchBtn.setTextColor(Color.parseColor("#173043"));
                Isheight = "cm";
                break;
            case R.id.maleBtnTapped:
                detailsBinding.maleBtnTapped.setBackgroundResource(R.drawable.blue_background_for_button);
                detailsBinding.femaleBtnTapped.setBackgroundResource(R.drawable.white_edit_text_background);
                genderForMeas = "Male";
                break;
            case R.id.femaleBtnTapped:
                detailsBinding.femaleBtnTapped.setBackgroundResource(R.drawable.blue_background_for_button);
                detailsBinding.maleBtnTapped.setBackgroundResource(R.drawable.white_edit_text_background);
                genderForMeas = "Female";
                break;
            case R.id.taperedBtn:
                detailsBinding.taperedBtn.setBackgroundResource(R.drawable.blue_background_for_button);
                detailsBinding.regularBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                detailsBinding.LooseBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                PrefferedType = "Tapered";
                break;
            case R.id.regularBtn:
                detailsBinding.regularBtn.setBackgroundResource(R.drawable.blue_background_for_button);
                detailsBinding.LooseBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                detailsBinding.taperedBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                PrefferedType = "Regular";
                break;
            case R.id.LooseBtn:
                detailsBinding.LooseBtn.setBackgroundResource(R.drawable.blue_background_for_button);
                detailsBinding.taperedBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                detailsBinding.regularBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                PrefferedType = "Loose";
                break;

            case R.id.nextBtn:

                userDetailsValidation();
                break;
        }

    }

    private void userDetailsValidation() {
        HeightData = HeightValidation();
        WeightData = weightValidation();

        if (detailsBinding.ageEdittext.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(EnterDetails.this, "Please enter your age.", false);
        }
        else if (Integer.parseInt(detailsBinding.ageEdittext.getText().toString()) < 13 || Integer.parseInt(detailsBinding.ageEdittext.getText().toString()) > 90) {
            CommFunc.ShowStatusPop(EnterDetails.this, "Age should be between the range of 13 to 90.", false);
        }
        else if (HeightData < 1) {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.height_wrong), false);
        }
        else if (HeightData < 1400 || HeightData > 2200) {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.height_range_wrong), false);
        }
        else if (WeightData == 0) {
            CommFunc.ShowStatusPop(EnterDetails.this, getResources().getString(R.string.weight_wrong), false);
        }
        else if (WeightData < 30 || WeightData > 150) {
            CommFunc.ShowStatusPop(EnterDetails.this, getResources().getString(R.string.weight_range_wrong), false);
        }
        else if (detailsBinding.shoeSizeEditText.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(this, "Please enter your shoe size.", false);
        }
        else if (PrefferedType.isEmpty()) {
            CommFunc.ShowStatusPop(this, "Please select your preferred fit type.", false);
        } else {
            setUserValue();
        }
    }

    public void setUserValue() {
        ComUserProfileData.setAge(Integer.parseInt(detailsBinding.ageEdittext.getText().toString()));
        ComUserProfileData.setHeight(HeightData);
        ComUserProfileData.setWeight(WeightData);
        ComUserProfileData.setHeightWithUnit(HeightData / 10 + " cm");
        ComUserProfileData.setWeightWithUnit(WeightData + " kg");
        ComUserProfileData.setShoeSize(detailsBinding.shoeSizeEditText.getText() + " "+ ShoeSizeUnit);
        ComUserProfileData.setPreferredFit(PrefferedType);
        ComUserProfileData.setmeasurementGender(genderForMeas);
        getProcessID();
    }

    private void getProcessID() {
        Intent in = new Intent(EnterDetails.this, DemoVideo.class);
        startActivity(in);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    private void userLoginByToken(String token) {
        if (token != null && !TextUtils.isEmpty(token))
            if (InternetConnection.isConnected(EnterDetails.this)) {
                CommFunc.ShowProgressbar(this);
                Log.d("keyss...","Trying to login==>"+token);
                this.userToken =  token;
                APICallList.userLoginByToken(token, "user login", response, EnterDetails.this);
            } else {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
            }
    }
    private void userLogin(String email, String password, String token) {
        if (password != null && !TextUtils.isEmpty(password) && token != null && !TextUtils.isEmpty(token)) {
            if (InternetConnection.isConnected(EnterDetails.this)) {
                CommFunc.ShowProgressbar(this);
                Log.d("keyss...", "Trying to login==>" + token);
                SignupRequest signupRequest = new SignupRequest(email, password);
                signupRequest.setToken(token);
                APICallList.userLogin(signupRequest, "user login via passcode", response, EnterDetails.this);
            } else {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
            }
        }else {
            CommFunc.ShowStatusPop(this, "Request could not be processed, require more information ", false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if(url_type.equalsIgnoreCase("user login")){
                CommFunc.DismissDialog();
                signUpResponse = (SignUpResponse) o;
                if (signUpResponse != null) {
                    if(signUpResponse.getCode()==1) {
//                        sessionManager = new SessionManager(EnterDetails.this);
                        if(signUpResponse.getData().getEmail()!=null && signUpResponse.getData().getPassword()!=null) {
                            sessionManager.logoutUser();
                            sessionManager.setUserEmail(signUpResponse.getData().getEmail());
                            sessionManager.setUserPassword(signUpResponse.getData().getPassword());
                            sessionManager.setUserToken(userToken);
                            userLogin(signUpResponse.getData().getEmail(), signUpResponse.getData().getPassword(), EnterDetails.this.userToken);
                        }else{
                            CommFunc.ShowStatusPop(EnterDetails.this, "Request could not be processed, requested information not found", false);
                        }
//                        sessionManager.setUserId("349");//For Testing need to be changed to signUpResponse.getData().getId());
//                        sessionManager.setLoginSession("Logged in");
//                        ShowPopUP("Logged in as "+signUpResponse.getData().getEmail(),true,signUpResponse);
                    }
                    else if (signUpResponse.getCode() == 2){
                        ShowPopUP(signUpResponse.getMessage(),false,signUpResponse);
                    }
                    else {
                        CommFunc.ShowStatusPop(EnterDetails.this,signUpResponse.getMessage(),false);
                    }
                }
            }else if(url_type.equalsIgnoreCase("user login via passcode")){
                CommFunc.DismissDialog();
                signUpResponse = (SignUpResponse) o;
                if (signUpResponse != null) {
                    if(signUpResponse.getCode()==1) {
                        sessionManager = new SessionManager(EnterDetails.this);
                        Log.d("keyss....=>","id==>"+String.valueOf(signUpResponse.getData().getId()));
                        sessionManager.setUserId(String.valueOf(signUpResponse.getData().getId()));
                        sessionManager.setLoginSession("Logged in");
                        ShowPopUP("Logged in as "+sessionManager.getUserEmail(),true,signUpResponse);
                    }
                    else if (signUpResponse.getCode() == 2){
                        ShowPopUP(signUpResponse.getMessage(),false,signUpResponse);
                    }
                    else {
                        CommFunc.ShowStatusPop(EnterDetails.this,signUpResponse.getMessage(),false);
                    }
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(EnterDetails.this, message, false);
        }
    };
    private void ShowPopUP(String message, boolean b, SignUpResponse signUpResponse) {
        final Dialog dialog = new Dialog(EnterDetails.this);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.pop_up_status);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView popheaderImage = dialog.findViewById(R.id.successPop_Header_image);
        TextView poperrorDescription = dialog.findViewById(R.id.successPop_error_description);
        Button submit = dialog.findViewById(R.id.successPop_done_button);

        poperrorDescription.setText(message);

        if (!b) {
            popheaderImage.setImageResource(R.drawable.oopsimage);
        } else {
            popheaderImage.setImageResource(R.drawable.ok);
        }
        submit.setOnClickListener(v -> {
            dialog.dismiss();

        });
    }

}