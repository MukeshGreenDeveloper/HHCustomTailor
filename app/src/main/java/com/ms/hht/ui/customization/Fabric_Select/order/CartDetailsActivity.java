package com.ms.hht.ui.customization.Fabric_Select.order;

import static com.ms.hht.ui.measure.MeasurementDetailListActivity.SelectedMeasurementId;
import static com.ms.hht.utils.Constants.PAYPAL_LIVE_CLIENT_ID;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ms.hht.R;
import com.ms.hht.data.SignUpResponse;
import com.ms.hht.data.request.SignupRequest;
import com.ms.hht.data.response.AppStatusResponse;
import com.ms.hht.data.response.CartListResponse;
import com.ms.hht.data.response.MeasurementHistoryResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityCartDetailsBinding;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.ui.login.SignUpActivity;
import com.ms.hht.ui.login.VerifyEmailActivity;
import com.ms.hht.ui.measure.MeasureNow;
import com.ms.hht.ui.measure.MeasurementDetailListActivity;
import com.ms.hht.ui.payment.PaymentAct;
import com.ms.hht.ui.payment.PlaceOrder_billing_address;
import com.ms.hht.ui.profile.AddressListAct;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.pyplcheckout.pojo.Card;

import java.util.List;

public class CartDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static CartListResponse.Data CART_LIST;
    ActivityCartDetailsBinding cartDetailsBinding;
    public static List<MeasurementHistoryResponse.MeasurementItem> measurementVALUEToPlaceOrder;
    AppStatusResponse appStatusResponse;
    CartListResponse cartListResponse;
    SessionManager sessionManager;
    // discount
    double cart_total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartDetailsBinding = ActivityCartDetailsBinding.inflate(getLayoutInflater());
        setContentView(cartDetailsBinding.getRoot());
        sessionManager = new SessionManager(CartDetailsActivity.this);
        cartDetailsBinding.backImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommFunc.DismissDialog();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        cartDetailsBinding.ApplyCouponBtn.setOnClickListener(this);
        cartDetailsBinding.editShipAddress.setOnClickListener(this);
        cartDetailsBinding.editBillAdd.setOnClickListener(this);
        cartDetailsBinding.conShopBtn.setOnClickListener(this);
        cartDetailsBinding.NextPageButton.setOnClickListener(this);
        if(CART_LIST==null){
            getCartList();
        }else
            cart_total = CART_LIST.getGrandTotal();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCartConstants();
//        PaymentAct.FINAL_AMOUNT_TO_BE_PAID = String.valueOf(CART_LIST.getGrandTotal());
        cartDetailsBinding.ShipAddVAlue.setText(Constants.shippingAddress_VALUE);
        cartDetailsBinding.BillAddValue.setText(Constants.billingAddress_VALUE);
        if(MeasureNow.activityComingFrom == "cart"){
//            CommFunc.ShowProgressbar(this);
            CommFunc.DismissDialog();
        }





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
            case R.id.editShipAddress:

                Intent int2 = new Intent(CartDetailsActivity.this, AddressListAct.class);
                AddressListAct.AddressListActivityComingFrom = "cart ship address";
                Constants.shippingAddress_VALUE = "";
                Constants.shippingAddress_VALUE_ObJ_TYPE = null;
                startActivity(int2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.editBillAdd:

                Intent int4 = new Intent(CartDetailsActivity.this, AddressListAct.class);
                AddressListAct.AddressListActivityComingFrom = "cart bill address";
                Constants.billingAddress_VALUE = "";
                Constants.billingAddress_VALUE_OBJ_TYPE = null;
                startActivity(int4);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.NextPageButton:

                if (InternetConnection.isConnected(CartDetailsActivity.this)) {

                    goTONextActivity();
                } else {
                    CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
                }



                break;

            case R.id.conShopBtn:

                Intent int3 = new Intent(CartDetailsActivity.this, HomeScreen.class);
                startActivity(int3);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.ApplyCouponBtn:
                if(cartDetailsBinding.couponTextField.getText().toString().isEmpty()){
                    CommFunc.ShowStatusPop(this,"Please enter discount amount.", false);
                }else if (Integer.parseInt((cartDetailsBinding.couponTextField.getText().toString())) > CART_LIST.getAvailableDiscount()){
                    CommFunc.ShowStatusPop(this,"Please enter correct discount amount.", false);
                }else {
                    double cart_Apply_Discount_Price = Double.parseDouble(cartDetailsBinding.couponTextField.getText().toString());
                    cart_total = (cart_total - cart_Apply_Discount_Price);
                    cartDetailsBinding.discountValue.setText(cartDetailsBinding.couponTextField.getText().toString());
                    cartDetailsBinding.orderTotalValue.setText("$ " + cart_total);
                    PaymentAct.CART_TOTAL_PAY = cart_total;
                    PaymentAct.DISCOUNT_AMOUNT = cart_Apply_Discount_Price;
                }
                break;
        }
    }

    private void goTONextActivity() {
        if (cartDetailsBinding.ShipAddVAlue.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(CartDetailsActivity.this, "Please select shipping address.", false);
        } else if (cartDetailsBinding.BillAddValue.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(CartDetailsActivity.this, "Please select billing address.", false);
        } else {
            CommFunc.ShowProgressbar(this);
            double cart_Apply_Discount_Price = TextUtils.isEmpty(cartDetailsBinding.couponTextField.getText().toString())?0.0:Double.parseDouble(cartDetailsBinding.couponTextField.getText().toString());
            cart_total = (cart_total - cart_Apply_Discount_Price);
            cartDetailsBinding.discountValue.setText(cartDetailsBinding.couponTextField.getText().toString());
            cartDetailsBinding.orderTotalValue.setText("$ " + cart_total);
            PaymentAct.CART_TOTAL_PAY = cart_total;
            PaymentAct.DISCOUNT_AMOUNT = cart_Apply_Discount_Price;
            MeasureNow.activityComingFrom = "cart";
            Intent payIntent = new Intent(CartDetailsActivity.this, PaymentAct.class);
            PayPalCheckout.setConfig(new CheckoutConfig(
                    getApplication(),
                    PAYPAL_LIVE_CLIENT_ID,
                    Environment.LIVE,
                    CurrencyCode.USD,
                    UserAction.PAY_NOW
            ));

            PaymentAct.cart_measurement_id_in_paymentAct = SelectedMeasurementId;
            startActivity(payIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }
    }
    private void getCartList(){
        if (InternetConnection.isConnected(CartDetailsActivity.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getcartList( "cart details data", response, CartDetailsActivity.this);

        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) {
            if (url_type.equalsIgnoreCase("cart details data")) {
                CommFunc.DismissDialog();
                cartListResponse = (CartListResponse) o;
                if (cartListResponse != null) {
                    if (cartListResponse.getCode() == 1) {
                        if (cartListResponse.getData() != null){
                            sessionManager.setCartCount(String.valueOf(cartListResponse.getData().getQuoteItem().size()));
                        }
                        if (cartListResponse != null && cartListResponse.getCode() == 1 &&
                            cartListResponse.getData() != null && cartListResponse.getData().getQuoteItem() != null &&
                            cartListResponse.getData().getQuoteItem().size() > 0) {
                            CART_LIST = cartListResponse.getData();
                            setCartConstants();
                            cart_total = CART_LIST.getGrandTotal();
                        }
                    }
                    else {
                        sessionManager.setCartCount(String.valueOf(0));
                        CommFunc.ShowStatusPop(CartDetailsActivity.this, cartListResponse.getMessage(), false);
                    }
                }
                else {
                    CommFunc.ShowStatusPop(CartDetailsActivity.this, "NO ERROR", false);
                }
            }
        }

        @Override
        public void onError(String message) {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(CartDetailsActivity.this, message, false);
        }
    };

    private void setCartConstants() {
        if (CART_LIST != null) {
            if (CART_LIST.getAvailableDiscount() > 0) {
                cartDetailsBinding.couponHeading.setVisibility(View.VISIBLE);
                cartDetailsBinding.discountValue.setVisibility(View.VISIBLE);
                cartDetailsBinding.couponsSubHeading.setVisibility(View.VISIBLE);
                cartDetailsBinding.couponTextField.setVisibility(View.VISIBLE);
                cartDetailsBinding.ApplyCouponBtn.setVisibility(View.VISIBLE);
                cartDetailsBinding.discountHeading.setVisibility(View.VISIBLE);
                cartDetailsBinding.discountValue.setText("$" + CART_LIST.getAvailableDiscount());
                cartDetailsBinding.couponHeading.setText("YOU HAVE US $" + CART_LIST.getAvailableDiscount() + " IN YOUR ACCOUNT.");
            } else {
                cartDetailsBinding.couponHeading.setVisibility(View.INVISIBLE);
                cartDetailsBinding.couponsSubHeading.setVisibility(View.INVISIBLE);
                cartDetailsBinding.couponTextField.setVisibility(View.INVISIBLE);
                cartDetailsBinding.discountValue.setVisibility(View.INVISIBLE);
                cartDetailsBinding.ApplyCouponBtn.setVisibility(View.INVISIBLE);
                cartDetailsBinding.discountHeading.setVisibility(View.INVISIBLE);
            }

            cartDetailsBinding.subtotalvalue.setText("$" + CART_LIST.getQuoteTotal());
            cartDetailsBinding.shipChargeValue.setText("$" + CART_LIST.getShippingCost());
            cartDetailsBinding.orderTotalValue.setText("$" + CART_LIST.getGrandTotal());
        }
    }
}