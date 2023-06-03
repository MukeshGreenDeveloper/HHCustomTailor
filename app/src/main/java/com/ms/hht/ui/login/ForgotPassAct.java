package com.ms.hht.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ms.hht.R;
import com.ms.hht.data.SignUpResponse;
import com.ms.hht.data.request.SignupRequest;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActForgotPassBinding;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;

public class ForgotPassAct extends AppCompatActivity implements View.OnClickListener {

    ActForgotPassBinding passBinding;
    SignUpResponse signUpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        passBinding = ActForgotPassBinding.inflate(getLayoutInflater());
        setContentView(passBinding.getRoot());
        passBinding.backActi.setOnClickListener(view -> {
            GoBack();
//            finish();
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        passBinding.proceBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.proceBtn:
                userForgotPassword();
                break;
        }
    }

    private void userForgotPassword() {
        if (passBinding.emailEdiTxt.getText().toString().trim().isEmpty()){
            CommFunc.ShowStatusPop(ForgotPassAct.this,"Please enter your email.",false);
        }else if (!passBinding.emailEdiTxt.getText().toString().trim().contains("@")){
            CommFunc.ShowStatusPop(ForgotPassAct.this,"Please enter a correct email.",false);
        }else if (!passBinding.emailEdiTxt.getText().toString().trim().contains(".")){
            CommFunc.ShowStatusPop(ForgotPassAct.this,"Please enter a correct email.",false);
        }else {
            if (InternetConnection.isConnected(ForgotPassAct.this)) {
                CommFunc.ShowProgressbar(this);
                SignupRequest signupRequest = new SignupRequest(
                        passBinding.emailEdiTxt.getText().toString().trim());
                APICallList.forgotPassword(signupRequest,"forgot password",response,ForgotPassAct.this);
            } else {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
            }
        }
    }


    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if(url_type.equalsIgnoreCase("forgot password")){
                CommFunc.DismissDialog();
                signUpResponse = (SignUpResponse) o;
                if (signUpResponse != null) {
                    if(signUpResponse.getCode()==1) {

                        Intent i1 = new Intent(ForgotPassAct.this, VerifyEmailActivity.class);
                        VerifyEmailActivity.ACTIVITY_FROM="Forgot Password";
                        VerifyEmailActivity.FORGOT_USER_EMAIL = passBinding.emailEdiTxt.getText().toString();
                        finish();
                        startActivity(i1);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }else {

                        CommFunc.ShowStatusPop(ForgotPassAct.this,signUpResponse.getMessage(),false);

                    }

                } else {

                    CommFunc.ShowStatusPop(ForgotPassAct.this, signUpResponse.getMessage(), false);

                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(ForgotPassAct.this, message, false);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GoBack();
//        finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void GoBack(){
            Intent i = new Intent(ForgotPassAct.this, LoginActivity.class);
            finish();
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}