package com.ms.hht.data.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.response.OrderListResponse;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderDetailsAct;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {
    Context context;
    List<OrderListResponse.DataItem> orderList;

    public OrderListAdapter(Context context, List<OrderListResponse.DataItem> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.order_in_progrees_rcy, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.orderDetailList_Data.setText(
                "ORDER NO. :  " + orderList.get(position).getIncrementId() + "\n\n" +
                "DATE :  " + orderList.get(position).getCreatedAt() + "\n\n" +
                "SHIP TO :  " + orderList.get(position).getShipto() + "\n\n" +
                "ORDER TOTAL :  $ " + orderList.get(position).getTotalPaid() + "\n"
        );



        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, OrderDetailsAct.class);
                OrderDetailsAct.ORDER_ID = orderList.get(position).getOrderId();
                OrderDetailsAct.INCREMENT_ID = orderList.get(position).getIncrementId();
                context.startActivity(intent1);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderDetailList_Data, orderStatus, date;
        RelativeLayout cardLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDetailList_Data = itemView.findViewById(R.id.orderDetailList_Data);
//            orderStatus = itemView.findViewById(R.id.status);
//            date = itemView.findViewById(R.id.date);
            cardLayout = itemView.findViewById(R.id.cardLayout);

        }
    }
}
