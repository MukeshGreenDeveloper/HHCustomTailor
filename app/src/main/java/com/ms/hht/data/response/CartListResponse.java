package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListResponse {

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

        @SerializedName("quote_item")
        private List<QuoteItemItem> quoteItem;

        @SerializedName("quote_total")
        private double quoteTotal;

        @SerializedName("shipping_cost")
        private int shippingCost;

        @SerializedName("available_discount")
        private int availableDiscount;

        @SerializedName("quote_id")
        private int quoteId;

        @SerializedName("weight")
        private double weight;

        @SerializedName("grand_total")
        private double grandTotal;

        public List<QuoteItemItem> getQuoteItem() {
            return quoteItem;
        }

        public void setQuoteItem(List<QuoteItemItem> quoteItem) {
            this.quoteItem = quoteItem;
        }

        public double getQuoteTotal() {
            return quoteTotal;
        }

        public void setQuoteTotal(double quoteTotal) {
            this.quoteTotal = quoteTotal;
        }

        public int getShippingCost() {
            return shippingCost;
        }

        public void setShippingCost(int shippingCost) {
            this.shippingCost = shippingCost;
        }

        public int getAvailableDiscount() {
            return availableDiscount;
        }

        public void setAvailableDiscount(int availableDiscount) {
            this.availableDiscount = availableDiscount;
        }

        public int getQuoteId() {
            return quoteId;
        }

        public void setQuoteId(int quoteId) {
            this.quoteId = quoteId;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public double getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(double grandTotal) {
            this.grandTotal = grandTotal;
        }

        public class QuoteItemItem{

            @SerializedName("quantity")
            private int quantity;

            @SerializedName("item_name")
            private String itemName;

            @SerializedName("comment")
            private String comment;

            @SerializedName("item_data")
            private List<ItemDataItem> itemData;

            @SerializedName("fabric_id")
            private int fabricId;

            @SerializedName("fabric_name")
            private String fabricName;

            @SerializedName("item_total")
            private double itemTotal;

            @SerializedName("quote_item_id")
            private int quoteItemId;

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getItemName() {
                return itemName;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public List<ItemDataItem> getItemData() {
                return itemData;
            }

            public void setItemData(List<ItemDataItem> itemData) {
                this.itemData = itemData;
            }

            public int getFabricId() {
                return fabricId;
            }

            public void setFabricId(int fabricId) {
                this.fabricId = fabricId;
            }

            public String getFabricName() {
                return fabricName;
            }

            public void setFabricName(String fabricName) {
                this.fabricName = fabricName;
            }

            public double getItemTotal() {
                return itemTotal;
            }

            public void setItemTotal(double itemTotal) {
                this.itemTotal = itemTotal;
            }

            public int getQuoteItemId() {
                return quoteItemId;
            }

            public void setQuoteItemId(int quoteItemId) {
                this.quoteItemId = quoteItemId;
            }

            public class ItemDataItem{

                @SerializedName("quote_item_data_id")
                private int quoteItemDataId;

                @SerializedName("additional_info")
                private AdditionalInfo additionalInfo;

                @SerializedName("piece_name")
                private String pieceName;

                @SerializedName("comment")
                private String comment;

                @SerializedName("selected_value")
                private List<SelectedValueItem> selectedValue;

                @SerializedName("feature_images")
                private FeatureImages featureImages;

                @SerializedName("piece_price")
                private int piecePrice;

                public int getQuoteItemDataId() {
                    return quoteItemDataId;
                }

                public void setQuoteItemDataId(int quoteItemDataId) {
                    this.quoteItemDataId = quoteItemDataId;
                }

                public AdditionalInfo getAdditionalInfo() {
                    return additionalInfo;
                }

                public void setAdditionalInfo(AdditionalInfo additionalInfo) {
                    this.additionalInfo = additionalInfo;
                }

                public String getPieceName() {
                    return pieceName;
                }

                public void setPieceName(String pieceName) {
                    this.pieceName = pieceName;
                }

                public String getComment() {
                    return comment;
                }

                public void setComment(String comment) {
                    this.comment = comment;
                }

                public List<SelectedValueItem> getSelectedValue() {
                    return selectedValue;
                }

                public void setSelectedValue(List<SelectedValueItem> selectedValue) {
                    this.selectedValue = selectedValue;
                }

                public FeatureImages getFeatureImages() {
                    return featureImages;
                }

                public void setFeatureImages(FeatureImages featureImages) {
                    this.featureImages = featureImages;
                }

                public int getPiecePrice() {
                    return piecePrice;
                }

                public void setPiecePrice(int piecePrice) {
                    this.piecePrice = piecePrice;
                }

                public class AdditionalInfo{

                    @SerializedName("heading")
                    private String heading;

                    @SerializedName("value")
                    private List<String> value;

                    public void setHeading(String heading){
                        this.heading = heading;
                    }

                    public String getHeading(){
                        return heading;
                    }

                    public void setValue(List<String> value){
                        this.value = value;
                    }

                    public List<String> getValue(){
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

                    @SerializedName("rear")
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
                        private int id;

                        @SerializedName("position")
                        private int position;

                        public String getImage() {
                            return image;
                        }

                        public void setImage(String image) {
                            this.image = image;
                        }

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public int getPosition() {
                            return position;
                        }

                        public void setPosition(int position) {
                            this.position = position;
                        }
                    }

                    public class RearItem{

                        @SerializedName("image")
                        private String image;

                        @SerializedName("id")
                        private int id;

                        @SerializedName("position")
                        private int position;

                        public String getImage() {
                            return image;
                        }

                        public void setImage(String image) {
                            this.image = image;
                        }

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public int getPosition() {
                            return position;
                        }

                        public void setPosition(int position) {
                            this.position = position;
                        }
                    }

                }
            }
        }
    }
}
