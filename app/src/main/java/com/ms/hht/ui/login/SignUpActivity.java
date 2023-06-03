package com.ms.hht.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ms.hht.R;
import com.ms.hht.data.SignUpResponse;
import com.ms.hht.data.request.SignupRequest;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivitySignUpBinding;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySignUpBinding signUpBinding;
    SignUpResponse signUpResponse;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        sessionManager = new SessionManager(SignUpActivity.this);

        signUpBinding.backSignUp.setOnClickListener(v -> {
            GoBack();
//            finish();
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        signUpBinding.signUpButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        GoBack();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.signUp_Button:

                userSignUp();

                break;
        }

    }

    private void userSignUp() {
        if (signUpBinding.nameEdit.getText().toString().trim().isEmpty()){
            CommFunc.ShowStatusPop(SignUpActivity.this,"Please enter your name.",false);
        }else if (signUpBinding.emailIdEdit.getText().toString().trim().isEmpty()){
            CommFunc.ShowStatusPop(SignUpActivity.this,"Please enter your email.",false);
        }else if (!signUpBinding.emailIdEdit.getText().toString().trim().contains("@")){
            CommFunc.ShowStatusPop(SignUpActivity.this,"Please enter a correct email.",false);
        }else if (!signUpBinding.emailIdEdit.getText().toString().trim().contains(".")){
            CommFunc.ShowStatusPop(SignUpActivity.this,"Please enter a correct email.",false);
        }else if (signUpBinding.passwordEdit.getText().toString().trim().isEmpty()){
            CommFunc.ShowStatusPop(SignUpActivity.this,"Please enter your password.",false);
        }
        else if (!CommFunc.Password_Validation(signUpBinding.passwordEdit.getText().toString())) {
            CommFunc.ShowStatusPop(SignUpActivity.this, "Your password should contain a minimum of 8 characters, and include at least 3 of the following: Lower Case, Upper Case, Digit, and/or Special Character", false);
        }
        else if (signUpBinding.cnfPasswordEdit.getText().toString().trim().isEmpty()){
            CommFunc.ShowStatusPop(SignUpActivity.this,"Please enter your confirm password.",false);
        }else if (!signUpBinding.cnfPasswordEdit.getText().toString().trim().
                equals(signUpBinding.passwordEdit.getText().toString().trim())){
            CommFunc.ShowStatusPop(SignUpActivity.this,"Password and confirm password do not match.",false);
        }else {
            if (InternetConnection.isConnected(SignUpActivity.this)) {
                CommFunc.ShowProgressbar(this);
                SignupRequest signupRequest = new SignupRequest(
                        signUpBinding.nameEdit.getText().toString().trim(),
                        signUpBinding.emailIdEdit.getText().toString().trim(),
                        signUpBinding.passwordEdit.getText().toString().trim());

                APICallList.userSignUp(signupRequest,"user signup",response,SignUpActivity.this);
            } else {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
            }
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if(url_type.equalsIgnoreCase("user signup")){
                CommFunc.DismissDialog();
                signUpResponse = (SignUpResponse) o;
                if (signUpResponse != null) {
                    if(signUpResponse.getCode()==1) {
                        sessionManager.setUserEmail(signUpBinding.emailIdEdit.getText().toString().trim());
                        sessionManager.setUserFirstName(signUpBinding.nameEdit.getText().toString());
                        sessionManager.setUserPassword(signUpBinding.passwordEdit.getText().toString().trim());
                        Intent i1 = new Intent(SignUpActivity.this,VerifyEmailActivity.class);
                        VerifyEmailActivity.ACTIVITY_FROM = "signUp";
                        VerifyEmailActivity.FORGOT_USER_EMAIL = signUpBinding.emailIdEdit.getText().toString().trim();
                        VerifyEmailActivity.CUSTOMER_ID = signUpResponse.getData().getId();
                        startActivity(i1);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                    else {
                        CommFunc.ShowStatusPop(SignUpActivity.this,signUpResponse.getMessage(),false);
                    }
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(SignUpActivity.this, message, false);
        }
    };

    public void showPassword(View view) {

        if(view.getId()==R.id.eyeIcon){

            if(signUpBinding.passwordEdit.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.show_password);

                //Show Password
                signUpBinding.passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else{
                ((ImageView)(view)).setImageResource(R.drawable.hide_password);

                //Hide Password
                signUpBinding.passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private void GoBack(){
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        finish();
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}