package com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter.AccentAdapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.response.AccentResponse;

import java.util.List;

public class AccentPopUpRecyItemAdapter extends RecyclerView.Adapter<AccentPopUpRecyItemAdapter.MyViewHolder> {

    Context context;
    List<AccentResponse.DataItem.ChoicesItem.AdditionalInfosItem.ImagesItem> images;

    public AccentPopUpRecyItemAdapter(Context context, List<AccentResponse.DataItem.ChoicesItem.AdditionalInfosItem.ImagesItem> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public AccentPopUpRecyItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflater.inflate(R.layout.accent_popup_layout, parent, false);
        return new AccentPopUpRecyItemAdapter.MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull AccentPopUpRecyItemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.fabricName.setText(images.get(position).getDisplayText());

        if (images.get(position).isAccentImageSelected()){
            holder.fabricCard.setBackgroundResource(R.drawable.blue_border_background);
        }
        else{
            holder.fabricCard.setBackgroundResource(R.drawable.white_background_low_border);
        }
        Glide.with(context).load(Uri.parse(images.get(position).getDisplayImage())).into(holder.fabricImages);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView fabricImages;
        RelativeLayout fabricCard;
        TextView fabricName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fabricImages = itemView.findViewById(R.id.fabricImages);
            fabricCard = itemView.findViewById(R.id.fabricCard);
            fabricName = itemView.findViewById(R.id.fabricName);
        }
    }
}