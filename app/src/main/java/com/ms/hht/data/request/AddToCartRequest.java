package com.ms.hht.data.request;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AddToCartRequest {

	@SerializedName("fabric_id")
	private Integer fabricId;

	@SerializedName("fabric_name")
	private String fabricName;

	@SerializedName("item_id")
	private Integer itemId;

	@SerializedName("type_id")
	private Integer typeId;

	@SerializedName("is_multiple")
	private Integer isMultiple;

	@SerializedName("options_data")
	private List<OptionsDataItem> optionsData;

	public void setFabricId(Integer fabricId){
		this.fabricId = fabricId;
	}

	public Integer getFabricId(){
		return fabricId;
	}

	public void setFabricName(String fabricName){
		this.fabricName = fabricName;
	}

	public String getFabricName(){
		return fabricName;
	}

	public void setItemId(Integer itemId){
		this.itemId = itemId;
	}

	public Integer getItemId(){
		return itemId;
	}

	public void setTypeId(Integer typeId){
		this.typeId = typeId;
	}

	public Integer getTypeId(){
		return typeId;
	}

	public void setIsMultiple(Integer isMultiple){
		this.isMultiple = isMultiple;
	}

	public Integer getIsMultiple(){
		return isMultiple;
	}

	public void setOptionsData(List<OptionsDataItem> optionsData){
		this.optionsData = optionsData;
	}

	public List<OptionsDataItem> getOptionsData(){
		return optionsData;
	}

	public class OptionsDataItem{

		@SerializedName("piece_id")
		private Integer pieceId;

		@SerializedName("features")
		private List<FeaturesItem> features;

		public void setPieceId(Integer pieceId){
			this.pieceId = pieceId;
		}

		public Integer getPieceId(){
			return pieceId;
		}

		public void setFeatures(List<FeaturesItem> features){
			this.features = features;
		}

		public List<FeaturesItem> getFeatures(){
			return features;
		}


		public class FeaturesItem{

			@SerializedName("feature_name")
			private String featureName;

			@SerializedName("selected_value")
			private List<String> selectedValue;

			@SerializedName("id")
			private Integer id;

			public void setFeatureName(String featureName){
				this.featureName = featureName;
			}

			public String getFeatureName(){
				return featureName;
			}

			public void setSelectedValue(List<String> selectedValue){
				this.selectedValue = selectedValue;
			}

			public List<String> getSelectedValue(){
				return selectedValue;
			}

			public void setId(Integer id){
				this.id = id;
			}

			public Integer getId(){
				return id;
			}
		}
	}
}