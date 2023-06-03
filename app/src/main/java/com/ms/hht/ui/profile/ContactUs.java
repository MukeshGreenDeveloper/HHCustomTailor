package com.ms.hht.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.ms.hht.R;
import com.ms.hht.databinding.ContactusBinding;

public class ContactUs extends AppCompatActivity {

    ContactusBinding contactUsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactUsBinding = ContactusBinding.inflate(getLayoutInflater());
        setContentView(contactUsBinding.getRoot());

        contactUsBinding.contactBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
