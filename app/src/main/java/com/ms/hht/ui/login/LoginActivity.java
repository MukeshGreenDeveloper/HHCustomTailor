package com.ms.hht.ui.login;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ms.hht.R;
import com.ms.hht.data.SignUpResponse;
import com.ms.hht.data.request.SignupRequest;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityLoginBinding;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding loginBinding;
    SignUpResponse signUpResponse;
    SessionManager sessionManager;
//    public static String from = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        sessionManager = new SessionManager(LoginActivity.this);
        loginBinding.registerText.setOnClickListener(this);
        loginBinding.signInButton.setOnClickListener(this);
        loginBinding.forgotPassText.setOnClickListener(this);
        loginBinding.passwordEditText.setOnClickListener(this);
        loginBinding.backLogin.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.registerText:
                Intent i1 = new Intent(LoginActivity.this, SignUpActivity.class);
                finish();
                startActivity(i1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.signIn_Button:
                userLogin();
                break;
            case R.id.forgotPassText:
                Intent intent2 = new Intent(LoginActivity.this, ForgotPassAct.class);
                finish();
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.backLogin:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
    }

    private void userLogin() {
        if (loginBinding.emailId.getText().toString().trim().isEmpty()){
            CommFunc.ShowStatusPop(LoginActivity.this,"Please enter your email.",false);
        }else if (!loginBinding.emailId.getText().toString().trim().contains("@")){
            CommFunc.ShowStatusPop(LoginActivity.this,"Please enter a correct email.",false);
        }else if (!loginBinding.emailId.getText().toString().trim().contains(".")){
            CommFunc.ShowStatusPop(LoginActivity.this,"Please enter a correct email.",false);
        }else if (loginBinding.passwordEditText.getText().toString().trim().isEmpty()){
            CommFunc.ShowStatusPop(LoginActivity.this,"Please enter your password.",false);
        }else {
            if (InternetConnection.isConnected(LoginActivity.this)) {
                CommFunc.ShowProgressbar(this);
                SignupRequest signupRequest = new SignupRequest(
                        loginBinding.emailId.getText().toString().trim(),
                        loginBinding.passwordEditText.getText().toString().trim());
                APICallList.userLogin(signupRequest,"user login",response,LoginActivity.this);
            } else {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
            }
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if(url_type.equalsIgnoreCase("user login")){
                CommFunc.DismissDialog();
                signUpResponse = (SignUpResponse) o;
                if (signUpResponse != null) {
                    if(signUpResponse.getCode()==1) {
                        sessionManager.setUserEmail(loginBinding.emailId.getText().toString().trim());
                        sessionManager.setUserPassword(loginBinding.passwordEditText.getText().toString().trim());
                        sessionManager.setUserId(String.valueOf(signUpResponse.getData().getId()));
                        sessionManager.setLoginSession("Logged in");
                        if (Constants.Login_From.isEmpty()){
                            Intent i1 = new Intent(LoginActivity.this, HomeScreen.class);
                            startActivity(i1);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        else {
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                    else if (signUpResponse.getCode() == 2){
                        sessionManager.setUserEmail(loginBinding.emailId.getText().toString().trim());
                        sessionManager.setUserPassword(loginBinding.passwordEditText.getText().toString().trim());
                        ShowPopUP(signUpResponse.getMessage(),false,signUpResponse);
                    }
                    else {
                        CommFunc.ShowStatusPop(LoginActivity.this,signUpResponse.getMessage(),false);
                    }
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(LoginActivity.this, message, false);
        }
    };

    private void ShowPopUP(String message, boolean b, SignUpResponse signUpResponse) {
        final Dialog dialog = new Dialog(LoginActivity.this);
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

        poperrorDescription.setText(message);

        if (!b) {
            popheaderImage.setImageResource(R.drawable.oopsimage);
        } else {
            popheaderImage.setImageResource(R.drawable.ok);
        }
        submit.setOnClickListener(v -> {
            dialog.dismiss();
            Intent i1 = new Intent(LoginActivity.this, VerifyEmailActivity.class);
            VerifyEmailActivity.CUSTOMER_ID = signUpResponse.getData().getId();
            VerifyEmailActivity.ACTIVITY_FROM = "LOGIN";
            VerifyEmailActivity.FORGOT_USER_EMAIL = loginBinding.emailId.getText().toString().trim();
            startActivity(i1);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    public void showHidePassword(View view) {
        if(view.getId()==R.id.eye_icon){
            if(loginBinding.passwordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.show_password);
                //Show Password
                loginBinding.passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.hide_password);
                //Hide Pass      word
                loginBinding.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}