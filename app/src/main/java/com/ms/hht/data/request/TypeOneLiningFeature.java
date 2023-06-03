package com.ms.hht.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class TypeOneLiningFeature {
    @SerializedName("id")
    private Integer id;

    @SerializedName("feature_name")
    private String feature_name;

    @SerializedName("selected_value")
    private HashMap<String, Object> lining_selected_value;

    @SerializedName("selected_value")
    private List<TypeOneAccentValueReq> accent_selected_value;



    public TypeOneLiningFeature(Integer id, String feature_name, HashMap<String, Object> lining_selected_value) {
        this.id = id;
        this.feature_name = feature_name;
        this.lining_selected_value = lining_selected_value;
    }

    public TypeOneLiningFeature(Integer id, String feature_name, List<TypeOneAccentValueReq> accent_selected_value) {
        this.id = id;
        this.feature_name = feature_name;
        this.accent_selected_value = accent_selected_value;
    }

}

