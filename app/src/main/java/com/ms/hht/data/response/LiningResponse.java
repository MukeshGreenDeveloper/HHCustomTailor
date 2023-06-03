package com.ms.hht.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LiningResponse {

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

	public static class DataItem{

		@SerializedName("image")
		private String image;

		@SerializedName("fabric_id")
		private Integer fabricId;

		@SerializedName("description")
		private String description;

		private  boolean isLiningSelected = false;

		public boolean isLiningSelected() {
			return isLiningSelected;
		}

		public void setLiningSelected(boolean liningSelected) {
			isLiningSelected = liningSelected;
		}

		public void setImage(String image){
			this.image = image;
		}

		public String getImage(){
			return image;
		}

		public void setFabricId(Integer fabricId){
			this.fabricId = fabricId;
		}

		public Integer getFabricId(){
			return fabricId;
		}

		public void setDescription(String description){
			this.description = description;
		}

		public String getDescription(){
			return description;
		}
	}
}