package com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.request.GetFeatureImagesRequest;
import com.ms.hht.data.response.FrontItem;
import com.ms.hht.data.response.GetFeatureImagesResponse;
import com.ms.hht.data.response.RearItem;
import com.ms.hht.data.response.StyleResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.ui.customization.CustomizationProcess.Activity.CustomizationAct;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class StyleAdapter extends RecyclerView.Adapter<StyleAdapter.MyViewHolder> {

    public GetFeatureImagesResponse feature_image_response;

    Context context;
    List<StyleResponse.DataItem> data;
    List<Integer> stylechoicesArraytogetFeatureimage = new ArrayList<>();

    public StyleAdapter(Context context, List<StyleResponse.DataItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public StyleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflater.inflate(R.layout.style_piece_item_file, parent, false);
        return new StyleAdapter.MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull StyleAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.StyleCardName.setText(data.get(position).getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        holder.styleCArdREcy.setLayoutManager(layoutManager);
        StyleCArdAdapter stylecardAdapter = new StyleCArdAdapter(context, data.get(position).getChoices());
        holder.styleCArdREcy.setAdapter(stylecardAdapter);
        holder.styleCArdREcy.setHasFixedSize(true);
        holder.styleCArdREcy.setItemViewCacheSize(20);
        holder.styleCArdREcy.setDrawingCacheEnabled(true);
        holder.styleCArdREcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        holder.styleCArdREcy.setNestedScrollingEnabled(false);

        holder.styleCArdREcy.addOnItemTouchListener(new RecyclerTouchListener(context,
                holder.styleCArdREcy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int CHPosition) {

                if (data.get(position).getChoices().size() > 0) {
                    for (int i = 0; i < data.get(position).getChoices().size(); i++) {
                        if ( i == CHPosition){
                            data.get(position).getChoices().get(i).setIsSelected(1);
                        }
                        else {
                            data.get(position).getChoices().get(i).setIsSelected(0);
                        }
                    }
                }
                CustomizationAct.styleResponse.setData(data);
                getfeatureImagesWithoutFilter();
                stylecardAdapter.notifyDataSetChanged();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void getfeatureImagesWithoutFilter() {
        stylechoicesArraytogetFeatureimage.clear();
        for (StyleResponse.DataItem style : data) {
            for (StyleResponse.DataItem.ChoicesItem choicesItem : style.getChoices()) {
                if (choicesItem.getIsSelected() == 1){
                    stylechoicesArraytogetFeatureimage.add(choicesItem.getId());
                }
            }
        }

        System.out.println("Called get feature Images Without Filter api function in Customization Activity +++++++++++       ");
        if (InternetConnection.isConnected(context)) {
            if (CustomizationAct.SELECTED_FABRIC.getFabricId() != null && CustomizationAct.SELECTED_LININIG_DATA != null) {
                int fabricId = 0;
                int liningid = 0;
                if (CustomizationAct.SELECTED_FABRIC.getFabricId() != null){
                    fabricId = CustomizationAct.SELECTED_FABRIC.getFabricId();
                }
                if (CustomizationAct.SELECTED_LININIG_DATA.getFabricId() != null){
                    liningid = CustomizationAct.SELECTED_LININIG_DATA.getFabricId();
                }
                GetFeatureImagesRequest getFeatureImagesRequest = new GetFeatureImagesRequest(
                        fabricId, stylechoicesArraytogetFeatureimage, liningid);
                APICallList.getFabricImages(getFeatureImagesRequest, "get feature Image list", response, context);

            }
            else if (CustomizationAct.SELECTED_FABRIC.getFabricId() != null && CustomizationAct.SELECTED_LININIG_DATA == null) {
                int fabricId = 0;
                if (CustomizationAct.SELECTED_FABRIC.getFabricId() != null){
                    fabricId = CustomizationAct.SELECTED_FABRIC.getFabricId();
                }
                GetFeatureImagesRequest getFeatureImagesRequest = new GetFeatureImagesRequest(
                        fabricId, stylechoicesArraytogetFeatureimage, 0);
                APICallList.getFabricImages(getFeatureImagesRequest, "get feature Image list", response, context);
            }
        } else {
            CommFunc.ShowStatusPop(context, context.getResources().getString(R.string.internet), false);
        }
    }

    public final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) {
            if (url_type.equalsIgnoreCase("get feature Image list")) {
                feature_image_response = (GetFeatureImagesResponse) o;

                if (feature_image_response != null) {

                    if (feature_image_response.getCode() == 1) {

                        CustomizationAct.getFeatureImagesResponse = feature_image_response;

                        if (CustomizationAct.currentImageTYpe.equalsIgnoreCase("front")){
                            CustomizationAct.customBinding.proImageRecy2.removeAllViews();
                            for (FrontItem frontItem : feature_image_response.getData().getFront()) {
                                RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                ImageView image = new ImageView(context);
                                image.setLayoutParams(vp);
                                CustomizationAct.customBinding.proImageRecy2.addView(image);
                                Glide.with(context).load(frontItem.getImage()).into(image);
                            }
                        }else {
                            CustomizationAct.customBinding.rearImageLay.removeAllViews();
                            for (RearItem rearItem : feature_image_response.getData().getRear()) {
                                RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                ImageView image1 = new ImageView(context);
                                image1.setLayoutParams(vp);
                                CustomizationAct.customBinding.rearImageLay.addView(image1);
                                Glide.with(context).load(rearItem.getImage()).into(image1);
                            }
                        }
                    } else {
                        CommFunc.ShowStatusPop(context, feature_image_response.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(context, "ERROR", false);
                }
            }
            else {
                    CommFunc.ShowStatusPop(context, "ERROR", false);
                }
            }

        @Override
        public void onError(String message) {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(context, message, false);
        }
    };

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView styleCArdREcy;
        TextView StyleCardName;
        RelativeLayout StyleCARD;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            StyleCARD = itemView.findViewById(R.id.StyleCARD);
            StyleCardName = itemView.findViewById(R.id.styleCardName);
            styleCArdREcy = itemView.findViewById(R.id.styleCardRecy);
        }
    }
}