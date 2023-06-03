package com.ms.hht.ui.customization.Fabric_Select.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ms.hht.R;
import com.ms.hht.data.adapter.CartListAdapter;
import com.ms.hht.data.response.CartListResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActOrderCartBinding;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

public class OrderCartAct extends AppCompatActivity implements View.OnClickListener{
    public static String ACTIVITY_FROM = "";
    ActOrderCartBinding cartBinding;
    public static CartListResponse cartListResponse;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartBinding = ActOrderCartBinding.inflate(getLayoutInflater());
        setContentView(cartBinding.getRoot());
        sessionManager = new SessionManager(OrderCartAct.this);
        getCartList();
        cartBinding.NextButton.setOnClickListener(this);
        cartBinding.backImage2.setOnClickListener(view -> {
            if (ACTIVITY_FROM.equalsIgnoreCase("CustomizationAct")){

                Intent i = new Intent(OrderCartAct.this,HomeScreen.class);
                finish();
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
            else if (ACTIVITY_FROM.equalsIgnoreCase("ProductPieceSelection")){
                Intent i = new Intent(OrderCartAct.this,HomeScreen.class);
                finishAffinity();
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
            else {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }

        });
        cartBinding.conShopBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (ACTIVITY_FROM.equalsIgnoreCase("CustomizationAct")){
            Intent i = new Intent(OrderCartAct.this,HomeScreen.class);
            finish();
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if (ACTIVITY_FROM.equalsIgnoreCase("ProductPieceSelection")){
            Intent i = new Intent(OrderCartAct.this,HomeScreen.class);
            finishAffinity();
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.NextButton:
                if (
                        cartListResponse != null && cartListResponse.getCode() == 1 &&
                        cartListResponse.getData() != null && cartListResponse.getData().getQuoteItem() != null &&
                                cartListResponse.getData().getQuoteItem().size() > 0)
                {
                    Intent int2 = new Intent(OrderCartAct.this, CartDetailsActivity.class);
                    CartDetailsActivity.CART_LIST = cartListResponse.getData();
                    startActivity(int2);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else {
                    CommFunc.ShowStatusPop(OrderCartAct.this,"Your cart list is empty.",false);
                }
                break;

            case R.id.conShopBtn:
                Intent int3 = new Intent(OrderCartAct.this, HomeScreen.class);
                finishAffinity();
                startActivity(int3);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    private void getCartList(){
        if (InternetConnection.isConnected(OrderCartAct.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getcartList( "cart details data", response, OrderCartAct.this);

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
                        setCartList(cartListResponse);
                    }
                    else {
                        sessionManager.setCartCount(String.valueOf(0));
                        CommFunc.ShowStatusPop(OrderCartAct.this, cartListResponse.getMessage(), false);
                    }
                }
                else {
                    CommFunc.ShowStatusPop(OrderCartAct.this, "NO ERROR", false);
                }
            }
        }

        @Override
        public void onError(String message) {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(OrderCartAct.this, message, false);
        }
    };

    private void setCartList(CartListResponse cartListResponse) {
        LinearLayoutManager layoutManager=new LinearLayoutManager(OrderCartAct.this);
        cartBinding.cartlistRecy.setLayoutManager(layoutManager);
        CartListAdapter adapter = new CartListAdapter(OrderCartAct.this,cartListResponse.getData().getQuoteItem());
        cartBinding.cartlistRecy.setAdapter(adapter);
        cartBinding.cartlistRecy.setHasFixedSize(true);
        cartBinding.cartlistRecy.setItemViewCacheSize(20);
        cartBinding.cartlistRecy.setDrawingCacheEnabled(true);
        cartBinding.cartlistRecy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        cartBinding.cartlistRecy.setNestedScrollingEnabled(false);
    }
}