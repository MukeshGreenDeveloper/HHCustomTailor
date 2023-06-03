package com.ms.hht.data.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.response.GETMSMeasurementResponse;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.SessionManager;


import java.util.List;

public class DisplayMeasurementResultAdapter extends RecyclerView.Adapter<DisplayMeasurementResultAdapter.MyViewHolder> {

    Context context;
    List<GETMSMeasurementResponse.DataItem> measurementItems;

    public DisplayMeasurementResultAdapter(Context context, List<GETMSMeasurementResponse.DataItem> measurementItems) {
        this.context = context;
        this.measurementItems = measurementItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.measuement_grid_layout, parent, false);
        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.girthValue.setText(measurementItems.get(position).getValueIninch());
        holder.measurementGirth.setText(measurementItems.get(position).getDisplayName());

        if (ComUserProfileData.getmeasurementGender().equalsIgnoreCase("male")) {
            Glide.with(context)
                    .load("https://commonms.s3.ap-south-1.amazonaws.com/male_small_icon/" + measurementItems.get(position).getPointName())
                    .into(holder.measureImage);
        } else {
            Glide.with(context)
                    .load("https://commonms.s3.ap-south-1.amazonaws.com/female_small_icon/" + measurementItems.get(position).getPointName())
                    .into(holder.measureImage);
        }

    }

    @Override
    public int getItemCount() {
        return measurementItems.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView measurementGirth, girthValue;
        RelativeLayout measureView;
        ImageView measureImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            measurementGirth = itemView.findViewById(R.id.nameTF);
            measureView = itemView.findViewById(R.id.measureView);
            girthValue = itemView.findViewById(R.id.measuremntValue);
            measureImage = itemView.findViewById(R.id.measurementImg);

        }
    }
}
