package com.ms.hht.data.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ms.hht.R;
import com.ms.hht.data.response.SubCategoryResponse;
import com.ms.hht.ui.description.ProductDescAct;
import com.ms.hht.utils.SessionManager;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {

    Context contextC;
    List<SubCategoryResponse.DataItem> imageList;
    SessionManager sessionManager;

    public SubCategoryAdapter(Context contextC, List<SubCategoryResponse.DataItem> imageList) {
        this.contextC = contextC;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) contextC.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3= inflater.inflate(R.layout.product_recy,parent,false);
        return new MyViewHolder(view3);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        sessionManager = new SessionManager(contextC);
        Glide.with(contextC)
                .load(imageList.get(position).getImage())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(holder.images);

        holder.productName2.setText(imageList.get(position).getName() + "");

        holder.buyNowBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(contextC, ProductDescAct.class);
                sessionManager.setItemId(imageList.get(position).getItemId());
                System.out.println("item id at sub category adapter class +++++++++++++"+imageList.get(position).getItemId());
                ProductDescAct.ITEM_ID_TO_CAll_PDAPI = imageList.get(position).getItemId();
                contextC.startActivity(intent11);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView productName2;
        Button  buyNowBtn1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buyNowBtn1 = itemView.findViewById(R.id.buyNowBtn1);
            images = itemView.findViewById(R.id.productImage2);
            productName2 = itemView.findViewById(R.id.productName2);

        }
    }
}
