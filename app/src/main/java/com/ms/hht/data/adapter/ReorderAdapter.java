package com.ms.hht.data.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.response.OrderDetailResponse;

import java.util.List;

public class ReorderAdapter extends RecyclerView.Adapter<ReorderAdapter.MyViewHolder> {
    Context contextR;
    List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> itemList;
    String ItemDatavalue = "";

    public ReorderAdapter(Context contextR, List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> itemList) {
        this.contextR = contextR;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) contextR.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3 = inflater.inflate(R.layout.reorder_item_file, parent, false);
        return new MyViewHolder(view3);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.selectItemName.setText(itemList.get(position).getPieceName());
        if (itemList.get(position).isSelectedItem()){
            holder.SelectItemcheckBox.setImageResource(R.drawable.check_box);
        }else {
            holder.SelectItemcheckBox.setImageResource(R.drawable.uncheck_box);
        }

        holder.reorder_items_data_price.setText("$ " + itemList.get(position).getPiecePrice());

        for (int i = 0; i < itemList.get(position).getSelectedValue().size(); i++) {
            ItemDatavalue = ItemDatavalue + itemList.get(position).getSelectedValue().get(i).getName()
                    + " : " + itemList.get(position).getSelectedValue().get(i).getValue() + "\n";
        }
        if (itemList.get(position).getLining() != null){
            if (!itemList.get(position).getLining().isEmpty()){
                ItemDatavalue = ItemDatavalue + "\nLining" + ": " + itemList.get(position).getLining()+"\n";
            }
        }

        if (itemList.get(position).getAdditionalInfo().getValue().size() > 0) {
            ItemDatavalue = ItemDatavalue + "\n" + itemList.get(position).getAdditionalInfo().getHeading() + ": ";
            for (int i =0; i<itemList.get(position).getAdditionalInfo().getValue().size(); i++){
                ItemDatavalue = ItemDatavalue + itemList.get(position).getAdditionalInfo().getValue().get(i).toString();
            }
            ItemDatavalue = ItemDatavalue + "\n";
        }

        if (!itemList.get(position).getComment().isEmpty()){
            ItemDatavalue = ItemDatavalue + "\nComment" + ": " + itemList.get(position).getComment()+"\n";
        }
        holder.reorder_items_data.setText(ItemDatavalue);
        ItemDatavalue = "";

        holder.selectItemImage.removeAllViews();
        for (OrderDetailResponse.Data.QuoteItemItem.FeatureImages.FrontItem frontItem : itemList.get(position).getFeatureImages().getFront()) {
            RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            ImageView image = new ImageView(contextR);
            image.setLayoutParams(vp);
            holder.selectItemImage.addView(image);
            Glide.with(contextR).load(frontItem.getImage()).into(image);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView selectItemName, reorder_items_data, reorder_items_data_price;
        ImageView SelectItemcheckBox ;
        RelativeLayout selectItemImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            selectItemName = itemView.findViewById(R.id.selectItemName);
            reorder_items_data = itemView.findViewById(R.id.selectItemsData);
            reorder_items_data_price = itemView.findViewById(R.id.selectItemsPrice);
            SelectItemcheckBox = itemView.findViewById(R.id.checkSelectItems);
            selectItemImage = itemView.findViewById(R.id.SelectImage2);
        }
    }
}