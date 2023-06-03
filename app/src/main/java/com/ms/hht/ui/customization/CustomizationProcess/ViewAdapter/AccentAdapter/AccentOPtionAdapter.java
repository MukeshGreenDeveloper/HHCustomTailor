package com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter.AccentAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.response.AccentResponse;
import com.ms.hht.ui.customization.CustomizationProcess.Activity.CustomizationAct;
import com.ms.hht.utils.CommFunc;

import java.util.ArrayList;
import java.util.List;

public class AccentOPtionAdapter extends RecyclerView.Adapter<AccentOPtionAdapter.MyViewHolder> {

    Context context;
    public List<AccentResponse.DataItem.ChoicesItem> choicesItemList;

    public AccentOPtionAdapter(Context context, List<AccentResponse.DataItem.ChoicesItem> choicesItemList) {
        this.context = context;
        this.choicesItemList = choicesItemList;
    }

    @NonNull
    @Override
    public AccentOPtionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflater.inflate(R.layout.accent_options_recy_item_file, parent, false);
        return new AccentOPtionAdapter.MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull AccentOPtionAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.AccentOPtionCheckBoxName.setText(choicesItemList.get(position).getName());
        String selectedValueName = "";
        String monogramName = "";

        if (choicesItemList.get(position).isIsaccentChoiceSelected()) {
            holder.AccentOPtionCheckBox.setImageResource(R.drawable.check_box);
        } else {
            holder.AccentOPtionCheckBox.setImageResource(R.drawable.uncheck_box);
        }

// Set the selected values on the TextView

        if (choicesItemList.get(position).isIsaccentChoiceSelected() && choicesItemList.get(position).getAdditionalInfos().size() > 0) {

            for (int ai = 0; ai < choicesItemList.get(position).getAdditionalInfos().size(); ai++) {

                if (choicesItemList.get(position).getAdditionalInfos().get(ai).getImages().size() > 0) {

                    for (AccentResponse.DataItem.ChoicesItem.AdditionalInfosItem.ImagesItem selecterImage : choicesItemList.get(position).getAdditionalInfos().get(ai).getImages()) {
                        if (selecterImage.isAccentImageSelected()) {
                            selectedValueName = selectedValueName + selecterImage.getDisplayText()+", ";
                        }
                    }
                }
                else {
                    if (choicesItemList.get(position).getAdditionalInfos().get(ai).getValue() != null && !choicesItemList.get(position).getAdditionalInfos().get(ai).getValue().isEmpty()) {
                        monogramName = choicesItemList.get(position).getAdditionalInfos().get(ai).getValue();
                        selectedValueName = selectedValueName + choicesItemList.get(position).getAdditionalInfos().get(ai).getValue() + ", ";
                    }
                    else if (choicesItemList.get(position).getAdditionalInfos().get(ai).getValue() != null && choicesItemList.get(position).getAdditionalInfos().get(ai).getValue().isEmpty()){
                        monogramName = "";
                    }
                }
            }

            if (selectedValueName.isEmpty() && monogramName.isEmpty()){
                holder.accentInputText.setText("");
            }
            else {
                String a = selectedValueName.substring(0,selectedValueName.length()-2);
                holder.accentInputText.setText(a);
            }
        }

        else {
            holder.accentInputText.setText("");
        }

//   Yes , No Selected
        holder.AccentOPtionCheckBoxCArd.setOnClickListener(view -> {

            for (int i = 0; i < choicesItemList.size(); i++) {
                if (position == i) {
                    choicesItemList.get(position).setIsaccentChoiceSelected(true);
                }
                else {
                    choicesItemList.get(i).setIsaccentChoiceSelected(false);
                }
            }

            notifyDataSetChanged();

//             open Accent yes Pop up
            if (choicesItemList.get(position).getAdditionalInfos().size() > 0) {
                Dialog accentPOPDialog = new Dialog(context);
                Window window = accentPOPDialog.getWindow();
                assert window != null;
                window.setGravity(Gravity.CENTER);
                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                accentPOPDialog.setContentView(R.layout.accent_popup_fields);
                accentPOPDialog.setCancelable(false);
                accentPOPDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                accentPOPDialog.show();

                ImageView closeIcon = accentPOPDialog.findViewById(R.id.closeIcon);
                RecyclerView AccentPOpUpRecyView = accentPOPDialog.findViewById(R.id.AccentPOpUpRecyView);
                TextView applyText = accentPOPDialog.findViewById(R.id.applyText);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                AccentPOpUpRecyView.setLayoutManager(linearLayoutManager);
                AccentPopUpAdapter accentPopUpAdapter = new AccentPopUpAdapter(context, choicesItemList.get(position).getAdditionalInfos(), position);
                AccentPOpUpRecyView.setAdapter(accentPopUpAdapter);
                AccentPOpUpRecyView.setHasFixedSize(true);
                AccentPOpUpRecyView.setItemViewCacheSize(20);
                AccentPOpUpRecyView.setDrawingCacheEnabled(true);
                AccentPOpUpRecyView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                AccentPOpUpRecyView.setNestedScrollingEnabled(false);

                closeIcon.setOnClickListener(v -> {
                    accentPOPDialog.dismiss();
                });

                applyText.setOnClickListener(v -> {

                    boolean isClose = true;

                    for (int ai = 0; ai < choicesItemList.get(position).getAdditionalInfos().size(); ai++) {

                        if (choicesItemList.get(position).getAdditionalInfos().get(ai).getImages().size() > 0) {

                            System.out.println("*********************************************** IMage found");

                            ArrayList<AccentResponse.DataItem.ChoicesItem.AdditionalInfosItem.ImagesItem> imageArray = new ArrayList<>();

                            for (AccentResponse.DataItem.ChoicesItem.AdditionalInfosItem.ImagesItem selecterImage : accentPopUpAdapter.additionalInfos.get(ai).getImages()){ //    choicesItemList.get(position).getAdditionalInfos().get(ai).getImages()) {
                                if (selecterImage.isAccentImageSelected()) {
                                    imageArray.add(selecterImage);
                                }
                            }
                            if (imageArray.isEmpty()) {
                                CommFunc.ShowStatusPop(context, "Please select all the data.", false);
//                                notifyDataSetChanged();
                                isClose = false;
                                break;
                            }

                        }
                        else {
//                            if (choicesItemList.get(position).getAdditionalInfos().get(ai).getValue() == null || choicesItemList.get(position).getAdditionalInfos().get(ai).getValue().isEmpty()) {
//                                CommFunc.ShowStatusPop(context, "Please enter monogram name.", false);
////                                notifyDataSetChanged();
//                                isClose = false;
//                                break;
//                            }

                            System.out.println("*********************************************** text only");

                            if ( accentPopUpAdapter.additionalInfos.get(ai).getValue() == null ||  accentPopUpAdapter.additionalInfos.get(ai).getValue().isEmpty()) {
                                CommFunc.ShowStatusPop(context, "Please enter monogram name.", false);
//                                notifyDataSetChanged();
                                isClose = false;
                                break;
                            }
                        }
                    }
                    if (isClose) {

                        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                        choicesItemList.get(position).setAdditionalInfos(accentPopUpAdapter.additionalInfos);
                        CustomizationAct.accentResponse.getData().get(0).setChoices(choicesItemList);
                        accentPOPDialog.dismiss();
                        notifyDataSetChanged();
                    }

                });
            }

        });
    }

    @Override
    public int getItemCount() {
        return choicesItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView AccentOPtionCheckBox;
        RelativeLayout AccentOPtionCheckBoxCArd;
        TextView AccentOPtionCheckBoxName, accentInputText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            AccentOPtionCheckBoxCArd = itemView.findViewById(R.id.AccentOPtionCheckBoxCArd);
            AccentOPtionCheckBox = itemView.findViewById(R.id.AccentOPtionCheckBox);
            AccentOPtionCheckBoxName = itemView.findViewById(R.id.AccentOPtionCheckBoxName);
            accentInputText = itemView.findViewById(R.id.accentInputText);
        }
    }
}
