package com.ms.hht.data.response;

import com.google.gson.annotations.SerializedName;

public class FrontItem{

	@SerializedName("image")
	private String image;

	@SerializedName("id")
	private Integer id;

	@SerializedName("position")
	private Integer position;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setPosition(Integer position){
		this.position = position;
	}

	public Integer getPosition(){
		return position;
	}
}