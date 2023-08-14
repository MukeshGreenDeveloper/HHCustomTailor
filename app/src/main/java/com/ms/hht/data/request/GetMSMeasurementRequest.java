package com.ms.hht.data.request;

public class GetMSMeasurementRequest {
     String   age;
     String   angle;
     String   apiKey;
     String  fitType;
     String  frontImage;
     String  gender;
     String   height;
     String  merchantid;
     String  mobilemodel;
     String   productname;
     String   sideImage;
     String userId;

     String userMobile ="";

     String userName = "Mirrorsize";
     String   weight;
     String  userType;
     String   profileId;
     String   customerEmailId;

     public GetMSMeasurementRequest(String userId, String height, String mobileModel, String weight,
                                    String angle, String age, String gender, String frontImage,
                                    String sideImage, String fitType, String merchantId,
                                    String userType, String productName, String profileId, String customerEmailId,
                                    String apiKey) {
          this.apiKey = apiKey;
          this.userId = userId;
          this.height = height;
          this.mobilemodel = mobileModel;
          this.weight = weight;
          this.angle = angle;
          this.age = age;
          this.gender = gender;
          this.frontImage = frontImage;
          this.sideImage = sideImage;
          this.fitType = fitType;
          this.merchantid = merchantId;
          this.userType = userType;
          this.productname = productName;
          this.profileId = profileId;
          this.customerEmailId = customerEmailId;
     }
}
