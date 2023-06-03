package com.ms.hht.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class LiningFilterListRequest {
    @SerializedName("filter_data")
    private HashMap<String, ArrayList<Integer>> filterData;

    public LiningFilterListRequest(HashMap<String, ArrayList<Integer>> filterData) {
        this.filterData = filterData;
    }
    public HashMap<String, ArrayList<Integer>> getFilterData() {
        return filterData;
    }

    public void setFilterData(HashMap<String, ArrayList<Integer>> filterData) {
        this.filterData = filterData;
    }
}
