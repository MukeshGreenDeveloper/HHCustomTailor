package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

public class UpdateAddressResponse {

	@SerializedName("code")
	private Integer code;

	@SerializedName("data")
	private String data;

	@SerializedName("message")
	private String message;

	public void setCode(Integer code){
		this.code = code;
	}

	public Integer getCode(){
		return code;
	}

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}