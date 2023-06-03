package com.ms.hht.data.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class StyleResponse {

    @SerializedName("code")
    private Integer code;

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("message")
    private String message;

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public List<DataItem> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public class DataItem {

        @SerializedName("name")
        private String name;

        @SerializedName("id")
        private Integer id;

        @SerializedName("choices")
        private List<ChoicesItem> choices;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public void setChoices(List<ChoicesItem> choices) {
            this.choices = choices;
        }

        public List<ChoicesItem> getChoices() {
            return choices;
        }

        public class ChoicesItem {

            @SerializedName("image")
            private String image;

            @SerializedName("is_selected")
            private Integer isSelected;

            @SerializedName("name")
            private String name;

            @SerializedName("id")
            private Integer id;

            public void setImage(String image) {
                this.image = image;
            }

            public String getImage() {
                return image;
            }

            public void setIsSelected(Integer isSelected) {
                this.isSelected = isSelected;
            }

            public Integer getIsSelected() {
                return isSelected;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getId() {
                return id;
            }
        }
    }
}