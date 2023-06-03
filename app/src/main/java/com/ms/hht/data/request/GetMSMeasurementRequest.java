package com.ms.hht.data.request;

public class GetMSMeasurementRequest {
     String userId;
     String   height;
     String  mobileModel;
     String   weight;
     String   angle;
     String   age;
     String  gender;
     String  frontImage;
     String   sideImage;
     String  fitType;
     String  merchantId;
     String  userType;
     String   productName;
     String   profileId;
     String   customerEmailId;

     public GetMSMeasurementRequest(String userId, String height, String mobileModel, String weight, String angle, String age, String gender, String frontImage, String sideImage, String fitType, String merchantId, String userType, String productName, String profileId, String customerEmailId) {
          this.userId = userId;
          this.height = height;
          this.mobileModel = mobileModel;
          this.weight = weight;
          this.angle = angle;
          this.age = age;
          this.gender = gender;
          this.frontImage = frontImage;
          this.sideImage = sideImage;
          this.fitType = fitType;
          this.merchantId = merchantId;
          this.userType = userType;
          this.productName = productName;
          this.profileId = profileId;
          this.customerEmailId = customerEmailId;
     }
}
