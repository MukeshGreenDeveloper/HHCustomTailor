package com.ms.hht.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ms.hht.R;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.ui.login.LoginActivity;
import com.ms.hht.utils.CommonFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.SessionManager;

public class SplashAct extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    SessionManager sessionManager;
    String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.CAMERA};
    VideoView videoView;
    AudioManager audioManager = null;
    Button registerBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        sessionManager = new SessionManager(SplashAct.this);

        if (sessionManager.getLoginSession().equalsIgnoreCase("logged in")) {
            Intent in2 = new Intent(SplashAct.this, HomeScreen.class);
            finish();
            startActivity(in2);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        videoView = findViewById(R.id.SplashVideoView);
        registerBtn = findViewById(R.id.splash_registerbtn);
        loginBtn = findViewById(R.id.splash_loginbtn);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.landing));

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        videoView.setOnCompletionListener(mp -> videoView.start());

        registerBtn.setOnClickListener(view -> {
            Intent in2 = new Intent(SplashAct.this, LoginActivity.class);
//            LoginActivity.from = "";
            Constants.Login_From = "";
            startActivity(in2);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });

        loginBtn.setOnClickListener(view -> {
            Intent in2 = new Intent(SplashAct.this, HomeScreen.class);
            finish();
            startActivity(in2);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        int permission = ActivityCompat.checkSelfPermission(SplashAct.this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    SplashAct.this, PERMISSION, REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                CommonFunc.ShowStatusPop(SplashAct.this, "this app requires camera permission to measure your health", false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }
}