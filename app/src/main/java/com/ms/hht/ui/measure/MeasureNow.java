package com.ms.hht.ui.measure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ms.hht.R;
import com.ms.hht.databinding.ActMeasureNowBinding;
import com.ms.hht.utils.SessionManager;

public class MeasureNow extends AppCompatActivity implements View.OnClickListener{

    public static String activityComingFrom ="";
    SessionManager sessionManager;
    ActMeasureNowBinding nowBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nowBinding = ActMeasureNowBinding.inflate(getLayoutInflater());
        setContentView(nowBinding.getRoot());
        sessionManager = new SessionManager(MeasureNow.this);
        nowBinding.getStartBtn.setOnClickListener(this);
        nowBinding.proceedBtn2.setOnClickListener(this);
        nowBinding.backImage1.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.getStartBtn:

                Intent intent5 = new Intent(MeasureNow.this,EnterDetails.class);
                startActivity(intent5);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.proceedBtn2:

                Intent next = new Intent(MeasureNow.this,MeasurementHistoryAct.class);
                MeasurementHistoryAct.MeasurementHistoryActivityComingFrom = activityComingFrom;
                startActivity(next);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }
}