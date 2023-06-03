package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

public class UpdateCartQuantityResponse {

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

		@SerializedName("shipping_amount")
		private Integer shippingAmount;

		@SerializedName("quote_total")
		private Integer quoteTotal;

		@SerializedName("available_discount")
		private Integer availableDiscount;

		@SerializedName("qty")
		private Integer qty;

		@SerializedName("grand_total")
		private Integer grandTotal;

		public void setShippingAmount(Integer shippingAmount){
			this.shippingAmount = shippingAmount;
		}

		public Integer getShippingAmount(){
			return shippingAmount;
		}

		public void setQuoteTotal(Integer quoteTotal){
			this.quoteTotal = quoteTotal;
		}

		public Integer getQuoteTotal(){
			return quoteTotal;
		}

		public void setAvailableDiscount(Integer availableDiscount){
			this.availableDiscount = availableDiscount;
		}

		public Integer getAvailableDiscount(){
			return availableDiscount;
		}

		public void setQty(Integer qty){
			this.qty = qty;
		}

		public Integer getQty(){
			return qty;
		}

		public void setGrandTotal(Integer grandTotal){
			this.grandTotal = grandTotal;
		}

		public Integer getGrandTotal(){
			return grandTotal;
		}
	}
}