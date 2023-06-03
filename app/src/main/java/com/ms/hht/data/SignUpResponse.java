package com.ms.hht.data;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private Data data;

    @SerializedName("message")
    private String message;


    public static class Data{

        @SerializedName("id")
        private int id;
        @SerializedName("email")
        private String email;
        @SerializedName("password")
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
