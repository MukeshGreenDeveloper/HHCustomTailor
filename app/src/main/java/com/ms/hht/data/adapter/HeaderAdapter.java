package com.ms.hht.data.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.response.TypeResponse;

import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.MyViewHolder> {

    public static String typeStyleValue = "";
    private Context context;
    private List<TypeResponse.DataItem> data;
     public static int adapterPosition = 0;

    public HeaderAdapter(Context context, List<TypeResponse.DataItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.header_item, parent, false);
        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderAdapter.MyViewHolder holder, int position) {

        holder.headerText.setText(data.get(position).getName());

        if (data.get(position).isSelected()){
            holder.headerText.setTextColor(Color.parseColor("#1294CC"));
        }else {
            holder.headerText.setTextColor(Color.parseColor("#333031"));
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView headerText;
        View headerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            headerText = itemView.findViewById(R.id.headerText);
            headerView = itemView.findViewById(R.id.headerView);

        }
    }
}
