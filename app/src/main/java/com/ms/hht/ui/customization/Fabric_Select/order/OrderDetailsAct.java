package com.ms.hht.ui.customization.Fabric_Select.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.gson.Gson;
import com.ms.hht.R;
import com.ms.hht.data.adapter.OrderDetailAdapter;
import com.ms.hht.data.response.OrderDetailResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityOrderDetailsBinding;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;

import java.util.ArrayList;

public class OrderDetailsAct extends AppCompatActivity implements View.OnClickListener {

    ActivityOrderDetailsBinding detailsBinding;
    OrderDetailResponse orderDetailResponse;
    public static int ORDER_ID = -1;
    public static String INCREMENT_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsBinding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(detailsBinding.getRoot());
        detailsBinding.backImage.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        detailsBinding.reOrderBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetail();
        detailsBinding.orderTV.setText("Order " + INCREMENT_ID);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reOrderBtn:
                Intent intent5 = new Intent(OrderDetailsAct.this, SelectReorderItemAct.class);
                startActivity(intent5);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
        }
    }

    private void getOrderDetail() {
        if (InternetConnection.isConnected(OrderDetailsAct.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getorderDETAIL(ORDER_ID, "order details data", response, OrderDetailsAct.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("order details data")) {
                CommFunc.DismissDialog();
                orderDetailResponse = (OrderDetailResponse) o;
                if (orderDetailResponse != null) {
                    if (orderDetailResponse.getCode() == 1) {
                        setOrderDetailData(orderDetailResponse);
                    } else {
                        CommFunc.ShowStatusPop(OrderDetailsAct.this, orderDetailResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(OrderDetailsAct.this, "NO ERROR", false);
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(OrderDetailsAct.this, message, false);
        }
    };


    private void setOrderDetailData(OrderDetailResponse orderDetailResponse) {

        SelectReorderItemAct.itemData = new ArrayList<>();
        for (int i = 0; i <= orderDetailResponse.getData().getQuoteItem().size() -1 ; i++ ){
            for (OrderDetailResponse.Data.QuoteItemItem.ItemDataItem dataItem : orderDetailResponse.getData().getQuoteItem().get(i).getItemData()) {
                System.out.println("item data in order details before adding  ---> " + new Gson().toJson(dataItem));
                SelectReorderItemAct.itemData.add(dataItem);
            }
        }

        detailsBinding.subTotalValue.setText("$ " + orderDetailResponse.getData().getQuoteTotal() + ".0");
        detailsBinding.shipnDeliveryvalue.setText("$ " + orderDetailResponse.getData().getShippingCost() + ".0");
        detailsBinding.discountValue.setText("$ " + orderDetailResponse.getData().getDiscount() + ".0");
        detailsBinding.TotalPaidvalue.setText("$ " + orderDetailResponse.getData().getTotalPaid() + ".0");
        detailsBinding.shippingAddressValue.setText(
                orderDetailResponse.getData().getShippingAddress().getStreet() + ",\n" +
                        orderDetailResponse.getData().getShippingAddress().getCity() + ", " +
                        orderDetailResponse.getData().getShippingAddress().getRegion() + ", " +
                        orderDetailResponse.getData().getShippingAddress().getPostcode() + "\n" +
                        orderDetailResponse.getData().getShippingAddress().getCountry() + "\n" +
                        "Phone No. : " + orderDetailResponse.getData().getShippingAddress().getTelephone());

        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetailsAct.this);
        detailsBinding.orderItemDetailRcy.setLayoutManager(layoutManager);
        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(OrderDetailsAct.this, orderDetailResponse.getData().getQuoteItem());
        detailsBinding.orderItemDetailRcy.setAdapter(orderDetailAdapter);
        detailsBinding.orderItemDetailRcy.setHasFixedSize(true);
        detailsBinding.orderItemDetailRcy.setItemViewCacheSize(20);
        detailsBinding.orderItemDetailRcy.setDrawingCacheEnabled(true);
        detailsBinding.orderItemDetailRcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        detailsBinding.orderItemDetailRcy.setNestedScrollingEnabled(false);
    }
}