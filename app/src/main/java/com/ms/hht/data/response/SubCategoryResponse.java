package com.ms.hht.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCategoryResponse {

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

		@SerializedName("subcategory_id")
		private Integer subcategoryId;

		@SerializedName("image")
		private String image;

		@SerializedName("item_id")
		private String itemId;

		@SerializedName("name")
		private String name;

		public void setSubcategoryId(Integer subcategoryId){
			this.subcategoryId = subcategoryId;
		}

		public Integer getSubcategoryId(){
			return subcategoryId;
		}

		public void setImage(String image){
			this.image = image;
		}

		public String getImage(){
			return image;
		}

		public void setItemId(String itemId){
			this.itemId = itemId;
		}

		public String getItemId(){
			return itemId;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}
	}
}