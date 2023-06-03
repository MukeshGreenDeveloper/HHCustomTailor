package com.ms.hht.data.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ms.hht.R;
import com.ms.hht.data.response.FabricFilterListResponse;
import com.ms.hht.data.response.FabricFilterResponse;
import com.ms.hht.data.response.SubCategoryResponse;
import com.ms.hht.ui.description.ProductDescAct;
import com.ms.hht.utils.RecyclerTouchListener;

import java.util.List;

public class FilterPopUpAdapter extends RecyclerView.Adapter<FilterPopUpAdapter.MyViewHolder> {

    Context contextC;
    List<FabricFilterListResponse> data;


    public FilterPopUpAdapter(Context context, List<FabricFilterResponse.DataItem> data) {
    }

    @NonNull
    @Override
    public FilterPopUpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) contextC.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3= inflater.inflate(R.layout.filter_popup_recy_item_file,parent,false);
        return new FilterPopUpAdapter.MyViewHolder(view3);

    }

    @Override
    public void onBindViewHolder(@NonNull FilterPopUpAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.filterHeadingName.setText(data.get(position).getData().get(position).getFiltername());

        holder.FilterValueRecy.addOnItemTouchListener(new RecyclerTouchListener(contextC,
                holder.FilterValueRecy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FabricFilterOptionsAdapter.filterOptionAdapterPosition = 1;
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView filterHeadingName;
        RecyclerView FilterValueRecy;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            filterHeadingName = itemView.findViewById(R.id.filterHeadingName);
            FilterValueRecy = itemView.findViewById(R.id.FilterValueRecy);
        }
    }
}