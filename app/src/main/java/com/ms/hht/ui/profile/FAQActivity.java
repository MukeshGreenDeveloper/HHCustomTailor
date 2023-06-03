package com.ms.hht.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ms.hht.R;
import com.ms.hht.databinding.ActivityFaqactivityBinding;
import com.ms.hht.databinding.ActivityPrivacyPolicyBinding;

public class FAQActivity extends AppCompatActivity {

    ActivityFaqactivityBinding faqactivityBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faqactivityBinding = ActivityFaqactivityBinding.inflate(getLayoutInflater());
        setContentView(faqactivityBinding.getRoot());

        faqactivityBinding.faqBackbtn.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
