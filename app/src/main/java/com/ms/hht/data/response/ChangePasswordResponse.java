package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse {

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

	}
}