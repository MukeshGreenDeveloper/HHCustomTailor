package com.ms.hht.ui.measure;

import static com.ms.hht.utils.Constants.IS_FROM_MENU_MEASUREMENT_HISTORY;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ms.hht.R;
import com.ms.hht.data.request.VolleyMultipartRequest;
import com.ms.hht.data.response.ResponseTypeValues;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.SessionManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.error.CameraErrorListener;
import io.fotoapparat.exception.camera.CameraException;
import io.fotoapparat.log.LoggersKt;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.result.transformer.ResolutionTransformersKt;
import io.fotoapparat.selector.JpegQualitySelectorsKt;
import io.fotoapparat.selector.LensPositionSelectorsKt;
import io.fotoapparat.selector.ResolutionSelectorsKt;
import io.fotoapparat.view.CameraView;

public class CameraAct extends AppCompatActivity implements SensorEventListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    boolean Correct_angle_status = true;
    String FrontImageFilePath;
    CountDownTimer ImageStatustimer;
    Boolean ImageStatustimerstatus = false;
    String SideImageFilePath;
    String SideImageS3Path = "Value";
    float angle = 0.0f;
    ImageView back;
    MediaPlayer beepplayer;
    CountDownTimer cameraCount;
    CameraView cameraView;
    String correctemail;
    MediaPlayer count1;
    Boolean count1isrunning = false;
    MediaPlayer count2;
    MediaPlayer count3;
    MediaPlayer count4;
    MediaPlayer count5;
    Boolean counter2isrunning = false;
    int d = 0;
    CountDownTimer delayCounter;
    Dialog dialog;
    Fotoapparat fotoapparat;
    TextView front;
    String frontImageS3Path = "value";
    boolean frontPoseStatus = false;
    String gender = "male";
    String imageName;
    int interval = 6;
    MediaPlayer m1;
    MediaPlayer m2;
    MediaPlayer m3;
    SensorManager mSensorManager;
    TextView number;
    ImageView poseimage;
    private RequestQueue rQueue;
    int s1 = 1;
    SessionManager session;
    boolean sidePoseStatus = false;
    MediaActionSound sound;
    View view1;
    View view2;
    View view3;
    View view4;

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.act_camera);
        Dialog dialog2 = new Dialog(this);
        this.dialog = dialog2;
        dialog2.setCancelable(true);
        this.cameraView = (CameraView) findViewById(R.id.camera_view);
        this.fotoapparat = createFotoapparat();
        this.session = new SessionManager(this);
        this.back = (ImageView) findViewById(R.id.camera_back);
        this.poseimage = (ImageView) findViewById(R.id.pose_image);
        this.front = (TextView) findViewById(R.id.top_text);
        this.number = (TextView) findViewById(R.id.number);
        this.view1 = findViewById(R.id.view1);
        this.view2 = findViewById(R.id.view2);
        this.view3 = findViewById(R.id.view3);
        this.view4 = findViewById(R.id.view4);
        this.gender = ComUserProfileData.getmeasurementGender();
        this.mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.sound = new MediaActionSound();
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreate(view);
            }
        });
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$0$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void onCreate(View view) {
        Intent intent = new Intent(this, PoseGuidelines.class);
        if(getIntent()!=null && getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false))
            intent.putExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false));
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private Fotoapparat createFotoapparat() {
        return Fotoapparat.with(this).into(this.cameraView).previewScaleType(ScaleType.CenterCrop)
                .photoResolution(ResolutionSelectorsKt.highestResolution())
                .lensPosition(LensPositionSelectorsKt.front())
                .jpegQuality(JpegQualitySelectorsKt.manualJpegQuality(90))
                .frameProcessor((FrameProcessor) new SampleFrameProcessor())
                .logger(LoggersKt.loggers(LoggersKt.logcat(), LoggersKt.fileLogger((Context) this)))
                .cameraErrorCallback(new CameraErrorListener() {
                    @Override
                    public void onError(@NonNull CameraException cameraException) {
                        createFotoapparat(cameraException);
                    }
                }).build();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createFotoapparat$1$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void createFotoapparat(CameraException cameraException) {
        Toast.makeText(this, cameraException.toString(), Toast.LENGTH_SHORT).show();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.fotoapparat.start();
        this.m1 = MediaPlayer.create(this, R.raw.audio1);
        this.m2 = MediaPlayer.create(this, R.raw.audio2);
        this.m3 = MediaPlayer.create(this, R.raw.audio3);
        this.beepplayer = MediaPlayer.create(this, R.raw.beep);
        this.count1 = MediaPlayer.create(this, R.raw.count1);
        this.count2 = MediaPlayer.create(this, R.raw.count2);
        this.count3 = MediaPlayer.create(this, R.raw.count3);
        this.count4 = MediaPlayer.create(this, R.raw.count4);
        this.count5 = MediaPlayer.create(this, R.raw.count5);
        SensorManager sensorManager = this.mSensorManager;
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(1), 2000000);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        this.fotoapparat.stop();
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.fotoapparat.start();
        this.m1 = MediaPlayer.create(this, R.raw.audio1);
        this.m2 = MediaPlayer.create(this, R.raw.audio2);
        this.m3 = MediaPlayer.create(this, R.raw.audio3);
        this.count1 = MediaPlayer.create(this, R.raw.count1);
        this.count2 = MediaPlayer.create(this, R.raw.count2);
        this.count3 = MediaPlayer.create(this, R.raw.count3);
        this.count4 = MediaPlayer.create(this, R.raw.count4);
        this.count5 = MediaPlayer.create(this, R.raw.count5);
        SensorManager sensorManager = this.mSensorManager;
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(1), 2000000);
        this.m1.start();
        delayOperation1();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.fotoapparat.stop();
        if (this.ImageStatustimerstatus.booleanValue()) {
            if(this.ImageStatustimer!=null)
                this.ImageStatustimer.cancel();
        }
        super.onPause();
        CommFunc.DismissDialog();
        SensorManager sensorManager = this.mSensorManager;
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(1));
        this.dialog.dismiss();
        this.m1.release();
        if (this.count1isrunning.booleanValue()) {
            if(this.cameraCount!=null)
                this.cameraCount.cancel();
        }
        if (this.counter2isrunning.booleanValue()) {
            this.delayCounter.cancel();
        }
        if (this.m2.isPlaying()) {
            this.m2.release();
        }
        if (this.m3.isPlaying()) {
            this.m3.release();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, PoseGuidelines.class);
        if(getIntent()!=null && getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false))
            intent.putExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false));
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] fArr = (float[]) sensorEvent.values.clone();
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[2];
        double sqrt = Math.sqrt((double) ((f * f) + (f2 * f2) + (f3 * f3)));
        fArr[0] = (float) (((double) fArr[0]) / sqrt);
        fArr[1] = (float) (((double) fArr[1]) / sqrt);
        float f4 = (float) (((double) fArr[2]) / sqrt);
        fArr[2] = f4;
        float round = (float) ((int) Math.round(Math.toDegrees(Math.acos((double) f4))));
        this.angle = round;
        if (round <= 86.0f || round >= 93.0f) {
            this.Correct_angle_status = false;
            this.view1.setBackgroundColor(getResources().getColor(R.color.CameraRed));
            this.view2.setBackgroundColor(getResources().getColor(R.color.CameraRed));
            this.view3.setBackgroundColor(getResources().getColor(R.color.CameraRed));
            this.view4.setBackgroundColor(getResources().getColor(R.color.CameraRed));
            return;
        }
        this.Correct_angle_status = true;
        this.view1.setBackgroundColor(getResources().getColor(R.color.CameraGreen));
        this.view2.setBackgroundColor(getResources().getColor(R.color.CameraGreen));
        this.view3.setBackgroundColor(getResources().getColor(R.color.CameraGreen));
        this.view4.setBackgroundColor(getResources().getColor(R.color.CameraGreen));
    }

    /* access modifiers changed from: private */
    public void mainfunc() {
        this.cameraCount = new CountDownTimer(6000, 1000) {
            public void onTick(long j) {
                CameraAct.this.interval--;
                CameraAct.this.count1isrunning = true;
                if (CameraAct.this.interval == 5) {
                    CameraAct.this.count5.start();
                    CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                } else if (CameraAct.this.interval == 4) {
                    CameraAct.this.count4.start();
                    CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                } else if (CameraAct.this.interval == 3) {
                    CameraAct.this.count3.start();
                    CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                } else if (CameraAct.this.interval == 2) {
                    CameraAct.this.count2.start();
                    CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                } else if (CameraAct.this.interval == 1) {
                    CameraAct.this.count1.start();
                    CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                } else if (CameraAct.this.interval == 0) {
                    CameraAct.this.imageName = "front";
                    CameraAct.this.captureImage();
                    if (CameraAct.this.gender.equalsIgnoreCase("male")) {
                        CameraAct.this.poseimage.setImageResource(R.drawable.maletightfitside);
                    } else {
                        CameraAct.this.poseimage.setImageResource(R.drawable.femalesidetightfit);
                    }
                    CameraAct.this.number.setText("");
                    CameraAct.this.front.setText(R.string.side);
                }
            }

            public void onFinish() {
                CameraAct.this.count1isrunning = false;
                CameraAct.this.interval = 6;
                CameraAct.this.m1.reset();
                CameraAct cameraAct = CameraAct.this;
                cameraAct.m1 = MediaPlayer.create(cameraAct, R.raw.audio5);
                CameraAct.this.m1.start();
                CameraAct.this.m1.setVolume(100.0f, 100.0f);
                CameraAct.this.m1.setOnCompletionListener(mOnCompleteListener);
            }

            MediaPlayer.OnCompletionListener mOnCompleteListener = new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    onFinish(mediaPlayer);
                }
            };

            /* access modifiers changed from: package-private */
            /* renamed from: lambda$onFinish$0$com-ms-hht-ui-measure-CameraAct$1  reason: not valid java name */
            public void onFinish(MediaPlayer mediaPlayer) {
                CameraAct.this.cameraCount = new CountDownTimer(5100, 1000) {
                    public void onTick(long j) {
                        CameraAct.this.count1isrunning = true;
                        CameraAct.this.interval--;
                        if (CameraAct.this.interval == 5) {
                            CameraAct.this.count5.start();
                            CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                        } else if (CameraAct.this.interval == 4) {
                            CameraAct.this.count4.start();
                            CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                        } else if (CameraAct.this.interval == 3) {
                            CameraAct.this.count3.start();
                            CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                        } else if (CameraAct.this.interval == 2) {
                            CameraAct.this.count2.start();
                            CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                        } else if (CameraAct.this.interval == 1) {
                            CameraAct.this.count1.start();
                            CameraAct.this.number.setText(String.valueOf(CameraAct.this.interval));
                        } else {
                            CameraAct.this.imageName = "side";
                            CameraAct.this.captureImage();
                            CameraAct.this.delayCounter.cancel();
                        }
                    }

                    public void onFinish() {
                        CommFunc.ShowProgressbar(CameraAct.this);
                        CameraAct.this.sound.release();
                        CameraAct.this.count1.release();
                        CameraAct.this.count2.release();
                        CameraAct.this.count3.release();
                        CameraAct.this.count4.release();
                        CameraAct.this.count5.release();
                        CameraAct.this.cameraCount.cancel();
                        CameraAct.this.mSensorManager.unregisterListener(CameraAct.this, CameraAct.this.mSensorManager.getDefaultSensor(1));
                        CameraAct.this.interval = 6;
                    }
                }.start();
            }
        }.start();
    }

    public void captureImage() {
        this.fotoapparat.takePicture().toBitmap(ResolutionTransformersKt.scaled(0.25f))
                .whenDone(new WhenDoneListener<BitmapPhoto>() {
                    @Override
                    public void whenDone(@Nullable BitmapPhoto bitmapPhoto) {
                        captureImage((BitmapPhoto) bitmapPhoto);
                    }
                });
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$captureImage$2$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void captureImage(BitmapPhoto bitmapPhoto) {
        if (bitmapPhoto == null) {
            Log.d("LOGnew", "Couldn't capture photo.");
            return;
        }
        Bitmap bitmap = bitmapPhoto.bitmap;

        //@TODO For Developer Debug purpose added this below line @Developertest
//        bitmap = BitmapFactory.decodeResource(getResources(),
//                (CameraAct.this.imageName == "side" ? R.drawable.img_side : R.drawable.img_front));
        Matrix matrix = new Matrix();
//        matrix.postRotate((float) (-bitmapPhoto.rotationDegrees));
        matrix.postRotate(-90);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        File outputMediaFile = getOutputMediaFile();
        if (outputMediaFile == null) {
            Log.d("LOGnew", "Error creating media file, check storage permissions: ");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputMediaFile);
            createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = outputMediaFile.getPath();
        if (path.contains("side")) {
            this.SideImageFilePath = path;
            Log.d("LOGnew", "SideImagePAth >>>>>     " + this.SideImageFilePath);
            uploadImageSide(createBitmap, path);
            return;
        }
        this.FrontImageFilePath = path;
        Log.d("LOGnew", "FrontImagePAth >>>>>     " + this.FrontImageFilePath);
        uploadImageFront(createBitmap, path);
    }

    private File getOutputMediaFile() {
        File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files");
        if (!file.exists() && !file.mkdirs()) {
            return null;
        }
        return new File(file.getPath() + File.separator + ("MI_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()) + this.imageName + ".jpg"));
    }

    /* access modifiers changed from: private */
    public void checkImageStatus() {
        Log.d("LOGnew", "frontpose  " + this.frontPoseStatus);
        Log.d("LOGnew", "sidepose   " + this.sidePoseStatus);
        if (this.frontPoseStatus && this.sidePoseStatus) {
            CommFunc.DismissDialog();
            finish();
            Intent intent = new Intent(getApplicationContext(), PosePreviewAct.class);
//            intent.putExtra("frontimagepath", this.frontImageS3Path);
//            intent.putExtra("sideimagepath", this.SideImageS3Path);
            if(getIntent()!=null && getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false))
                intent.putExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false));
            intent.putExtra("frontFilePath", this.FrontImageFilePath);
            intent.putExtra("sideFilePath", this.SideImageFilePath);
            ComUserProfileData.setAngle(this.angle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /* access modifiers changed from: package-private */
    public void delayOperation1() {
        this.m1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                delayOperation1(mediaPlayer);
            }
        });
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$delayOperation1$3$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void delayOperation1(MediaPlayer mediaPlayer) {
        this.delayCounter = new CountDownTimer(800000, 1000) {
            public void onTick(long j) {
                CameraAct.this.counter2isrunning = true;
                if (!CameraAct.this.Correct_angle_status) {
                    CameraAct.this.s1 = 1;
                    CameraAct.this.imageName = "front";
                    CameraAct.this.d = 0;
                    CameraAct.this.frontPoseStatus = false;
                    CameraAct.this.sidePoseStatus = false;
                    CameraAct.this.front.setText(R.string.front);
                    if (CameraAct.this.gender.equalsIgnoreCase("female")) {
                        CameraAct.this.poseimage.setImageResource(R.drawable.femalefronttightfit);
                    } else {
                        CameraAct.this.poseimage.setImageResource(R.drawable.maletightfitfront);
                    }
                    if (CameraAct.this.count1isrunning.booleanValue()) {
                        CameraAct.this.cameraCount.cancel();
                        CameraAct.this.interval = 6;
                        CameraAct.this.number.setText(" ");
                    }
                    if (CameraAct.this.m1.isPlaying()) {
                        CameraAct.this.m1.stop();
                    }
                    if (CameraAct.this.m2.isPlaying()) {
                        Log.d("player2", "playing");
                    } else if (CameraAct.this.m3.isPlaying()) {
                        CameraAct.this.m3.stop();
                    } else {
                        CameraAct cameraAct = CameraAct.this;
                        cameraAct.m2 = MediaPlayer.create(cameraAct, R.raw.audio2);
                        CameraAct.this.m2.start();
                    }
                } else if (CameraAct.this.s1 != 1) {
                } else {
                    if (CameraAct.this.m3.isPlaying()) {
                        CameraAct.this.m3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                onTick(mp);
                            }
                        });
                    } else if (CameraAct.this.m2.isPlaying()) {
                        CameraAct.this.m2.stop();
                    } else {
                        CameraAct cameraAct2 = CameraAct.this;
                        cameraAct2.m3 = MediaPlayer.create(cameraAct2, R.raw.audio3);
                        CameraAct.this.m3.start();
//                        CameraAct.this.m3.setOnCompletionListener(new CameraAct$2$$ExternalSyntheticLambda1(this));
                        CameraAct.this.m3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                onTick(mp);
                            }
                        });
                    }
                }
            }

            /* access modifiers changed from: package-private */
            /* renamed from: lambda$onTick$0$com-ms-hht-ui-measure-CameraAct$2  reason: not valid java name */
            public /* synthetic */ void onTick(MediaPlayer mediaPlayer) {
                CameraAct.this.s1 = 2;
                CameraAct.this.mainfunc();
            }

            public void onFinish() {
                CameraAct.this.counter2isrunning = false;
            }
        }.start();
    }

    private void ShowPopup() {
        this.dialog.setContentView(R.layout.status_popup);
        this.dialog.show();
        this.dialog.setCancelable(false);
        ((ImageView) this.dialog.findViewById(R.id.successPop_Header_image)).setImageResource(R.drawable.oopsimage);
        ((TextView) this.dialog.findViewById(R.id.successPop_error_description)).setText("Couldn't upload image. Check your internet connection and try again.");
        ((Button) this.dialog.findViewById(R.id.successPop_done_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$ShowPopup$4$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void ShowPopup(View view) {
        finishAffinity();
        this.dialog.dismiss();
        startActivity(new Intent(getApplicationContext(), DemoVideo.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void uploadImageFront(Bitmap bitmap, String str) {
        this.frontImageS3Path = getBase64Encode_Bitmap(bitmap);
        PosePreviewAct.imageUrl1 =getBase64Encode_Bitmap(bitmap);
        this.frontPoseStatus = true;
//        String trim = ("https://api.mysize.mirrorsize.com/api/utils/s3ImageUploadMultipartFront/M25uwr95/" + this.session.getUserDetails().get(SessionManager.PROCESS_ID) + "/").trim();
//        Log.d("LOGnew", "Image_uploadapi>>> " + trim);
//        final String str2 = str;
//        final Bitmap bitmap2 = bitmap;
//        VolleyMultipartRequest r1 = new VolleyMultipartRequest(1, trim, new Response.Listener<NetworkResponse>() {
//            @Override
//            public void onResponse(NetworkResponse response) {
//                uploadImageFront((NetworkResponse) response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                uploadImageFront(error);
//            }
//        }) {
//            /* access modifiers changed from: protected */
//            public Map<String, String> getParams() {
//                return new HashMap();
//            }
//
//            /* access modifiers changed from: protected */
//            public Map<String, DataPart> getByteData() {
//                HashMap hashMap = new HashMap();
//                hashMap.put("uploadfile", new DataPart(str2 + ".jpg", CameraAct.this.getFileDataFromDrawable(bitmap2)));
//                return hashMap;
//            }
//        };
//        r1.setRetryPolicy(new DefaultRetryPolicy(180000, 0, 1.0f));
//        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
//        this.rQueue = newRequestQueue;
//        newRequestQueue.add(r1);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$uploadImageFront$5$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public /* synthetic */ void uploadImageFront(NetworkResponse networkResponse) {
        Log.d("LOGNew", "Front Image Uploading Completed");
        this.rQueue.getCache().clear();
        try {
            JSONObject jSONObject = new JSONObject(new String(networkResponse.data));
            Log.d("LogNew", "::::::::::::::::::::: Api ChangePasswordResponse" + jSONObject);
            if (jSONObject.getInt(ResponseTypeValues.CODE) == 1) {
                String string = jSONObject.getString("message");
                this.frontImageS3Path = string;
                PosePreviewAct.imageUrl1 = string;
                this.frontPoseStatus = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$uploadImageFront$6$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void uploadImageFront(VolleyError volleyError) {
        Log.d("LogNew", volleyError.toString());
        this.cameraCount.cancel();
        if (this.m1.isPlaying()) {
            this.m1.stop();
        }
        if (this.counter2isrunning.booleanValue()) {
            this.delayCounter.cancel();
        }
        ShowPopup();
    }

    private void uploadImageSide(Bitmap bitmap, String str) {

        this.SideImageS3Path = getBase64Encode_Bitmap(bitmap);
        PosePreviewAct.imageUrl2 = this.SideImageS3Path;
        this.sidePoseStatus = true;
        this.ImageStatustimerstatus = true;
        CameraAct.this.checkImageStatus();
//        this.ImageStatustimer = new CountDownTimer(120000, 1000) {
//            public void onFinish() {
//            }
//
//            public void onTick(long j) {
//                CameraAct.this.checkImageStatus();
//            }
//        };
//        this.SideImageS3Path = string;
//        PosePreviewAct.imageUrl2 = string;
//        this.sidePoseStatus = true;
//        this.ImageStatustimerstatus = true;

//        String trim = ("https://api.mysize.mirrorsize.com/api/utils/s3ImageUploadMultipartFront/M25uwr95/" + this.session.getUserDetails().get(SessionManager.PROCESS_ID) + "/").trim();
//        Log.d("LOGnew", "Image_uploadapi>>> " + trim);
//        final String str2 = str;
//        final Bitmap bitmap2 = bitmap;
//        VolleyMultipartRequest r1 = new VolleyMultipartRequest(1, trim, new Response.Listener<NetworkResponse>() {
//            @Override
//            public void onResponse(NetworkResponse response) {
//                uploadImageSide((NetworkResponse) response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                uploadImageSide(error);
//            }
//        }) {
//            /* access modifiers changed from: protected */
//            public Map<String, String> getParams() {
//                return new HashMap();
//            }
//
//            /* access modifiers changed from: protected */
//            public Map<String, DataPart> getByteData() {
//                HashMap hashMap = new HashMap();
//                hashMap.put("uploadfile", new DataPart(str2 + ".jpg", CameraAct.this.getFileDataFromDrawable(bitmap2)));
//                return hashMap;
//            }
//        };
//        r1.setRetryPolicy(new DefaultRetryPolicy(180000, 0, 1.0f));
//        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
//        this.rQueue = newRequestQueue;
//        newRequestQueue.add(r1);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$uploadImageSide$7$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void uploadImageSide(NetworkResponse networkResponse) {
        Log.d("LOGNew", "Side Image Uploading Completed");
        this.rQueue.getCache().clear();
        try {
            JSONObject jSONObject = new JSONObject(new String(networkResponse.data));
            Log.d("LogNew", "::::::::::::::::::::: Api ChangePasswordResponse" + jSONObject);
            if (jSONObject.getInt(ResponseTypeValues.CODE) == 1) {
                String string = jSONObject.getString("message");
                this.SideImageS3Path = string;
                PosePreviewAct.imageUrl2 = string;
                this.sidePoseStatus = true;
                this.ImageStatustimerstatus = true;
                this.ImageStatustimer = new CountDownTimer(120000, 1000) {
                    public void onFinish() {
                    }

                    public void onTick(long j) {
                        CameraAct.this.checkImageStatus();
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$uploadImageSide$8$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void uploadImageSide(VolleyError volleyError) {
        Log.d("LogNew", volleyError.toString());
        this.cameraCount.cancel();
        if (this.m1.isPlaying()) {
            this.m1.stop();
        }
        if (this.counter2isrunning.booleanValue()) {
            this.delayCounter.cancel();
        }
        ShowPopup();
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private String getBase64Encode_Bitmap(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public static Bitmap getBitmapDecode_Base64(String base64){
        byte[] imageAsBytes = Base64.decode(base64.getBytes(), Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    private class SampleFrameProcessor implements FrameProcessor {
        public void process(Frame frame) {
        }

        private SampleFrameProcessor() {
        }
    }
}
