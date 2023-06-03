package com.ms.hht.ui.measure;


import static com.ms.hht.ui.measure.MeasurementHistoryAct.MeasurementHistoryActivityComingFrom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.response.MeasurementHistoryResponse;
import com.ms.hht.ui.customization.Fabric_Select.order.CartDetailsActivity;
import com.ms.hht.ui.payment.PaymentAct;

import java.util.List;

public class MeasurementHistoryAdapter extends RecyclerView.Adapter<MeasurementHistoryAdapter.MyViewHolder> {

    Context context;
    List<MeasurementHistoryResponse.DataItem> data;

    public MeasurementHistoryAdapter(Context context, List<MeasurementHistoryResponse.DataItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflatert = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflatert.inflate(R.layout.measurement_history_item_file, parent, false);
        return new MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.nameTF.setText(data.get(position).getName());
        holder.eyeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(context, MeasurementDetailListActivity.class);
                MeasurementDetailListActivity.measurementDetailList = data.get(position).getMeasurement();
                MeasurementDetailListActivity.MeasurementHistoryActivityComingFrom = MeasurementHistoryActivityComingFrom;
                MeasurementDetailListActivity.SelectedMeasurementId = position;
                PaymentAct.cart_measurement_id_in_paymentAct = data.get(position).getId();
                context.startActivity(i1);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        if (MeasurementHistoryActivityComingFrom.equalsIgnoreCase("cart")){
            holder.measurementHistoryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for ( int i=0; i< data.size(); i++){
                        if (i == position){
                            data.get(position).setChecked(true);
                            MeasurementHistoryAct.SelectedMeasurementId =  position;
                            PaymentAct.cart_measurement_id_in_paymentAct = data.get(position).getId();
                        }
                        else {
                            data.get(i).setChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }


        if (data.get(position).isChecked()) {
            holder.measurementHistoryCard.setBackgroundResource(R.drawable.white_selected_background_low_border);
            CartDetailsActivity.measurementVALUEToPlaceOrder = data.get(position).getMeasurement();
        }
        else {
            holder.measurementHistoryCard.setBackgroundResource(R.drawable.white_background_low_border);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTF;
        TextView dateTF;
        RelativeLayout measurementHistoryCard;
        ImageView eyeImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTF = itemView.findViewById(R.id.nameTF);
            dateTF = itemView.findViewById(R.id.dateTF);
            eyeImg = itemView.findViewById(R.id.eyeImg);
            measurementHistoryCard = itemView.findViewById(R.id.measurementHistoryCard);

        }
    }
}
