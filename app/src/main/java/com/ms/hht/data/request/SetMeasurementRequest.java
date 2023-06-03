package com.ms.hht.data.request;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SetMeasurementRequest {

	private General general;
	private String comment;
	private List<Object> measurement;

	public static class General{

		private String weight;
		private String height;
		private Integer age;
		private String gender;
		private String shoe_size;
		private String preferred_fit;

		public General(String weight, String height, Integer age, String gender, String shoe_size, String preferred_fit) {
			this.weight = weight;
			this.height = height;
			this.age = age;
			this.gender = gender;
			this.shoe_size = shoe_size;
			this.preferred_fit = preferred_fit;
		}
	}
	public static class MeasurementItem{

		private String pointName;
		private String part;
		private String valueIncm;
		private String description;
		private String value;

//		public MeasurementItem(String pointName, String part, String valueIncm, String description, String value) {
//			this.pointName = pointName;
//			this.part = part;
//			this.valueIncm = valueIncm;
//			this.description = description;
//			this.value = value;
//		}


		public String getPointName() {
			return pointName;
		}

		public void setPointName(String pointName) {
			this.pointName = pointName;
		}

		public String getPart() {
			return part;
		}

		public void setPart(String part) {
			this.part = part;
		}

		public String getValueIncm() {
			return valueIncm;
		}

		public void setValueIncm(String valueIncm) {
			this.valueIncm = valueIncm;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public SetMeasurementRequest( List<Object> measurement, String comment, General general ) {

		this.measurement = measurement;
		this.comment = comment;
		this.general = general;
	}

}