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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.response.StyleResponse;

import java.util.List;

public class StyleCArdAdapter extends RecyclerView.Adapter<StyleCArdAdapter.MyViewHolder> {

    Context context;
    List<StyleResponse.DataItem.ChoicesItem> choicesItems;


    public StyleCArdAdapter(Context context, List<StyleResponse.DataItem.ChoicesItem> choicesItems) {
        this.context = context;
        this.choicesItems = choicesItems;
    }

    @NonNull
    @Override
    public StyleCArdAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflater.inflate(R.layout.fabric_selected_item_file, parent, false);
        return new StyleCArdAdapter.MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull StyleCArdAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.StyleCardName.setText(choicesItems.get(position).getName());
        Glide.with(context).load(Uri.parse(choicesItems.get(position).getImage()))
                .into(holder.styleImage);

        if (choicesItems.get(position).getIsSelected() == 1) {
            holder.styleImage.setBackgroundResource(R.drawable.white_selected_background_low_border);
        } else {
            holder.styleImage.setBackgroundResource(R.drawable.white_background_low_border);
        }

    }

    @Override
    public int getItemCount() {
        return choicesItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout styleSelectedCArd;
        TextView StyleCardName;
        ImageView styleImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            styleSelectedCArd = itemView.findViewById(R.id.styleSelectedCArd);
            styleImage = itemView.findViewById(R.id.fabric_selected_item_Image);
            StyleCardName = itemView.findViewById(R.id.fabric_selected_item_name);
        }
    }
}
