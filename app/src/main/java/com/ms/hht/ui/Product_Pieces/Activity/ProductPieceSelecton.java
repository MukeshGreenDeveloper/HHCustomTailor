package com.ms.hht.ui.Product_Pieces.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ms.hht.R;
import com.ms.hht.data.response.ProductDescriptionRes;
import com.ms.hht.databinding.ActivityProductPieceSelectonBinding;
import com.ms.hht.ui.Product_Pieces.Adapter.PieceSelectionAdapter;
import com.ms.hht.data.request.LiningAddToCartRequest;
import com.ms.hht.data.response.AddToCartResponse;
import com.ms.hht.data.response.FabricFilterResponse;
import com.ms.hht.data.response.PieceSelectionResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.ui.customization.CustomizationProcess.Activity.CustomizationAct;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderCartAct;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.RecyclerTouchListener;
import com.ms.hht.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductPieceSelecton extends AppCompatActivity implements View.OnClickListener {

    public static FabricFilterResponse.DataItem SELECTED_FABRIC;
    public static ProductDescriptionRes productDescriptionRes;

    public static ActivityProductPieceSelectonBinding pieceSelectonBinding;
    public static PieceSelectionResponse pieceSelectionResponse;
//    public static int SUB_CATEGORY_ID_FROM_FAB_List_TO_PRO_PIECE_SEL = -1;

    public static List<Object> options_data = new ArrayList<>();
    public static List<Object> piece_data = new ArrayList<>();

    public static PieceSelectionAdapter pieceSelectionAdapter;
    SessionManager sessionManager;
    AddToCartResponse addToCartResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pieceSelectonBinding = ActivityProductPieceSelectonBinding.inflate(getLayoutInflater());
        setContentView(pieceSelectonBinding.getRoot());

        sessionManager = new SessionManager(ProductPieceSelecton.this);
        pieceSelectonBinding.PieceBackImg.setOnClickListener(this);
        pieceSelectonBinding.PieceCustomizeBtn.setOnClickListener(this);

        if (sessionManager.getTypeId().equalsIgnoreCase("2")) {
            pieceSelectonBinding.fabricLayout.setVisibility(View.GONE);

        } else {
            pieceSelectonBinding.fabricLayout.setVisibility(View.VISIBLE);
            pieceSelectonBinding.fabricDescription.setText(SELECTED_FABRIC.getDescription());
            Glide.with(ProductPieceSelecton.this).load(SELECTED_FABRIC.getImage())
                    .into(pieceSelectonBinding.fabricImage);
        }
        getPieceTYpes();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PieceBackImg:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.PieceCustomizeBtn:
                if (checkAllTrue()) {
                    if (sessionManager.getTypeId().equalsIgnoreCase("1")) {
                       LiningAddToCartRequest liningAddToCartRequest = new LiningAddToCartRequest(Integer.parseInt(sessionManager.getItemId()),
                               Integer.parseInt(sessionManager.getTypeId()),
                               SELECTED_FABRIC.getFabricId(),
                               SELECTED_FABRIC.getDescription(),
                               Integer.parseInt(sessionManager.getIsMultipart()),
                               piece_data);
                        CommFunc.ShowProgressbar(this);
                        APICallList.LiningProductAddToCart(liningAddToCartRequest, "add product to cart", response, ProductPieceSelecton.this);
                    }
                    else {
                        LiningAddToCartRequest addToCartRequest = new LiningAddToCartRequest(
                                Integer.parseInt(sessionManager.getItemId()),
                                Integer.parseInt(sessionManager.getTypeId()),
                                Integer.parseInt(sessionManager.getIsMultipart()),
                                piece_data);
                        CommFunc.ShowProgressbar(this);
                        APICallList.LiningProductAddToCart(addToCartRequest, "add product to cart", response, ProductPieceSelecton.this);
                    }

                } else {
                    CommFunc.ShowStatusPop(ProductPieceSelecton.this, "Please customize all the parts.", false);
                }
                break;
        }
    }

    private boolean checkAllTrue() {
        piece_data.clear();
        for (PieceSelectionResponse.DataItem i : pieceSelectionResponse.getData()) {
            if (i.isPieceSelected()) {
                piece_data.add(i.getObject_data());
            } else {
                return false;
            }
        }
        return true;
    }

    private void getPieceTYpes() {
        if (InternetConnection.isConnected(ProductPieceSelecton.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getPiece(productDescriptionRes.getData().getSubcategoryId(), "Piece Selection", response, ProductPieceSelecton.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) {
            if (url_type.equalsIgnoreCase("Piece Selection")) {
                CommFunc.DismissDialog();
                pieceSelectionResponse = (PieceSelectionResponse) o;
                if (pieceSelectionResponse != null) {
                    if (pieceSelectionResponse.getCode() == 1 && pieceSelectionResponse.getData().size() > 0 ){
                        pieceSelectonBinding.PieceName.setText(pieceSelectionResponse.getData().size() + " PIECE");
                        setPieceADapter(pieceSelectionResponse.getData());
                    }
                    else {
                        CommFunc.ShowStatusPop(ProductPieceSelecton.this, pieceSelectionResponse.getMessage(), false);
                    }
                }
                else {
                    CommFunc.ShowStatusPop(ProductPieceSelecton.this, "No data found. Please try again", false);
                }
            }
            else if (url_type.equalsIgnoreCase("add product to cart")) {
                CommFunc.DismissDialog();
                addToCartResponse = (AddToCartResponse) o;
                if (addToCartResponse != null) {
                    if (addToCartResponse.getCode() == 1) {
                        Intent cartintent = new Intent(ProductPieceSelecton.this, OrderCartAct.class);
                        OrderCartAct.ACTIVITY_FROM = "ProductPieceSelection";
                        startActivity(cartintent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    else {
                        CommFunc.ShowStatusPop(ProductPieceSelecton.this, addToCartResponse.getMessage(), false);
                    }
                }
                else {
                    CommFunc.ShowStatusPop(ProductPieceSelecton.this, "add to cart request failed. Please try again", false);
                }
            }
        }


        @Override
        public void onError(String message) {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(ProductPieceSelecton.this, message, false);
        }
    };

    private void setPieceADapter(List<PieceSelectionResponse.DataItem> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductPieceSelecton.this);
        pieceSelectonBinding.PieceRecy.setLayoutManager(layoutManager);
        pieceSelectionAdapter = new PieceSelectionAdapter(ProductPieceSelecton.this, data);
        pieceSelectonBinding.PieceRecy.setAdapter(pieceSelectionAdapter);
        pieceSelectonBinding.PieceRecy.setHasFixedSize(true);
        pieceSelectonBinding.PieceRecy.setItemViewCacheSize(6);
        pieceSelectonBinding.PieceRecy.setDrawingCacheEnabled(true);
        pieceSelectonBinding.PieceRecy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        pieceSelectonBinding.PieceRecy.addOnItemTouchListener(new RecyclerTouchListener(ProductPieceSelecton.this,
                pieceSelectonBinding.PieceRecy, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                    sessionManager.setPieceId(data.get(position).getId().toString());

                    for (int i=0 ; i<data.size() ; i++){
                        if (i<position){
                            if (!data.get(i).isPieceSelected()){
                                CommFunc.ShowStatusPop(ProductPieceSelecton.this, "Please customize the above parts first.", false);
                                break;
                            }
                        }
                        else if(i == position){

//                            Condition check for pieces for the same fabric.

                            if (sessionManager.getTypeId().equalsIgnoreCase("2")){
                                if ((pieceSelectionResponse.getData().get(0).getItemId().equals(
                                        pieceSelectionResponse.getData().get(position).getItemId())) &&
                                        (!pieceSelectionResponse.getData().get(0).getId().equals(
                                                pieceSelectionResponse.getData().get(position).getId()))){

                                    JSONObject selectedAttributes = new JSONObject((Map) pieceSelectionResponse.getData().get(0).getObject_data());
                                    if (selectedAttributes.length()>0){
                                        try {
                                            JSONArray ja = selectedAttributes.getJSONArray("features");
                                            for (int j=0 ; j<ja.length() ; j++ ){
                                                JSONObject jsonObject = (JSONObject) ja.get(j);
                                                if (jsonObject.getString("feature_name").equalsIgnoreCase("Fabric")){
                                                    JSONObject jso = (JSONObject) jsonObject.get("selected_value");
                                                    SELECTED_FABRIC = new FabricFilterResponse.DataItem();
                                                    SELECTED_FABRIC.setFabricId(jso.getInt("id"));
                                                    SELECTED_FABRIC.setDescription(jso.getString("fabric_name"));
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
//                            Condition check for the piece selection.
                            if (pieceSelectionResponse.getData().get(position).isPieceSelected()){
                                CustomizationAct.previousSelectedPieceValues = pieceSelectionResponse.getData().get(position).getObject_data();
                            }
                            System.out.println("PIECE PREVIEW++++++++++"+new Gson().toJson(CustomizationAct.previousSelectedPieceValues));

                                Intent i1 = new Intent(ProductPieceSelecton.this, CustomizationAct.class);
//                                CustomizationAct.SELECTED_FABRIC = new FabricFilterResponse.DataItem();
                                CustomizationAct.SELECTED_FABRIC = SELECTED_FABRIC;
                                CustomizationAct.ITEM_POSITION = position;
//                                CustomizationAct.PEICE_SELECTION = pieceSelectionResponse.getData().get(position);
                                CustomizationAct.PRODUCT_NAME = pieceSelectionResponse.getData().get(position).getName();
                                CustomizationAct.PIECE_ID_FROM_PROD_DESC_TO_FABRIC_SELECTION_TO_GET_PIECE_TYPE = data.get(position).getId();
                                CustomizationAct.SUB_CATEGORY_ID_FROM_PIECE_SEL_TO_CUSTOMIZATION_TO_CALL_PIECE_FABRIC_FILTER_API = data.get(position).getSubcategoryId();
                                startActivity(i1);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }
            }

            @Override
            public void onLongClick(View view, int position) {}
        }));
    }
}