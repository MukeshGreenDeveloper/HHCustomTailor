package com.ms.hht.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FabricFilterResponse {

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("data")
    @Expose
    private List<DataItem> data;

    @SerializedName("message")
    @Expose
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataItem {
        @SerializedName("subcategory_id")
        @Expose
        private Integer subcategoryId;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("fabric_id")
        @Expose
        private Integer fabricId;

        @SerializedName("description")
        @Expose
        private String description;

        private Boolean isSelect = false;

        public Boolean getSelect() {
            return isSelect;
        }

        public void setSelect(Boolean select) {
            isSelect = select;
        }

        public Integer getSubcategoryId() {
            return subcategoryId;
        }

        public void setSubcategoryId(Integer subcategoryId) {
            this.subcategoryId = subcategoryId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getFabricId() {
            return fabricId;
        }

        public void setFabricId(Integer fabricId) {
            this.fabricId = fabricId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}