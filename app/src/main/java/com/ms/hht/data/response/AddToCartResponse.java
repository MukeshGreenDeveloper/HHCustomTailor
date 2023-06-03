package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

public class AddToCartResponse {

	@SerializedName("message")
	private String message;

	@SerializedName("code")
	private Integer code;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setCode(Integer code){
		this.code = code;
	}

	public Integer getCode(){
		return code;
	}
}