package com.ms.hht.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Context _context;

    public static final String PREFFR_NAME = "UserSession";

    // user details
    public static final String USER_ID = "USER_ID";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_FIRST_NAME = "";
    public static final String USER_LAST_NAME = "";
    public static final String USER_GENDER = "";

    public static final String PROCESS_ID = "PROCESS_ID";
    public static final String FIRST_PLAY = "true";
    public static final String SENSOR_AVAILABILITY = "SENSOR_AVAILABILITY";
    public static final String User_Session = "false";
    public static final String Login_session = "Login_session";
    public static final String USER_TOKEN = "USER_TOKEN";

    public static final String CART_COUNT = "CART_COUNT";


    // used for add to cart conditions
    public static final String ITEM_ID = "ITEM_ID";
    public static final String FABRIC_ID = "fabricId";
    public static final String TYPE_ID = "TYPE_ID";
    public static final String IS_MULTIPART = "IS_MULTIPART";
    public static final String FABRIC_NAME = "FABRIC_NAME";
    public static final String PIECE_ID = "PIECE_ID";

    public static final String MERCHANT_EMAIL = "MERCHANT_EMAIL";
    public static final String PRODUCT_NAME = "PRODUCT_NAME";
    public static final String FIT_TYPE = "FIT_TYPE";
    public static final String USER_TYPE = "USER_TYPE";



    public SessionManager(Context context) {
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREFFR_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    // user details

    public void setUserId(String userId) {

        editor.putString(USER_ID, userId);
        editor.commit();

    }


    public void setMerchantEmail(String merchantEmail) {

        editor.putString(MERCHANT_EMAIL, merchantEmail);
        editor.commit();

    }

    public String getMerchantEmail() {
        return pref.getString(MERCHANT_EMAIL, "raj@hhcustomtailor.com");
    }

    public String getUserType() {
        return pref.getString(USER_TYPE, "merchant");
    }

    public void setUserType(String userType) {
        editor.putString(USER_TYPE, userType);
        editor.commit();
    }

    public void setProductName(String productName) {

        editor.putString(PRODUCT_NAME, productName);
        editor.commit();

    }

    public String getProductName() {
        return pref.getString(PRODUCT_NAME, "GET_MEASURED");
    }

    public void setFitType(String fitType) {

        editor.putString(FIT_TYPE, fitType);
        editor.commit();

    }

    public String getFitType() {
        return pref.getString(FIT_TYPE, "tightfit");
    }

    public String getUserId() {
        return pref.getString(USER_ID, "");
    }

    public void setUserEmail(String userEmail) {

        editor.putString(USER_EMAIL, userEmail);
        editor.commit();

    }

    public String getUserEmail() {
        return pref.getString(USER_EMAIL, "");
    }

    public void setUserPassword(String userPassword) {

        editor.putString(USER_PASSWORD, userPassword);
        editor.commit();

    }

    public String getUserPassword() {
        return pref.getString(USER_PASSWORD, "");
    }

    public void setUserFirstName(String userFirstName) {

        editor.putString(USER_FIRST_NAME, userFirstName);
        editor.commit();

    }

    public String getUserFirstName() {
        return pref.getString(USER_FIRST_NAME, "");
    }

    public void setUserLastName(String userLastName) {

        editor.putString(USER_LAST_NAME, userLastName);
        editor.commit();

    }

    public String getsetUserLastName() {
        return pref.getString(USER_LAST_NAME, "");
    }

    public void setUserGender(String userGender) {

        editor.putString(USER_GENDER, userGender);
        editor.commit();

    }

    public String getUserGender() {
        return pref.getString(USER_GENDER, "");
    }



    public void updateProcessId(String processid) {
        editor.putString(PROCESS_ID, processid);
        editor.commit();
    }



    public void updateVideoStatus(String status) {
        editor.putString(FIRST_PLAY, status);
        editor.commit();
    }

    public void updateSession(String value) {
        editor.putString(User_Session, value);
        editor.commit();
    }

    public void updateSensor(String sensorAvail) {
        editor.putString(SENSOR_AVAILABILITY, sensorAvail);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<>();

        user.put(FIRST_PLAY, pref.getString(FIRST_PLAY, "true"));
        user.put(User_Session, pref.getString(User_Session, "false"));
        user.put(PROCESS_ID, pref.getString(PROCESS_ID, "processId"));
        return user;

    }


    public void setLoginSession(String loginsession) {
        editor.putString(Login_session, loginsession);
        editor.commit();
    }

    public String getLoginSession() {
        return pref.getString(Login_session, "");
    }

    public void setUserToken(String userToken) {
        editor.putString(USER_TOKEN, userToken);
        editor.commit();
    }

    public String getUserToken() {
        return pref.getString(USER_TOKEN, "");
    }

    public void setCartCount(String cartCount) {

        editor.putString(CART_COUNT, cartCount);
        editor.commit();

    }

    public String getcartCount(){return  pref.getString(CART_COUNT, "0");}


    // used for add to cart conditions

    public void setItemId(String itemId) {

        editor.putString(ITEM_ID, itemId);
        editor.commit();

    }

    public String getItemId() {
        return pref.getString(ITEM_ID, "");
    }


    public void setFabricId(String fabricId) {

        editor.putString(FABRIC_ID, fabricId);
        editor.commit();

    }

    public String getFabricId() {
        return pref.getString(FABRIC_ID, "");
    }

    public void setTypeId(String typeId) {
        editor.putString(TYPE_ID, typeId);
        editor.commit();
    }

    public String getTypeId() {
        return pref.getString(TYPE_ID, "0");
    }

    public void setIsMultipart(String isMultipart) {
        editor.putString(IS_MULTIPART, isMultipart);
        editor.commit();
    }

    public String getIsMultipart() {
        return pref.getString(IS_MULTIPART, "");
    }

    public void setFabricName(String fabricName) {
        editor.putString(FABRIC_NAME, fabricName);
        editor.commit();
    }

    public String getFabricName() {
        return pref.getString(FABRIC_NAME, "");
    }


    public void setPieceId(String pieceID) {
        editor.putString(PIECE_ID, pieceID);
        editor.commit();
    }

    public String getPieceId() {
        return pref.getString(PIECE_ID, "");
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        editor.clear();
        editor.commit();

    }
}
