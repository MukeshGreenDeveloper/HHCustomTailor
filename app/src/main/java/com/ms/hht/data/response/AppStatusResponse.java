package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

public class AppStatusResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private Integer status;

    public static class Data{

        @SerializedName("active")
        private Boolean active;

        public void setActive(Boolean active){
            this.active = active;
        }

        public Boolean isActive(){
            return active;
        }

    }

    public void setData(Data data){
        this.data = data;
    }

    public Data getData(){
        return data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

}
