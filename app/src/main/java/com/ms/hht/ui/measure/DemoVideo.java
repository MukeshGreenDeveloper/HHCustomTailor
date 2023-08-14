package com.ms.hht.ui.measure;

import static com.ms.hht.utils.Constants.IS_FROM_MENU_MEASUREMENT_HISTORY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ms.hht.R;
import com.ms.hht.databinding.ActDemoVideoBinding;
import com.ms.hht.utils.SessionManager;

public class DemoVideo extends AppCompatActivity {

    ActDemoVideoBinding videoBinding;
    CountDownTimer countDownTimer;
    Boolean timerStatus = false;
    AudioManager audioManager = null;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        videoBinding = ActDemoVideoBinding.inflate(getLayoutInflater());
        setContentView(videoBinding.getRoot());

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        sessionManager = new SessionManager(DemoVideo.this);
        /* actDemoVideoBinding.GotButton.setVisibility(View.GONE);*/

        String firstTime = sessionManager.getUserDetails().get(SessionManager.FIRST_PLAY);
        assert firstTime != null;
        if (firstTime.equalsIgnoreCase("true")) {
            videoBinding.skipButton.setVisibility(View.GONE);
        }

        videoBinding.skipButton.setOnClickListener(v -> nextactivity());
        /* actDemoVideoBinding.GotButton.setOnClickListener(v -> nextactivity());*/
        videoBinding.infoback.setOnClickListener(v -> goback());

        videoBinding.videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tightfit));

        videoBinding.videoView.setOnCompletionListener(mp -> {

            videoBinding.skipButton.setVisibility(View.GONE);

            sessionManager.updateVideoStatus("false");

            countDownTimer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerStatus = true;
                }

                @Override
                public void onFinish() {
                    nextactivity();
                }
            }.start();
        });

    }

    @Override
    public void onBackPressed() {
        goback();
    }

    public void nextactivity() {

        Intent i = new Intent(getApplicationContext(), PoseGuidelines.class);
        if(getIntent()!=null && getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false))
            i.putExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false));
        finish();
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    protected void onResume() {
        super.onResume();
        videoBinding.videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoBinding.videoView.pause();
        if (timerStatus) {
            countDownTimer.cancel();
        }
    }

    public void goback() {
        Intent i1 = new Intent(DemoVideo.this, EnterDetails.class);
        if(getIntent()!=null && getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false))
            i1.putExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false));
        finish();
        startActivity(i1);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }



}
