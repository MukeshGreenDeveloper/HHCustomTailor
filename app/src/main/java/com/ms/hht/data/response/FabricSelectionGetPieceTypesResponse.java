package com.ms.hht.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FabricSelectionGetPieceTypesResponse {

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

		@SerializedName("isGetPieceSelected")
		private boolean isGetPieceSelected;

		@SerializedName("extra_cost")
		private String extra_cost;

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

		public boolean isGetPieceSelected() {
			return isGetPieceSelected;
		}

		public void setGetPieceSelected(boolean getPieceSelected) {
			isGetPieceSelected = getPieceSelected;
		}

		public String getExtra_cost() {
			return extra_cost;
		}

		public void setExtra_cost(String extra_cost) {
			this.extra_cost = extra_cost;
		}
	}

}