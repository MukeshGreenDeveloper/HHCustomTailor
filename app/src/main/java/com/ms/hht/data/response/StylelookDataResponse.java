package com.ms.hht.data.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


import java.util.List;

public class StylelookDataResponse {

    @SerializedName("code")
    @Nullable
    private Integer code;

    @SerializedName("data")
    @Nullable
    private List<DataItem> data;

    @SerializedName("message")
    @Nullable
    private String message;

    public class DataItem {
        @SerializedName("image")
        @Nullable
        private String image;

        @SerializedName("category_id")
        @Nullable
        private int categoryId;

        @SerializedName("item_id")
        @Nullable
        private String itemId;

        @SerializedName("name")
        @Nullable
        private String name;

        @Nullable
        public String getImage() {
            return image;
        }

        public void setImage(@Nullable String image) {
            this.image = image;
        }

        @Nullable
        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(@Nullable int categoryId) {
            this.categoryId = categoryId;
        }

        @Nullable
        public String getItemId() {
            return itemId;
        }

        public void setItemId(@Nullable String itemId) {
            this.itemId = itemId;
        }


        public void setName(@Nullable String name){
            this.name = name;
        }

        @Nullable
        public String getName(){
            return name;
        }
    }


    public void setCode(@Nullable Integer code){
        this.code = code;
    }

    @Nullable
    public Integer getCode(){
        return code;
    }

    public void setMessage(@Nullable String message){
        this.message = message;
    }

    @Nullable
    public String getMessage(){
        return message;
    }

    public void setData(@Nullable List<DataItem> data){
        this.data = data;
    }

    @Nullable
    public List<DataItem> getData(){
        return data;
    }


}
