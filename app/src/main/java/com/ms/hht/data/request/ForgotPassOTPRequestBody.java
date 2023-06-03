package com.ms.hht.data.request;

public class ForgotPassOTPRequestBody {
    String otp;
    String prompt;
    String customer_id;

    public ForgotPassOTPRequestBody(String otp, String prompt, String customer_id) {
        this.otp = otp;
        this.prompt = prompt;
        this.customer_id = customer_id;
    }
}