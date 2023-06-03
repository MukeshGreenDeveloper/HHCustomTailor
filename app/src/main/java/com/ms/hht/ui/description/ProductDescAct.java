package com.ms.hht.ui.description;

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

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.response.ProductDescriptionRes;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActProductDescBinding;
import com.ms.hht.ui.Product_Pieces.Activity.ProductPieceSelecton;
import com.ms.hht.ui.customization.CustomizationProcess.Activity.CustomizationAct;
import com.ms.hht.ui.customization.Fabric_Select.Activity.FabricListActivity;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderCartAct;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.ui.login.LoginActivity;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

public class ProductDescAct extends AppCompatActivity implements View.OnClickListener {

    public static String ITEM_ID_TO_CAll_PDAPI = "";

    ActProductDescBinding descBinding;
    ProductDescriptionRes productDescriptionRes;
    SessionManager sessionManager;
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        descBinding = ActProductDescBinding.inflate(getLayoutInflater());
        setContentView(descBinding.getRoot());

        sessionManager = new SessionManager(ProductDescAct.this);
        descBinding.prevAct.setOnClickListener(view -> finish());
        descBinding.styleCustomBtn.setOnClickListener(this);
        descBinding.cartImage.setOnClickListener(this);

        getProductDescription();
    }

    @Override
    protected void onResume() {
        super.onResume();

        descBinding.userName.setText("Hi "+sessionManager.getUserFirstName());
        email = sessionManager.getUserEmail();
        int cartCount = Integer.parseInt(sessionManager.getcartCount());

        if (cartCount == 0) {
            descBinding.cartCount.setVisibility(View.GONE);
        } else {
            descBinding.cartCount.setVisibility(View.VISIBLE);
            descBinding.cartCount.setText(sessionManager.getcartCount());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prevAct:
                backkFunction();
                break;
            case R.id.cartImage:
                if (email.isEmpty()){
                    ShowResultPopUp();
                }
                else {
                    Intent intent2 = new Intent(ProductDescAct.this, OrderCartAct.class);
                    OrderCartAct.ACTIVITY_FROM = "";
                    startActivity(intent2);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
            case R.id.styleCustomBtn:

                if (email.isEmpty()){
                    ShowResultPopUp();
                }
                else {
                    sessionManager.setItemId(productDescriptionRes.getData().getId().toString());
                    sessionManager.setIsMultipart(productDescriptionRes.getData().getIsMultiple().toString());
                    if ((Integer.parseInt(sessionManager.getTypeId())) == 1) {
                        System.out.println("FABRIC+++++++++++++++++++1");
                        sessionManager.setPieceId(String.valueOf(productDescriptionRes.getData().getPieceId()));

                        Intent intent5 = new Intent(ProductDescAct.this, FabricListActivity.class);
                        FabricListActivity.productDescriptionRes = productDescriptionRes;
                        startActivity(intent5);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    if (productDescriptionRes.getData().getIsMultiple() == 0) {
//                        sessionManager.setPieceId(String.valueOf(productDescriptionRes.getData().getPieceId()));
//                        Intent inten5 = new Intent(ProductDescAct.this, FabricListActivity.class);
//                        FabricListActivity.subCategoryID_from_prod_desc_of_custom_to_call_fabricAPI = productDescriptionRes.getData().getSubcategoryId();
////                        CustomizationAct.PRODUCT_NAME = productDescriptionRes.getData().getName();
//                        startActivity(inten5);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    } else {
//                        Intent inten5 = new Intent(ProductDescAct.this, FabricListActivity.class);
//                        FabricListActivity.subCategoryID_from_prod_desc_of_custom_to_call_fabricAPI = productDescriptionRes.getData().getSubcategoryId();
////                        CustomizationAct.PRODUCT_NAME = productDescriptionRes.getData().getName();
//                        startActivity(inten5);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    }
                    } else {

                        if (productDescriptionRes.getData().getIsMultiple() == 0) {

                            Intent inten5 = new Intent(ProductDescAct.this, CustomizationAct.class);
                            sessionManager.setPieceId(String.valueOf(productDescriptionRes.getData().getPieceId()));
                            CustomizationAct.SUB_CATEGORY_ID_FROM_PIECE_SEL_TO_CUSTOMIZATION_TO_CALL_PIECE_FABRIC_FILTER_API = productDescriptionRes.getData().getSubcategoryId();
                            CustomizationAct.PRODUCT_NAME = productDescriptionRes.getData().getName();
                            CustomizationAct.PIECE_ID_FROM_PROD_DESC_TO_FABRIC_SELECTION_TO_GET_PIECE_TYPE = productDescriptionRes.getData().getPieceId();
                            startActivity(inten5);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        else
                        {
                            Intent inten5 = new Intent(ProductDescAct.this, ProductPieceSelecton.class);
                            ProductPieceSelecton.productDescriptionRes = productDescriptionRes;
//                        CustomizationAct.PRODUCT_NAME = productDescriptionRes.getData().getName();
//                        ProductPieceSelecton.SUB_CATEGORY_ID_FROM_FAB_List_TO_PRO_PIECE_SEL = productDescriptionRes.getData().getSubcategoryId();
                            startActivity(inten5);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backkFunction();
    }

    private void backkFunction() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private void ShowResultPopUp() {
        final Dialog dialog = new Dialog(ProductDescAct.this);
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

        popheaderImage.setImageResource(R.drawable.oopsimage);
        poperrorDescription.setText(getString(R.string.Not_registered_user));

        submit.setOnClickListener(v ->{
            dialog.dismiss();
            Intent i  = new Intent(ProductDescAct.this, LoginActivity.class);
//            LoginActivity.from = "ProductDescAct";
            Constants.Login_From = "ProductDescAct";
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void getProductDescription() {
        if (InternetConnection.isConnected(ProductDescAct.this)) {
            CommFunc.ShowProgressbar(this);
            System.out.println("ITEM ID for getting desc +++++++++++   " + ITEM_ID_TO_CAll_PDAPI);
//            int id = Integer.parseInt(sessionManager.getItemId());
            if (!ITEM_ID_TO_CAll_PDAPI.isEmpty()) {
                int id = Integer.parseInt(ITEM_ID_TO_CAll_PDAPI);
                APICallList.getProductDescp(id, "PRODUCT DATA", response, ProductDescAct.this);
            }
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("PRODUCT DATA")) {
                CommFunc.DismissDialog();
                productDescriptionRes = (ProductDescriptionRes) o;

                if (productDescriptionRes != null) {
//                    FabricListActivity.IS_MULTIPART = productDescriptionRes.getData().getIsMultiple();
//                    CustomizationAct.PIECE_ID_FROM_PROD_DESC_TO_FABRIC_SELECTION_TO_GET_PIECE_TYPE = productDescriptionRes.getData().getPieceId();
                    Glide.with(ProductDescAct.this).load(productDescriptionRes.getData().getImage()).into(descBinding.productDescImage);
                    descBinding.ItemNameTxt.setText(productDescriptionRes.getData().getName());
                    descBinding.price.setText(productDescriptionRes.getData().getPrice());
                    descBinding.prodDetails.setText(productDescriptionRes.getData().getDescription());
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(ProductDescAct.this, message, false);
        }
    };


}