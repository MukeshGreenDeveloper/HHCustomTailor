package com.ms.hht.data.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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
import com.ms.hht.data.response.FabricSelectionGetPieceTypesResponse;

import java.util.List;

public class CustomizeTextAdapter extends RecyclerView.Adapter<CustomizeTextAdapter.MyViewHolder> {

    Context context;
    List<FabricSelectionGetPieceTypesResponse.DataItem> data;

    public CustomizeTextAdapter(Context context, List<FabricSelectionGetPieceTypesResponse.DataItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view4 = layoutInflater.inflate(R.layout.customize_text_rcy, parent, false);
        return new MyViewHolder(view4);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tabTitle.setText(data.get(position).getName());
        if (data.get(position).isGetPieceSelected()) {
            holder.textdataView.setBackgroundResource(R.drawable.selected_boreder_background);
        } else {
            holder.textdataView.setBackgroundResource(R.drawable.border_background_layout);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tabTitle;
        RelativeLayout textdataView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tabTitle = itemView.findViewById(R.id.tabTitle);
            textdataView = itemView.findViewById(R.id.textdataView);
        }
    }
}
