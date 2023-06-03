package com.ms.hht.ui.customization.CustomizationProcess.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ms.hht.R;
import com.ms.hht.data.adapter.CustomizeTextAdapter;
import com.ms.hht.data.request.FabricListRequest;
import com.ms.hht.data.request.GetFeatureImagesRequest;
import com.ms.hht.data.request.LiningAddToCartRequest;
import com.ms.hht.data.request.LiningFilterListRequest;
import com.ms.hht.data.response.AccentResponse;
import com.ms.hht.data.response.AddToCartResponse;
import com.ms.hht.data.response.FabricFilterResponse;
import com.ms.hht.data.response.FabricSelectionGetPieceTypesResponse;
import com.ms.hht.data.response.FrontItem;
import com.ms.hht.data.response.GetFeatureImagesResponse;
import com.ms.hht.data.response.LiningResponse;
import com.ms.hht.data.response.RearItem;
import com.ms.hht.data.response.StyleResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActCustomizationBinding;
import com.ms.hht.ui.Product_Pieces.Activity.ProductPieceSelecton;
import com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter.AccentAdapter.AccentAdapter;
import com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter.FabricAdapter.CusFabricAdapter;
import com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter.LiningAdapter;
import com.ms.hht.ui.customization.CustomizationProcess.ViewAdapter.StyleAdapter;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderCartAct;
import com.ms.hht.ui.customization.Product_Zoom.Activity.ViewImageActivity;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.RecyclerTouchListener;
import com.ms.hht.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomizationAct extends AppCompatActivity implements View.OnClickListener {

    public static FabricFilterResponse.DataItem SELECTED_FABRIC;
//    public static ProductDescriptionRes productDescriptionRes;

    public static ActCustomizationBinding customBinding;
    public static Object previousSelectedPieceValues;

    public static Integer PIECE_ID_FROM_PROD_DESC_TO_FABRIC_SELECTION_TO_GET_PIECE_TYPE = -1;
    public static Integer SUB_CATEGORY_ID_FROM_PIECE_SEL_TO_CUSTOMIZATION_TO_CALL_PIECE_FABRIC_FILTER_API = -1;
    public static String PRODUCT_NAME = "";
    public static int ITEM_POSITION = 0;
    public static boolean isZoom = false;

    public static LiningResponse.DataItem SELECTED_LININIG_DATA;
    public static StyleResponse styleResponse;
    public static AccentResponse accentResponse;
    public static GetFeatureImagesResponse getFeatureImagesResponse;
    public static String currentImageTYpe = "front";
    public int topRecyclerPosition = 0;
    // ChangePasswordResponse Classes
    FabricSelectionGetPieceTypesResponse fabricSelectionGetPieceTypesResponse;
    LiningResponse liningResponse;

    AddToCartResponse addToCartResponse;
    FabricFilterResponse fabricListResponse;
    String typeofPieceSelected = "";
    String comment_text = "";
    String comment = "";
    SessionManager sessionManager;
    int SelectedPieceIDForCart = -1;
    private CustomizeTextAdapter customizeTextAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customBinding = ActCustomizationBinding.inflate(getLayoutInflater());
        setContentView(customBinding.getRoot());
        sessionManager = new SessionManager(this);

//        getFeatureImagesResponse = new GetFeatureImagesResponse();
        accentResponse = new AccentResponse();
        styleResponse = new StyleResponse();

        customBinding.prevActivi.setOnClickListener(view -> {
            isZoom = false;
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        customBinding.jacketTV.setText(PRODUCT_NAME);
        customBinding.nextActBtn.setOnClickListener(this);
        customBinding.proImageRecy2.setOnClickListener(this);
        customBinding.rearImageLay.setOnClickListener(this);
        customBinding.frontImg.setOnClickListener(this);
        customBinding.RearImage.setOnClickListener(this);
        getPieceType();

    }

    private void resetStaticVariableValues() {

        SELECTED_FABRIC = new FabricFilterResponse.DataItem();
        SELECTED_LININIG_DATA = new LiningResponse.DataItem();
        getFeatureImagesResponse = null;
        previousSelectedPieceValues = null;
        currentImageTYpe = "front";
        isZoom = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextActBtn:

                topRecyclerPosition = topRecyclerPosition + 1;
                if (topRecyclerPosition <= fabricSelectionGetPieceTypesResponse.getData().size() - 1) {
                    for (int i = 0; i < fabricSelectionGetPieceTypesResponse.getData().size(); i++) {
                        if (i == topRecyclerPosition) {
                            typeofPieceSelected = fabricSelectionGetPieceTypesResponse.getData().get(topRecyclerPosition).getName();
                            SelectedPieceIDForCart = fabricSelectionGetPieceTypesResponse.getData().get(topRecyclerPosition).getId();
                            fabricSelectionGetPieceTypesResponse.getData().get(topRecyclerPosition).setGetPieceSelected(true);
                        } else {
                            fabricSelectionGetPieceTypesResponse.getData().get(i).setGetPieceSelected(false);
                        }
                    }
                    customizeTextAdapter.notifyDataSetChanged();
                    hideLayoutAccToSelection();

                } else {
                    commentPopup();
                }

                break;

            case R.id.frontImg:

                currentImageTYpe = "front";
                customBinding.proImageRecy2.setVisibility(View.VISIBLE);
                customBinding.rearImageLay.setVisibility(View.GONE);
                customBinding.frontImg.setBackgroundColor(Color.parseColor("#333031"));
                customBinding.frontImg.setTextColor(Color.parseColor("#FFFFFF"));
                customBinding.RearImage.setBackgroundColor(Color.parseColor("#F5F5ED"));
                customBinding.RearImage.setTextColor(Color.parseColor("#333031"));
                setFrontImagesAccTOSelection();

                break;

            case R.id.RearImage:

                currentImageTYpe = "rear";
                customBinding.proImageRecy2.setVisibility(View.GONE);
                customBinding.rearImageLay.setVisibility(View.VISIBLE);
                customBinding.frontImg.setBackgroundColor(Color.parseColor("#F5F5ED"));
                customBinding.frontImg.setTextColor(Color.parseColor("#333031"));
                customBinding.RearImage.setBackgroundColor(Color.parseColor("#333031"));
                customBinding.RearImage.setTextColor(Color.parseColor("#FFFFFF"));
                setRearImagesAccTOSelection();

                break;

            case R.id.proImageRecy2:

                if (getFeatureImagesResponse != null && getFeatureImagesResponse.getData().getFront().size() > 0) {
                    isZoom = true;
                    Intent viewFrontImage = new Intent(CustomizationAct.this, ViewImageActivity.class);
                    ViewImageActivity.FEATURE_IMAGE_DATA = getFeatureImagesResponse.getData();
                    ViewImageActivity.COMING_FROM = "front image";

                    startActivity(viewFrontImage);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;

            case R.id.rearImageLay:

                if (getFeatureImagesResponse != null && getFeatureImagesResponse.getData().getRear().size() > 0) {
                    isZoom = true;
                    Intent viewRearImage = new Intent(CustomizationAct.this, ViewImageActivity.class);
                    ViewImageActivity.FEATURE_IMAGE_DATA = getFeatureImagesResponse.getData();
                    ViewImageActivity.COMING_FROM = "rear image";
                    startActivity(viewRearImage);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isZoom) {
            resetStaticVariableValues();
        }
    }

    private void commentPopup() {
        final Dialog dialog = new Dialog(CustomizationAct.this);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.comment_popup);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ImageView closeImage = dialog.findViewById(R.id.closeImage);

        EditText commentEditText = dialog.findViewById(R.id.commentEditText);

        if (previousSelectedPieceValues != null) {
            JSONObject jo = new JSONObject((Map) previousSelectedPieceValues);
            try {
                String message = jo.getString("comment");
                if (!message.isEmpty()) {
                    commentEditText.setText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Button submitButton = dialog.findViewById(R.id.submitButton);
        if (!comment.isEmpty()) {
            commentEditText.setText(comment);
        }
        closeImage.setOnClickListener(v -> {
            dialog.dismiss();
        });
        submitButton.setOnClickListener(v -> {
            comment_text = commentEditText.getText().toString().trim();
            dialog.dismiss();
            addCartDataBeforeCAllingApi();
        });

    }

    private void getPieceType() {
        if (InternetConnection.isConnected(CustomizationAct.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getPIECETYPE(PIECE_ID_FROM_PROD_DESC_TO_FABRIC_SELECTION_TO_GET_PIECE_TYPE, "get piece type list", response, CustomizationAct.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private void get_Fabric_Data() {

        HashMap<String, ArrayList<Integer>> fabricFilterEmptyData = new HashMap<>();
        FabricListRequest fabricListRequest = new FabricListRequest(fabricFilterEmptyData);
        APICallList.getfabricList(fabricListRequest, SUB_CATEGORY_ID_FROM_PIECE_SEL_TO_CUSTOMIZATION_TO_CALL_PIECE_FABRIC_FILTER_API,
                "get fabric data without filter", response, CustomizationAct.this);
    }

    private void get_Lining_Data() {

        HashMap<String, ArrayList<Integer>> liningFilterEmptyData = new HashMap<>();
        LiningFilterListRequest liningFilterListRequest = new LiningFilterListRequest(liningFilterEmptyData);
        APICallList.getDataOfLiningFilter(liningFilterListRequest, PIECE_ID_FROM_PROD_DESC_TO_FABRIC_SELECTION_TO_GET_PIECE_TYPE, "get lining data", response, CustomizationAct.this);
    }

    private void get_StyleOption_Data() {
        APICallList.getStyleData(PIECE_ID_FROM_PROD_DESC_TO_FABRIC_SELECTION_TO_GET_PIECE_TYPE, "get style option data", response, CustomizationAct.this);
    }

    private void get_Accent_Data() {
        APICallList.getAccentData(PIECE_ID_FROM_PROD_DESC_TO_FABRIC_SELECTION_TO_GET_PIECE_TYPE, "get accent option data", response, CustomizationAct.this);
    }

    private void get_feature_Images() {

        if (SELECTED_FABRIC != null && styleResponse != null && SELECTED_LININIG_DATA != null) {
            int fabricId = 0;
            int liningid = 0;
            if (SELECTED_FABRIC.getFabricId() != null) {
                fabricId = SELECTED_FABRIC.getFabricId();
            }
            if (SELECTED_LININIG_DATA.getFabricId() != null) {
                liningid = SELECTED_LININIG_DATA.getFabricId();
            }
            List<Integer> selected_styleData = new ArrayList<>();
            if (styleResponse.getData() != null) {
                for (StyleResponse.DataItem style : styleResponse.getData()) {
                    for (StyleResponse.DataItem.ChoicesItem choicesItem : style.getChoices()) {
                        if (choicesItem.getIsSelected() == 1) {
                            selected_styleData.add(choicesItem.getId());
                        }
                    }
                }
                GetFeatureImagesRequest getFeatureImagesRequest = new GetFeatureImagesRequest(
                        fabricId, selected_styleData, liningid);
                APICallList.getFabricImages(getFeatureImagesRequest, "get feature Image list", response, CustomizationAct.this);
            }


        } else if (SELECTED_FABRIC != null && styleResponse != null) {
            int fabricId = 0;
            if (SELECTED_FABRIC.getFabricId() != null) {
                fabricId = SELECTED_FABRIC.getFabricId();
            }

            List<Integer> selected_styleData = new ArrayList<>();
            if (styleResponse.getData() != null) {
                for (StyleResponse.DataItem style : styleResponse.getData()) {
                    for (StyleResponse.DataItem.ChoicesItem choicesItem : style.getChoices()) {
                        if (choicesItem.getIsSelected() == 1) {
                            selected_styleData.add(choicesItem.getId());
                        }
                    }
                }
                GetFeatureImagesRequest getFeatureImagesRequest = new GetFeatureImagesRequest(
                        fabricId, selected_styleData, 0);
                APICallList.getFabricImages(getFeatureImagesRequest, "get feature Image list", response, CustomizationAct.this);
            }
        }
    }

    private void callAttributesApi(List<FabricSelectionGetPieceTypesResponse.DataItem> data) {

        ArrayList<FabricSelectionGetPieceTypesResponse.DataItem> typeData = new ArrayList<>();

        typeData.addAll(data);

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equalsIgnoreCase("fabric")) {
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).getName().equalsIgnoreCase("fabric")) {
                        typeData.remove(0);
                        typeData.add(0, data.get(j));
                    } else {
                        typeData.remove(j + 1);
                        typeData.add(j + 1, data.get(j));
                    }
                }
                fabricSelectionGetPieceTypesResponse.getData().clear();
                fabricSelectionGetPieceTypesResponse.getData().addAll(typeData);
                get_Fabric_Data();
            } else if (data.get(i).getName().equalsIgnoreCase("lining")) {

                double extraCost = Double.parseDouble(data.get(i).getExtra_cost());
                if (extraCost > 0) {
                    customBinding.LiningHeader.setText("Select Lining\nPrice " + fabricSelectionGetPieceTypesResponse.getData().get(i).getExtra_cost() + " USD");
                }
                get_Lining_Data();
            } else if (data.get(i).getName().equalsIgnoreCase("style")) {
                get_StyleOption_Data();
            } else if (data.get(i).getName().equalsIgnoreCase("accent")) {

                double extraCost = Double.parseDouble(data.get(i).getExtra_cost());

                if (extraCost > 0) {
                    customBinding.AccentHeading.setText("Price " + fabricSelectionGetPieceTypesResponse.getData().get(i).getExtra_cost() + " USD");
                }
                get_Accent_Data();
            }
        }
        setPieceTypeAdapter(fabricSelectionGetPieceTypesResponse.getData());
    }    public final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) {
            if (url_type.equalsIgnoreCase("get piece type list")) {
                fabricSelectionGetPieceTypesResponse = (FabricSelectionGetPieceTypesResponse) o;
                if (fabricSelectionGetPieceTypesResponse != null) {
                    if (fabricSelectionGetPieceTypesResponse.getCode() == 1) {
                        callAttributesApi(fabricSelectionGetPieceTypesResponse.getData());
                        typeofPieceSelected = fabricSelectionGetPieceTypesResponse.getData().get(0).getName();
                        SelectedPieceIDForCart = fabricSelectionGetPieceTypesResponse.getData().get(0).getId();
                        fabricSelectionGetPieceTypesResponse.getData().get(0).setGetPieceSelected(true);
                        hideLayoutAccToSelection();
                    } else {
                        CommFunc.ShowStatusPop(CustomizationAct.this, fabricSelectionGetPieceTypesResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(CustomizationAct.this, getResources().getString(R.string.try_again), false);
                }
            } else if (url_type.equalsIgnoreCase("get lining data")) {
                CommFunc.DismissDialog();
                liningResponse = (LiningResponse) o;
                if (liningResponse != null) {
                    if (liningResponse.getCode() == 1 && liningResponse.getData().size() > 0) {
                        if (previousSelectedPieceValues != null) {
                            JSONObject jo = new JSONObject((Map) previousSelectedPieceValues);
                            try {
                                JSONArray ja = jo.getJSONArray("features");
                                for (int j = 0; j < ja.length(); j++) {
                                    JSONObject jsonObject = (JSONObject) ja.get(j);
                                    if (jsonObject.getString("feature_name").equalsIgnoreCase("Lining")) {

                                        JSONObject jso = (JSONObject) jsonObject.get("selected_value");
                                        int selectedFabricId = jso.getInt("id");

                                        for (int k = 0; k < liningResponse.getData().size(); k++) {
                                            if (liningResponse.getData().get(k).getFabricId() == selectedFabricId) {
                                                liningResponse.getData().get(k).setLiningSelected(true);
                                                SELECTED_LININIG_DATA = liningResponse.getData().get(k);
                                                get_feature_Images();
                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setliningAdapter();
                    } else {
                        CommFunc.ShowStatusPop(CustomizationAct.this, liningResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(CustomizationAct.this, getResources().getString(R.string.try_again), false);
                }
            } else if (url_type.equalsIgnoreCase("get style option data")) {
                CommFunc.DismissDialog();
                styleResponse = (StyleResponse) o;
                if (styleResponse != null) {

                    if (styleResponse.getCode() == 1 && styleResponse.getData().size() > 0) {
                        if (previousSelectedPieceValues != null) {
                            JSONObject jo = new JSONObject((Map) previousSelectedPieceValues);
                            try {
                                JSONArray ja = jo.getJSONArray("features");
                                for (int j = 0; j < ja.length(); j++) {
                                    JSONObject jsonObject = (JSONObject) ja.get(j);
                                    if (jsonObject.getString("feature_name").equalsIgnoreCase("Style")) {

                                        JSONArray jso = (JSONArray) jsonObject.get("selected_value");
                                        for (int s = 0; s < jso.length(); s++) {
                                            JSONObject jsonStyle = (JSONObject) jso.get(s);
                                            int selectedStyleId = jsonStyle.getInt("id");
                                            if (selectedStyleId == styleResponse.getData().get(s).getId()) {

                                                int selectedChoiceId = jsonStyle.getInt("choice_id");
                                                if (styleResponse.getData().get(s).getChoices().size() > 0) {
                                                    for (int si = 0; si < styleResponse.getData().get(s).getChoices().size(); si++) {
                                                        if (styleResponse.getData().get(s).getChoices().get(si).getId() ==
                                                                selectedChoiceId) {
                                                            styleResponse.getData().get(s).getChoices().get(si).setIsSelected(1);
                                                        } else {
                                                            styleResponse.getData().get(s).getChoices().get(si).setIsSelected(0);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                System.out.println("dATA SET IN style response for after selection");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        get_feature_Images();
                        setStyleAdapter(styleResponse.getData());

                    } else {
                        CommFunc.ShowStatusPop(CustomizationAct.this, styleResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(CustomizationAct.this, getResources().getString(R.string.try_again), false);
                }
            } else if (url_type.equalsIgnoreCase("get feature Image list")) {
                CommFunc.DismissDialog();
                getFeatureImagesResponse = new GetFeatureImagesResponse();
                getFeatureImagesResponse = (GetFeatureImagesResponse) o;
                if (getFeatureImagesResponse != null) {
                    if (getFeatureImagesResponse.getCode() == 1) {
                        if (currentImageTYpe.equalsIgnoreCase("front")) {
                            setFrontImagesAccTOSelection();
                        } else {
                            setRearImagesAccTOSelection();
                        }

                    } else {
                        CommFunc.ShowStatusPop(CustomizationAct.this, getFeatureImagesResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(CustomizationAct.this, getResources().getString(R.string.try_again), false);
                }
            } else if (url_type.equalsIgnoreCase("get accent option data")) {
                CommFunc.DismissDialog();
                accentResponse = (AccentResponse) o;
                if (accentResponse != null) {
                    if (accentResponse.getCode() == 1 && accentResponse.getData().size() > 0) {

                        if (previousSelectedPieceValues != null) {

                            System.out.println("*********************************************************");
                            JSONObject jo = new JSONObject((Map) previousSelectedPieceValues);
                            try {
                                JSONArray ja = jo.getJSONArray("features");
                                System.out.println(ja);
                                for (int j = 0; j < ja.length(); j++) {
                                    JSONObject jsonObject = (JSONObject) ja.get(j);
                                    if (jsonObject.getString("feature_name").equalsIgnoreCase("Accent")) {

                                        JSONArray jso = (JSONArray) jsonObject.get("selected_value");//selected_value
                                        System.out.println("*********************************************************");
                                        System.out.println(jso);

                                        for (int a = 0; a < jso.length(); a++) {

                                            JSONObject indexData = (JSONObject) jso.getJSONObject(a);

                                            int indexId = indexData.getInt("id");

                                            if (indexId == accentResponse.getData().get(a).getId()) {

                                                int indexChoiceID = indexData.getInt("choice_id");

                                                for (int b = 0; b < accentResponse.getData().get(a).getChoices().size(); b++) {

                                                    if (indexChoiceID == accentResponse.getData().get(a).getChoices().get(b).getId()) {
                                                        accentResponse.getData().get(a).getChoices().get(b).setIsaccentChoiceSelected(true);

                                                        if (accentResponse.getData().get(a).getChoices().get(b).getAdditionalInfos() != null && accentResponse.getData().get(a).getChoices().get(b).getAdditionalInfos().size() > 0) {

                                                            JSONArray indexAdditionalInfo = (JSONArray) indexData.get("additional_infos");
                                                            for (int addInfo = 0; addInfo < accentResponse.getData().get(a).getChoices().get(b).getAdditionalInfos().size(); addInfo++) {

                                                                JSONObject addInfoObject = (JSONObject) indexAdditionalInfo.get(addInfo);
                                                                String addInfoValue = addInfoObject.getString("value");

                                                                if (accentResponse.getData().get(a).getChoices().get(b).getAdditionalInfos().get(addInfo).getImages().size() > 0) {

                                                                    for (int c = 0; c < accentResponse.getData().get(a).getChoices().get(b).getAdditionalInfos().get(addInfo).getImages().size(); c++) {

                                                                        if (addInfoValue.equalsIgnoreCase(accentResponse.getData().get(a).getChoices().get(b).getAdditionalInfos().get(addInfo).getImages().get(c).getDisplayText())) {
                                                                            accentResponse.getData().get(a).getChoices().get(b).getAdditionalInfos().get(addInfo).getImages().get(c).setAccentImageSelected(true);
                                                                        }
                                                                    }
                                                                } else {
                                                                    accentResponse.getData().get(a).getChoices().get(b).getAdditionalInfos().get(addInfo).setValue(addInfoValue);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        setAccentAdapter(accentResponse.getData());
                    } else {
                        CommFunc.ShowStatusPop(CustomizationAct.this, accentResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(CustomizationAct.this, getResources().getString(R.string.try_again), false);
                }
            } else if (url_type.equalsIgnoreCase("add product to cart")) {
                CommFunc.DismissDialog();
                addToCartResponse = (AddToCartResponse) o;
                if (addToCartResponse != null) {
                    if (addToCartResponse.getCode() == 1) {
                        isZoom = false;
                        Intent cartintent = new Intent(CustomizationAct.this, OrderCartAct.class);
                        OrderCartAct.ACTIVITY_FROM = "CustomizationAct";
                        startActivity(cartintent);
                        finish();

                    } else {
                        CommFunc.ShowStatusPop(CustomizationAct.this, addToCartResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(CustomizationAct.this, getResources().getString(R.string.try_again), false);
                }
            } else if (url_type.equalsIgnoreCase("get fabric data without filter")) {
                CommFunc.DismissDialog();
                fabricListResponse = (FabricFilterResponse) o;
                if (fabricListResponse != null) {
                    if (fabricListResponse.getCode() == 1 && fabricListResponse.getData().size() > 0) {
                        if (previousSelectedPieceValues != null) {
                            JSONObject jo = new JSONObject((Map) previousSelectedPieceValues);
                            try {
                                JSONArray features_jsonA = jo.getJSONArray("features");
                                for (int j = 0; j < features_jsonA.length(); j++) {
                                    JSONObject featureName_JO = (JSONObject) features_jsonA.get(j);
                                    if (featureName_JO.getString("feature_name").equalsIgnoreCase("Fabric")) {
                                        JSONObject selected_valueJO = (JSONObject) featureName_JO.get("selected_value");
                                        int selectedFabricId = selected_valueJO.getInt("id");

                                        for (int k = 0; k < fabricListResponse.getData().size(); k++) {
                                            if (fabricListResponse.getData().get(k).getFabricId() == selectedFabricId) {
                                                fabricListResponse.getData().get(k).setSelect(true);
                                                SELECTED_FABRIC = fabricListResponse.getData().get(k);
                                                get_feature_Images();
                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setFabricOPtionAdapter(fabricListResponse.getData());
                    } else {
                        CommFunc.ShowStatusPop(CustomizationAct.this, fabricListResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(CustomizationAct.this, getResources().getString(R.string.try_again), false);
                }
            }
        }

        @Override
        public void onError(String message) {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(CustomizationAct.this, message, false);
        }
    };

    private void setPieceTypeAdapter(List<FabricSelectionGetPieceTypesResponse.DataItem> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CustomizationAct.this, LinearLayoutManager.HORIZONTAL, false);
        customBinding.textRcy.setLayoutManager(linearLayoutManager);
        customizeTextAdapter = new CustomizeTextAdapter(CustomizationAct.this, data);
        customBinding.textRcy.setAdapter(customizeTextAdapter);
        customBinding.textRcy.setHasFixedSize(true);
        customBinding.textRcy.setItemViewCacheSize(20);
        customBinding.textRcy.setDrawingCacheEnabled(true);
        customBinding.textRcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        customBinding.textRcy.setNestedScrollingEnabled(false);

        customBinding.textRcy.addOnItemTouchListener(new RecyclerTouchListener(CustomizationAct.this,
                customBinding.textRcy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                for (int i = 0; i < fabricSelectionGetPieceTypesResponse.getData().size(); i++) {
                    if (i == position) {
                        topRecyclerPosition = position;
                        typeofPieceSelected = data.get(position).getName();
                        SelectedPieceIDForCart = fabricSelectionGetPieceTypesResponse.getData().get(i).getId();
                        data.get(position).setGetPieceSelected(true);
                    } else {
                        data.get(i).setGetPieceSelected(false);
                    }
                }
                hideLayoutAccToSelection();
                customizeTextAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void setliningAdapter() {

        GridLayoutManager layoutManager = new GridLayoutManager(CustomizationAct.this, 3);
        customBinding.liningTYpeREcy.setLayoutManager(layoutManager);
        LiningAdapter liningAdapter = new LiningAdapter(CustomizationAct.this, liningResponse.getData());
        customBinding.liningTYpeREcy.setAdapter(liningAdapter);
        customBinding.liningTYpeREcy.setHasFixedSize(true);
        customBinding.liningTYpeREcy.setItemViewCacheSize(20);
        customBinding.liningTYpeREcy.setDrawingCacheEnabled(true);
        customBinding.liningTYpeREcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        customBinding.liningTYpeREcy.setNestedScrollingEnabled(false);

        customBinding.liningTYpeREcy.addOnItemTouchListener(new RecyclerTouchListener(CustomizationAct.this,
                customBinding.liningTYpeREcy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                for (int i = 0; i < liningResponse.getData().size(); i++) {
                    if (i == position) {
                        if (liningResponse.getData().get(position).isLiningSelected()) {
                            liningResponse.getData().get(i).setLiningSelected(false);
                            SELECTED_LININIG_DATA = null;
                        } else {
                            SELECTED_LININIG_DATA = liningResponse.getData().get(position);
                            liningResponse.getData().get(i).setLiningSelected(true);
                        }
                        liningAdapter.notifyItemChanged(i);
                    } else {
                        liningResponse.getData().get(i).setLiningSelected(false);
                        liningAdapter.notifyItemChanged(i);
                    }
                }
                get_feature_Images();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void setStyleAdapter(List<StyleResponse.DataItem> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(CustomizationAct.this, LinearLayoutManager.VERTICAL, false);
        customBinding.styleTYpeREcy.setLayoutManager(layoutManager);
        StyleAdapter styleAdapter = new StyleAdapter(CustomizationAct.this, data);
        customBinding.styleTYpeREcy.setAdapter(styleAdapter);
        customBinding.styleTYpeREcy.setHasFixedSize(true);
        customBinding.styleTYpeREcy.setItemViewCacheSize(20);
        customBinding.styleTYpeREcy.setDrawingCacheEnabled(true);
        customBinding.styleTYpeREcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        customBinding.styleTYpeREcy.setNestedScrollingEnabled(false);
    }

    private void setAccentAdapter(List<AccentResponse.DataItem> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(CustomizationAct.this, 1);
        customBinding.accentTYpeREcy.setLayoutManager(layoutManager);
        AccentAdapter accentAdapter = new AccentAdapter(CustomizationAct.this, data);
        customBinding.accentTYpeREcy.setAdapter(accentAdapter);
        customBinding.accentTYpeREcy.setHasFixedSize(true);
        customBinding.accentTYpeREcy.setItemViewCacheSize(20);
        customBinding.accentTYpeREcy.setDrawingCacheEnabled(true);
        customBinding.accentTYpeREcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        customBinding.accentTYpeREcy.setNestedScrollingEnabled(false);
    }

    private void setFabricOPtionAdapter(List<FabricFilterResponse.DataItem> data) {

        if (data.size() == 1) {
            data.get(0).setSelect(true);
            SELECTED_FABRIC = data.get(0);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(CustomizationAct.this, 3);
        customBinding.fabTYpeREcy.setLayoutManager(layoutManager);
        CusFabricAdapter cusFabricOPtionAdapter = new CusFabricAdapter(CustomizationAct.this, data);
        customBinding.fabTYpeREcy.setAdapter(cusFabricOPtionAdapter);
        customBinding.fabTYpeREcy.setHasFixedSize(true);
        customBinding.fabTYpeREcy.setItemViewCacheSize(50);
        customBinding.fabTYpeREcy.setDrawingCacheEnabled(true);
        customBinding.fabTYpeREcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        customBinding.fabTYpeREcy.setNestedScrollingEnabled(false);

        customBinding.fabTYpeREcy.addOnItemTouchListener(new RecyclerTouchListener(CustomizationAct.this,
                customBinding.fabTYpeREcy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                for (int i = 0; i < fabricListResponse.getData().size(); i++) {
                    if (i == position) {
                        SELECTED_FABRIC = fabricListResponse.getData().get(position);
                        fabricListResponse.getData().get(i).setSelect(true);
                    } else {
                        fabricListResponse.getData().get(i).setSelect(false);
                    }
                }
                cusFabricOPtionAdapter.notifyDataSetChanged();
                get_feature_Images();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void hideLayoutAccToSelection() {
        if (typeofPieceSelected.equalsIgnoreCase("Fabric")) {
            customBinding.selLining.setText("Select Fabric");
            customBinding.fabTYpeREcy.setVisibility(View.VISIBLE);
            customBinding.selLining.setVisibility(View.VISIBLE);
            customBinding.liningTYpeREcy.setVisibility(View.GONE);
            customBinding.styleTYpeREcy.setVisibility(View.GONE);
            customBinding.accentTYpeREcy.setVisibility(View.GONE);
            customBinding.AccentHeading.setVisibility(View.GONE);
            customBinding.LiningHeader.setVisibility(View.GONE);
        } else if (typeofPieceSelected.equalsIgnoreCase("Lining")) {
            customBinding.liningTYpeREcy.setVisibility(View.VISIBLE);
            customBinding.selLining.setVisibility(View.GONE);
            customBinding.LiningHeader.setVisibility(View.VISIBLE);
            customBinding.fabTYpeREcy.setVisibility(View.GONE);
            customBinding.styleTYpeREcy.setVisibility(View.GONE);
            customBinding.accentTYpeREcy.setVisibility(View.GONE);
            customBinding.AccentHeading.setVisibility(View.GONE);
        } else if (typeofPieceSelected.equalsIgnoreCase("Style")) {
            customBinding.styleTYpeREcy.setVisibility(View.VISIBLE);
            customBinding.fabTYpeREcy.setVisibility(View.GONE);
            customBinding.LiningHeader.setVisibility(View.GONE);
            customBinding.liningTYpeREcy.setVisibility(View.GONE);
            customBinding.selLining.setVisibility(View.GONE);
            customBinding.accentTYpeREcy.setVisibility(View.GONE);
            customBinding.AccentHeading.setVisibility(View.GONE);
        } else if (typeofPieceSelected.equalsIgnoreCase("Accent")) {
            customBinding.accentTYpeREcy.setVisibility(View.VISIBLE);
            customBinding.AccentHeading.setVisibility(View.VISIBLE);
            customBinding.fabTYpeREcy.setVisibility(View.GONE);
            customBinding.LiningHeader.setVisibility(View.GONE);
            customBinding.liningTYpeREcy.setVisibility(View.GONE);
            customBinding.selLining.setVisibility(View.GONE);
            customBinding.styleTYpeREcy.setVisibility(View.GONE);

        }
    }

    private void setFrontImagesAccTOSelection() {
        customBinding.proImageRecy2.removeAllViews();
        if (getFeatureImagesResponse != null && getFeatureImagesResponse.getData().getFront().size() > 0) {
            for (FrontItem frontItem : getFeatureImagesResponse.getData().getFront()) {
                RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                ImageView image = new ImageView(this);
                image.setLayoutParams(vp);
                customBinding.proImageRecy2.addView(image);
                Glide.with(CustomizationAct.this)
                        .load(frontItem.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(image);


            }
        }
    }

    private void setRearImagesAccTOSelection() {
        customBinding.rearImageLay.removeAllViews();
        if (getFeatureImagesResponse != null && getFeatureImagesResponse.getData().getRear().size() > 0) {
            for (RearItem rearItem : getFeatureImagesResponse.getData().getRear()) {
                RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                ImageView image1 = new ImageView(this);
                image1.setLayoutParams(vp);
                customBinding.rearImageLay.addView(image1);
                Glide.with(CustomizationAct.this)
                        .load(rearItem.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(image1);
            }
        }
    }

    private void addCartDataBeforeCAllingApi() {
        isZoom = false;
        boolean checkIfListIsEmpty = false;
        List<Object> featureList = new ArrayList<>();

        for (FabricSelectionGetPieceTypesResponse.DataItem i : fabricSelectionGetPieceTypesResponse.getData()) {
            HashMap<String, Object> selectedAttribute = new HashMap<>();

            if (i.getName().equalsIgnoreCase("Lining")) {

                HashMap<String, Object> selectedLiningMap = new HashMap<>();
                for (LiningResponse.DataItem j : liningResponse.getData()) {
                    if (j.isLiningSelected()) {
                        selectedLiningMap.put("id", j.getFabricId());
                        selectedLiningMap.put("fabric_name", j.getDescription());
                        selectedLiningMap.put("fabric_image", j.getImage());

                    }
                }
                if (selectedLiningMap.size() > 0) {
                    selectedAttribute.put("id", i.getId());
                    selectedAttribute.put("feature_name", i.getName());
                    selectedAttribute.put("selected_value", selectedLiningMap);
                    featureList.add(selectedAttribute);
                }
            } else if (i.getName().equalsIgnoreCase("style")) {
                List<Object> selectedStyleArray = new ArrayList<>();
                for (StyleResponse.DataItem j : styleResponse.getData()) {
                    HashMap<String, Object> selectedStyleChoice;
                    if (j.getChoices().size() > 0) {
                        selectedStyleChoice = new HashMap<>();
                        for (StyleResponse.DataItem.ChoicesItem k : j.getChoices()) {
                            if (k.getIsSelected() == 1) {
                                selectedStyleChoice.put("id", j.getId());
                                selectedStyleChoice.put("choice_id", k.getId());
                            }
                        }

                        if (fabricSelectionGetPieceTypesResponse.getData().get(0).getName().equalsIgnoreCase("fabric")) {
                            if (selectedStyleChoice.isEmpty()) {
                                // show pop up
                                CommFunc.ShowStatusPop(this, "please select style", false);
                                checkIfListIsEmpty = true;
                                break;
                            }
                        }
                        selectedStyleArray.add(selectedStyleChoice);
                    }
                }
                selectedAttribute.put("id", i.getId());
                selectedAttribute.put("feature_name", i.getName());
                selectedAttribute.put("selected_value", selectedStyleArray);
                featureList.add(selectedAttribute);
            } else if (i.getName().equalsIgnoreCase("Accent")) {
                List<Object> selectedAccentArray = new ArrayList<>();
                for (AccentResponse.DataItem l : accentResponse.getData()) {
                    if (l.getChoices().size() > 0) {
                        for (AccentResponse.DataItem.ChoicesItem m : l.getChoices()) {
                            HashMap<String, Object> selectedChoiceMap = new HashMap<>();
                            if (m.isIsaccentChoiceSelected()) {
                                List<Object> selectedAdditionalInfoArray = new ArrayList<>();
                                if (m.getAdditionalInfos().size() > 0) {
                                    HashMap<String, Object> selectedAdditionalImagesMap;
                                    for (AccentResponse.DataItem.ChoicesItem.AdditionalInfosItem n : m.getAdditionalInfos()) {
                                        selectedAdditionalImagesMap = new HashMap<>();

                                        if (n.getImages().size() > 0) {
                                            for (AccentResponse.DataItem.ChoicesItem.AdditionalInfosItem.ImagesItem o : n.getImages()) {
                                                if (o.isAccentImageSelected()) {
                                                    selectedAdditionalImagesMap.put("info_id", n.getInfoId());
                                                    selectedAdditionalImagesMap.put("value", o.getDisplayText());
                                                }
                                            }
                                        } else {
                                            System.out.println("text case ");
                                            if (n.getValue() != null && !n.getValue().isEmpty()) {
                                                selectedAdditionalImagesMap.put("info_id", n.getInfoId());
                                                selectedAdditionalImagesMap.put("value", n.getValue()); // value from edit textt
                                            } else {
                                                CommFunc.ShowStatusPop(this, "Please enter additional information.", false);
                                                checkIfListIsEmpty = true;
                                                break;
                                            }
                                        }

                                        if (selectedAdditionalImagesMap.isEmpty()) {
                                            // show pop up
                                            CommFunc.ShowStatusPop(this, "Please enter additional information.", false);
                                            checkIfListIsEmpty = true;
                                            break;
                                        }
                                        selectedAdditionalInfoArray.add(selectedAdditionalImagesMap);
                                    }
                                    selectedChoiceMap.put("id", l.getId());
                                    selectedChoiceMap.put("choice_id", m.getId());
                                    selectedChoiceMap.put("additional_infos", selectedAdditionalInfoArray);
                                } else {
                                    selectedChoiceMap.put("id", l.getId());
                                    selectedChoiceMap.put("choice_id", m.getId());
                                }
                                selectedAccentArray.add(selectedChoiceMap);
                            }
                        }
                    }
                }
                selectedAttribute.put("id", i.getId());
                selectedAttribute.put("feature_name", i.getName());
                selectedAttribute.put("selected_value", selectedAccentArray);
                featureList.add(selectedAttribute);
            } else if (i.getName().equalsIgnoreCase("Fabric")) {
                HashMap<String, Object> selectedFabricMap = new HashMap<>();
                for (FabricFilterResponse.DataItem j : fabricListResponse.getData()) {
                    if (j.getSelect()) {
                        selectedFabricMap.put("id", j.getFabricId());
                        selectedFabricMap.put("fabric_name", j.getDescription());
                        selectedFabricMap.put("fabric_image", j.getImage());
                    }
                }
                if (selectedFabricMap.isEmpty()) {
                    // show pop up
                    CommFunc.ShowStatusPop(this, "please select fabric", false);
                    checkIfListIsEmpty = true;
                    break;
                }
                selectedAttribute.put("id", i.getId());
                selectedAttribute.put("feature_name", i.getName());
                selectedAttribute.put("selected_value", selectedFabricMap);
                featureList.add(selectedAttribute);
            }
        }

        HashMap<String, Object> optionsDataHashMap = new HashMap<>();
        int PieceIDForAddToCart = 0;
        if (!sessionManager.getPieceId().isEmpty()) {
            PieceIDForAddToCart = Integer.parseInt(sessionManager.getPieceId());
        }
        optionsDataHashMap.put("piece_id", PieceIDForAddToCart);
        optionsDataHashMap.put("comment", comment_text);
        optionsDataHashMap.put("features", featureList);
        List<Object> optionsList = new ArrayList<>();
        optionsList.add(optionsDataHashMap);
        if (!checkIfListIsEmpty) {
            if (sessionManager.getTypeId().equalsIgnoreCase("1")) {

                if (sessionManager.getIsMultipart().equalsIgnoreCase("0")) {
                    addToCartAPIToAddTYPEONEProduct(optionsList);
                } else {
                    ProductPieceSelecton.pieceSelectionResponse.getData().get(ITEM_POSITION).setPieceSelected(true);
                    ProductPieceSelecton.pieceSelectionResponse.getData().get(ITEM_POSITION).setObject_data(optionsDataHashMap);
                    ProductPieceSelecton.pieceSelectionAdapter.notifyDataSetChanged();
                    finish();
                }
            } else if (sessionManager.getTypeId().equalsIgnoreCase("2")) {
                if (sessionManager.getIsMultipart().equalsIgnoreCase("0")) {
                    addToCartAPIToAddTYPEONEProduct(optionsList);
                } else {
                    ProductPieceSelecton.pieceSelectionResponse.getData().get(ITEM_POSITION).setPieceSelected(true);
                    ProductPieceSelecton.pieceSelectionResponse.getData().get(ITEM_POSITION).setObject_data(optionsDataHashMap);
                    ProductPieceSelecton.pieceSelectionAdapter.notifyDataSetChanged();
                    finish();
                }
            }
        }
    }

    private void addToCartAPIToAddTYPEONEProduct(List<Object> resList) {

        LiningAddToCartRequest liningAddToCartRequest = null;
        if (InternetConnection.isConnected(CustomizationAct.this)) {
            if (sessionManager.getTypeId().equalsIgnoreCase("1")) {
                if (sessionManager.getIsMultipart().equalsIgnoreCase("0")) {
                    liningAddToCartRequest = new LiningAddToCartRequest(
                            Integer.parseInt(sessionManager.getItemId()),
                            Integer.parseInt(sessionManager.getTypeId()),
                            Integer.parseInt(sessionManager.getFabricId()),
                            sessionManager.getFabricName(),
                            Integer.parseInt(sessionManager.getIsMultipart()),
                            resList);
                }
            } else {
                finish();
                ProductPieceSelecton.options_data.add(resList);
            }
            CommFunc.ShowProgressbar(CustomizationAct.this);
            APICallList.LiningProductAddToCart(liningAddToCartRequest, "add product to cart", response, CustomizationAct.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }



}
