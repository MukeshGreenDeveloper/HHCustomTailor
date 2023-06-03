package com.ms.hht.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class LiningAddToCartRequest {

    @SerializedName("item_id")
    private Integer itemId;

    @SerializedName("type_id")
    private Integer typeId;

    @SerializedName("fabric_id")
    private Integer fabricId;

    @SerializedName("fabric_name")
    private String fabricName;

    @SerializedName("is_multiple")
    private Integer isMultiple;

    @SerializedName("options_data")
    private List<Object> optionsData;

    public static class OptionsDataItem {

        @SerializedName("piece_id")
        private Integer pieceId;

        @SerializedName("features")
        private List<TypeOneLiningFeature> features;

        public OptionsDataItem(Integer pieceId, List<TypeOneLiningFeature> features) {
            this.pieceId = pieceId;
            this.features = features;
        }
    }

    public LiningAddToCartRequest(Integer itemId, Integer typeId, Integer fabricId, String fabricName, Integer isMultiple, List<Object> optionsData) {
        this.itemId = itemId;
        this.typeId = typeId;
        this.fabricId = fabricId;
        this.fabricName = fabricName;
        this.isMultiple = isMultiple;
        this.optionsData = optionsData;
    }

    public LiningAddToCartRequest(Integer itemId, Integer typeId, Integer isMultiple, List<Object> optionsData) {
        this.itemId = itemId;
        this.typeId = typeId;
        this.isMultiple = isMultiple;
        this.optionsData = optionsData;
    }
}