package com.ms.hht.ui.customization.Fabric_Select.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.ms.hht.R;
import com.ms.hht.data.adapter.ReOrderItemAdapter;
import com.ms.hht.data.request.ReorderCartRequest;
import com.ms.hht.data.response.AddToCartResponse;
import com.ms.hht.data.response.OrderDetailResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityReorderSelectedItemsBinding;
import com.ms.hht.utils.CommFunc;

import com.ms.hht.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;

public class ReorderSelectedItemsActivity extends AppCompatActivity {

    ActivityReorderSelectedItemsBinding reorderSelectedItemsBinding;
    public static List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> reorderItemList;
    AddToCartResponse addToCartResponse;
    List<Integer> selectedProductArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        reorderSelectedItemsBinding = ActivityReorderSelectedItemsBinding.inflate(getLayoutInflater());
        setContentView(reorderSelectedItemsBinding.getRoot());

        for (int i = 0; i < reorderItemList.size(); i++) {
            selectedProductArray.add(reorderItemList.get(i).getQuoteItemDataId());
        }

        reorderSelectedItemsBinding.backImage.setOnClickListener(view -> {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } );

        reorderSelectedItemsBinding.addTOCArtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSelectedProductTOCArt();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setReorderItemList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setReorderItemList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReorderSelectedItemsActivity.this);
        reorderSelectedItemsBinding.reorderitemREcy.setLayoutManager(layoutManager);
        ReOrderItemAdapter reorderItemAdapter = new ReOrderItemAdapter(ReorderSelectedItemsActivity.this, reorderItemList); // ReorderSelectedItemsActivity.this,reorderItemList send this
        reorderSelectedItemsBinding.reorderitemREcy.setAdapter(reorderItemAdapter);
        reorderSelectedItemsBinding.reorderitemREcy.setHasFixedSize(true);
        reorderSelectedItemsBinding.reorderitemREcy.setItemViewCacheSize(20);
        reorderSelectedItemsBinding.reorderitemREcy.setDrawingCacheEnabled(true);
        reorderSelectedItemsBinding.reorderitemREcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        reorderSelectedItemsBinding.reorderitemREcy.setNestedScrollingEnabled(false);
    }

    private void addSelectedProductTOCArt() {
        if (InternetConnection.isConnected(ReorderSelectedItemsActivity.this)) {
            CommFunc.ShowProgressbar(this);
            ReorderCartRequest reorderCartRequest = new ReorderCartRequest(selectedProductArray);

            APICallList.ReorderAddToCart(reorderCartRequest, "add product list to cart", response, ReorderSelectedItemsActivity.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("add product list to cart")) {
                CommFunc.DismissDialog();
                addToCartResponse = (AddToCartResponse) o;
                if (addToCartResponse != null) {
                    if (addToCartResponse.getCode() == 1) {
                        startActivity(new Intent(ReorderSelectedItemsActivity.this, OrderCartAct.class));
                        OrderCartAct.ACTIVITY_FROM = "";
//                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        CommFunc.ShowStatusPop(ReorderSelectedItemsActivity.this, addToCartResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(ReorderSelectedItemsActivity.this, getResources().getString(R.string.try_again), false);
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(ReorderSelectedItemsActivity.this, message, false);
        }
    };
}