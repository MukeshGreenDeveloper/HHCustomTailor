package com.ms.hht.ui.Sub_Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import com.ms.hht.R;
import com.ms.hht.data.adapter.SubCategoryAdapter;
import com.ms.hht.data.response.SubCategoryResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;

import com.ms.hht.databinding.ActStyleLookBinding;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderCartAct;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.ui.login.LoginActivity;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;
import com.paypal.pyplcheckout.home.viewmodel.SpinnerState;

import java.util.List;

public class SubCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    //    public static Integer ENTITY_ID ;
    public static String HEADING;
    public static Integer Category_ID_FOR_API;

    ActStyleLookBinding lookBinding;
    SubCategoryResponse subCategoryResponse;
    SessionManager sessionManager;
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lookBinding = ActStyleLookBinding.inflate(getLayoutInflater());
        setContentView(lookBinding.getRoot());
        sessionManager = new SessionManager(SubCategoryActivity.this);
        lookBinding.cartImage.setOnClickListener(this);
        lookBinding.prevActIma.setOnClickListener(view -> finish());
        lookBinding.classTxt.setText(HEADING);

        getSubCategoryData();

    }

    @Override
    protected void onResume() {
        super.onResume();

        email = sessionManager.getUserEmail();
        lookBinding.userName.setText("Hi "+sessionManager.getUserFirstName());
        int cartCount  = Integer.parseInt(sessionManager.getcartCount());
        if (cartCount==0) {
            lookBinding.cartCount.setVisibility(View.INVISIBLE);
        } else {
            lookBinding.cartCount.setText(sessionManager.getcartCount());
            lookBinding.cartCount.setVisibility(View.VISIBLE);
        }
    }

    private void getSubCategoryData() {
        if (InternetConnection.isConnected(SubCategoryActivity.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getCustImages(Category_ID_FOR_API, "SUB CATEGORY DATA", response, SubCategoryActivity.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("SUB CATEGORY DATA")) {
                CommFunc.DismissDialog();
                subCategoryResponse = (SubCategoryResponse) o;
                if (subCategoryResponse != null) {
                    if (subCategoryResponse.getCode() == 1) {
                        if (subCategoryResponse.getData().size() > 0) {
                            setCategoryData(subCategoryResponse.getData());
                            System.out.println("id after successs response ++++++++++++     " + subCategoryResponse.getData().get(0).getSubcategoryId()
                                    + "+++++++++++++++++++++      " + subCategoryResponse.getData().get(0).getItemId());
                        }
                    } else {
                        CommFunc.ShowStatusPop(SubCategoryActivity.this, subCategoryResponse.getMessage(), false);
                    }
                }
            }
        }
        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(SubCategoryActivity.this, message, false);
        }
    };

    private void setCategoryData(List<SubCategoryResponse.DataItem> data) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(SubCategoryActivity.this);
        lookBinding.buyProRcy.setLayoutManager(layoutManager);
        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(SubCategoryActivity.this, data);
        lookBinding.buyProRcy.setAdapter(subCategoryAdapter);
        lookBinding.buyProRcy.setHasFixedSize(true);
        lookBinding.buyProRcy.setItemViewCacheSize(20);
        lookBinding.buyProRcy.setDrawingCacheEnabled(true);
        lookBinding.buyProRcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        lookBinding.buyProRcy.setNestedScrollingEnabled(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cartImage:

                if (email.isEmpty()){
                    ShowResultPopUp();
                }
                else {

                    Intent cartIntent = new Intent(SubCategoryActivity.this, OrderCartAct.class);
                    OrderCartAct.ACTIVITY_FROM = "";
                    startActivity(cartIntent);
                }
                break;
        }
    }

    private void ShowResultPopUp() {
        final Dialog dialog = new Dialog(SubCategoryActivity.this);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.pop_up_status);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView popheaderImage = dialog.findViewById(R.id.successPop_Header_image);
        TextView poperrorDescription = dialog.findViewById(R.id.successPop_error_description);
        Button submit = dialog.findViewById(R.id.successPop_done_button);

        popheaderImage.setImageResource(R.drawable.oopsimage);
        poperrorDescription.setText(getString(R.string.Not_registered_user));

        submit.setOnClickListener(v ->{
            dialog.dismiss();
            Intent i  = new Intent(SubCategoryActivity.this, LoginActivity.class);
//            LoginActivity.from = "SubCategoryActivity";
            Constants.Login_From = "SubCategoryActivity";
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}