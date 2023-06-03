package com.ms.hht.data.request;

public class UpdateDefaultAddressRequest {
    int shipping_address_id;
    int billing_address_id;

    public UpdateDefaultAddressRequest(int shipping_address_id, int billing_address_id) {
        this.shipping_address_id = shipping_address_id;
        this.billing_address_id = billing_address_id;
    }
}
