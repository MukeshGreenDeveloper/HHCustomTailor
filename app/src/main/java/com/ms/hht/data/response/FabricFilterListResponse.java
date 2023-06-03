package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FabricFilterListResponse {

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

        @SerializedName("options")
        private List<OptionsItem> options;

        @SerializedName("filtername")
        private String filtername;

        public void setOptions(List<OptionsItem> options) {
            this.options = options;
        }

        public List<OptionsItem> getOptions() {
            return options;
        }

        public void setFiltername(String filtername) {
            this.filtername = filtername;
        }

        public String getFiltername() {
            return filtername;
        }

        public class OptionsItem {

            @SerializedName("color_id")
            public Integer colorId;

            @SerializedName("color_name")
            public String colorName;

            public boolean isSelected = false;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public void setColorId(Integer colorId) {
                this.colorId = colorId;
            }

            public Integer getColorId() {
                return colorId;
            }

            public void setColorName(String colorName) {
                this.colorName = colorName;
            }

            public String getColorName() {
                return colorName;
            }

        }

    }
}