package com.ms.hht.data.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class GETMSMeasurementResponse {

	@SerializedName("code")
	private Integer code;

	@SerializedName("data")
	private List<DataItem> data;

	public String getUserId() {
		return userId;
	}

	@SerializedName("message")
	private String message;
	@SerializedName("userId")
	private String userId;

	@SerializedName("measurements")
	private Measurements measurements;
	@SerializedName("measurementData")
	private HashMap<String,String> measurementData;

	public void setCode(Integer code){
		this.code = code;
	}

	public Integer getCode(){
		return code;
	}

	public HashMap<String, String> getMeasurementData() {
		return measurementData;
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

	public void setMeasurements(Measurements measurements){
		this.measurements = measurements;
	}

	public Measurements getMeasurements(){
		return measurements;
	}

	public class DataItem{

		@SerializedName("displayName")
		private String displayName;

		@SerializedName("pointName")
		private String pointName;

		@SerializedName("valueIncm")
		private String valueIncm;

		@SerializedName("description")
		private String description;

		@SerializedName("valueIninch")
		private String valueIninch;

		public void setDisplayName(String displayName){
			this.displayName = displayName;
		}

		public String getDisplayName(){
			return displayName;
		}

		public void setPointName(String pointName){
			this.pointName = pointName;
		}

		public String getPointName(){
			return pointName;
		}

		public void setValueIncm(String valueIncm){
			this.valueIncm = valueIncm;
		}

		public String getValueIncm(){
			return valueIncm;
		}

		public void setDescription(String description){
			this.description = description;
		}

		public String getDescription(){
			return description;
		}

		public void setValueIninch(String valueIninch){
			this.valueIninch = valueIninch;
		}

		public String getValueIninch(){
			return valueIninch;
		}
	}
	public class Measurements{

		@SerializedName("gender")
		private String gender;

		@SerializedName("frontImage")
		private String frontImage;

		@SerializedName("measurementStatus")
		private String measurementStatus;

		@SerializedName("errorMessage")
		private String errorMessage;

		@SerializedName("weight")
		private String weight;

		@SerializedName("measurement")
		private Measurement measurement;

		@SerializedName("merchantId")
		private String merchantId;

		@SerializedName("measurementApproval")
		private String measurementApproval;

		@SerializedName("mobileModel")
		private String mobileModel;

		@SerializedName("objUrl")
		private String objUrl;

		@SerializedName("fitType")
		private String fitType;

		@SerializedName("angle")
		private String angle;

		@SerializedName("sideImage")
		private String sideImage;

		@SerializedName("age")
		private String age;

		@SerializedName("height")
		private String height;

		public void setGender(String gender){
			this.gender = gender;
		}

		public String getGender(){
			return gender;
		}

		public void setFrontImage(String frontImage){
			this.frontImage = frontImage;
		}

		public String getFrontImage(){
			return frontImage;
		}

		public void setMeasurementStatus(String measurementStatus){
			this.measurementStatus = measurementStatus;
		}

		public String getMeasurementStatus(){
			return measurementStatus;
		}

		public void setErrorMessage(String errorMessage){
			this.errorMessage = errorMessage;
		}

		public String getErrorMessage(){
			return errorMessage;
		}

		public void setWeight(String weight){
			this.weight = weight;
		}

		public String getWeight(){
			return weight;
		}

		public void setMeasurement(Measurement measurement){
			this.measurement = measurement;
		}

		public Measurement getMeasurement(){
			return measurement;
		}

		public void setMerchantId(String merchantId){
			this.merchantId = merchantId;
		}

		public String getMerchantId(){
			return merchantId;
		}

		public void setMeasurementApproval(String measurementApproval){
			this.measurementApproval = measurementApproval;
		}

		public String getMeasurementApproval(){
			return measurementApproval;
		}

		public void setMobileModel(String mobileModel){
			this.mobileModel = mobileModel;
		}

		public String getMobileModel(){
			return mobileModel;
		}

		public void setObjUrl(String objUrl){
			this.objUrl = objUrl;
		}

		public String getObjUrl(){
			return objUrl;
		}

		public void setFitType(String fitType){
			this.fitType = fitType;
		}

		public String getFitType(){
			return fitType;
		}

		public void setAngle(String angle){
			this.angle = angle;
		}

		public String getAngle(){
			return angle;
		}

		public void setSideImage(String sideImage){
			this.sideImage = sideImage;
		}

		public String getSideImage(){
			return sideImage;
		}

		public void setAge(String age){
			this.age = age;
		}

		public String getAge(){
			return age;
		}

		public void setHeight(String height){
			this.height = height;
		}

		public String getHeight(){
			return height;
		}

		public class Measurement{

			@SerializedName("backChestWidth")
			private Double backChestWidth;

			@SerializedName("jacketLength")
			private Double jacketLength;

			@SerializedName("hipDepth")
			private Double hipDepth;

			@SerializedName("upperwaist")
			private Double upperwaist;

			@SerializedName("thigh")
			private Double thigh;

			@SerializedName("legLengthHighWaist")
			private Double legLengthHighWaist;

			@SerializedName("strappedSlant")
			private Double strappedSlant;

			@SerializedName("vestBack")
			private Double vestBack;

			@SerializedName("stomach")
			private Double stomach;

			@SerializedName("upperHip")
			private Double upperHip;

			@SerializedName("waistHeight")
			private Double waistHeight;

			@SerializedName("wrist")
			private Double wrist;

			@SerializedName("hip")
			private Double hip;

			@SerializedName("armInseam")
			private Double armInseam;

			@SerializedName("frontKneeLength")
			private Double frontKneeLength;

			@SerializedName("chestDepth")
			private Double chestDepth;

			@SerializedName("naturalwaistWaistBack")
			private Double naturalwaistWaistBack;

			@SerializedName("upperArmLength")
			private Double upperArmLength;

			@SerializedName("centerFrontLength")
			private Double centerFrontLength;

			@SerializedName("urise")
			private Double urise;

			@SerializedName("kneetoAnkleLength")
			private Double kneetoAnkleLength;

			@SerializedName("waistDepth")
			private Double waistDepth;

			@SerializedName("biceps")
			private Double biceps;

			@SerializedName("height")
			private Double height;

			@SerializedName("chest")
			private Double chest;

			@SerializedName("midwaistHeight")
			private Double midwaistHeight;

			@SerializedName("underChest")
			private Double underChest;

			@SerializedName("weight")
			private Double weight;

			@SerializedName("sideseam")
			private Double sideseam;

			@SerializedName("foreArmLength")
			private Double foreArmLength;

			@SerializedName("scyeDepth")
			private Double scyeDepth;

			@SerializedName("shoulderAcross")
			private Double shoulderAcross;

			@SerializedName("uriseHighLow")
			private Double uriseHighLow;

			@SerializedName("strappedLinear")
			private Double strappedLinear;

			@SerializedName("thighSlant")
			private Double thighSlant;

			@SerializedName("shoulderSeam")
			private Double shoulderSeam;

			@SerializedName("jacketLengthLong")
			private Double jacketLengthLong;

			@SerializedName("sleeveLengthFull")
			private Double sleeveLengthFull;

			@SerializedName("naturalwaistWaist")
			private Double naturalwaistWaist;

			@SerializedName("waist")
			private Double waist;

			@SerializedName("waistToKneeLength")
			private Double waistToKneeLength;

			@SerializedName("ankleGirth")
			private Double ankleGirth;

			@SerializedName("frontTorsoLength")
			private Double frontTorsoLength;

			@SerializedName("highWaist")
			private Double highWaist;

			@SerializedName("midThighGirth")
			private Double midThighGirth;

			@SerializedName("cervicalLength")
			private Double cervicalLength;

			@SerializedName("frontWaistLength")
			private Double frontWaistLength;

			@SerializedName("halfSleeveLength")
			private Double halfSleeveLength;

			@SerializedName("naturalWaistGirth")
			private Double naturalWaistGirth;

			@SerializedName("jacketLengthShort")
			private Double jacketLengthShort;

			@SerializedName("frontSoulderAcross")
			private Double frontSoulderAcross;

			@SerializedName("naturalWaistLength")
			private Double naturalWaistLength;

			@SerializedName("armsLength")
			private Double armsLength;

			@SerializedName("centerBackLength")
			private Double centerBackLength;

			@SerializedName("upperKneeGirth")
			private Double upperKneeGirth;

			@SerializedName("inseam")
			private Double inseam;

			@SerializedName("shirtLength")
			private Double shirtLength;

			@SerializedName("chestHeight")
			private Double chestHeight;

			@SerializedName("foreArmGirth")
			private Double foreArmGirth;

			@SerializedName("lowWaist")
			private Double lowWaist;

			@SerializedName("uriseLowHigh")
			private Double uriseLowHigh;

			@SerializedName("midwaistToHipHeight")
			private Double midwaistToHipHeight;

			@SerializedName("shoulderSlope")
			private Double shoulderSlope;

			@SerializedName("legLength")
			private Double legLength;

			@SerializedName("stomachHeight")
			private Double stomachHeight;

			@SerializedName("frontChestWidth")
			private Double frontChestWidth;

			@SerializedName("kneeGirth")
			private Double kneeGirth;

			@SerializedName("upperNeck")
			private Double upperNeck;

			@SerializedName("armhole")
			private Double armhole;

			@SerializedName("hipHeight")
			private Double hipHeight;

			@SerializedName("vestFront")
			private Double vestFront;

			@SerializedName("legLengthLowWaist")
			private Double legLengthLowWaist;

			@SerializedName("cervicalNaturalwaist")
			private Double cervicalNaturalwaist;

			@SerializedName("neck")
			private Double neck;

			@SerializedName("backTorsoLength")
			private Double backTorsoLength;

			@SerializedName("calfMuscle")
			private Double calfMuscle;

			@SerializedName("midWaist")
			private Double midWaist;

			@SerializedName("shoulderNaturalwaist")
			private Double shoulderNaturalwaist;

			@SerializedName("halfBackChestWidth")
			private Double halfBackChestWidth;

			@SerializedName("legLengthMidWaist")
			private Double legLengthMidWaist;

			@SerializedName("stomachDepth")
			private Double stomachDepth;

			@SerializedName("sleeveLength")
			private Double sleeveLength;

			@SerializedName("rise")
			private Double rise;

			@SerializedName("armholeLevel")
			private Double armholeLevel;

			public void setBackChestWidth(Double backChestWidth){
				this.backChestWidth = backChestWidth;
			}

			public Double getBackChestWidth(){
				return backChestWidth;
			}

			public void setJacketLength(Double jacketLength){
				this.jacketLength = jacketLength;
			}

			public Double getJacketLength(){
				return jacketLength;
			}

			public void setHipDepth(Double hipDepth){
				this.hipDepth = hipDepth;
			}

			public Double getHipDepth(){
				return hipDepth;
			}

			public void setUpperwaist(Double upperwaist){
				this.upperwaist = upperwaist;
			}

			public Double getUpperwaist(){
				return upperwaist;
			}

			public void setThigh(Double thigh){
				this.thigh = thigh;
			}

			public Double getThigh(){
				return thigh;
			}

			public void setLegLengthHighWaist(Double legLengthHighWaist){
				this.legLengthHighWaist = legLengthHighWaist;
			}

			public Double getLegLengthHighWaist(){
				return legLengthHighWaist;
			}

			public void setStrappedSlant(Double strappedSlant){
				this.strappedSlant = strappedSlant;
			}

			public Double getStrappedSlant(){
				return strappedSlant;
			}

			public void setVestBack(Double vestBack){
				this.vestBack = vestBack;
			}

			public Double getVestBack(){
				return vestBack;
			}

			public void setStomach(Double stomach){
				this.stomach = stomach;
			}

			public Double getStomach(){
				return stomach;
			}

			public void setUpperHip(Double upperHip){
				this.upperHip = upperHip;
			}

			public Double getUpperHip(){
				return upperHip;
			}

			public void setWaistHeight(Double waistHeight){
				this.waistHeight = waistHeight;
			}

			public Double getWaistHeight(){
				return waistHeight;
			}

			public void setWrist(Double wrist){
				this.wrist = wrist;
			}

			public Double getWrist(){
				return wrist;
			}

			public void setHip(Double hip){
				this.hip = hip;
			}

			public Double getHip(){
				return hip;
			}

			public void setArmInseam(Double armInseam){
				this.armInseam = armInseam;
			}

			public Double getArmInseam(){
				return armInseam;
			}

			public void setFrontKneeLength(Double frontKneeLength){
				this.frontKneeLength = frontKneeLength;
			}

			public Double getFrontKneeLength(){
				return frontKneeLength;
			}

			public void setChestDepth(Double chestDepth){
				this.chestDepth = chestDepth;
			}

			public Double getChestDepth(){
				return chestDepth;
			}

			public void setNaturalwaistWaistBack(Double naturalwaistWaistBack){
				this.naturalwaistWaistBack = naturalwaistWaistBack;
			}

			public Double getNaturalwaistWaistBack(){
				return naturalwaistWaistBack;
			}

			public void setUpperArmLength(Double upperArmLength){
				this.upperArmLength = upperArmLength;
			}

			public Double getUpperArmLength(){
				return upperArmLength;
			}

			public void setCenterFrontLength(Double centerFrontLength){
				this.centerFrontLength = centerFrontLength;
			}

			public Double getCenterFrontLength(){
				return centerFrontLength;
			}

			public void setUrise(Double urise){
				this.urise = urise;
			}

			public Double getUrise(){
				return urise;
			}

			public void setKneetoAnkleLength(Double kneetoAnkleLength){
				this.kneetoAnkleLength = kneetoAnkleLength;
			}

			public Double getKneetoAnkleLength(){
				return kneetoAnkleLength;
			}

			public void setWaistDepth(Double waistDepth){
				this.waistDepth = waistDepth;
			}

			public Double getWaistDepth(){
				return waistDepth;
			}

			public void setBiceps(Double biceps){
				this.biceps = biceps;
			}

			public Double getBiceps(){
				return biceps;
			}

			public void setHeight(Double height){
				this.height = height;
			}

			public Double getHeight(){
				return height;
			}

			public void setChest(Double chest){
				this.chest = chest;
			}

			public Double getChest(){
				return chest;
			}

			public void setMidwaistHeight(Double midwaistHeight){
				this.midwaistHeight = midwaistHeight;
			}

			public Double getMidwaistHeight(){
				return midwaistHeight;
			}

			public void setUnderChest(Double underChest){
				this.underChest = underChest;
			}

			public Double getUnderChest(){
				return underChest;
			}

			public void setWeight(Double weight){
				this.weight = weight;
			}

			public Double getWeight(){
				return weight;
			}

			public void setSideseam(Double sideseam){
				this.sideseam = sideseam;
			}

			public Double getSideseam(){
				return sideseam;
			}

			public void setForeArmLength(Double foreArmLength){
				this.foreArmLength = foreArmLength;
			}

			public Double getForeArmLength(){
				return foreArmLength;
			}

			public void setScyeDepth(Double scyeDepth){
				this.scyeDepth = scyeDepth;
			}

			public Double getScyeDepth(){
				return scyeDepth;
			}

			public void setShoulderAcross(Double shoulderAcross){
				this.shoulderAcross = shoulderAcross;
			}

			public Double getShoulderAcross(){
				return shoulderAcross;
			}

			public void setUriseHighLow(Double uriseHighLow){
				this.uriseHighLow = uriseHighLow;
			}

			public Double getUriseHighLow(){
				return uriseHighLow;
			}

			public void setStrappedLinear(Double strappedLinear){
				this.strappedLinear = strappedLinear;
			}

			public Double getStrappedLinear(){
				return strappedLinear;
			}

			public void setThighSlant(Double thighSlant){
				this.thighSlant = thighSlant;
			}

			public Double getThighSlant(){
				return thighSlant;
			}

			public void setShoulderSeam(Double shoulderSeam){
				this.shoulderSeam = shoulderSeam;
			}

			public Double getShoulderSeam(){
				return shoulderSeam;
			}

			public void setJacketLengthLong(Double jacketLengthLong){
				this.jacketLengthLong = jacketLengthLong;
			}

			public Double getJacketLengthLong(){
				return jacketLengthLong;
			}

			public void setSleeveLengthFull(Double sleeveLengthFull){
				this.sleeveLengthFull = sleeveLengthFull;
			}

			public Double getSleeveLengthFull(){
				return sleeveLengthFull;
			}

			public void setNaturalwaistWaist(Double naturalwaistWaist){
				this.naturalwaistWaist = naturalwaistWaist;
			}

			public Double getNaturalwaistWaist(){
				return naturalwaistWaist;
			}

			public void setWaist(Double waist){
				this.waist = waist;
			}

			public Double getWaist(){
				return waist;
			}

			public void setWaistToKneeLength(Double waistToKneeLength){
				this.waistToKneeLength = waistToKneeLength;
			}

			public Double getWaistToKneeLength(){
				return waistToKneeLength;
			}

			public void setAnkleGirth(Double ankleGirth){
				this.ankleGirth = ankleGirth;
			}

			public Double getAnkleGirth(){
				return ankleGirth;
			}

			public void setFrontTorsoLength(Double frontTorsoLength){
				this.frontTorsoLength = frontTorsoLength;
			}

			public Double getFrontTorsoLength(){
				return frontTorsoLength;
			}

			public void setHighWaist(Double highWaist){
				this.highWaist = highWaist;
			}

			public Double getHighWaist(){
				return highWaist;
			}

			public void setMidThighGirth(Double midThighGirth){
				this.midThighGirth = midThighGirth;
			}

			public Double getMidThighGirth(){
				return midThighGirth;
			}

			public void setCervicalLength(Double cervicalLength){
				this.cervicalLength = cervicalLength;
			}

			public Double getCervicalLength(){
				return cervicalLength;
			}

			public void setFrontWaistLength(Double frontWaistLength){
				this.frontWaistLength = frontWaistLength;
			}

			public Double getFrontWaistLength(){
				return frontWaistLength;
			}

			public void setHalfSleeveLength(Double halfSleeveLength){
				this.halfSleeveLength = halfSleeveLength;
			}

			public Double getHalfSleeveLength(){
				return halfSleeveLength;
			}

			public void setNaturalWaistGirth(Double naturalWaistGirth){
				this.naturalWaistGirth = naturalWaistGirth;
			}

			public Double getNaturalWaistGirth(){
				return naturalWaistGirth;
			}

			public void setJacketLengthShort(Double jacketLengthShort){
				this.jacketLengthShort = jacketLengthShort;
			}

			public Double getJacketLengthShort(){
				return jacketLengthShort;
			}

			public void setFrontSoulderAcross(Double frontSoulderAcross){
				this.frontSoulderAcross = frontSoulderAcross;
			}

			public Double getFrontSoulderAcross(){
				return frontSoulderAcross;
			}

			public void setNaturalWaistLength(Double naturalWaistLength){
				this.naturalWaistLength = naturalWaistLength;
			}

			public Double getNaturalWaistLength(){
				return naturalWaistLength;
			}

			public void setArmsLength(Double armsLength){
				this.armsLength = armsLength;
			}

			public Double getArmsLength(){
				return armsLength;
			}

			public void setCenterBackLength(Double centerBackLength){
				this.centerBackLength = centerBackLength;
			}

			public Double getCenterBackLength(){
				return centerBackLength;
			}

			public void setUpperKneeGirth(Double upperKneeGirth){
				this.upperKneeGirth = upperKneeGirth;
			}

			public Double getUpperKneeGirth(){
				return upperKneeGirth;
			}

			public void setInseam(Double inseam){
				this.inseam = inseam;
			}

			public Double getInseam(){
				return inseam;
			}

			public void setShirtLength(Double shirtLength){
				this.shirtLength = shirtLength;
			}

			public Double getShirtLength(){
				return shirtLength;
			}

			public void setChestHeight(Double chestHeight){
				this.chestHeight = chestHeight;
			}

			public Double getChestHeight(){
				return chestHeight;
			}

			public void setForeArmGirth(Double foreArmGirth){
				this.foreArmGirth = foreArmGirth;
			}

			public Double getForeArmGirth(){
				return foreArmGirth;
			}

			public void setLowWaist(Double lowWaist){
				this.lowWaist = lowWaist;
			}

			public Double getLowWaist(){
				return lowWaist;
			}

			public void setUriseLowHigh(Double uriseLowHigh){
				this.uriseLowHigh = uriseLowHigh;
			}

			public Double getUriseLowHigh(){
				return uriseLowHigh;
			}

			public void setMidwaistToHipHeight(Double midwaistToHipHeight){
				this.midwaistToHipHeight = midwaistToHipHeight;
			}

			public Double getMidwaistToHipHeight(){
				return midwaistToHipHeight;
			}

			public void setShoulderSlope(Double shoulderSlope){
				this.shoulderSlope = shoulderSlope;
			}

			public Double getShoulderSlope(){
				return shoulderSlope;
			}

			public void setLegLength(Double legLength){
				this.legLength = legLength;
			}

			public Double getLegLength(){
				return legLength;
			}

			public void setStomachHeight(Double stomachHeight){
				this.stomachHeight = stomachHeight;
			}

			public Double getStomachHeight(){
				return stomachHeight;
			}

			public void setFrontChestWidth(Double frontChestWidth){
				this.frontChestWidth = frontChestWidth;
			}

			public Double getFrontChestWidth(){
				return frontChestWidth;
			}

			public void setKneeGirth(Double kneeGirth){
				this.kneeGirth = kneeGirth;
			}

			public Double getKneeGirth(){
				return kneeGirth;
			}

			public void setUpperNeck(Double upperNeck){
				this.upperNeck = upperNeck;
			}

			public Double getUpperNeck(){
				return upperNeck;
			}

			public void setArmhole(Double armhole){
				this.armhole = armhole;
			}

			public Double getArmhole(){
				return armhole;
			}

			public void setHipHeight(Double hipHeight){
				this.hipHeight = hipHeight;
			}

			public Double getHipHeight(){
				return hipHeight;
			}

			public void setVestFront(Double vestFront){
				this.vestFront = vestFront;
			}

			public Double getVestFront(){
				return vestFront;
			}

			public void setLegLengthLowWaist(Double legLengthLowWaist){
				this.legLengthLowWaist = legLengthLowWaist;
			}

			public Double getLegLengthLowWaist(){
				return legLengthLowWaist;
			}

			public void setCervicalNaturalwaist(Double cervicalNaturalwaist){
				this.cervicalNaturalwaist = cervicalNaturalwaist;
			}

			public Double getCervicalNaturalwaist(){
				return cervicalNaturalwaist;
			}

			public void setNeck(Double neck){
				this.neck = neck;
			}

			public Double getNeck(){
				return neck;
			}

			public void setBackTorsoLength(Double backTorsoLength){
				this.backTorsoLength = backTorsoLength;
			}

			public Double getBackTorsoLength(){
				return backTorsoLength;
			}

			public void setCalfMuscle(Double calfMuscle){
				this.calfMuscle = calfMuscle;
			}

			public Double getCalfMuscle(){
				return calfMuscle;
			}

			public void setMidWaist(Double midWaist){
				this.midWaist = midWaist;
			}

			public Double getMidWaist(){
				return midWaist;
			}

			public void setShoulderNaturalwaist(Double shoulderNaturalwaist){
				this.shoulderNaturalwaist = shoulderNaturalwaist;
			}

			public Double getShoulderNaturalwaist(){
				return shoulderNaturalwaist;
			}

			public void setHalfBackChestWidth(Double halfBackChestWidth){
				this.halfBackChestWidth = halfBackChestWidth;
			}

			public Double getHalfBackChestWidth(){
				return halfBackChestWidth;
			}

			public void setLegLengthMidWaist(Double legLengthMidWaist){
				this.legLengthMidWaist = legLengthMidWaist;
			}

			public Double getLegLengthMidWaist(){
				return legLengthMidWaist;
			}

			public void setStomachDepth(Double stomachDepth){
				this.stomachDepth = stomachDepth;
			}

			public Double getStomachDepth(){
				return stomachDepth;
			}

			public void setSleeveLength(Double sleeveLength){
				this.sleeveLength = sleeveLength;
			}

			public Double getSleeveLength(){
				return sleeveLength;
			}

			public void setRise(Double rise){
				this.rise = rise;
			}

			public Double getRise(){
				return rise;
			}

			public void setArmholeLevel(Double armholeLevel){
				this.armholeLevel = armholeLevel;
			}

			public Double getArmholeLevel(){
				return armholeLevel;
			}
		}
	}
}