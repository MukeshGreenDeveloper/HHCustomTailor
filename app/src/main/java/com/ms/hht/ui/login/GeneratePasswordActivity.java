package com.ms.hht.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ms.hht.R;
import com.ms.hht.data.SignUpResponse;
import com.ms.hht.data.request.GeneratePasswordRequest;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityGeneratePasswordBinding;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;

public class GeneratePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    public static int CUSTOMER_ID;
    public static String CUSTOMER_EMAIL;
    ActivityGeneratePasswordBinding generatePasswordBinding;
    SignUpResponse signUpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generatePasswordBinding = ActivityGeneratePasswordBinding.inflate(getLayoutInflater());
        setContentView(generatePasswordBinding.getRoot());

        generatePasswordBinding.generatePsswrdBtn.setOnClickListener(this);
        generatePasswordBinding.backgenerateNewPass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.generatePsswrdBtn:
                if (generatePasswordBinding.newPasswordText.getText().toString().trim().isEmpty()) {
                    CommFunc.ShowStatusPop(GeneratePasswordActivity.this, "Please enter new password.", false);
                }
                else if (!CommFunc.Password_Validation(generatePasswordBinding.newPasswordText.getText().toString())) {
                    CommFunc.ShowStatusPop(GeneratePasswordActivity.this, "Your password should contain a minimum of 8 characters, and include at least 3 of the following: Lower Case, Upper Case, Digit, and/or Special Character", false);
                }
                else if (generatePasswordBinding.newcnfPasswordText.getText().toString().trim().isEmpty()) {
                    CommFunc.ShowStatusPop(GeneratePasswordActivity.this, "Please enter confirm password.", false);
                } else if (!generatePasswordBinding.newPasswordText.getText().toString().trim().equals(generatePasswordBinding.newcnfPasswordText.getText().toString().trim())) {
                    CommFunc.ShowStatusPop(GeneratePasswordActivity.this, "Password and confirm password do not match.", false);
                } else {
                    if (InternetConnection.isConnected(GeneratePasswordActivity.this)) {
                        CommFunc.ShowProgressbar(this);
                        GeneratePasswordRequest generatePasswordRequest = new GeneratePasswordRequest(
                                CUSTOMER_ID, generatePasswordBinding.newPasswordText.getText().toString().trim()
                        );
                        APICallList.generatePassword(generatePasswordRequest, "generate password", response, GeneratePasswordActivity.this);
                    } else {
                        CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
                    }
                }
                break;
            case R.id.backgenerateNewPass:
//                finish();
                GoBack();
                break;
        }
    }

    public void showPasswordGen(View view) {
        if (view.getId() == R.id.eyeIconImg) {

            if (generatePasswordBinding.newPasswordText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.show_password);
                //Show Password
                generatePasswordBinding.newPasswordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.hide_password);
                //Hide Password
                generatePasswordBinding.newPasswordText.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }

    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("generate password")) {
                CommFunc.DismissDialog();
                signUpResponse = (SignUpResponse) o;
                if (signUpResponse != null) {
                    if (signUpResponse.getCode() == 1) {
                        sowPopUp(signUpResponse, true);

                    } else {
                        CommFunc.ShowStatusPop(GeneratePasswordActivity.this, signUpResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(GeneratePasswordActivity.this, "ERROR", false);
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(GeneratePasswordActivity.this, message, false);
        }
    };

    private void sowPopUp(SignUpResponse signUpResponse, boolean b) {

        final Dialog dialog = new Dialog(GeneratePasswordActivity.this);
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
            Intent i1 = new Intent(GeneratePasswordActivity.this, LoginActivity.class);
            finish();
            startActivity(i1);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    @Override
    public void onBackPressed() {
        GoBack();
    }

    private void GoBack(){
        Intent i = new Intent(GeneratePasswordActivity.this, VerifyEmailActivity.class);
        VerifyEmailActivity.ACTIVITY_FROM="Forgot Password";
        VerifyEmailActivity.FORGOT_USER_EMAIL = CUSTOMER_EMAIL;
        finish();
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}