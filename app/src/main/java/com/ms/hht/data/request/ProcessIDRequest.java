package com.ms.hht.data.request;


public class ProcessIDRequest {

    private String apiKey;
    private String merchantid;
    private String productname;
    private String gender;

    public ProcessIDRequest(String apiKey, String merchantid, String productname, String gender) {
        this.apiKey = apiKey;
        this.merchantid = merchantid;
        this.productname = productname;
        this.gender = gender;
    }
}
