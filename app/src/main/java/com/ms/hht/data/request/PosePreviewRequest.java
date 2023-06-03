package com.ms.hht.data.request;

public class PosePreviewRequest {

    String customerEmailId;
    String userId;
    double height;
    String mobileModel;
    double weight;
    int angle;
    int age;
    String gender;
    String frontImage;
    String sideImage;
    String fitType;
    String merchantId;
    String userType;
    String productName;
    int profileId;

    public PosePreviewRequest(String customerEmailId, String userId, double height, String mobileModel, double weight, int angle, int age, String gender, String frontImage, String sideImage, String fitType, String merchantId, String userType, String productName, int profileId) {
        this.customerEmailId = customerEmailId;
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
    }
}
