package com.ms.hht.ui.customization.Fabric_Select.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.response.LiningFilterOptionResponse;

import java.util.List;

public class ColorPatternAdapterForLining extends RecyclerView.Adapter<ColorPatternAdapterForLining.MyViewHolder> {

    Context context ;
    List<LiningFilterOptionResponse.DataItem.OptionsItem> options;
    int optionPosition = -1;

    public ColorPatternAdapterForLining(Context context, List<LiningFilterOptionResponse.DataItem.OptionsItem> options) {
        this.context = context;
        this.options = options;
    }

    @NonNull
    @Override
    public ColorPatternAdapterForLining.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflater.inflate(R.layout.color_item_layout, parent, false);

        return new ColorPatternAdapterForLining.MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorPatternAdapterForLining.MyViewHolder holder, int position) {

        holder.colorName.setText(options.get(position).getColorName());
        holder.checkbox.setOnClickListener(v->{
            if (!options.get(position).isSelected) {
                optionPosition = holder.getAdapterPosition();
                options.get(position).setSelected(true);
                notifyDataSetChanged();
            }else {
                optionPosition = holder.getAdapterPosition();
                options.get(position).setSelected(false);
                notifyDataSetChanged();
            }
        });

        if (options.get(position).isSelected()){
            holder.checkbox.setImageResource(R.drawable.check_box);
        }else {
            holder.checkbox.setImageResource(R.drawable.blank_check_box);
        }


    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView checkbox;
        TextView colorName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkbox = itemView.findViewById(R.id.checkbox);
            colorName = itemView.findViewById(R.id.colorName);
        }
    }
}
