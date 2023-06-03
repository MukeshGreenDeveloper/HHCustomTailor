package com.ms.hht.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;

import java.util.List;

public class CustomzationAdapter extends RecyclerView.Adapter<CustomzationAdapter.MyViewHolder> {
    Context contxt;
//    List<ProductImage> productImages;
List<String> productImages;
//
//    public CustomzationAdapter(Context contxt, List<ProductImage> productImages) {
//        this.contxt = contxt;
//        this.productImages = productImages;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) contxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3 = layoutInflater.inflate(R.layout.custom_rcy,parent,false);
        return new MyViewHolder(view3);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.images.setImageResource(productImages.get(position).getProductImage());
    }

    @Override
    public int getItemCount() {
        return productImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView images;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            images = itemView.findViewById(R.id.customImg);

        }
    }
}
