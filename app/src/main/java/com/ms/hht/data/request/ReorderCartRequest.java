package com.ms.hht.data.request;

import java.util.ArrayList;
import java.util.List;

public class ReorderCartRequest {

    List<Integer> quote_item_data_id = new ArrayList<>();

    public ReorderCartRequest(List<Integer> quote_item_data_id) {
        this.quote_item_data_id = quote_item_data_id;
    }
}
