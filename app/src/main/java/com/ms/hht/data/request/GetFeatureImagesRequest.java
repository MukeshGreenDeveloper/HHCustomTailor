package com.ms.hht.data.request;

import java.util.ArrayList;
import java.util.List;

public class GetFeatureImagesRequest {

    int fabric_id = 0;
    int lining_fabric_id =0 ;
    List<Integer> choices = new ArrayList<>();

    public GetFeatureImagesRequest(int fabric_id, List<Integer> choices, int lining_fabric_id) {
        this.fabric_id = fabric_id;
        this.lining_fabric_id = lining_fabric_id;
        this.choices = choices;
    }
}
