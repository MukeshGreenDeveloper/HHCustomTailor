package com.ms.hht.ui.measure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.ms.hht.R;
import com.ms.hht.data.SignUpResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActMeasureNowBinding;
import com.ms.hht.noui.HHTApplication;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

public class MeasureNow extends AppCompatActivity implements View.OnClickListener{

    public static String activityComingFrom ="";
    SessionManager sessionManager;
    SignUpResponse signUpResponse;
    ActMeasureNowBinding nowBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nowBinding = ActMeasureNowBinding.inflate(getLayoutInflater());
        setContentView(nowBinding.getRoot());
        sessionManager = new SessionManager(MeasureNow.this);
        nowBinding.getStartBtn.setOnClickListener(this);
        nowBinding.proceedBtn2.setOnClickListener(this);
        nowBinding.backImage1.setOnClickListener(view -> {
            if(!((HHTApplication)getApplication()).isHomeScreenPaused()){
                Intent i1 = new Intent(MeasureNow.this, HomeScreen.class);
                startActivity(i1);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }else {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        Log.d("keyss..", "Received Data here==>activion=" + appLinkAction);
        Log.d("keyss..", "Received Data here==>appLinkData=" + appLinkData);
        try {
            FirebaseApp.initializeApp(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                FirebaseDynamicLinks.getInstance()
                        .getDynamicLink(appLinkIntent)
                        .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                            @Override
                            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                                // Get deep link from result (may be null if no link is found)
                                Uri deepLink = null;
                                String token = null;
                                if (pendingDynamicLinkData != null) {
                                    deepLink = pendingDynamicLinkData.getLink();
                                    token = deepLink.getQueryParameter("token");
                                    userLogin(token);

                                }
                                Log.w("keysss....Success", "Receied Link data=>" + "__token==>" + token);
                            }
                        })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("keysss....Error", "getDynamicLink:onFailure", e);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.getStartBtn:

                Intent intent5 = new Intent(MeasureNow.this,EnterDetails.class);
                startActivity(intent5);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.proceedBtn2:

                Intent next = new Intent(MeasureNow.this,MeasurementHistoryAct.class);
                MeasurementHistoryAct.MeasurementHistoryActivityComingFrom = activityComingFrom;
                startActivity(next);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }
    private void userLogin(String token) {
        if (token != null && !TextUtils.isEmpty(token))
            if (InternetConnection.isConnected(MeasureNow.this)) {
                CommFunc.ShowProgressbar(this);
                Log.d("keyss...","Trying to login==>"+token);
                APICallList.userLoginByToken(token, "user login", response, MeasureNow.this);
            } else {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
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
                        sessionManager = new SessionManager(MeasureNow.this);
                        sessionManager.setUserEmail(signUpResponse.getData().getEmail());
                        sessionManager.setUserPassword(signUpResponse.getData().getPassword());
                        sessionManager.setUserId("349");//For Testing need to be changed to signUpResponse.getData().getId());
                        sessionManager.setLoginSession("Logged in");
                        ShowPopUP("Logged in as "+signUpResponse.getData().getEmail(),true,signUpResponse);
                    }
                    else if (signUpResponse.getCode() == 2){
                        ShowPopUP(signUpResponse.getMessage(),false,signUpResponse);
                    }
                    else {
                        CommFunc.ShowStatusPop(MeasureNow.this,signUpResponse.getMessage(),false);
                    }
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(MeasureNow.this, message, false);
        }
    };
    private void ShowPopUP(String message, boolean b, SignUpResponse signUpResponse) {
        final Dialog dialog = new Dialog(MeasureNow.this);
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

        });
    }

    @Override
    public void onBackPressed() {
        if(!((HHTApplication)getApplication()).isHomeScreenPaused()){
            Intent i1 = new Intent(MeasureNow.this, HomeScreen.class);
            startActivity(i1);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }else
            super.onBackPressed();
    }
}