package com.ms.hht.ui.measure;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.response.MeasurementHistoryResponse;

import java.util.List;

public class MeasurementDetailAdapter extends RecyclerView.Adapter<MeasurementDetailAdapter.MyViewHolder> {

    Context context;
    List<MeasurementHistoryResponse.MeasurementItem> data;
    Dialog dialog;

    public MeasurementDetailAdapter(Context context, List<MeasurementHistoryResponse.MeasurementItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflatert = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflatert.inflate(R.layout.measurement_detail_item_file, parent, false);
        return new MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementDetailAdapter.MyViewHolder holder, int position) {
        holder.nameTF.setText(data.get(position).getPart());
        holder.measurementValue.setText(data.get(position).getValue() + " cm");
        Glide.with(context).load(data.get(position).getPointName()).into(holder.measurementImage);
        dialog = new Dialog(context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTF, measurementValue;
        ImageView measurementImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTF = itemView.findViewById(R.id.nameTF);
            measurementValue = itemView.findViewById(R.id.measuremntValue);
            measurementImage = itemView.findViewById(R.id.measurementImg);
        }
    }
}
