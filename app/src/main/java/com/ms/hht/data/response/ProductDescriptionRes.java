package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

public class ProductDescriptionRes {

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

		@SerializedName("subcategory_id")
		private Integer subcategoryId;

		@SerializedName("image")
		private String image;

		@SerializedName("piece_id")
		private Integer pieceId;

		@SerializedName("price")
		private String price;

		@SerializedName("name")
		private String name;

		@SerializedName("description")
		private String description;

		@SerializedName("id")
		private Integer id;

		@SerializedName("is_multiple")
		private Integer isMultiple;

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

		public void setPieceId(Integer pieceId){
			this.pieceId = pieceId;
		}

		public Integer getPieceId(){
			return pieceId;
		}

		public void setPrice(String price){
			this.price = price;
		}

		public String getPrice(){
			return price;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setDescription(String description){
			this.description = description;
		}

		public String getDescription(){
			return description;
		}

		public void setId(Integer id){
			this.id = id;
		}

		public Integer getId(){
			return id;
		}

		public void setIsMultiple(Integer isMultiple){
			this.isMultiple = isMultiple;
		}

		public Integer getIsMultiple(){
			return isMultiple;
		}
	}

}
