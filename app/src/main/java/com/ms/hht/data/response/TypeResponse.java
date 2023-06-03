package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypeResponse {

    @SerializedName("code")
    private Integer code;

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("message")
    private String message;

    public void setCode(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }


    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }


    public static class DataItem{

        @SerializedName("name")
        private String name;

        @SerializedName("id")
        private Integer id;

        private boolean isSelected = false;

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        public void setId(Integer id){
            this.id = id;
        }

        public Integer getId(){
            return id;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }
}
