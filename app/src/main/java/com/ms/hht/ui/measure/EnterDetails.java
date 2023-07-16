package com.ms.hht.ui.measure;

import static com.ms.hht.utils.Constants.IS_FROM_MENU_MEASUREMENT_HISTORY;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.ms.hht.R;
import com.ms.hht.data.request.ProcessIDRequest;
import com.ms.hht.data.response.ProcessResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActEnterDetailsBinding;
import com.ms.hht.noui.HHTApplication;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;
import com.paypal.pyplcheckout.userprofile.model.UserStateKt;

public class EnterDetails extends AppCompatActivity implements View.OnClickListener {
    int HeightData;
    String IsKG = "kg";
    String Isheight = "cm";
    String PrefferedType = "";
    String ShoeSizeUnit = UserStateKt.US_COUNTRY;
    int WeightData;
    ComUserProfileData commUserProfileData;
    ActEnterDetailsBinding detailsBinding;
    String genderForMeas = "Male";
    private final DisposableData res = new DisposableData() {
        public void onSuccess(String str, Object obj) throws Exception {
            if (str.equalsIgnoreCase("GET_PROCESS_ID")) {
                CommFunc.DismissDialog();
                ProcessResponse processResponse = (ProcessResponse) obj;
                if (processResponse == null) {
                    CommFunc.ShowStatusPop(EnterDetails.this, processResponse.getMessage(), false);
                } else if (processResponse.getCode().equalsIgnoreCase("1")) {
                    EnterDetails.this.sessionManager.updateProcessId(processResponse.getUserId());
                    Intent mIntent = new Intent(EnterDetails.this, DemoVideo.class);
                    if(getIntent()!=null && getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false))
                        mIntent.putExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,getIntent().getBooleanExtra(IS_FROM_MENU_MEASUREMENT_HISTORY,false));
                    startActivity(mIntent);


                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    CommFunc.ShowStatusPop(EnterDetails.this, processResponse.getMessage(), false);
                }
            }
        }

        public void onError(String str) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(EnterDetails.this, str, false);
        }
    };
    SessionManager sessionManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActEnterDetailsBinding inflate = ActEnterDetailsBinding.inflate(getLayoutInflater());
        this.detailsBinding = inflate;
        setContentView((View) inflate.getRoot());
        this.commUserProfileData = new ComUserProfileData();
        this.sessionManager = new SessionManager(this);
        this.detailsBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!((HHTApplication) getApplication()).isHomeScreenPaused()) {
                    Intent i1 = new Intent(EnterDetails.this, HomeScreen.class);
                    startActivity(i1);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });
        this.detailsBinding.nextBtn.setOnClickListener(this);
        this.detailsBinding.cmBtn.setOnClickListener(this);
        this.detailsBinding.inchBtn.setOnClickListener(this);
        this.detailsBinding.kgBtn.setOnClickListener(this);
        this.detailsBinding.lbsBtn.setOnClickListener(this);
        this.detailsBinding.usBtn.setOnClickListener(this);
        this.detailsBinding.euBtn.setOnClickListener(this);
        this.detailsBinding.taperedBtn.setOnClickListener(this);
        this.detailsBinding.regularBtn.setOnClickListener(this);
        this.detailsBinding.LooseBtn.setOnClickListener(this);
        this.detailsBinding.maleBtnTapped.setOnClickListener(this);
        this.detailsBinding.femaleBtnTapped.setOnClickListener(this);
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (!((HHTApplication) getApplication()).isHomeScreenPaused()) {
            Intent i1 = new Intent(EnterDetails.this, HomeScreen.class);
            startActivity(i1);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    public int HeightValidation() {
        if (this.Isheight.equalsIgnoreCase("feet")) {
            if (this.detailsBinding.fteditText.getText().toString().isEmpty()) {
                return 0;
            }
            if (this.detailsBinding.inchEditText.getText().toString().isEmpty()) {
                return (int) Math.round(Double.parseDouble(this.detailsBinding.fteditText.getText().toString()) * 12.0d * 2.54d * 10.0d);
            }
            return (int) ((Double.parseDouble(this.detailsBinding.fteditText.getText().toString()) * 12.0d * 2.54d * 10.0d) + (Double.parseDouble(this.detailsBinding.inchEditText.getText().toString()) * 2.54d * 10.0d));
        } else if (this.detailsBinding.cmeditText.getText().toString().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(this.detailsBinding.cmeditText.getText().toString()) * 10;
        }
    }

    private int weightValidation() {
        if (this.IsKG.equalsIgnoreCase("kg")) {
            if (this.detailsBinding.weightEditText.getText().toString().isEmpty()) {
                return 0;
            }
            return Integer.parseInt(this.detailsBinding.weightEditText.getText().toString());
        } else if (this.detailsBinding.weightEditText.getText().toString().isEmpty()) {
            return 0;
        } else {
            return (int) (Double.parseDouble(this.detailsBinding.weightEditText.getText().toString()) / 2.2d);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LooseBtn:
                this.detailsBinding.LooseBtn.setBackgroundResource(R.drawable.blue_background_for_button);
                this.detailsBinding.taperedBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                this.detailsBinding.regularBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                this.PrefferedType = "Loose";
                return;
            case R.id.cmBtn:
                this.detailsBinding.cmeditText.setVisibility(0);
                this.detailsBinding.FtnInchLayout.setVisibility(8);
                this.detailsBinding.cmeditText.setHint(R.string.height);
                this.detailsBinding.cmBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                this.detailsBinding.inchBtn.setBackgroundResource(R.drawable.white_background);
                this.detailsBinding.inchBtn.setTextColor(Color.parseColor("#173043"));
                this.Isheight = "cm";
                return;
            case R.id.euBtn:
                this.detailsBinding.euBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                this.detailsBinding.usBtn.setTextColor(Color.parseColor("#173043"));
                this.detailsBinding.usBtn.setBackgroundResource(R.drawable.white_background);
                this.detailsBinding.shoeSizeEditText.setText("");
                this.ShoeSizeUnit = "EU";
                return;
            case R.id.femaleBtnTapped:
                this.detailsBinding.femaleBtnTapped.setBackgroundResource(R.drawable.blue_background_for_button);
                this.detailsBinding.maleBtnTapped.setBackgroundResource(R.drawable.white_edit_text_background);
                this.genderForMeas = "Female";
                return;
            case R.id.inchBtn:
                this.detailsBinding.FtnInchLayout.setVisibility(0);
                this.detailsBinding.cmeditText.setVisibility(8);
                this.detailsBinding.cmBtn.setBackgroundResource(R.drawable.white_background);
                this.detailsBinding.cmBtn.setTextColor(Color.parseColor("#173043"));
                this.detailsBinding.inchBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                this.Isheight = "feet";
                return;
            case R.id.kgBtn:
                this.detailsBinding.kgBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                this.detailsBinding.lbsBtn.setBackgroundResource(R.drawable.white_background);
                this.detailsBinding.lbsBtn.setTextColor(Color.parseColor("#173043"));
                this.IsKG = "kg";
                return;
            case R.id.lbsBtn:
                this.detailsBinding.lbsBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                this.detailsBinding.kgBtn.setBackgroundResource(R.drawable.white_background);
                this.detailsBinding.kgBtn.setTextColor(Color.parseColor("#173043"));
                this.IsKG = "lbs";
                return;
            case R.id.maleBtnTapped:
                this.detailsBinding.maleBtnTapped.setBackgroundResource(R.drawable.blue_background_for_button);
                this.detailsBinding.femaleBtnTapped.setBackgroundResource(R.drawable.white_edit_text_background);
                this.genderForMeas = "Male";
                return;
            case R.id.nextBtn:
                userDetailsValidation();
                return;
            case R.id.regularBtn:
                this.detailsBinding.regularBtn.setBackgroundResource(R.drawable.blue_background_for_button);
                this.detailsBinding.LooseBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                this.detailsBinding.taperedBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                this.PrefferedType = "Regular";
                return;
            case R.id.taperedBtn:
                this.detailsBinding.taperedBtn.setBackgroundResource(R.drawable.blue_background_for_button);
                this.detailsBinding.regularBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                this.detailsBinding.LooseBtn.setBackgroundResource(R.drawable.white_edit_text_background);
                this.PrefferedType = "Tapered";
                return;
            case R.id.usBtn:
                this.detailsBinding.usBtn.setBackgroundResource(R.drawable.btn_blue_bg);
                this.detailsBinding.euBtn.setTextColor(Color.parseColor("#173043"));
                this.detailsBinding.euBtn.setBackgroundResource(R.drawable.white_background);
                this.detailsBinding.shoeSizeEditText.setText("");
                this.ShoeSizeUnit = UserStateKt.US_COUNTRY;
                return;
            default:
                return;
        }
    }

    private void userDetailsValidation() {
        this.HeightData = HeightValidation();
        this.WeightData = weightValidation();
        if (this.detailsBinding.ageEdittext.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(this, "Please enter your age.", false);
        } else if (Integer.parseInt(this.detailsBinding.ageEdittext.getText().toString()) < 13 || Integer.parseInt(this.detailsBinding.ageEdittext.getText().toString()) > 90) {
            CommFunc.ShowStatusPop(this, "Age should be between the range of 13 to 90.", false);
        } else {
            int i = this.HeightData;
            if (i < 1) {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.height_wrong), false);
            } else if (i < 1400 || i > 2200) {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.height_range_wrong), false);
            } else {
                int i2 = this.WeightData;
                if (i2 == 0) {
                    CommFunc.ShowStatusPop(this, getResources().getString(R.string.weight_wrong), false);
                } else if (i2 < 30 || i2 > 150) {
                    CommFunc.ShowStatusPop(this, getResources().getString(R.string.weight_range_wrong), false);
                } else if (this.detailsBinding.shoeSizeEditText.getText().toString().isEmpty()) {
                    CommFunc.ShowStatusPop(this, "Please enter your shoe size.", false);
                } else if (this.PrefferedType.isEmpty()) {
                    CommFunc.ShowStatusPop(this, "Please select your preferred fit type.", false);
                } else {
                    setUserValue();
                }
            }
        }
    }

    public void setUserValue() {
        ComUserProfileData.setAge(Integer.parseInt(this.detailsBinding.ageEdittext.getText().toString()));
        ComUserProfileData.setHeight((double) this.HeightData);
        ComUserProfileData.setWeight((double) this.WeightData);
        ComUserProfileData.setHeightWithUnit((this.HeightData / 10) + " cm");
        ComUserProfileData.setWeightWithUnit(this.WeightData + " kg");
        ComUserProfileData.setShoeSize(this.detailsBinding.shoeSizeEditText.getText() + " " + this.ShoeSizeUnit);
        ComUserProfileData.setPreferredFit(this.PrefferedType);
        ComUserProfileData.setmeasurementGender(this.genderForMeas);
        getProcessID();
    }

    private void getProcessID() {
        if (InternetConnection.isConnected(this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.processID(new ProcessIDRequest(Constants.MIRROR_ID,
                    this.sessionManager.getMerchantEmail(), this.sessionManager.getProductName(), this.genderForMeas),
                    "GET_PROCESS_ID", this.res, this);
            return;
        }
        CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
    }
}
