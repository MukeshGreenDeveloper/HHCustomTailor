package com.ms.hht.data.request;

public class UpdateCartQuantityRequest {
    int quote_item_id;
    double qty;

    public UpdateCartQuantityRequest(int quote_item_id, double qty) {
        this.quote_item_id = quote_item_id;
        this.qty = qty;
    }
}
