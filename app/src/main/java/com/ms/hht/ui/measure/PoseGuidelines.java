package com.ms.hht.ui.measure;

import static com.ms.hht.utils.CommonFunc.hasPermissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ms.hht.R;
import com.ms.hht.databinding.ActPoseGuidelinesBinding;
import com.ms.hht.ui.SplashAct;
import com.ms.hht.ui.login.LoginActivity;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.CommonFunc;
import com.ms.hht.utils.SessionManager;

public class PoseGuidelines extends AppCompatActivity {

    ActPoseGuidelinesBinding guidelinesBinding;
    SessionManager sessionManager;
    String[] PERMISSION = {Manifest.permission.CAMERA};
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        guidelinesBinding = ActPoseGuidelinesBinding.inflate(getLayoutInflater());
        setContentView(guidelinesBinding.getRoot());

        sessionManager = new SessionManager(this);

//        System.out.println("GENDER+++++++++++++"+sessionManager.getUserGender());

        if (ComUserProfileData.getmeasurementGender().equalsIgnoreCase("female")){

            guidelinesBinding.poseOne.setImageResource(R.drawable.pose_img_1);
            guidelinesBinding.pose2.setImageResource(R.drawable.pose_img_2);
            guidelinesBinding.pose3.setImageResource(R.drawable.pose_img_3);

        }else {

            guidelinesBinding.poseOne.setImageResource(R.drawable.male_intro1);
            guidelinesBinding.pose2.setImageResource(R.drawable.male_intro2);
            guidelinesBinding.pose3.setImageResource(R.drawable.male_intro3);

        }

        guidelinesBinding.backAct.setOnClickListener(view -> {
                    Intent i1 = new Intent(PoseGuidelines.this, DemoVideo.class);
                    finish();
                    startActivity(i1);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        );

        guidelinesBinding.proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!hasPermissions(PoseGuidelines.this, PERMISSION)) {
                    ActivityCompat.requestPermissions(PoseGuidelines.this, PERMISSION, REQUEST_CODE);
                }
                else {
                }

            }
        });
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                } else {
                    CommonFunc.ShowStatusPop(PoseGuidelines.this, "this app requires camera permission to measure your health", false);
                }
            }
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i1 = new Intent(PoseGuidelines.this, DemoVideo.class);
        finish();
        startActivity(i1);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}