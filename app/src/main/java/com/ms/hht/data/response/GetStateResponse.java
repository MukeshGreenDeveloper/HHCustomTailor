package com.ms.hht.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetStateResponse {

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

		@SerializedName("region_id")
		private Integer regionId;

		@SerializedName("name")
		private String name;

		public void setRegionId(Integer regionId){
			this.regionId = regionId;
		}

		public Integer getRegionId(){
			return regionId;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}
	}
}