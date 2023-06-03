package com.ms.hht.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.response.GetCountryResponse;
import com.ms.hht.ui.profile.AddAddress;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapterClass extends RecyclerView.Adapter<CountryAdapterClass.MyViewHolder> {

    Context context;
    List<GetCountryResponse.DataItem> data;
    ArrayList<GetCountryResponse.DataItem> itemlistcusValue;

    public CountryAdapterClass(Context context, List<GetCountryResponse.DataItem> data) {
        this.context = context;
        this.data = data;
        itemlistcusValue = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflatert = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflatert.inflate(R.layout.country_layout_file, parent, false);
        return new MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.countryName.setText(data.get(position).getName());
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GetCountryResponse.DataItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemlistcusValue);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (GetCountryResponse.DataItem item : itemlistcusValue) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.countrylabel);
        }
    }
}
