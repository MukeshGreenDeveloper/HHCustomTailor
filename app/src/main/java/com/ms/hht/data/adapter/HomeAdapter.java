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
import com.ms.hht.data.response.StylelookDataResponse;
import com.ms.hht.ui.Sub_Category.SubCategoryActivity;
import com.ms.hht.ui.description.ProductDescAct;
import com.ms.hht.utils.SessionManager;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    Context context;
    List<StylelookDataResponse.DataItem> dataItem;
    SessionManager sessionManager;

    public HomeAdapter(Context context, List<StylelookDataResponse.DataItem> data) {
        this.context = context;
        this.dataItem = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.home_cate_items, parent, false);
        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        sessionManager = new SessionManager(context);
        if (dataItem.get(position).getImage() != null) {
            Glide.with(context)
                    .load(dataItem.get(position).getImage())
                    .fitCenter()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .into(holder.productImage);
        }

        holder.categoryTitleText.setText(dataItem.get(position).getName());

        holder.buyNow.setOnClickListener(view -> {
            if ((Integer.parseInt(sessionManager.getTypeId())) == 2) {
                ProductDescAct.ITEM_ID_TO_CAll_PDAPI = dataItem.get(position).getItemId();
                Intent intent6 = new Intent(context, ProductDescAct.class);
                context.startActivity(intent6);

            } else if ((Integer.parseInt(sessionManager.getTypeId())) == 1) {
                Intent i1 = new Intent(context, SubCategoryActivity.class);
                SubCategoryActivity.HEADING =  dataItem.get(position).getName();
                SubCategoryActivity.Category_ID_FOR_API = dataItem.get(position).getCategoryId();
                context.startActivity(i1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitleText;
        ImageView productImage;
        Button buyNow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTitleText = itemView.findViewById(R.id.categoryTitleText);
            productImage = itemView.findViewById(R.id.productImage);
            buyNow = itemView.findViewById(R.id.detailButton);
        }
    }
}




