package com.ms.hht.data.response.cartresponse;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	@SerializedName("userId")
	private String userId;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}
}