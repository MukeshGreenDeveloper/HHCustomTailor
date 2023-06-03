package com.ms.hht.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.ms.hht.R;
import com.ms.hht.data.SignUpResponse;
import com.ms.hht.data.request.ForgotPassOTPRequestBody;
import com.ms.hht.data.request.SignupRequest;
import com.ms.hht.data.request.VerifyOtpRequest;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityVerifyEmailBinding;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

public class VerifyEmailActivity extends AppCompatActivity implements View.OnClickListener {

    public static int CUSTOMER_ID = -1;
    public static String ACTIVITY_FROM = "";
    public static String FORGOT_USER_EMAIL = "";

    SessionManager sessionManager;
    SignUpResponse signUpResponse;
    ActivityVerifyEmailBinding verifyEmailBinding;

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("verify otp")) {
                CommFunc.DismissDialog();
                signUpResponse = (SignUpResponse) o;
                if (signUpResponse != null) {
                    if (signUpResponse.getCode() == 1) {
                        showPopUp(signUpResponse, true);
                    }
                    else {
                        CommFunc.ShowStatusPop(VerifyEmailActivity.this, signUpResponse.getMessage(), false);
                    }
                }
            }
            else if (url_type.equalsIgnoreCase("resend otp")) {
                CommFunc.DismissDialog();
                signUpResponse = (SignUpResponse) o;
                if (signUpResponse != null) {
                    CommFunc.ShowStatusPop(VerifyEmailActivity.this, signUpResponse.getMessage(), signUpResponse.getCode() == 1);
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(VerifyEmailActivity.this, message, false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyEmailBinding = ActivityVerifyEmailBinding.inflate(getLayoutInflater());
        setContentView(verifyEmailBinding.getRoot());

        sessionManager = new SessionManager(VerifyEmailActivity.this);

        verifyEmailBinding.backButton.setOnClickListener(v -> {
            GoBack();
//            finish();
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        verifyEmailBinding.verifyButton.setOnClickListener(this);
        verifyEmailBinding.resendOTP.setOnClickListener(this);

        if (ACTIVITY_FROM.equalsIgnoreCase("LOGIN")) {
            resendOTP();
        }

        verifyEmailBinding.emailText.setText(FORGOT_USER_EMAIL);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_Button:
                verifyOtpButton();
                break;

            case R.id.resendOTP:
                resendOTP();
                break;
        }
    }

    private void verifyOtpButton() {

        if (verifyEmailBinding.verifyEditText.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(VerifyEmailActivity.this, "Please enter OTP.", false);
        }
        else {
            String OTP = verifyEmailBinding.verifyEditText.getText().toString().trim();
            if (InternetConnection.isConnected(VerifyEmailActivity.this)) {
                CommFunc.ShowProgressbar(this);
                if (ACTIVITY_FROM.equalsIgnoreCase("Forgot Password") ){
                    ForgotPassOTPRequestBody  verifyOtpRequest = new ForgotPassOTPRequestBody(OTP,"forgot_password", FORGOT_USER_EMAIL );
                    APICallList.ForgotUserOTP(verifyOtpRequest, "verify otp", response, VerifyEmailActivity.this);
                }
                else
                {
                    VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest(CUSTOMER_ID, OTP, "registration");
                    APICallList.verifyOTP(verifyOtpRequest, "verify otp", response, VerifyEmailActivity.this);
                }
            }
            else {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
            }
        }
    }

    private void resendOTP() {
        if (InternetConnection.isConnected(VerifyEmailActivity.this)) {
            CommFunc.ShowProgressbar(this);
            SignupRequest signupRequest = new SignupRequest(FORGOT_USER_EMAIL);
            APICallList.resendOTP(signupRequest, "resend otp", response, VerifyEmailActivity.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private void showPopUp(SignUpResponse signUpResponse, boolean b) {
        final Dialog dialog = new Dialog(VerifyEmailActivity.this);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.pop_up_status);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView popheaderImage = dialog.findViewById(R.id.successPop_Header_image);
        TextView poperrorDescription = dialog.findViewById(R.id.successPop_error_description);
        Button submit = dialog.findViewById(R.id.successPop_done_button);

        poperrorDescription.setText(signUpResponse.getMessage());

        if (!b) {
            popheaderImage.setImageResource(R.drawable.oopsimage);
        } else {
            popheaderImage.setImageResource(R.drawable.ok);
        }
        submit.setOnClickListener(v -> {
            dialog.dismiss();
            if (ACTIVITY_FROM.equals("Forgot Password")) {
                Intent i2 = new Intent(VerifyEmailActivity.this, GeneratePasswordActivity.class);
                finish();
                GeneratePasswordActivity.CUSTOMER_ID = signUpResponse.getData().getId();
                GeneratePasswordActivity.CUSTOMER_EMAIL = FORGOT_USER_EMAIL;
                startActivity(i2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else {
                if (!Constants.Login_From.isEmpty()){
                    sessionManager.setLoginSession("Logged in");
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else {
                    sessionManager.setLoginSession("Logged in");
                    Intent i1 = new Intent(VerifyEmailActivity.this, HomeScreen.class);
                    startActivity(i1);
                    finishAffinity();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GoBack();
//        finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void GoBack(){
        if (ACTIVITY_FROM.equalsIgnoreCase("Forgot Password")){
            Intent i = new Intent(VerifyEmailActivity.this, ForgotPassAct.class);
            finish();
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else {
            Intent i = new Intent(VerifyEmailActivity.this, SignUpActivity.class);
            finish();
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
}