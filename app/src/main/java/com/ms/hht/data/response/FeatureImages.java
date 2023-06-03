package com.ms.hht.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FeatureImages{

	@SerializedName("rear")
	private List<RearItem> rear;

	@SerializedName("front")
	private List<FrontItem> front;

	public void setRear(List<RearItem> rear){
		this.rear = rear;
	}

	public List<RearItem> getRear(){
		return rear;
	}

	public void setFront(List<FrontItem> front){
		this.front = front;
	}

	public List<FrontItem> getFront(){
		return front;
	}
}