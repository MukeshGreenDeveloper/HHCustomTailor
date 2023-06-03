package com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter.AccentAdapter;

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
import com.ms.hht.data.response.AccentResponse;

import java.util.List;

public class AccentAdapter extends RecyclerView.Adapter<AccentAdapter.MyViewHolder> {
    Context context;
    List<AccentResponse.DataItem> data;

    public AccentAdapter(Context context, List<AccentResponse.DataItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AccentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflater.inflate(R.layout.accent_adapter_item_file, parent, false);
        return new AccentAdapter.MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull AccentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.accentOPtionName.setText(data.get(position).getName());

        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        holder.AccentOptionREcy.setLayoutManager(layoutManager);
        AccentOPtionAdapter accentOPtionAdapter = new AccentOPtionAdapter(context, data.get(position).getChoices());
        holder.AccentOptionREcy.setAdapter(accentOPtionAdapter);
        holder.AccentOptionREcy.setHasFixedSize(true);
        holder.AccentOptionREcy.setItemViewCacheSize(20);
        holder.AccentOptionREcy.setDrawingCacheEnabled(true);
        holder.AccentOptionREcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        holder.AccentOptionREcy.setNestedScrollingEnabled(false);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView accentOPtionName;
        RecyclerView AccentOptionREcy;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            accentOPtionName = itemView.findViewById(R.id.accentOPtionName);
            AccentOptionREcy = itemView.findViewById(R.id.AccentOptionREcy);
        }
    }
}