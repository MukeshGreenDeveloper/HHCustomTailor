package com.ms.hht.ui.profile;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ms.hht.R;
import com.ms.hht.data.request.UpdateUserProfileRequest;
import com.ms.hht.data.response.GetUserProfileResponse;
import com.ms.hht.data.response.UpdateUserProfileResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityEditProfileBinding;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    ActivityEditProfileBinding editProfileBinding;
    String gender = "";
    GetUserProfileResponse getUserProfileResponse;
    SessionManager sessionManager;
    UpdateUserProfileResponse updateUserProfileResponse;
    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("get user profile data")) {
                CommFunc.DismissDialog();
                getUserProfileResponse = (GetUserProfileResponse) o;
                if (getUserProfileResponse != null) {
                    if (getUserProfileResponse.getCode() == 1) {
                        setUserProfileData(getUserProfileResponse.getData().getFirstname(),
                                getUserProfileResponse.getData().getLastname(),
                                getUserProfileResponse.getData().getEmail(),
                                getUserProfileResponse.getData().getAlternate_email(),
                                getUserProfileResponse.getData().getGender());
                        sessionManager.setUserGender(getUserProfileResponse.getData().getGender());
                    } else {
                        CommFunc.ShowStatusPop(EditProfile.this, getUserProfileResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(EditProfile.this, getResources().getString(R.string.try_again), false);
                }
            } else if (url_type.equalsIgnoreCase("update user profile data")) {
                CommFunc.DismissDialog();
                updateUserProfileResponse = (UpdateUserProfileResponse) o;
                if (updateUserProfileResponse != null) {
                    if (updateUserProfileResponse.getCode() == 1) {
                        sessionManager.setUserFirstName(updateUserProfileResponse.getData().get(0).getFirstname());
                        if (updateUserProfileResponse.getData().get(0).getLastname()!=null) {
                            sessionManager.setUserLastName(updateUserProfileResponse.getData().get(0).getLastname());
                        }
                        sessionManager.setUserGender(updateUserProfileResponse.getData().get(0).getGender());
                        sessionManager.setUserEmail(updateUserProfileResponse.getData().get(0).getEmail());
                        showUpdateProfilePopUp(updateUserProfileResponse.getMessage());

//                        setUserProfileData(updateUserProfileResponse.getData().get(0).getFirstname(),
//                                updateUserProfileResponse.getData().get(0).getLastname(),
//                                updateUserProfileResponse.getData().get(0).getEmail(),
//                                updateUserProfileResponse.getData().get(0).getAlternate_email(),
//                                updateUserProfileResponse.getData().get(0).getGender());

                    } else {
                        CommFunc.ShowStatusPop(EditProfile.this, updateUserProfileResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(EditProfile.this, getResources().getString(R.string.try_again), false);
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(EditProfile.this, message, false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editProfileBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(editProfileBinding.getRoot());

        sessionManager = new SessionManager(EditProfile.this);

        editProfileBinding.editProfileBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        editProfileBinding.femaleLayout.setOnClickListener(this);
        editProfileBinding.maleLayout.setOnClickListener(this);
        editProfileBinding.EditProfileUpdateBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.femaleLayout:
                gender = "female";
                editProfileBinding.femaleLayout.setBackgroundResource(R.drawable.blue_border_background);
                editProfileBinding.maleLayout.setBackgroundResource(R.drawable.white_background_low_border);
                break;

            case R.id.maleLayout:
                gender = "male";
                editProfileBinding.femaleLayout.setBackgroundResource(R.drawable.white_background_low_border);
                editProfileBinding.maleLayout.setBackgroundResource(R.drawable.blue_border_background);
                break;

            case R.id.EditProfileUpdateBtn:
//                call update POST api and send the request body
                if (editProfileBinding.EditProfileUserFNameTF.getText().toString().trim().isEmpty()) {
                    CommFunc.ShowStatusPop(EditProfile.this, "Please enter your first name.", false);
                } else if (editProfileBinding.EditProfileUserEmailTF.getText().toString().trim().isEmpty()) {
                    CommFunc.ShowStatusPop(EditProfile.this, "Please enter your email.", false);
                } else if (!editProfileBinding.EditProfileUserEmailTF.getText().toString().trim().contains("@")) {
                    CommFunc.ShowStatusPop(EditProfile.this, "Please enter a correct email.", false);
                } else if (!editProfileBinding.EditProfileUserEmailTF.getText().toString().trim().contains(".")) {
                    CommFunc.ShowStatusPop(EditProfile.this, "Please enter a correct email.", false);
                } else if (gender.isEmpty()) {
                    CommFunc.ShowStatusPop(EditProfile.this, "Please select your gender.", false);
                } else {
                    updateUserProfileData(editProfileBinding.EditProfileUserFNameTF.getText().toString().trim(),
                            editProfileBinding.EditProfileUserLNameTF.getText().toString().trim(),
                            editProfileBinding.EditProfileUserEmailTF.getText().toString().trim(), editProfileBinding.alternateEditProfileUserEmailTF.getText().toString().trim() ,gender);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserProfileData();
    }

    private void getUserProfileData() {
        if (InternetConnection.isConnected(EditProfile.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getUserProfileData("get user profile data", response, EditProfile.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private void setUserProfileData(String UserFirstName, String UserLastName, String UserEmail, String UserAlternateEmail, String UserGender) {

        editProfileBinding.EditProfileUserFNameTF.setText(UserFirstName);
        editProfileBinding.EditProfileUserLNameTF.setText(UserLastName);
        editProfileBinding.EditProfileUserEmailTF.setText(UserEmail);
        editProfileBinding.alternateEditProfileUserEmailTF.setText(UserAlternateEmail);
        gender = UserGender;
        // after sucess response
        if (gender.equalsIgnoreCase("female")) {
            editProfileBinding.femaleLayout.setBackgroundResource(R.drawable.blue_border_background);
            editProfileBinding.maleLayout.setBackgroundResource(R.drawable.white_background_low_border);
        } else if (gender.equalsIgnoreCase("male")) {
            editProfileBinding.femaleLayout.setBackgroundResource(R.drawable.white_background_low_border);
            editProfileBinding.maleLayout.setBackgroundResource(R.drawable.blue_border_background);
        } else {
            editProfileBinding.maleLayout.setBackgroundResource(R.drawable.white_background_low_border);
            editProfileBinding.femaleLayout.setBackgroundResource(R.drawable.white_background_low_border);
        }
    }

    private void updateUserProfileData(String UserFirstName, String UserLastName, String UserEmail, String UserAlternateEmail, String UserGender) {
        // call api to update Profile
        if (InternetConnection.isConnected(EditProfile.this)) {
            CommFunc.ShowProgressbar(this);
            UpdateUserProfileRequest updateUserProfileRequest = new UpdateUserProfileRequest(UserFirstName, UserLastName, UserEmail, UserAlternateEmail, UserGender);
            APICallList.updateUserProfile(updateUserProfileRequest, "update user profile data", response, EditProfile.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private void showUpdateProfilePopUp(String message) {

        final Dialog dialog = new Dialog(EditProfile.this);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.pop_up_status);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView poperrorDescription = dialog.findViewById(R.id.successPop_error_description);
        Button submit = dialog.findViewById(R.id.successPop_done_button);
        poperrorDescription.setText(message);
        submit.setOnClickListener(view -> {
            dialog.dismiss();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

    }
}