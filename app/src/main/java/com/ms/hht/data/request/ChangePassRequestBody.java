package com.ms.hht.data.request;


public class ChangePassRequestBody {
    String old_password;
    String new_password;

    public ChangePassRequestBody(String old_password, String new_password) {
        this.old_password = old_password;
        this.new_password = new_password;
    }
}