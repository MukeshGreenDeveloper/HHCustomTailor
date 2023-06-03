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
import com.ms.hht.R;
import com.ms.hht.data.response.OrderDetailResponse;

import java.util.List;

public class OrderItemDetailAdapter extends RecyclerView.Adapter<OrderItemDetailAdapter.MyViewHolder> {

    Context contextR;
    List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> selectedDataItems;
    String ItemDatavalue = "";

    public OrderItemDetailAdapter(Context contextR, List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> selectedDataItems) {
        this.contextR = contextR;
        this.selectedDataItems = selectedDataItems;
    }

    @NonNull
    @Override
    public OrderItemDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) contextR.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3 = inflater.inflate(R.layout.order_item_detail_itemfile, parent, false);
        return new OrderItemDetailAdapter.MyViewHolder(view3);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemDetailAdapter.MyViewHolder holder, int position) {

        holder.ordered_ItemName.setText(selectedDataItems.get(position).getPieceName());
        holder.ordered_items_data_price.setVisibility(View.GONE);
//        holder.ordered_items_data_price.setText("$ " + selectedDataItems.get(position).getPiecePrice());

        for (int i = 0; i < selectedDataItems.get(position).getSelectedValue().size(); i++) {
            ItemDatavalue = ItemDatavalue + selectedDataItems.get(position).getSelectedValue().get(i).getName()
                    + " : " + selectedDataItems.get(position).getSelectedValue().get(i).getValue()+"\n";
        }
        if (selectedDataItems.get(position).getLining() != null) {
            if (!selectedDataItems.get(position).getLining().isEmpty()) {
                ItemDatavalue = ItemDatavalue + "\nLining" + ": " + selectedDataItems.get(position).getLining()+"\n";
            }
        }
        if (selectedDataItems.get(position).getAdditionalInfo().getValue().size() > 0) {
             ItemDatavalue = ItemDatavalue + "\n" + selectedDataItems.get(position).getAdditionalInfo().getHeading() + ": ";
            for (int i =0; i<selectedDataItems.get(position).getAdditionalInfo().getValue().size(); i++){
                ItemDatavalue = ItemDatavalue + selectedDataItems.get(position).getAdditionalInfo().getValue().get(i).toString();
            }
            ItemDatavalue = ItemDatavalue + "\n";
        }
        if (!selectedDataItems.get(position).getComment().isEmpty()) {
            ItemDatavalue = ItemDatavalue + "\nComment" + ": " + selectedDataItems.get(position).getComment()+"\n";
        }

        holder.ordered_items_data.setText(ItemDatavalue);
        ItemDatavalue = "";

        holder.orderedItem_SelectImage2.removeAllViews();
        for (OrderDetailResponse.Data.QuoteItemItem.FeatureImages.FrontItem frontItem : selectedDataItems.get(position).getFeatureImages().getFront()) {
            RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            ImageView image = new ImageView(contextR);
            image.setLayoutParams(vp);
            holder.orderedItem_SelectImage2.addView(image);
            Glide.with(contextR).load(frontItem.getImage()).into(image);
        }

    }

    @Override
    public int getItemCount() {
        return selectedDataItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ordered_ItemName, ordered_items_data, ordered_items_data_price;

        RelativeLayout orderedItem_SelectImage2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ordered_ItemName = itemView.findViewById(R.id.orderedItem_selectItemName);
            ordered_items_data = itemView.findViewById(R.id.orderedItem_selectItemsData);
            ordered_items_data_price = itemView.findViewById(R.id.orderedItem_selectItemsPrice);
            orderedItem_SelectImage2 = itemView.findViewById(R.id.orderedItem_SelectImage2);
        }
    }
}