package com.ms.hht.data.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AccentResponse  {

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

        @SerializedName("name")
        private String name;

        @SerializedName("id")
        private Integer id;

        @SerializedName("choices")
        private List<ChoicesItem> choices;

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

        public void setChoices(List<ChoicesItem> choices){
            this.choices = choices;
        }

        public List<ChoicesItem> getChoices(){
            return choices;
        }

        public class ChoicesItem{

            @SerializedName("name")
            private String name;

            @SerializedName("id")
            private Integer id;

            private boolean isaccentChoiceSelected = false;

            public boolean isIsaccentChoiceSelected() {
                return isaccentChoiceSelected;
            }

            public void setIsaccentChoiceSelected(boolean isaccentChoiceSelected) {
                this.isaccentChoiceSelected = isaccentChoiceSelected;
            }

            @SerializedName("additional_infos")
            private List<AdditionalInfosItem> additionalInfos;

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

            public void setAdditionalInfos(List<AdditionalInfosItem> additionalInfos){
                this.additionalInfos = additionalInfos;
            }

            public List<AdditionalInfosItem> getAdditionalInfos(){
                return additionalInfos;
            }


            public class AdditionalInfosItem{

                @SerializedName("images")
                private List<ImagesItem> images;

                @SerializedName("info_id")
                private Integer infoId;

                @SerializedName("info_name")
                private String infoName;

                private String value = "";

                public void setImages(List<ImagesItem> images){
                    this.images = images;
                }

                public List<ImagesItem> getImages(){
                    return images;
                }

                public void setInfoId(Integer infoId){
                    this.infoId = infoId;
                }

                public Integer getInfoId(){
                    return infoId;
                }

                public void setInfoName(String infoName){
                    this.infoName = infoName;
                }

                public String getInfoName(){
                    return infoName;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public class ImagesItem{

                    @SerializedName("display_text")
                    private String displayText;

                    @SerializedName("display_image")
                    private String displayImage;

                    private  boolean isAccentImageSelected = false;


                    public boolean isAccentImageSelected() {
                        return isAccentImageSelected;
                    }

                    public void setAccentImageSelected(boolean accentImageSelected) {
                        isAccentImageSelected = accentImageSelected;
                    }

                    public void setDisplayText(String displayText){
                        this.displayText = displayText;
                    }

                    public String getDisplayText(){
                        return displayText;
                    }

                    public void setDisplayImage(String displayImage){
                        this.displayImage = displayImage;
                    }

                    public String getDisplayImage(){
                        return displayImage;
                    }
                }
            }
        }
    }

}