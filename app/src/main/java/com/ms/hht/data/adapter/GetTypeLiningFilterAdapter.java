package com.ms.hht.data.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.response.LiningFilterOptionResponse;
import com.ms.hht.ui.customization.Fabric_Select.ViewAdapter.ColorPatternAdapterForLining;

import java.util.List;

public class GetTypeLiningFilterAdapter extends RecyclerView.Adapter<GetTypeLiningFilterAdapter.MyViewHolder> {

    Context context;
    List<LiningFilterOptionResponse.DataItem> data;
    public static int filterOptionAdapterPosition = -1;

    public GetTypeLiningFilterAdapter(Context context, List<LiningFilterOptionResponse.DataItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public GetTypeLiningFilterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3 = inflater.inflate(R.layout.filter_recycler_layout, parent, false);
        return new GetTypeLiningFilterAdapter.MyViewHolder(view3);

    }

    @Override
    public void onBindViewHolder(@NonNull GetTypeLiningFilterAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String cap = data.get(position).getFiltername().substring(0, 1).toUpperCase() + data.get(position).getFiltername().substring(1);
        holder.headingText.setText(cap);

        GridLayoutManager layoutManager1 = new GridLayoutManager(context,2);
        holder.colorRecycler.setLayoutManager(layoutManager1);
        ColorPatternAdapterForLining patternAdapterClass = new ColorPatternAdapterForLining(context, data.get(position).getOptions());
        holder.colorRecycler.setAdapter(patternAdapterClass);
        holder.colorRecycler.setHasFixedSize(true);
        holder.colorRecycler.setItemViewCacheSize(20);
        holder.colorRecycler.setDrawingCacheEnabled(true);
        holder.colorRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        holder.colorRecycler.setNestedScrollingEnabled(false);
//        holder.checkBox.setText(data.get(position).getData().get(position).getOptions().get(position).getColorName());
//        if (filterOptionAdapterPosition ==position){
//            holder.checkBox.setChecked(true);
//        }else{
//            holder.checkBox.setChecked(false);
//        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //        CheckBox checkBox;
        RecyclerView colorRecycler;
        TextView headingText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            headingText = itemView.findViewById(R.id.headingText);
            colorRecycler = itemView.findViewById(R.id.colorRecycler);
        }
    }
}
