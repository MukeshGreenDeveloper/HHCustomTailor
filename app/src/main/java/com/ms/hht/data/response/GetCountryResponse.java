package com.ms.hht.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetCountryResponse {

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

		@SerializedName("country_id")
		private String countryId;

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setCountryId(String countryId){
			this.countryId = countryId;
		}

		public String getCountryId(){
			return countryId;
		}
	}
}