package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MeasurementHistoryResponse {

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

    public void setData(List<DataItem> data){
        this.data = data;
    }

    public List<DataItem> getData(){
        return data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public class DataItem{

        @SerializedName("general")
        private General general;

        @SerializedName("name")
        private String name;

        @SerializedName("isChecked")
        private boolean isChecked = false;

        @SerializedName("id")
        private Integer id;

        @SerializedName("measurement")
        private List<MeasurementItem> measurement;

        public void setGeneral(General general){
            this.general = general;
        }

        public General getGeneral(){
            return general;
        }

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public void setId(Integer id){
            this.id = id;
        }

        public Integer getId(){
            return id;
        }

        public void setMeasurement(List<MeasurementItem> measurement){
            this.measurement = measurement;
        }

        public List<MeasurementItem> getMeasurement(){
            return measurement;
        }
    }

    public class General{

        @SerializedName("shoe_size")
        private String shoeSize;

        @SerializedName("weight")
        private String weight;

        @SerializedName("preferred_fit")
        private String preferredFit;

        @SerializedName("age")
        private Integer age;

        @SerializedName("height")
        private String height;

        public void setShoeSize(String shoeSize){
            this.shoeSize = shoeSize;
        }

        public String getShoeSize(){
            return shoeSize;
        }

        public void setWeight(String weight){
            this.weight = weight;
        }

        public String getWeight(){
            return weight;
        }

        public void setPreferredFit(String preferredFit){
            this.preferredFit = preferredFit;
        }

        public String getPreferredFit(){
            return preferredFit;
        }

        public void setAge(Integer age){
            this.age = age;
        }

        public Integer getAge(){
            return age;
        }

        public void setHeight(String height){
            this.height = height;
        }

        public String getHeight(){
            return height;
        }
    }

    public class MeasurementItem{

        @SerializedName("pointName")
        private String pointName;

        @SerializedName("part")
        private String part;

        @SerializedName("valueIncm")
        private String valueIncm;

        @SerializedName("description")
        private String description;

        @SerializedName("value")
        private String value;

        public void setPointName(String pointName){
            this.pointName = pointName;
        }

        public String getPointName(){
            return pointName;
        }

        public void setPart(String part){
            this.part = part;
        }

        public String getPart(){
            return part;
        }

        public void setValueIncm(String valueIncm){
            this.valueIncm = valueIncm;
        }

        public String getValueIncm(){
            return valueIncm;
        }

        public void setDescription(String description){
            this.description = description;
        }

        public String getDescription(){
            return description;
        }

        public void setValue(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }
}
