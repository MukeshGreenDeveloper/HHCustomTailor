package com.ms.hht.ui.customization.Fabric_Select.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ms.hht.R;
import com.ms.hht.data.response.CartListResponse;
import java.util.List;

public class CartProdDescAdapter extends RecyclerView.Adapter<CartProdDescAdapter.MyViewHolder> {

    Context contextR;
    List<CartListResponse.Data.QuoteItemItem.ItemDataItem> cartProdDescList;
    String ItemDatavalue = "";

    public CartProdDescAdapter(Context contextR, List<CartListResponse.Data.QuoteItemItem.ItemDataItem> cartProdDescList) {
        this.contextR = contextR;
        this.cartProdDescList = cartProdDescList;
    }

    @NonNull
    @Override
    public CartProdDescAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) contextR.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3 = inflater.inflate(R.layout.selected_reorder_item_file, parent, false);
        return new CartProdDescAdapter.MyViewHolder(view3);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProdDescAdapter.MyViewHolder holder, int position) {

        System.out.println("CART LIST RESPONSE DETAILS*********"+new Gson().toJson(cartProdDescList));

        holder.selectItemName.setText(cartProdDescList.get(position).getPieceName());

        for (int i = 0; i < cartProdDescList.get(position).getSelectedValue().size(); i++) {
            ItemDatavalue = ItemDatavalue + cartProdDescList.get(position).getSelectedValue().get(i).getName()
                    + " : " + cartProdDescList.get(position).getSelectedValue().get(i).getValue()+"\n";
        }

        if (cartProdDescList.get(position).getAdditionalInfo().getValue().size() > 0) {
            ItemDatavalue = ItemDatavalue + "\n" + cartProdDescList.get(position).getAdditionalInfo().getHeading() + ": ";
            for (int i =0; i<cartProdDescList.get(position).getAdditionalInfo().getValue().size(); i++){
                ItemDatavalue = ItemDatavalue + cartProdDescList.get(position).getAdditionalInfo().getValue().get(i);
            }
            ItemDatavalue = ItemDatavalue+"\n";
        }

        if (!cartProdDescList.get(position).getComment().isEmpty()){
            ItemDatavalue = ItemDatavalue + "\nComment" + ": " + cartProdDescList.get(position).getComment()+"\n";
        }
        holder.reorder_items_data.setText(ItemDatavalue);
        ItemDatavalue = "";


        holder.CartSelectImage.removeAllViews();

        for (CartListResponse.Data.QuoteItemItem.ItemDataItem.FeatureImages.FrontItem frontItem : cartProdDescList.get(position).getFeatureImages().getFront()) {
            RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            ImageView image = new ImageView(contextR);
            image.setForegroundGravity(1);
            image.setLayoutParams(vp);
            holder.CartSelectImage.addView(image);
            Glide.with(contextR).load(frontItem.getImage()).into(image);
        }

        holder.reorder_items_data_price.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return cartProdDescList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView selectItemName, reorder_items_data, reorder_items_data_price;
        RelativeLayout CartSelectImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            selectItemName = itemView.findViewById(R.id.selectItemName);
            reorder_items_data = itemView.findViewById(R.id.selectItemsData);
            reorder_items_data_price = itemView.findViewById(R.id.selectItemsPrice);
            CartSelectImage = itemView.findViewById(R.id.SelectImage);
        }
    }
}