package com.ms.hht.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PieceSelectionResponse {

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

	public class DataItem {

		@SerializedName("subcategory_id")
		private Integer subcategoryId;

		@SerializedName("images")
		private String images;

		@SerializedName("is_customizable")
		private Integer isCustomizable;

		@SerializedName("item_id")
		private Integer itemId;

		@SerializedName("price")
		private Integer price;

		@SerializedName("name")
		private String name;

		@SerializedName("template_id")
		private Integer templateId;

		@SerializedName("id")
		private Integer id;

		private Object object_data;

		private boolean isPieceSelected = false;

		public boolean isPieceSelected() {
			return isPieceSelected;
		}

		public Object getObject_data() {
			return object_data;
		}

		public void setObject_data(Object object_data) {
			this.object_data = object_data;
		}

		public void setPieceSelected(boolean pieceSelected) {
			isPieceSelected = pieceSelected;
		}

		public void setSubcategoryId(Integer subcategoryId){
			this.subcategoryId = subcategoryId;
		}

		public Integer getSubcategoryId(){
			return subcategoryId;
		}

		public void setImages(String images){
			this.images = images;
		}

		public String getImages(){
			return images;
		}

		public void setIsCustomizable(Integer isCustomizable){
			this.isCustomizable = isCustomizable;
		}

		public Integer getIsCustomizable(){
			return isCustomizable;
		}

		public void setItemId(Integer itemId){
			this.itemId = itemId;
		}

		public Integer getItemId(){
			return itemId;
		}

		public void setPrice(Integer price){
			this.price = price;
		}

		public Integer getPrice(){
			return price;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setTemplateId(Integer templateId){
			this.templateId = templateId;
		}

		public Integer getTemplateId(){
			return templateId;
		}

		public void setId(Integer id){
			this.id = id;
		}

		public Integer getId(){
			return id;
		}
	}
}