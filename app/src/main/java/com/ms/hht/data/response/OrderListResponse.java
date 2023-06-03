package com.ms.hht.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrderListResponse {

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

		@SerializedName("total_paid")
		private Integer totalPaid;

		@SerializedName("increment_id")
		private String incrementId;

		@SerializedName("created_at")
		private String createdAt;

		@SerializedName("order_id")
		private Integer orderId;

		@SerializedName("shipto")
		private String shipto;

		@SerializedName("status")
		private String status;

		public void setTotalPaid(Integer totalPaid){
			this.totalPaid = totalPaid;
		}

		public Integer getTotalPaid(){
			return totalPaid;
		}

		public void setIncrementId(String incrementId){
			this.incrementId = incrementId;
		}

		public String getIncrementId(){
			return incrementId;
		}

		public void setCreatedAt(String createdAt){
			this.createdAt = createdAt;
		}

		public String getCreatedAt(){
			return createdAt;
		}

		public void setOrderId(Integer orderId){
			this.orderId = orderId;
		}

		public Integer getOrderId(){
			return orderId;
		}

		public void setShipto(String shipto){
			this.shipto = shipto;
		}

		public String getShipto(){
			return shipto;
		}

		public void setStatus(String status){
			this.status = status;
		}

		public String getStatus(){
			return status;
		}
	}
}