package com.ms.hht.data.adapter;

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
import com.ms.hht.data.response.OrderDetailResponse;
import java.util.List;

public class ReOrderItemAdapter extends RecyclerView.Adapter<ReOrderItemAdapter.MyViewHolder> {

    Context contextR;
    List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> selectedDataItems;
    String ItemDatavalue = "";

    public ReOrderItemAdapter(Context contextR, List<OrderDetailResponse.Data.QuoteItemItem.ItemDataItem> selectedDataItems) {
        this.contextR = contextR;
        this.selectedDataItems = selectedDataItems;
    }

    @NonNull
    @Override
    public ReOrderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) contextR.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3 = inflater.inflate(R.layout.selected_reorder_item_file, parent, false);
        return new ReOrderItemAdapter.MyViewHolder(view3);
    }

    @Override
    public void onBindViewHolder(@NonNull ReOrderItemAdapter.MyViewHolder holder, int position) {

        if (selectedDataItems.get(position).isSelectedItem()) {
            holder.selectItemName.setText(selectedDataItems.get(position).getPieceName());

            holder.reorder_items_data_price.setText("$ " + selectedDataItems.get(position).getPiecePrice());

            for (int i = 0; i < selectedDataItems.get(position).getSelectedValue().size(); i++) {
                ItemDatavalue = ItemDatavalue + selectedDataItems.get(position).getSelectedValue().get(i).getName()
                        + " : " + selectedDataItems.get(position).getSelectedValue().get(i).getValue()+"\n";
            }
            if (selectedDataItems.get(position).getLining() != null){
                if (!selectedDataItems.get(position).getLining().isEmpty()){
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

            if (!selectedDataItems.get(position).getComment().isEmpty()){
                ItemDatavalue = ItemDatavalue + "\nComment" + ": " + selectedDataItems.get(position).getComment()+"\n";
            }

            holder.reorder_items_data.setText(ItemDatavalue);
            ItemDatavalue = "";

            holder.selectItemImage.removeAllViews();
            for (OrderDetailResponse.Data.QuoteItemItem.FeatureImages.FrontItem frontItem : selectedDataItems.get(position).getFeatureImages().getFront()) {
                RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                ImageView image = new ImageView(contextR);
                image.setLayoutParams(vp);
                holder.selectItemImage.addView(image);
                Glide.with(contextR).load(frontItem.getImage()).into(image);
            }

        }
    }

    @Override
    public int getItemCount() {
        return selectedDataItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView selectItemName, reorder_items_data, reorder_items_data_price;

        RelativeLayout selectItemImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            selectItemName = itemView.findViewById(R.id.selectItemName);
            reorder_items_data = itemView.findViewById(R.id.selectItemsData);
            reorder_items_data_price = itemView.findViewById(R.id.selectItemsPrice);
            selectItemImage = itemView.findViewById(R.id.SelectImage);
        }
    }
}