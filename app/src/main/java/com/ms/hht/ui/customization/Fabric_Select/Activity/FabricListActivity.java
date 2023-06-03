package com.ms.hht.ui.customization.Fabric_Select.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ms.hht.R;
import com.ms.hht.data.adapter.FabricFilterOptionsAdapter;
import com.ms.hht.data.request.FabricListRequest;
import com.ms.hht.data.response.FabricFilterListResponse;
import com.ms.hht.data.response.FabricFilterResponse;
import com.ms.hht.data.response.ProductDescriptionRes;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityFabricListBinding;
import com.ms.hht.ui.Product_Pieces.Activity.ProductPieceSelecton;
import com.ms.hht.ui.customization.CustomizationProcess.Activity.CustomizationAct;
import com.ms.hht.ui.customization.Fabric_Select.ViewAdapter.FabricListAdapter;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.RecyclerTouchListener;
import com.ms.hht.utils.SessionManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FabricListActivity extends AppCompatActivity implements View.OnClickListener {

//    public static int subCategoryID_from_prod_desc_of_custom_to_call_fabricAPI;
//    public static Integer IS_MULTIPART = -1;
    public static ProductDescriptionRes productDescriptionRes;

    public HashMap<String, ArrayList<Integer>> filterData;
    public Dialog colorDialog;
    public HashMap<String, ArrayList<Integer>> value = new HashMap<>();
    FabricFilterResponse fabricFilterResponse;
    FabricFilterResponse.DataItem selectedFabricData;
    boolean isFabricSelected = false;
    ActivityFabricListBinding fabricListBinding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fabricListBinding = ActivityFabricListBinding.inflate(getLayoutInflater());
        setContentView(fabricListBinding.getRoot());
        sessionManager = new SessionManager(this);
        fabricListBinding.prevActivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        fabricListBinding.nextActBtn.setOnClickListener(this);
        fabricListBinding.Filterlayout.setOnClickListener(this);

        this.filterData = new HashMap<>();

        getfabriclist();
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) {
            if (url_type.equalsIgnoreCase("fabric list data")) {
                CommFunc.DismissDialog();
                fabricFilterResponse = (FabricFilterResponse) o;
                if (fabricFilterResponse != null) {
                    if (fabricFilterResponse.getCode() == 1) {
                        if (fabricFilterResponse.getData().size() > 0) {
                            setProductImageList(fabricFilterResponse.getData());
                        }
                    } else {
                        CommFunc.ShowStatusPop(FabricListActivity.this, fabricFilterResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(FabricListActivity.this, getResources().getString(R.string.try_again), false);
                }
            }
            else if (url_type.equalsIgnoreCase("color patter list")) {
                CommFunc.DismissDialog();
                FabricFilterListResponse filterListResponse = (FabricFilterListResponse) o;
                if (filterListResponse != null) {
                    if (filterListResponse.getCode() == 1) {
                        if (filterListResponse.getData().size() > 0) {

                            showColorPatternPopUp(filterListResponse.getData());
                        }
                    } else {
                        CommFunc.ShowStatusPop(FabricListActivity.this, filterListResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(FabricListActivity.this, getResources().getString(R.string.try_again), false);
                }
            }
        }

        @Override
        public void onError(String message) {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(FabricListActivity.this, message, false);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.nextActBtn:
                if (isFabricSelected) {
                    if (productDescriptionRes.getData().getIsMultiple() == 0)
                    {
                        Intent i1 = new Intent(FabricListActivity.this, CustomizationAct.class);
                        CustomizationAct.SELECTED_FABRIC = selectedFabricData;
                        CustomizationAct.PIECE_ID_FROM_PROD_DESC_TO_FABRIC_SELECTION_TO_GET_PIECE_TYPE = productDescriptionRes.getData().getPieceId();
                        CustomizationAct.SUB_CATEGORY_ID_FROM_PIECE_SEL_TO_CUSTOMIZATION_TO_CALL_PIECE_FABRIC_FILTER_API = productDescriptionRes.getData().getSubcategoryId();
                        CustomizationAct.PRODUCT_NAME = productDescriptionRes.getData().getName();
                        startActivity(i1);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    else
                    {
                        Intent intent5 = new Intent(FabricListActivity.this, ProductPieceSelecton.class);
                        ProductPieceSelecton.productDescriptionRes = productDescriptionRes;
                        ProductPieceSelecton.SELECTED_FABRIC = selectedFabricData;
                        startActivity(intent5);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                } else {
                    CommFunc.ShowStatusPop(FabricListActivity.this, "Please select Fabric", false);
                }
                break;

            case R.id.Filterlayout:

                colorPatternApi();

                break;
        }
    }

    private void colorPatternApi() {
        if (InternetConnection.isConnected(FabricListActivity.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getfilterList(productDescriptionRes.getData().getSubcategoryId(), "color patter list", response, FabricListActivity.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private void showColorPatternPopUp(List<FabricFilterListResponse.DataItem> data) {

        colorDialog = new Dialog(FabricListActivity.this);
        Window window = colorDialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        colorDialog.setContentView(R.layout.color_pattern_layout);
        colorDialog.setCancelable(false);
        colorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        colorDialog.show();

        ImageView closeIcon = colorDialog.findViewById(R.id.closeIcon);
        RecyclerView patternRecycler = colorDialog.findViewById(R.id.patternRecycler);
        TextView applyText = colorDialog.findViewById(R.id.applyText);
        closeIcon.setOnClickListener(v -> colorDialog.dismiss());

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(FabricListActivity.this);
        patternRecycler.setLayoutManager(layoutManager1);
        FabricFilterOptionsAdapter fabricAdapter = new FabricFilterOptionsAdapter(FabricListActivity.this, data);
        patternRecycler.setAdapter(fabricAdapter);
        patternRecycler.setHasFixedSize(true);
        patternRecycler.setItemViewCacheSize(20);
        patternRecycler.setDrawingCacheEnabled(true);
        patternRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        patternRecycler.setNestedScrollingEnabled(false);

        if (value.size()>0){
        for (int i=0 ; i<data.size() ; i++){

            for(Map.Entry m : value.entrySet()){
                System.out.println(m.getKey()+" "+m.getValue());
                if (m.getKey().toString().equalsIgnoreCase(data.get(i).getFiltername())){
                    if (data.get(i).getOptions().size()>0){
                        System.out.println("key name "+m.getKey());
                        JSONArray ja = new JSONArray(value.get(m.getKey()));
                        System.out.println("key value "+ ja.toString());

                        for(int a = 0; a< data.get(i).getOptions().size() ; a++){
                            for (int j=0 ; j< ja.length() ; j++){

                                try{
                                    if (ja.get(j) == data.get(i).getOptions().get(a).getColorId()){
                                        data.get(i).getOptions().get(a).setSelected(true);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }
            }
        }

        }

        applyText.setOnClickListener(v -> {

            for (FabricFilterListResponse.DataItem filterNameData : data) {
                ArrayList<Integer> optionsItemList = new ArrayList<>();
                for (FabricFilterListResponse.DataItem.OptionsItem FilterValue : filterNameData.getOptions()) {
                    if (FilterValue.isSelected) {
                        optionsItemList.add(FilterValue.getColorId());
                    }
                }
                value.put(filterNameData.getFiltername(), optionsItemList);
            }
            getFilterfabriclist(value);
        });

    }

    private void getFilterfabriclist(HashMap<String, ArrayList<Integer>> value) {
        colorDialog.dismiss();
        if (InternetConnection.isConnected(FabricListActivity.this)) {
            CommFunc.ShowProgressbar(this);
            FabricListRequest fabricListRequest = new FabricListRequest(value);

            System.out.println(new Gson().toJson(fabricListRequest));
            APICallList.getfabricList(fabricListRequest, productDescriptionRes.getData().getSubcategoryId(), "fabric list data", response, FabricListActivity.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private void getfabriclist() {
        if (InternetConnection.isConnected(FabricListActivity.this)) {
            CommFunc.ShowProgressbar(this);
            FabricListRequest fabricListRequest = new FabricListRequest(filterData);
            System.out.println(new Gson().toJson(fabricListRequest));
            APICallList.getfabricList(fabricListRequest, productDescriptionRes.getData().getSubcategoryId(), "fabric list data", response, FabricListActivity.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private void setProductImageList(List<FabricFilterResponse.DataItem> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(FabricListActivity.this, 3);
        fabricListBinding.proTypeRcy.setLayoutManager(layoutManager);
        FabricListAdapter fabricAdapter = new FabricListAdapter(FabricListActivity.this, data);
        fabricListBinding.proTypeRcy.setAdapter(fabricAdapter);
        fabricListBinding.proTypeRcy.setHasFixedSize(true);
        fabricListBinding.proTypeRcy.setItemViewCacheSize(20);
        fabricListBinding.proTypeRcy.setDrawingCacheEnabled(true);
        fabricListBinding.proTypeRcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        fabricListBinding.proTypeRcy.setNestedScrollingEnabled(false);
        fabricListBinding.proTypeRcy.addOnItemTouchListener(new RecyclerTouchListener(FabricListActivity.this,
                fabricListBinding.proTypeRcy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setSelect(i == position);
                }
                fabricFilterResponse.setData(data);
                isFabricSelected = true;
                selectedFabricData = data.get(position);
                sessionManager.setFabricId(String.valueOf(data.get(position).getFabricId()));
                sessionManager.setFabricName(String.valueOf(data.get(position).getDescription()));
                fabricAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }


}