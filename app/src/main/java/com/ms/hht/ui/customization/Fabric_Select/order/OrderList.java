package com.ms.hht.ui.customization.Fabric_Select.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ms.hht.R;
import com.ms.hht.data.adapter.OrderListAdapter;
import com.ms.hht.data.response.OrderListResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActMyOrderBinding;
import com.ms.hht.ui.home.HomeScreen;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;

public class OrderList extends AppCompatActivity{

    public static String ACTIVITY_FROM = "";

    ActMyOrderBinding myOrderBinding;
    OrderListResponse orderListResponse;
    public String comingFrom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myOrderBinding = ActMyOrderBinding.inflate(getLayoutInflater());
        setContentView(myOrderBinding.getRoot());
        myOrderBinding.backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ACTIVITY_FROM.equalsIgnoreCase("PaymentSuccess")){
                    Intent i = new Intent(OrderList.this, HomeScreen.class);
                    finish();
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ACTIVITY_FROM = "";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (ACTIVITY_FROM.equalsIgnoreCase("PaymentSuccess")){
            Intent i = new Intent(OrderList.this, HomeScreen.class);
            finish();
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            finish();
        }

    }

    private void getOrderList(){
        if (InternetConnection.isConnected(OrderList.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getOrderLIST(  "order list data", response, OrderList.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("order list data")) {
                CommFunc.DismissDialog();
                orderListResponse = (OrderListResponse) o;
                if (orderListResponse != null) {
                    if (orderListResponse.getCode() == 1) {
                        if (orderListResponse.getData().size() > 0) {
                            setOrderList(orderListResponse);
                        }
                    } else {
                        CommFunc.ShowStatusPop(OrderList.this, orderListResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(OrderList.this, getResources().getString(R.string.try_again), false);
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(OrderList.this, message, false);
        }
    };


    private void setOrderList(OrderListResponse orderListResponse) {
        LinearLayoutManager layoutManager=new LinearLayoutManager(OrderList.this);
        myOrderBinding.myOrderRcy.setLayoutManager(layoutManager);
        OrderListAdapter orderAdapter = new OrderListAdapter(OrderList.this,orderListResponse.getData());
        myOrderBinding.myOrderRcy.setAdapter(orderAdapter);
        myOrderBinding.myOrderRcy.setHasFixedSize(true);
        myOrderBinding.myOrderRcy.setItemViewCacheSize(20);
        myOrderBinding.myOrderRcy.setDrawingCacheEnabled(true);
        myOrderBinding.myOrderRcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        myOrderBinding.myOrderRcy.setNestedScrollingEnabled(false);
    }


}