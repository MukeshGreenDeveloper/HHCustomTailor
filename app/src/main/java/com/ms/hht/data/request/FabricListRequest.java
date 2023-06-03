package com.ms.hht.data.request;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FabricListRequest {

    @SerializedName("filter_data")
    private HashMap<String, ArrayList<Integer>> filterData;

    public FabricListRequest(HashMap<String, ArrayList<Integer>> filterData) {
        this.filterData = filterData;
    }
    public HashMap<String, ArrayList<Integer>> getFilterData() {
        return filterData;
    }

    public void setFilterData(HashMap<String, ArrayList<Integer>> filterData) {
        this.filterData = filterData;
    }
}
