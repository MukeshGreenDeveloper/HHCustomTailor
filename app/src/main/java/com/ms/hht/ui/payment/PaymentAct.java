package com.ms.hht.ui.payment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ms.hht.R;
import com.ms.hht.data.response.CartListResponse;
import com.ms.hht.data.response.ChangePasswordResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderList;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import java.util.ArrayList;

public class PaymentAct extends AppCompatActivity {

    public static double DISCOUNT_AMOUNT = 0.0;

    public static CartListResponse.Data CART_LIST_DATA;
    public static double CART_TOTAL_PAY = 0.0;
    public static int cart_measurement_id_in_paymentAct;
    PaymentButtonContainer paymentButtonContainer;
    ImageView backBtn;
    Button conShopBtn, placeOrderButton;
    TextView totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_payment);

        totalAmount = findViewById(R.id.totalAmount);
        totalAmount.setText("$ "+CART_TOTAL_PAY);
        paymentButtonContainer = findViewById(R.id.payment_button_container);
        backBtn = findViewById(R.id.back);
        backBtn.setVisibility(View.INVISIBLE);
        conShopBtn = findViewById(R.id.conShopBtn2);
        placeOrderButton = findViewById(R.id.makePaymentBtn);

        backBtn.setOnClickListener(v -> finish());
        conShopBtn.setOnClickListener(view -> {
            Intent i = new Intent(PaymentAct.this, HomeScreen.class);
            finishAffinity();
            startActivity(i);
        });

        placeOrderButton.setOnClickListener(view -> CommFunc.ShowStatusPop(PaymentAct.this,"Please select a payment method to continue.",false));


        paymentButtonContainer.setup(
                createOrderActions -> {
                    ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                    purchaseUnits.add(
                            new PurchaseUnit.Builder()
                                    .amount(
                                            new Amount.Builder()
                                                    .currencyCode(CurrencyCode.USD)
                                                    .value(CART_TOTAL_PAY+"")
                                                    .build()
                                    )
                                    .build()
                    );
                    Order order = new Order(
                            OrderIntent.CAPTURE,
                            new AppContext.Builder()
                                    .userAction(UserAction.PAY_NOW)
                                    .build(),
                            purchaseUnits
                    );
                    createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                },
                approval -> approval.getOrderActions().capture(result -> {
                    Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
                    callingPlaceOrderApi();
                }), (shippingChangeData, shippingChangeActions) -> {

                }, () -> {
                    Log.d("OnCancel", "Buyer cancelled the PayPal experience.");
                    CommFunc.ShowStatusPop(PaymentAct.this, "Payment cancelled. Please try again.", false);
                }, errorInfo -> {
                    Log.d("OnError", String.format("Error: %s", errorInfo));
                    CommFunc.ShowStatusPop(PaymentAct.this, "Something went wrong. Please try again.", false);
                }
        );
    }

    @Override
    public void onBackPressed() {
        System.out.println("****************************************");
    }

    private void callingPlaceOrderApi() {
        if (InternetConnection.isConnected(PaymentAct.this)) {
            CommFunc.ShowProgressbar(this);
            PlaceOrderRequestBody orderRequestBody = new PlaceOrderRequestBody(CART_LIST_DATA.getQuoteId(), cart_measurement_id_in_paymentAct, Constants.billingAddress_VALUE_OBJ_TYPE,
                    Constants.shippingAddress_VALUE_ObJ_TYPE,
                    (int) CART_LIST_DATA.getQuoteTotal(), (int) DISCOUNT_AMOUNT, CART_LIST_DATA.getShippingCost(),
                    (int) CART_LIST_DATA.getGrandTotal(),  (int) CART_TOTAL_PAY, CART_LIST_DATA.getWeight(), "PayPal");

            APICallList.placeOrderAPi(orderRequestBody, "place order", response, PaymentAct.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {

        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("place order")) {
                CommFunc.DismissDialog();
                ChangePasswordResponse getPlaceOrderResponse = (ChangePasswordResponse) o;
                if (getPlaceOrderResponse != null) {
                    if (getPlaceOrderResponse.getCode() == 1) {
                        ShowPopUP(getPlaceOrderResponse.getMessage());
                    } else {
                        CommFunc.ShowStatusPop(PaymentAct.this, getPlaceOrderResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(PaymentAct.this, getResources().getString(R.string.try_again), false);
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(PaymentAct.this, message, false);
        }
    };


    private void ShowPopUP(String message) {
        final Dialog dialog = new Dialog(PaymentAct.this);
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

        popheaderImage.setImageResource(R.drawable.ok);

        submit.setOnClickListener(v -> {
            dialog.dismiss();
            Intent i1 = new Intent(PaymentAct.this, OrderList.class);
            OrderList.ACTIVITY_FROM = "PaymentSuccess";
            startActivity(i1);
            finishAffinity();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}