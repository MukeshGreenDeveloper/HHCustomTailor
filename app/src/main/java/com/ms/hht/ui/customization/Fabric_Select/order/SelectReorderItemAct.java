package com.ms.hht.ui.customization.Fabric_Select.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.ms.hht.R;
import com.ms.hht.data.adapter.ReorderAdapter;
import com.ms.hht.data.response.OrderDetailResponse;
import com.ms.hht.databinding.ActReorderBinding;
import com.ms.hht.ui.customization.CustomizationProcess.Activity.CustomizationAct;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class SelectReorderItemAct extends AppCompatActivity implements View.OnClickListener {

    public static List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> itemData;
    private List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> reorderItemList = new ArrayList<>();
    ActReorderBinding reorderBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reorderBinding = ActReorderBinding.inflate(getLayoutInflater());
        setContentView(reorderBinding.getRoot());
        reorderBinding.backImage2.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        reorderBinding.ProceedBtn.setOnClickListener(this);
        setItemList();
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
        if (reorderItemList != null && reorderItemList.size()>0){
            reorderItemList.clear();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ProceedBtn:

                for (int i = 0; i < itemData.size(); i++) {
                    if (itemData.get(i).isSelectedItem()) {
                        reorderItemList.add(itemData.get(i));
                    }
                }

                if (reorderItemList.size() > 0) {
                    Intent intent0 = new Intent(SelectReorderItemAct.this, ReorderSelectedItemsActivity.class);
                    ReorderSelectedItemsActivity.reorderItemList = reorderItemList;
                    startActivity(intent0);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    CommFunc.ShowStatusPop(SelectReorderItemAct.this, "Please select at least one item to proceed.", false);
                }
                break;
        }
    }

    private void setItemList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectReorderItemAct.this);
        reorderBinding.reorderRcy.setLayoutManager(layoutManager);
        ReorderAdapter reorderAdapter = new ReorderAdapter(SelectReorderItemAct.this, itemData);
        reorderBinding.reorderRcy.setAdapter(reorderAdapter);
        reorderBinding.reorderRcy.setHasFixedSize(true);
        reorderBinding.reorderRcy.setItemViewCacheSize(20);
        reorderBinding.reorderRcy.setDrawingCacheEnabled(true);
        reorderBinding.reorderRcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        reorderBinding.reorderRcy.setNestedScrollingEnabled(false);

        reorderBinding.reorderRcy.addOnItemTouchListener(new RecyclerTouchListener(SelectReorderItemAct.this,
                reorderBinding.reorderRcy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

             if (itemData.get(position).isSelectedItem()){
                 itemData.get(position).setSelectedItem(false);
             }
             else {
                 itemData.get(position).setSelectedItem(true);

             }
                reorderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }
}