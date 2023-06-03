package com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.response.LiningResponse;

import java.util.List;

public class LiningAdapter extends RecyclerView.Adapter<LiningAdapter.MyViewHolder> {

    Context context;
    List<LiningResponse.DataItem> data;

    public LiningAdapter(Context context, List<LiningResponse.DataItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public LiningAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflater.inflate(R.layout.fabric_adapter_item_file, parent, false);
        return new LiningAdapter.MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull LiningAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(Uri.parse(data.get(position).getImage())).into(holder.fabricImages);
        holder.fabricName.setText(data.get(position).getDescription());
        if (data.get(position).isLiningSelected()){
            holder.fabricCard.setBackgroundResource(R.drawable.blue_border_background);
        }else {
            holder.fabricCard.setBackgroundResource(R.drawable.white_background_low_border);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView fabricImages;
        RelativeLayout fabricCard;
        TextView fabricName;
        CardView fabricCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fabricCardView = itemView.findViewById(R.id.fabricCardView);
            fabricImages = itemView.findViewById(R.id.fabricImages);
            fabricCard = itemView.findViewById(R.id.fabricCard);
            fabricName = itemView.findViewById(R.id.fabricName);
        }
    }
}