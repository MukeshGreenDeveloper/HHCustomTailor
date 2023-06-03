package com.ms.hht.data.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.response.OrderDetailResponse;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderItemDetailList;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    Context context;
    List<OrderDetailResponse.Data.QuoteItemItem> quoteItem;

    public OrderDetailAdapter(Context context, List<OrderDetailResponse.Data.QuoteItemItem> quoteItem) {
        this.context = context;
        this.quoteItem = quoteItem;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.order_detail_item_file, parent, false);

        return new OrderDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.OrderedItemName.setText(quoteItem.get(position).getItemName());
        holder.OrderedItemPrice.setText("$ "+quoteItem.get(position).getItemTotal());
        holder.viewDetailBtn.setOnClickListener(view -> {
            Intent i = new Intent(context, OrderItemDetailList.class);
            OrderItemDetailList.orderedItemList = quoteItem.get(position).getItemData();
            context.startActivity(i);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    @Override
    public int getItemCount() {
        return quoteItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView OrderedItemName,OrderedItemPrice,viewDetailBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            OrderedItemName = itemView.findViewById(R.id.ItemName);
            OrderedItemPrice = itemView.findViewById(R.id.priceText);
            viewDetailBtn = itemView.findViewById(R.id.viewDetailstxt);

        }
    }
}
