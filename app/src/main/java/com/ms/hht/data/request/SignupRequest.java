package com.ms.hht.data.request;

public class SignupRequest {

    String firstname;
    String email;
    String password;


    public SignupRequest(String firstname, String email, String password) {
        this.firstname = firstname;
        this.email = email;
        this.password = password;
    }

    public SignupRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignupRequest(String email) {
        this.email = email;
    }
}
