package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

public class UserProfileResponse{

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

		@SerializedName("cart_count")
		private Integer cartCount;

		@SerializedName("firstname")
		private String firstname;

		@SerializedName("gender")
		private String gender;

		@SerializedName("email")
		private String email;

		@SerializedName("lastname")
		private String lastname;

		public void setCartCount(Integer cartCount){
			this.cartCount = cartCount;
		}

		public Integer getCartCount(){
			return cartCount;
		}

		public void setFirstname(String firstname){
			this.firstname = firstname;
		}

		public String getFirstname(){
			return firstname;
		}

		public void setGender(String gender){
			this.gender = gender;
		}

		public String getGender(){
			return gender;
		}

		public void setEmail(String email){
			this.email = email;
		}

		public String getEmail(){
			return email;
		}

		public void setLastname(String lastname){
			this.lastname = lastname;
		}

		public String getLastname(){
			return lastname;
		}
	}
}