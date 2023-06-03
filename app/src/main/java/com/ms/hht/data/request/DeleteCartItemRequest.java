package com.ms.hht.data.request;

public class DeleteCartItemRequest {

    String type;
    int quote_item_id;
    double price;

    public DeleteCartItemRequest(String type, int quote_item_id, double price) {
        this.type = type;
        this.quote_item_id = quote_item_id;
        this.price = price;
    }
}
