package com.ms.hht.utils;

public class ComUserProfileData {
    public static double Height;
    public static double Weight;
    public static int age;
    public static String measGender;
    public static float angle;
    public static String email;
    public static String shoeSize;
    public static String PreferredFit;
    public static String weightWithUnit;
    public static String heightWithUnit;

    public static String getShoeSize() {
        return shoeSize;
    }

    public static void setShoeSize(String shoeSize) {
        ComUserProfileData.shoeSize = shoeSize;
    }

    public static String getPreferredFit() {
        return PreferredFit;
    }

    public static void setPreferredFit(String preferredFit) {
        PreferredFit = preferredFit;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        ComUserProfileData.email = email;
    }

    public static double getHeight() {
        return Height;
    }

    public static void setHeight(double height) {
        Height = height;
    }

    public static void setWeightWithUnit(String heightU) {
        weightWithUnit = heightU;
    }

    public static void setHeightWithUnit(String heightU) {
        heightWithUnit = heightU;
    }

    public static String getWeightWithUnit() {
        return weightWithUnit;
    }

    public static String getHeightWithUnit() {
        return heightWithUnit;
    }

    public static double getWeight() {
        return Weight;
    }

    public static void setWeight(double weight) {
        Weight = weight;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        ComUserProfileData.age = age;
    }

    public static String getmeasurementGender() {
        return measGender;
    }

    public static void setmeasurementGender(String measurementGender) {
        ComUserProfileData.measGender = measurementGender;
    }

    public static float getAngle() {
        return angle;
    }

    public static void setAngle(float angle) {
        ComUserProfileData.angle = angle;
    }

}
