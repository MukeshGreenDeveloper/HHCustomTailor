package com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter.AccentAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.response.AccentResponse;
import com.ms.hht.utils.RecyclerTouchListener;

import java.util.List;

public class AccentPopUpAdapter extends RecyclerView.Adapter<AccentPopUpAdapter.MyViewHolder> {

    Context context;
    public List<AccentResponse.DataItem.ChoicesItem.AdditionalInfosItem> additionalInfos;
    AccentPopUpRecyItemAdapter accentPopUpitemAdapter ;
    int choicePosition;

    public AccentPopUpAdapter(Context context, List<AccentResponse.DataItem.ChoicesItem.AdditionalInfosItem> additionalInfos, int choicePosition) {
        this.context = context;
        this.additionalInfos = additionalInfos;
        this.choicePosition = choicePosition;
    }

    @NonNull
    @Override
    public AccentPopUpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflater.inflate(R.layout.accent_popup_recy_item_file, parent, false);
        return new AccentPopUpAdapter.MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull AccentPopUpAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.AccentPOpUpTextLabel.setText(additionalInfos.get(position).getInfoName());

        if ( additionalInfos.get(position).getImages().size()>0){

            holder.accentPopupRcyItemFilerecy.setVisibility(View.VISIBLE);
            holder.AccentPOpUpTextInput.setVisibility(View.GONE);

            GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
            holder.accentPopupRcyItemFilerecy.setLayoutManager(layoutManager);
            accentPopUpitemAdapter = new AccentPopUpRecyItemAdapter(context, additionalInfos.get(position).getImages());
            holder.accentPopupRcyItemFilerecy.setAdapter(accentPopUpitemAdapter);
            holder.accentPopupRcyItemFilerecy.setHasFixedSize(true);
            holder.accentPopupRcyItemFilerecy.setItemViewCacheSize(20);
            holder.accentPopupRcyItemFilerecy.setDrawingCacheEnabled(true);
            holder.accentPopupRcyItemFilerecy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            holder.accentPopupRcyItemFilerecy.setNestedScrollingEnabled(false);

            holder.accentPopupRcyItemFilerecy.addOnItemTouchListener(new RecyclerTouchListener(context, holder.accentPopupRcyItemFilerecy, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position2) {
                    for (int i = 0; i<additionalInfos.get(position).getImages().size(); i++ )
                    {
                        if (i == position2){
                            additionalInfos.get(position).getImages().get(position2).setAccentImageSelected(true);
                        }
                        else {
                            additionalInfos.get(position).getImages().get(i).setAccentImageSelected(false);
                        }
                    }
                    notifyDataSetChanged();
                }
                @Override
                public void onLongClick(View view, int position) {}
            }));
        }

        else{
            holder.accentPopupRcyItemFilerecy.setVisibility(View.GONE);
            holder.AccentPOpUpTextInput.setVisibility(View.VISIBLE);
        }

        if (additionalInfos.get(position).getValue() != null && !additionalInfos.get(position).getValue().isEmpty()){
            holder.AccentPOpUpTextInput.setText(additionalInfos.get(position).getValue());
        }

        holder.AccentPOpUpTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    additionalInfos.get(position).setValue(charSequence.toString());
                }
                if(charSequence.length() == 0) {
                    additionalInfos.get(position).setValue("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

    }

    @Override
    public int getItemCount() {
        return additionalInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView accentPopupRcyItemFilerecy;
        TextView AccentPOpUpTextLabel;
        EditText AccentPOpUpTextInput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            accentPopupRcyItemFilerecy = itemView.findViewById(R.id.accentPopupRcyItemFilerecy);
            AccentPOpUpTextLabel = itemView.findViewById(R.id.AccentPOpUpTextLabel);
            AccentPOpUpTextInput = itemView.findViewById(R.id.AccentPOpUpTextInput);
        }
    }
}