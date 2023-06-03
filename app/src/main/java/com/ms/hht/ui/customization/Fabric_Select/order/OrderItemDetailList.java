package com.ms.hht.ui.customization.Fabric_Select.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.ms.hht.R;
import com.ms.hht.data.response.OrderDetailResponse;
import com.ms.hht.databinding.ActivityOrderItemDetailListBinding;

import java.util.List;

public class OrderItemDetailList extends AppCompatActivity {

    ActivityOrderItemDetailListBinding orderItemDetailListBinding;

    public static List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> orderedItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        orderItemDetailListBinding = ActivityOrderItemDetailListBinding.inflate(getLayoutInflater());
        setContentView(orderItemDetailListBinding.getRoot());
        orderItemDetailListBinding.backImage.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setorderedItemDetailList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setorderedItemDetailList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderItemDetailList.this);
        orderItemDetailListBinding.orderitemREcy.setLayoutManager(layoutManager);
        OrderItemDetailAdapter orderItemAdapter = new OrderItemDetailAdapter(OrderItemDetailList.this, orderedItemList); // ReorderSelectedItemsActivity.this,reorderItemList send this
        orderItemDetailListBinding.orderitemREcy.setAdapter(orderItemAdapter);
        orderItemDetailListBinding.orderitemREcy.setHasFixedSize(true);
        orderItemDetailListBinding.orderitemREcy.setItemViewCacheSize(20);
        orderItemDetailListBinding.orderitemREcy.setDrawingCacheEnabled(true);
        orderItemDetailListBinding.orderitemREcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        orderItemDetailListBinding.orderitemREcy.setNestedScrollingEnabled(false);
    }
}