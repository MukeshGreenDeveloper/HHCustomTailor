package com.ms.hht.data.request;

public class GeneratePasswordRequest {

    int customer_id;
    String new_password;

    public GeneratePasswordRequest(int customer_id, String new_password) {
        this.customer_id = customer_id;
        this.new_password = new_password;
    }
}
