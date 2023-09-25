package com.ms.hht.ui.measure;

import static com.ms.hht.utils.Constants.IS_FROM_MENU_MEASUREMENT_HISTORY;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Base64;
import android.util.Log;
import android.util.SizeF;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.ms.hht.R;
import com.ms.hht.data.BodyValidationFields;
import com.ms.hht.data.response.ResponseTypeValues;
import com.ms.hht.lib.fotoapparat.exception.camera.CameraException;
import com.ms.hht.lib.fotoapparat.preview.Frame;
import com.ms.hht.lib.fotoapparat.preview.FrameProcessor;
import com.ms.hht.lib.fotoapparat.result.BitmapPhoto;
import com.ms.hht.mlkit.CameraSource;
import com.ms.hht.mlkit.CameraSourcePreview;
import com.ms.hht.mlkit.GraphicOverlay;
import com.ms.hht.mlkit.PoseDetectionNotifier;
import com.ms.hht.mlkit.java.posedetector.PoseDetectorProcessor;
import com.ms.hht.mlkit.preference.PreferenceUtils;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.HHLogger;
import com.ms.hht.utils.ImageUtils;
import com.ms.hht.utils.SessionManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CameraAct extends AppCompatActivity implements SensorEventListener, PoseDetectionNotifier {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    boolean Correct_angle_status = true;
    String FrontImageFilePath;
    CountDownTimer ImageStatustimer;
    Boolean ImageStatustimerstatus = false;
    String SideImageFilePath;
    String SideImageS3Path = "Value";
    float angle = 0.0f;
    ImageView back;
    //    MediaPlayer beepplayer;
    CountDownTimer cameraCount;
    //    CameraView cameraView;
    String correctemail;
    //    MediaPlayer count1;
    Boolean count1isrunning = false;
    //    MediaPlayer count2;
//    MediaPlayer count3;
//    MediaPlayer count4;
//    MediaPlayer count5;
    Boolean counter2isrunning = false;
    int d = 0;
    CountDownTimer delayCounter;
    Dialog dialog;
    //    Fotoapparat fotoapparat;
    TextView front;
    String frontImageS3Path = "value";
    boolean frontPoseStatus = false;
    String gender = "male";
    String imageName;
    int interval = 6;
    //    MediaPlayer m1;
//    MediaPlayer m2;
//    MediaPlayer m3;
    SensorManager mSensorManager;
    TextView number;
    ImageView poseimage;
    private RequestQueue rQueue;
    int s1 = 1;
    SessionManager session;
    boolean sidePoseStatus = false;
    //    MediaActionSound sound;
    View view1;
    View view2;
    View view3;
    View view4;
    private static final String POSE_DETECTION = "Pose Detection";
    private static final String TAG = "LivePreviewActivity";

    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;
    private PreviewView previewView;
    private String selectedModel = POSE_DETECTION;
    public static float FLX = 0;
    public static float FLY = 0;
    public static float FLPX = 0;
    public static float FLPY = 0;
    public static double horizontalAngle = 0;
    public static double verticalAngle = 0;
    public static int width = 0;
    public static int height = 0;
    private TextToSpeech textToSpeech;
    private boolean isTextToSpeechReady = false, isTextPlaying = false;
    float F = 1f;           //focal length
    float angleX, angleY;
//    boolean startCheckingMeasurement= false;

    private Camera frontCam() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            Log.v("CAMID", camIdx + "");
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e("FAIL", "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return cam;
    }


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
//        this.cameraView = (CameraView) findViewById(R.id.camera_view);
//        this.fotoapparat = createFotoapparat();
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
//        this.sound = new MediaActionSound();
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreate(view);
            }
        });

        //Pose Detection Options
        graphicOverlay = findViewById(R.id.graphic_overlay);
        preview = findViewById(R.id.preview_view);
        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay = findViewById(R.id.graphic_overlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            Toast.makeText(this, "Grant Permission and restart app", Toast.LENGTH_SHORT).show();
        } else {
            Camera camera = frontCam();
            Camera.Parameters campar = camera.getParameters();
            FLX = campar.getFocalLength();
            F = campar.getFocalLength();
            angleX = campar.getHorizontalViewAngle();
            angleY = campar.getVerticalViewAngle();
            horizontalAngle = (float) (Math.tan(Math.toRadians(angleX / 2)) * 2 * F);
            verticalAngle = (float) (Math.tan(Math.toRadians(angleY / 2)) * 2 * F);
            camera.stopPreview();
            camera.release();
        }

        CameraManager cameraManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        }
        String cameraId = "";// Specify the camera ID


        CameraCharacteristics characteristics = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            try {
                String[] cameraIds = cameraManager.getCameraIdList();
                // Now cameraIds array contains the available camera IDs
                cameraId = cameraIds[0];
                characteristics = cameraManager.getCameraCharacteristics(cameraId);

                float[] focalLengths = new float[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    focalLengths = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
                }

                if (focalLengths != null && focalLengths.length > 0) {
                    float focalLength = focalLengths[0]; // Focal length in millimeters

                    //FLX = focalLength;
                    // FLY = focalLength;
                    SizeF sensorSize = characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
                    //   horizontalAngle = (2f * atan((sensorSize.getWidth()  /  (focalLength * 2f))));// * 180.0 / Math.PI;
                    //     verticalAngle =   (2f * atan((sensorSize.getHeight() /  (focalLength * 2f)))) ;// 180.0 / Math.PI;
                    // You can convert millimeters to other units if needed
                }

                float[] lensIntrinsicCalibration = new float[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    lensIntrinsicCalibration = characteristics.get(CameraCharacteristics.LENS_INTRINSIC_CALIBRATION);
                    if (lensIntrinsicCalibration != null && lensIntrinsicCalibration.length >= 2) {
                        float principalPointX = lensIntrinsicCalibration[0];
                        FLPX = lensIntrinsicCalibration[0];
                        FLPY = lensIntrinsicCalibration[1];// The first element represents principalPointX
                    }
                }
                Log.d("tryFL", FLX + "  " + FLPY + "   " + FLPX + "  " + FLPY);

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }


        }
        createCameraSource(selectedModel);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.ERROR) {
                    isTextToSpeechReady = false;
                } else if (status == TextToSpeech.SUCCESS) {
                    isTextToSpeechReady = false;
                    textToSpeech.setLanguage(Locale.UK);
                    isTextPlaying = false;
                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String s) {
                            isTextPlaying = true;
                        }

                        @Override
                        public void onDone(String s) {
                            //On Complete
                            isTextPlaying = false;
//                            delayOperation1(null);
                        }

                        @Override
                        public void onError(String s) {
                            isTextPlaying = false;
                        }
                    });
                }
            }
        });
    }

    private void createCameraSource(String model) {

        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = new CameraSource(this, graphicOverlay);
            cameraSource.setFacing(CameraSource.CAMERA_FACING_FRONT);

        }

        try {
            switch (model) {
                case POSE_DETECTION:
                    PoseDetectorOptionsBase poseDetectorOptions =
                            PreferenceUtils.getPoseDetectorOptionsForLivePreview(this);
                    Log.i(TAG, "Using Pose Detector with options " + poseDetectorOptions);
                    boolean shouldShowInFrameLikelihood =
                            PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this);
                    boolean visualizeZ = PreferenceUtils.shouldPoseDetectionVisualizeZ(this);
                    boolean rescaleZ = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this);
                    boolean runClassification = PreferenceUtils.shouldPoseDetectionRunClassification(this);
                    cameraSource.setMachineLearningFrameProcessor(
                            new PoseDetectorProcessor(
                                    this,
                                    poseDetectorOptions,
                                    shouldShowInFrameLikelihood,
                                    visualizeZ,
                                    rescaleZ,
                                    runClassification,
                                    /* isStreamMode = */ true, this));
                    break;
                default:
                    Log.e(TAG, "Unknown model: " + model);
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Can not create image processor: " + model, e);
            Toast.makeText(
                            getApplicationContext(),
                            "Can not create image processor: " + e.getMessage(),
                            Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);

            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreate$0$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void onCreate(View view) {
        Intent intent = new Intent(this, PoseGuidelines.class);
        if (getIntent() != null && getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY, false))
            intent.putExtra(IS_FROM_MENU_MEASUREMENT_HISTORY, getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY, false));
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

//    private Fotoapparat createFotoapparat() {
//        return Fotoapparat.with(this).into(this.cameraView).previewScaleType(ScaleType.CenterCrop)
//                .photoResolution(ResolutionSelectorsKt.highestResolution())
//                .lensPosition(LensPositionSelectorsKt.front())
//                .jpegQuality(JpegQualitySelectorsKt.manualJpegQuality(90))
//                .frameProcessor((FrameProcessor) new SampleFrameProcessor())
//                .logger(LoggersKt.loggers(LoggersKt.logcat(), LoggersKt.fileLogger((Context) this)))
//                .cameraErrorCallback(new CameraErrorListener() {
//                    @Override
//                    public void onError(@NonNull CameraException cameraException) {
//                        createFotoapparat(cameraException);
//                    }
//                }).build();
//    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createFotoapparat$1$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void createFotoapparat(CameraException cameraException) {
        Toast.makeText(this, cameraException.toString(), Toast.LENGTH_SHORT).show();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
//        this.fotoapparat.start();
//        this.m1 = MediaPlayer.create(this, R.raw.audio1);
//        this.m2 = MediaPlayer.create(this, R.raw.audio2);
//        this.m3 = MediaPlayer.create(this, R.raw.audio3);
//        this.beepplayer = MediaPlayer.create(this, R.raw.beep);
//        this.count1 = MediaPlayer.create(this, R.raw.count1);
//        this.count2 = MediaPlayer.create(this, R.raw.count2);
//        this.count3 = MediaPlayer.create(this, R.raw.count3);
//        this.count4 = MediaPlayer.create(this, R.raw.count4);
//        this.count5 = MediaPlayer.create(this, R.raw.count5);
        SensorManager sensorManager = this.mSensorManager;
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(1), 2000000);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
//        this.fotoapparat.stop();
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
//        this.fotoapparat.start();
//        this.m1 = MediaPlayer.create(this, R.raw.audio1);
//        this.m2 = MediaPlayer.create(this, R.raw.audio2);
//        this.m3 = MediaPlayer.create(this, R.raw.audio3);
//        this.count1 = MediaPlayer.create(this, R.raw.count1);
//        this.count2 = MediaPlayer.create(this, R.raw.count2);
//        this.count3 = MediaPlayer.create(this, R.raw.count3);
//        this.count4 = MediaPlayer.create(this, R.raw.count4);
//        this.count5 = MediaPlayer.create(this, R.raw.count5);
        SensorManager sensorManager = this.mSensorManager;
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(1), 2000000);
//        this.m1.start();

        delayOperation1();
        Log.d(TAG, "onResume");
        createCameraSource(selectedModel);
        startCameraSource();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
//        this.fotoapparat.stop();
        if (this.ImageStatustimerstatus.booleanValue()) {
            if (this.ImageStatustimer != null)
                this.ImageStatustimer.cancel();
        }
        super.onPause();
        CommFunc.DismissDialog();
        SensorManager sensorManager = this.mSensorManager;
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(1));
        this.dialog.dismiss();
//        this.m1.release();
        if (this.count1isrunning.booleanValue()) {
            if (this.cameraCount != null)
                this.cameraCount.cancel();
        }
        if (this.counter2isrunning.booleanValue()) {
            this.delayCounter.cancel();
        }
//        if (this.m2.isPlaying()) {
//            this.m2.release();
//        }
//        if (this.m3.isPlaying()) {
//            this.m3.release();
//        }

        preview.stop();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, PoseGuidelines.class);
        if (getIntent() != null && getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY, false))
            intent.putExtra(IS_FROM_MENU_MEASUREMENT_HISTORY, getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY, false));
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
//                interval--;
//                count1isrunning = true;
//                if (interval == 5) {
//                    count5.start();
//                    number.setText(String.valueOf(interval));
//                } else if (interval == 4) {
//                    count4.start();
//                    number.setText(String.valueOf(interval));
//                } else if (interval == 3) {
//                    count3.start();
//                    number.setText(String.valueOf(interval));
//                } else if (interval == 2) {
//                    count2.start();
//                    number.setText(String.valueOf(interval));
//                } else if (interval == 1) {
//                    count1.start();
//                    number.setText(String.valueOf(interval));
//                } else if (interval == 0) {
//                    imageName = "front";
//                    captureImage();
//                    if (gender.equalsIgnoreCase("male")) {
//                        poseimage.setImageResource(R.drawable.maletightfitside);
//                    } else {
//                        poseimage.setImageResource(R.drawable.femalesidetightfit);
//                    }
//                    number.setText("");
//                    front.setText(R.string.side);
//                }
            }

            public void onFinish() {
                count1isrunning = false;
                interval = 6;
//                m1.reset();
//                m1 = MediaPlayer.create(cameraAct, R.raw.audio5);
//                m1.start();
                nextMesage(getString(R.string.voice_verticalystrait));
//                m1.setVolume(100.0f, 100.0f);
//                m1.setOnCompletionListener(mOnCompleteListener);
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
                cameraCount = new CountDownTimer(5100, 1000) {
                    public void onTick(long j) {
                        count1isrunning = true;
                        interval--;
                        if (interval == 5) {
                            nextMesage(getString(R.string.voice_five));
                            number.setText(String.valueOf(interval));
                        } else if (interval == 4) {
                            nextMesage(getString(R.string.voice_four));
                            number.setText(String.valueOf(interval));
                        } else if (interval == 3) {
                            nextMesage(getString(R.string.voice_three));
                            number.setText(String.valueOf(interval));
                        } else if (interval == 2) {
                            nextMesage(getString(R.string.voice_two));
                            number.setText(String.valueOf(interval));
                        } else if (interval == 1) {
                            nextMesage(getString(R.string.voice_one));
                            number.setText(String.valueOf(interval));
                        } else {
                            imageName = "side";
                            captureImage();
                            delayCounter.cancel();
                        }
                    }

                    public void onFinish() {
                        CommFunc.ShowProgressbar(CameraAct.this);
//                        sound.release();
//                        count1.release();
//                        count2.release();
//                        count3.release();
//                        count4.release();
//                        count5.release();
                        cameraCount.cancel();
                        mSensorManager.unregisterListener(CameraAct.this, mSensorManager.getDefaultSensor(1));
                        interval = 6;
                    }
                }.start();
            }
        }.start();
    }
    private void captureFrontCameraInitate(){
        cameraCount = new CountDownTimer(5100, 1000) {
            public void onTick(long j) {
                count1isrunning = true;
                interval--;
                if (interval == 5) {
                    nextMesage(getString(R.string.voice_five));
                    number.setText(String.valueOf(interval));
                } else if (interval == 4) {
                    nextMesage(getString(R.string.voice_four));
                    number.setText(String.valueOf(interval));
                } else if (interval == 3) {
                    nextMesage(getString(R.string.voice_three));
                    number.setText(String.valueOf(interval));
                } else if (interval == 2) {
                    nextMesage(getString(R.string.voice_two));
                    number.setText(String.valueOf(interval));
                } else if (interval == 1) {
                    nextMesage(getString(R.string.voice_one));
                    number.setText(String.valueOf(interval));
//                } else {
//                    imageName = "side";
                    captureImage();
                    delayCounter.cancel();
                }
            }

            public void onFinish() {
                CommFunc.ShowProgressbar(CameraAct.this);
                cameraCount.cancel();
                mSensorManager.unregisterListener(CameraAct.this, mSensorManager.getDefaultSensor(1));
                interval = 6;
                count1isrunning = false;
                imageName = "side";
                nextMesage(getString(R.string.voice_turn_right));
            }
        }.start();
    }
    private void captureSideCameraInitate(){
        cameraCount = new CountDownTimer(5100, 1000) {
            public void onTick(long j) {
                counter2isrunning = true;
                interval--;
                if (interval == 5) {
                    nextMesage(getString(R.string.voice_five));
                    number.setText(String.valueOf(interval));
                } else if (interval == 4) {
                    nextMesage(getString(R.string.voice_four));
                    number.setText(String.valueOf(interval));
                } else if (interval == 3) {
                    nextMesage(getString(R.string.voice_three));
                    number.setText(String.valueOf(interval));
                } else if (interval == 2) {
                    nextMesage(getString(R.string.voice_two));
                    number.setText(String.valueOf(interval));
                } else if (interval == 1) {
                    nextMesage(getString(R.string.voice_one));
                    number.setText(String.valueOf(interval));
//                } else {
                    imageName = "side";
                    captureImage();
                    delayCounter.cancel();
                }
            }

            public void onFinish() {
                CommFunc.ShowProgressbar(CameraAct.this);
                cameraCount.cancel();
                mSensorManager.unregisterListener(CameraAct.this, mSensorManager.getDefaultSensor(1));
                interval = 6;
                counter2isrunning = false;
                imageName = "side";
                nextMesage(getString(R.string.voice_verticalystrait));
            }
        }.start();
    }


    public void captureImage() {
        Log.d("keyss...", "Capture Image()==>");
//        cameraSource.stop();
        cameraSource.takePicture(new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                Log.d("keyss...", "Shutter Taken()==>");
            }
        }, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
//        new Photo(bytes,-90);
//                if(bytes!=null){
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                    Log.d("keyss...","Picker Taken()==>");
//                }

            }
        }, new Camera.PictureCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                Log.d("keyss...", "Shutter Taken()==>");
//        new Photo(bytes,-90);
                if (bytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    captureImage(new BitmapPhoto(bitmap, -90));
                    try {
                        cameraSource.restartCamera();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
        Bitmap bitmap = ImageUtils.compressImage(bitmapPhoto.bitmap);
        Matrix matrix = new Matrix();
        matrix.postRotate(-90);

        //@TODO For Developer Debug purpose added this below line @Developertest
//        Bitmap bitmap = ImageUtils.compressImage(BitmapFactory.decodeResource(getResources(),
//                (imageName == "side" ? R.drawable.img_side : R.drawable.img_front)));
//        Matrix matrix = new Matrix();
//        matrix.postRotate((float) (-bitmapPhoto.rotationDegrees));

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
            if (getIntent() != null && getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY, false))
                intent.putExtra(IS_FROM_MENU_MEASUREMENT_HISTORY, getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY, false));
            intent.putExtra("frontFilePath", this.FrontImageFilePath);
            intent.putExtra("sideFilePath", this.SideImageFilePath);
            ComUserProfileData.setAngle(this.angle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /* access modifiers changed from: package-private */
    public void delayOperation1() {
//        this.m1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
        delayOperation1(null);
//            }
//        });
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$delayOperation1$3$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void delayOperation1(MediaPlayer mediaPlayer) {
        this.delayCounter = new CountDownTimer(800000, 1000) {
            public void onTick(long j) {
                counter2isrunning = true;
                if(imageName==null){
                    if (!Correct_angle_status) {
                        s1 = 1;
                        imageName = "front";
                        d = 0;
                        frontPoseStatus = false;
                        sidePoseStatus = false;
                        front.setText(R.string.front);
                        if (gender.equalsIgnoreCase("female")) {
                            poseimage.setImageResource(R.drawable.femalefronttightfit);
                        } else {
                            poseimage.setImageResource(R.drawable.maletightfitfront);
                        }
                        if (count1isrunning.booleanValue()) {
                            cameraCount.cancel();
                            interval = 6;
                            number.setText(" ");
                        }
                        nextMesage(getString(R.string.voice_not_positioned_correctly),true);
                    } else if (s1 != 1) {
                    } else {
                        if(validationField!=null){
                            if(validationField.objDistanceCamera>= 4 && validationField.objDistanceCamera<=8){
                                if(validationField.objDistanceLegs<11){
                                    nextMesage(getString(R.string.voice_please_keep_leg_distance),true);
                                }else {
                                    if ((validationField.armsAngle >= 30 && validationField.armsAngle <= 50)
                                            && (validationField.rDegree >= 30 && validationField.rDegree <= 50)) {
                                        nextMesage(getString(R.string.voice_please_wait_capture),true);
                                        imageName = "front";
//                                        mainfunc();
                                        captureFrontCameraInitate();
                                    } else {
                                        nextMesage(getString(R.string.voice_please_keep_angle),true);
                                    }
                                }
                            }else{
                                nextMesage(getString(R.string.voice_please_stand_distance_camera),true);
                            }
                        }else{
                            nextMesage(getString(R.string.voice_we_are_not_able_to_identify),true);
                        }
                    }
                }
                else if(imageName!=null && imageName.equals("front")){
                    if (!Correct_angle_status) {
                        s1 = 1;
                        imageName = "front";
                        d = 0;
                        frontPoseStatus = false;
                        sidePoseStatus = false;
                        front.setText(R.string.front);
                        if (gender.equalsIgnoreCase("female")) {
                            poseimage.setImageResource(R.drawable.femalefronttightfit);
                        } else {
                            poseimage.setImageResource(R.drawable.maletightfitfront);
                        }
                        if (count1isrunning.booleanValue()) {
                            cameraCount.cancel();
                            interval = 6;
                            number.setText(" ");
                        }
                        nextMesage(getString(R.string.voice_not_positioned_correctly),true);
//                        m2.start();
//                    }
                    } else if (s1 != 1) {
                    } else {
                        if(validationField!=null){
                            if(validationField.objDistanceCamera>= 4 && validationField.objDistanceCamera<=8){
                                if(validationField.objDistanceLegs<11){
                                    nextMesage(getString(R.string.voice_please_keep_leg_distance),true);
                                }else {
                                    if ((validationField.armsAngle >= 30 && validationField.armsAngle <= 50)
                                            && (validationField.rDegree >= 30 && validationField.rDegree <= 50)) {
                                        nextMesage(getString(R.string.voice_please_wait_capture),true);
                                        imageName = "front";
                                        captureFrontCameraInitate();
                                    } else {
                                        nextMesage(getString(R.string.voice_please_keep_angle),true);
                                    }
                                }
                            }else{
                                nextMesage(getString(R.string.voice_please_stand_distance_camera),true);
                            }
                        }else{
                            nextMesage(getString(R.string.voice_we_are_not_able_to_identify),true);
                        }
                    }
                }else if(imageName.equals("side")){

                }
            }

            /* access modifiers changed from: package-private */
            /* renamed from: lambda$onTick$0$com-ms-hht-ui-measure-CameraAct$2  reason: not valid java name */
            public /* synthetic */ void onTick(MediaPlayer mediaPlayer) {
                s1 = 2;
                mainfunc();
            }

            public void onFinish() {
                counter2isrunning = false;
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
        PosePreviewAct.imageUrl1 = getBase64Encode_Bitmap(bitmap);
        this.frontPoseStatus = true;
        HHLogger.getINSTANCE(CameraAct.this).LOG("CameraAct", PosePreviewAct.imageUrl1, "FrontImage");
        captureSideCameraInitate();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$uploadImageFront$5$com-ms-hht-ui-measure-CameraAct  reason: not valid java name */
    public void uploadImageFront(NetworkResponse networkResponse) {
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
//        if (this.m1.isPlaying()) {
//            this.m1.stop();
//        }
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
        HHLogger.getINSTANCE(CameraAct.this).LOG("CameraAct", PosePreviewAct.imageUrl2, "SidImage");
        checkImageStatus();
    }

    private String getBase64Encode_Bitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void humanDetected(List<PoseLandmark> landmarks, Pose pose, Canvas canvas) {

    }


    @Override
    public void nextMesage(String mesage) {
//        if (!isTextPlaying)
//            textToSpeech.speak(mesage, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void nextMesage(String mesage, boolean forstStop) {
        /*if (forstStop) {
            textToSpeech.stop();
            textToSpeech.speak(mesage, TextToSpeech.QUEUE_FLUSH, null, null);
        } else */
        if (!isTextPlaying)
            textToSpeech.speak(mesage, forstStop?TextToSpeech.QUEUE_FLUSH:TextToSpeech.QUEUE_ADD, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
    }

    @Override
    public void readyToCapture(String pose_complete) {

    }

    @Override
    public void setBodyValidationField(BodyValidationFields validationField) {
        this.validationField = validationField;
    }

    BodyValidationFields validationField;

    @Override
    public BodyValidationFields getBodyValidationField() {
        return validationField;
    }
}
