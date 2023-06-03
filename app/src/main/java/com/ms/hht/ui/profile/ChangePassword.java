package com.ms.hht.ui.profile;

import android.app.Dialog;
import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;

import com.ms.hht.R;
import com.ms.hht.data.request.ChangePassRequestBody;
import com.ms.hht.data.response.ChangePasswordResponse;
import com.ms.hht.data.response.DeleteAddressResponse;
import com.ms.hht.data.response.UpdateDefaultAddressResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActChangePasswordBinding;
import com.ms.hht.ui.login.SignUpActivity;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    ActChangePasswordBinding passwordBinding;
    SessionManager sessionManager;
    ChangePasswordResponse changePasswordResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordBinding = ActChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(passwordBinding.getRoot());

        sessionManager = new SessionManager(ChangePassword.this);
        passwordBinding.backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        passwordBinding.proceedBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.proceedBtn:
                if (changePassValidation()){
                    callChangePassApi();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void hideShowPassword(View view) {

        if (view.getId() == R.id.old_eye_icon) {
            if (passwordBinding.OldpasswordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.show_password);
                //Show Password
                passwordBinding.OldpasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.hide_password);
                //Hide Password
                passwordBinding.OldpasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }else if (view.getId() == R.id.newPass_eye_icon) {
            if (passwordBinding.newpasswordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.show_password);
                //Show Password
                passwordBinding.newpasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.hide_password);
                //Hide Password
                passwordBinding.newpasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    private Boolean changePassValidation(){
        if (passwordBinding.OldpasswordEditText.getText().toString().isEmpty()){
            CommFunc.ShowStatusPop(ChangePassword.this,"Please enter your old password.",false);
            return false;
        }else if (passwordBinding.newpasswordEditText.getText().toString().isEmpty()){
            CommFunc.ShowStatusPop(ChangePassword.this,"Please enter your new password.",false);
            return false;
        }
        else if (!CommFunc.Password_Validation(passwordBinding.newpasswordEditText.getText().toString())) {
            CommFunc.ShowStatusPop(ChangePassword.this, "Your password should contain a minimum of 8 characters, and include at least 3 of the following: Lower Case, Upper Case, Digit, and/or Special Character", false);
            return false;
        }
        else if (passwordBinding.cnfNewPassEditTxt.getText().toString().isEmpty()){
            CommFunc.ShowStatusPop(ChangePassword.this,"Please enter your confirm password.",false);
            return false;
        }else if (!passwordBinding.newpasswordEditText.getText().toString().equals(passwordBinding.cnfNewPassEditTxt.getText().toString())) {
            CommFunc.ShowStatusPop(ChangePassword.this,"New password and confirm password do not match.",false);
            return false;
        }
        return true;
    }

    private void callChangePassApi() {
        if (InternetConnection.isConnected(ChangePassword.this)) {
            CommFunc.ShowProgressbar(this);
            ChangePassRequestBody changePassRequestBody = new ChangePassRequestBody(passwordBinding.OldpasswordEditText.getText().toString(),passwordBinding.newpasswordEditText.getText().toString());
            APICallList.ChangePassword(changePassRequestBody,"change password", response, ChangePassword.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("change password")) {
                CommFunc.DismissDialog();
                changePasswordResponse = (ChangePasswordResponse) o;
                if (changePasswordResponse != null) {
                    if (changePasswordResponse.getCode() == 1) {
                        showSuccessPopUP(ChangePassword.this, changePasswordResponse.getMessage(), true);
                        sessionManager.setUserPassword(passwordBinding.newpasswordEditText.getText().toString());
                    } else {
                        CommFunc.ShowStatusPop(ChangePassword.this, changePasswordResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(ChangePassword.this, getResources().getString(R.string.try_again), false);
                }

            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(ChangePassword.this, message, false);
        }
    };

    private void showSuccessPopUP(Context context, String description, boolean status) {
        final Dialog dialog = new Dialog(context);
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

        poperrorDescription.setText(description);

        if (!status) {
            popheaderImage.setImageResource(R.drawable.oopsimage);
        } else {
            popheaderImage.setImageResource(R.drawable.ok);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
               finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}