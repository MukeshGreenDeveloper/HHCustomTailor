package com.ms.hht.data.adapter;


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
import com.ms.hht.data.response.GETMSMeasurementResponse;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.SessionManager;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DisplayMeasurementResultAdapter extends RecyclerView.Adapter<DisplayMeasurementResultAdapter.MyViewHolder> {

    Context context;
    Map<String,String> measurementItems;
    List<String> listOfKeys=new ArrayList<>();

    public DisplayMeasurementResultAdapter(Context context, Map<String, String> measurementItems) {
        this.context = context;
        this.measurementItems = measurementItems;
        this.listOfKeys.addAll(measurementItems.keySet());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.measuement_grid_layout, parent, false);
        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.girthValue.setText(measurementItems.get(listOfKeys.get(position)));
        holder.measurementGirth.setText(getNameOfItem(listOfKeys.get(position)));
    }

    public static String getNameOfItem(String itemKey) {
        switch (itemKey){
            case "jacketLength":
                return "Jacket Length";
            case "backChestWidth":
                return "Back Chest Width";
            case "hipDepth":
                return "Hip Depth";
            case "thigh":
                return "Thigh";
            case "cervicalLength":
                return "Cervical Length";
            case "stomach":
                return "Stomach";
            case "naturalWaistGirth":
                return "Natural Waist Girth";
            case "halfSleeveLength":
                return "Half Sleeve Length";
            case "upperHip":
                return "Upper Hip";
            case "hip":
                return "Hip";
            case "naturalWaistLength":
                return "Natural Waist Length";
            case "armsLength":
                return "Arms Length";
            case "shirtLength":
                return "Shirt Length";
            case "inseam":
                return "Inseam";
            case "chestDepth":
                return "Chest Depth";
            case "urise":
                return "Urise";
            case "waistDepth":
                return "Waist Depth";
            case "legLength":
                return "Leg Length";
            case "chest":
                return "Chest";
            case "frontChestWidth":
                return "Front Chest Width";
            case "kneeGirth":
                return "Knee Girth";
            case "upperNeck":
                return "Upper Neck";
            case "vestFront":
                return "Vest Front";
            case "neck":
                return "Neck";
            case "shoulderAcross":
                return "Shoulder Across";
            case "calfMuscle":
                return "Calf Muscle";
            case "stomachDepth":
                return "Stomach Depth";
            case "sleeveLengthFull":
                return "Sleeve Length Full";
            case "waist":
                return "Waist";
            case "sleeveLength":
                return "Sleeve Length";
            default:
                return itemKey;
        }
    }

    @Override
    public int getItemCount() {
        return measurementItems.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView measurementGirth, girthValue;
        RelativeLayout measureView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            measurementGirth = itemView.findViewById(R.id.nameTF);
            measureView = itemView.findViewById(R.id.measureView);
            girthValue = itemView.findViewById(R.id.measuremntValue);
        }
    }
}
