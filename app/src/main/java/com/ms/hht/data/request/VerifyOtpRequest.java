package com.ms.hht.data.request;

public class VerifyOtpRequest {

    int customer_id;
    String otp;
    String prompt;

    public VerifyOtpRequest(int customer_id, String otp, String prompt) {
        this.customer_id = customer_id;
        this.otp = otp;
        this.prompt = prompt;
    }
}
