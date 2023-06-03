package com.ms.hht.ui.customization.Fabric_Select.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ms.hht.R;
import com.ms.hht.data.response.CartListResponse;

import java.util.List;

public class CartProdDesc extends AppCompatActivity {

    ImageView backImage2;
    RecyclerView reorderitemREcy;
    public static List<CartListResponse.Data.QuoteItemItem.ItemDataItem> CartProdDescList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_prod_desc);

        backImage2 = findViewById(R.id.backImage2);
        reorderitemREcy = findViewById(R.id.reorderitemREcy);

        backImage2.setOnClickListener(view -> finish());

        LinearLayoutManager layoutManager = new LinearLayoutManager(CartProdDesc.this);
        reorderitemREcy.setLayoutManager(layoutManager);
        CartProdDescAdapter cartProdAdapter = new CartProdDescAdapter(CartProdDesc.this, CartProdDescList);
        reorderitemREcy.setAdapter(cartProdAdapter);
        reorderitemREcy.setHasFixedSize(true);
        reorderitemREcy.setItemViewCacheSize(20);
        reorderitemREcy.setDrawingCacheEnabled(true);
        reorderitemREcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        reorderitemREcy.setNestedScrollingEnabled(false);
    }
}