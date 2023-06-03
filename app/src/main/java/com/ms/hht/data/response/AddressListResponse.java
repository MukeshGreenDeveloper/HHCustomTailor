package com.ms.hht.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AddressListResponse {

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

		@SerializedName("country")
		private String country;

		@SerializedName("firstname")
		private String firstname;

		@SerializedName("city")
		private String city;

		@SerializedName("street")
		private String street;

		@SerializedName("is_default_shipping")
		private Integer isDefaultShipping;

		@SerializedName("region_id")
		private Integer regionId;

		@SerializedName("postcode")
		private String postcode;

		@SerializedName("telephone")
		private String telephone;

		@SerializedName("id")
		private Integer id;

		@SerializedName("region")
		private String region;

		@SerializedName("country_id")
		private String countryId;

		@SerializedName("lastname")
		private String lastname;

		public void setCountry(String country){
			this.country = country;
		}

		public String getCountry(){
			return country;
		}

		public void setFirstname(String firstname){
			this.firstname = firstname;
		}

		public String getFirstname(){
			return firstname;
		}

		public void setCity(String city){
			this.city = city;
		}

		public String getCity(){
			return city;
		}

		public void setStreet(String street){
			this.street = street;
		}

		public String getStreet(){
			return street;
		}

		public void setIsDefaultShipping(Integer isDefaultShipping){
			this.isDefaultShipping = isDefaultShipping;
		}

		public Integer getIsDefaultShipping(){
			return isDefaultShipping;
		}

		public void setRegionId(Integer regionId){
			this.regionId = regionId;
		}

		public Integer getRegionId(){
			return regionId;
		}

		public void setPostcode(String postcode){
			this.postcode = postcode;
		}

		public String getPostcode(){
			return postcode;
		}

		public void setTelephone(String telephone){
			this.telephone = telephone;
		}

		public String getTelephone(){
			return telephone;
		}

		public void setId(Integer id){
			this.id = id;
		}

		public Integer getId(){
			return id;
		}

		public void setRegion(String region){
			this.region = region;
		}

		public String getRegion(){
			return region;
		}

		public void setCountryId(String countryId){
			this.countryId = countryId;
		}

		public String getCountryId(){
			return countryId;
		}

		public void setLastname(String lastname){
			this.lastname = lastname;
		}

		public String getLastname(){
			return lastname;
		}
	}
}