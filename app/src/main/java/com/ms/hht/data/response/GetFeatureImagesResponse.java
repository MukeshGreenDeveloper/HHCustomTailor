package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFeatureImagesResponse {

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

		@SerializedName("rear")
		private List<RearItem> rear;

		@SerializedName("standard lining")
		private String standardLining;

		@SerializedName("front")
		private List<FrontItem> front;

		public void setRear(List<RearItem> rear){
			this.rear = rear;
		}

		public List<RearItem> getRear(){
			return rear;
		}

		public void setStandardLining(String standardLining){
			this.standardLining = standardLining;
		}

		public String getStandardLining(){
			return standardLining;
		}

		public void setFront(List<FrontItem> front){
			this.front = front;
		}

		public List<FrontItem> getFront(){
			return front;
		}
	}
}