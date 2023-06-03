package com.ms.hht.ui.measure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ms.hht.R;
import com.ms.hht.databinding.ActEnterDetailsBinding;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.CommFunc;
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

}