package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailResponse {

    @SerializedName("code")
    private Integer code;

    @SerializedName("data")
    private Data data;

    @SerializedName("message")
    private String message;

    public void setCode(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
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

    public class Data{

        @SerializedName("quote_total")
        private Double quoteTotal;

        @SerializedName("shipping_cost")
        private Integer shippingCost;

        @SerializedName("total_paid")
        private Integer totalPaid;

        @SerializedName("quote_id")
        private Integer quoteId;

        @SerializedName("discount")
        private Integer discount;

        @SerializedName("weight")
        private Double weight;

        @SerializedName("quote_item")
        private List<QuoteItemItem> quoteItem;

        @SerializedName("grand_total")
        private Integer grandTotal;

        @SerializedName("shipping_address")
        private ShippingAddress shippingAddress;

        public void setQuoteTotal(Double quoteTotal){
            this.quoteTotal = quoteTotal;
        }

        public Double getQuoteTotal(){
            return quoteTotal;
        }

        public void setQuoteId(Integer quoteId){
            this.quoteId = quoteId;
        }

        public Integer getQuoteId(){
            return quoteId;
        }

        public void setQuoteItem(List<QuoteItemItem> quoteItem){
            this.quoteItem = quoteItem;
        }

        public List<QuoteItemItem> getQuoteItem(){
            return quoteItem;
        }


        public void setShippingAddress(ShippingAddress shippingAddress){
            this.shippingAddress = shippingAddress;
        }

        public Integer getShippingCost() {
            return shippingCost;
        }

        public void setShippingCost(Integer shippingCost) {
            this.shippingCost = shippingCost;
        }

        public Integer getTotalPaid() {
            return totalPaid;
        }

        public void setTotalPaid(Integer totalPaid) {
            this.totalPaid = totalPaid;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public Integer getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(Integer grandTotal) {
            this.grandTotal = grandTotal;
        }

        public ShippingAddress getShippingAddress(){
            return shippingAddress;
        }

        public class QuoteItemItem{

            @SerializedName("fabric_id")
            private Integer fabricId;

            @SerializedName("fabric_name")
            private String fabricName;

            @SerializedName("quantity")
            private Integer quantity;

            @SerializedName("item_name")
            private String itemName;

            @SerializedName("item_total")
            private Integer itemTotal;

            @SerializedName("quote_item_id")
            private Integer quoteItemId;

            @SerializedName("item_data")
            private List<ItemDataItem> itemData;

            public void setFabricId(Integer fabricId){
                this.fabricId = fabricId;
            }

            public Integer getFabricId(){
                return fabricId;
            }

            public void setFabricName(String fabricName){
                this.fabricName = fabricName;
            }

            public String getFabricName(){
                return fabricName;
            }

            public void setQuantity(Integer quantity){
                this.quantity = quantity;
            }

            public Integer getQuantity(){
                return quantity;
            }

            public void setItemName(String itemName){
                this.itemName = itemName;
            }

            public String getItemName(){
                return itemName;
            }

            public Integer getItemTotal() {
                return itemTotal;
            }

            public void setItemTotal(Integer itemTotal) {
                this.itemTotal = itemTotal;
            }

            public void setQuoteItemId(Integer quoteItemId){
                this.quoteItemId = quoteItemId;
            }

            public Integer getQuoteItemId(){
                return quoteItemId;
            }

            public void setItemData(List<ItemDataItem> itemData){
                this.itemData = itemData;
            }

            public List<ItemDataItem> getItemData(){
                return itemData;
            }


            public class ItemDataItem{

                private  boolean isSelectedItem = false;

                @SerializedName("quote_item_data_id")
                private Integer quoteItemDataId;

                @SerializedName("piece_price")
                private Integer piecePrice;

                @SerializedName("piece_name")
                private String pieceName;

                @SerializedName("comment")
                private String comment;

                @SerializedName("lining")
                private String lining;

                @SerializedName("additional_info")
                private AdditionalInfo additionalInfo;

                @SerializedName("selected_value")
                private List<SelectedValueItem> selectedValue;

                @SerializedName("feature_images")
                private FeatureImages featureImages;

                public boolean isSelectedItem() {
                    return isSelectedItem;
                }

                public void setSelectedItem(boolean selectedItem) {
                    isSelectedItem = selectedItem;
                }

                public void setQuoteItemDataId(Integer quoteItemDataId){
                    this.quoteItemDataId = quoteItemDataId;
                }

                public Integer getQuoteItemDataId(){
                    return quoteItemDataId;
                }

                public void setAdditionalInfo(AdditionalInfo additionalInfo){
                    this.additionalInfo = additionalInfo;
                }

                public String getLining() {
                    return lining;
                }

                public void setLining(String lining) {
                    this.lining = lining;
                }

                public AdditionalInfo getAdditionalInfo(){
                    return additionalInfo;
                }

                public void setPieceName(String pieceName){
                    this.pieceName = pieceName;
                }

                public String getPieceName(){
                    return pieceName;
                }

                public void setComment(String comment){
                    this.comment = comment;
                }

                public String getComment(){
                    return comment;
                }

                public void setSelectedValue(List<SelectedValueItem> selectedValue){
                    this.selectedValue = selectedValue;
                }

                public List<SelectedValueItem> getSelectedValue(){
                    return selectedValue;
                }

                public void setFeatureImages(FeatureImages featureImages){
                    this.featureImages = featureImages;
                }

                public FeatureImages getFeatureImages(){
                    return featureImages;
                }

                public void setPiecePrice(Integer piecePrice){
                    this.piecePrice = piecePrice;
                }

                public Integer getPiecePrice(){
                    return piecePrice;
                }
            }


            public class AdditionalInfo{

                @SerializedName("heading")
                private String heading;

                @SerializedName("value")
                private List<Object> value;

                public void setHeading(String heading){
                    this.heading = heading;
                }

                public String getHeading(){
                    return heading;
                }

                public void setValue(List<Object> value){
                    this.value = value;
                }

                public List<Object> getValue(){
                    return value;
                }
            }

            public class SelectedValueItem{

                @SerializedName("name")
                private String name;

                @SerializedName("value")
                private String value;

                public void setName(String name){
                    this.name = name;
                }

                public String getName(){
                    return name;
                }

                public void setValue(String value){
                    this.value = value;
                }

                public String getValue(){
                    return value;
                }
            }

            public class FeatureImages{

                private List<RearItem> rear;

                @SerializedName("front")
                private List<FrontItem> front;

                public void setRear(List<RearItem> rear){
                    this.rear = rear;
                }

                public List<RearItem> getRear(){
                    return rear;
                }

                public void setFront(List<FrontItem> front){
                    this.front = front;
                }

                public List<FrontItem> getFront(){
                    return front;
                }

                public class FrontItem{

                    @SerializedName("image")
                    private String image;

                    @SerializedName("id")
                    private Integer id;

                    @SerializedName("position")
                    private Integer position;

                    public void setImage(String image){
                        this.image = image;
                    }

                    public String getImage(){
                        return image;
                    }

                    public void setId(Integer id){
                        this.id = id;
                    }

                    public Integer getId(){
                        return id;
                    }

                    public void setPosition(Integer position){
                        this.position = position;
                    }

                    public Integer getPosition(){
                        return position;
                    }
                }

                public class RearItem{

                    @SerializedName("image")
                    private String image;

                    @SerializedName("id")
                    private Integer id;

                    @SerializedName("position")
                    private Integer position;

                    public void setImage(String image){
                        this.image = image;
                    }

                    public String getImage(){
                        return image;
                    }

                    public void setId(Integer id){
                        this.id = id;
                    }

                    public Integer getId(){
                        return id;
                    }

                    public void setPosition(Integer position){
                        this.position = position;
                    }

                    public Integer getPosition(){
                        return position;
                    }
                }
            }
        }
        public class ShippingAddress{

            @SerializedName("country")
            private String country;

            @SerializedName("city")
            private String city;

            @SerializedName("street")
            private String street;

            @SerializedName("postcode")
            private String postcode;

            @SerializedName("telephone")
            private String telephone;

            @SerializedName("region")
            private String region;

            public void setCountry(String country){
                this.country = country;
            }

            public String getCountry(){
                return country;
            }

            public void setCity(String city){
                this.city = city;
            }

            public String getCity(){
                return city;
            }

            public void setStreet(String street){
                this.street = street;
            }

            public String getStreet(){
                return street;
            }

            public void setPostcode(String postcode){
                this.postcode = postcode;
            }

            public String getPostcode(){
                return postcode;
            }

            public void setTelephone(String telephone){
                this.telephone = telephone;
            }

            public String getTelephone(){
                return telephone;
            }

            public void setRegion(String region){
                this.region = region;
            }

            public String getRegion(){
                return region;
            }
        }
    }
}