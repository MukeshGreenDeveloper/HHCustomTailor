package com.ms.hht.data.service;

import com.ms.hht.data.SignUpResponse;
import com.ms.hht.data.request.AddAndUpdateAddressRequest;
import com.ms.hht.data.request.ChangePassRequestBody;
import com.ms.hht.data.request.DeleteCartItemRequest;
import com.ms.hht.data.request.FabricListRequest;
import com.ms.hht.data.request.ForgotPassOTPRequestBody;
import com.ms.hht.data.request.GeneratePasswordRequest;
import com.ms.hht.data.request.GetFeatureImagesRequest;
import com.ms.hht.data.request.GetMSMeasurementRequest;
import com.ms.hht.data.request.LiningAddToCartRequest;
import com.ms.hht.data.request.LiningFilterListRequest;
import com.ms.hht.data.request.MeasureMeRequest;
import com.ms.hht.data.request.ProcessIDRequest;
import com.ms.hht.data.request.ReorderCartRequest;
import com.ms.hht.data.request.SignupRequest;
import com.ms.hht.data.request.UpdateCartQuantityRequest;
import com.ms.hht.data.request.UpdateDefaultAddressRequest;
import com.ms.hht.data.request.UpdateUserProfileRequest;
import com.ms.hht.data.request.VerifyOtpRequest;
import com.ms.hht.data.response.AccentResponse;
import com.ms.hht.data.response.AddAddressResponse;
import com.ms.hht.data.response.AddToCartResponse;
import com.ms.hht.data.response.AddressListResponse;
import com.ms.hht.data.response.AppStatusResponse;
import com.ms.hht.data.response.CartListResponse;
import com.ms.hht.data.response.ChangePasswordResponse;
import com.ms.hht.data.response.CheckMeasureMe;
import com.ms.hht.data.response.DeleteAddressResponse;
import com.ms.hht.data.response.DeleteCartResponse;
import com.ms.hht.data.response.FabricFilterListResponse;
import com.ms.hht.data.response.FabricFilterResponse;
import com.ms.hht.data.response.FabricSelectionGetPieceTypesResponse;
import com.ms.hht.data.response.GETMSMeasurementResponse;
import com.ms.hht.data.response.GetCountryResponse;
import com.ms.hht.data.response.GetCustomerInfoResponse;
import com.ms.hht.data.response.GetFeatureImagesResponse;
import com.ms.hht.data.response.GetStateResponse;
import com.ms.hht.data.response.GetUserProfileResponse;
import com.ms.hht.data.response.LiningFilterOptionResponse;
import com.ms.hht.data.response.LiningResponse;
import com.ms.hht.data.response.MeasurementHistoryResponse;
import com.ms.hht.data.response.OrderDetailResponse;
import com.ms.hht.data.response.OrderListResponse;
import com.ms.hht.data.response.PieceResponse;
import com.ms.hht.data.response.PieceSelectionResponse;
import com.ms.hht.data.response.ProcessResponse;
import com.ms.hht.data.response.ProductDescriptionRes;
import com.ms.hht.data.response.SETmeasurementResponse;
import com.ms.hht.data.response.StyleResponse;
import com.ms.hht.data.response.StylelookDataResponse;
import com.ms.hht.data.response.SubCategoryResponse;
import com.ms.hht.data.response.TypeResponse;
import com.ms.hht.data.response.UpdateAddressResponse;
import com.ms.hht.data.response.UpdateCartQuantityResponse;
import com.ms.hht.data.response.UpdateDefaultAddressResponse;
import com.ms.hht.data.response.UpdateUserProfileResponse;
import com.ms.hht.ui.payment.PlaceOrderRequestBody;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @POST("cart/")
    Call<AddToCartResponse> LiningAddToCart(@Body LiningAddToCartRequest liningAddToCartRequest);

    @POST("reorder/")
    Call<AddToCartResponse> ReORDERaddToCart(@Body ReorderCartRequest reorderCartRequest);

    @POST("address/")
    Call<AddAddressResponse> addUserAddress(@Body AddAndUpdateAddressRequest addAndUpdateAddressRequest);

    @POST("change_password/")
    Call<ChangePasswordResponse> changePasseord(@Body ChangePassRequestBody changePassRequestBody);

    @POST("api/user/checkprocessid")
    Call<CheckMeasureMe> checkProcessId(@Body MeasureMeRequest measureMeRequest);

    @DELETE("address/{address_id}")
    Call<DeleteAddressResponse> deleteAddress(@Path("address_id") int i);

    @HTTP(hasBody = true, method = "DELETE", path = "cart/")
    Call<DeleteCartResponse> deleteCart(@Body DeleteCartItemRequest deleteCartItemRequest);

    @DELETE("delete_account/")
    Call<AddToCartResponse> deleteUserAccount();

    @POST("forgot_password/")
    Call<SignUpResponse> forgotPassword(@Body SignupRequest signupRequest);

    @POST("generate_new_password/")
    Call<SignUpResponse> generateNewPassword(@Body GeneratePasswordRequest generatePasswordRequest);

    @POST("api/ms_initialize_user")
    Call<ProcessResponse> generateProcess(@Body ProcessIDRequest processIDRequest);

    @GET("accent/{piece_id}/")
    Call<AccentResponse> getAccentData(@Path("piece_id") int i);

    @GET("address/")
    Call<AddressListResponse> getAddList();

    @GET("api/app-status")
    Call<AppStatusResponse> getAppStatus(@Query("appName") String str, @Query("screenName") String str2, @Query("appOs") String str3);

    @GET("cart/")
    Call<CartListResponse> getCartList();

    @GET("country/")
    Call<GetCountryResponse> getCountry();

    @GET("customer_info/")
    Call<GetCustomerInfoResponse> getCustomerInfo();

    @POST("lining_filters/{piece_id}/")
    Call<LiningResponse> getDataToSetLiningFilter(@Body LiningFilterListRequest liningFilterListRequest, @Path("piece_id") int i);

    @POST("filters/{subcategory_id}/")
    Call<FabricFilterResponse> getFabricFilter(@Body FabricListRequest fabricListRequest, @Path("subcategory_id") int i);

    @POST("feature_images/")
    Call<GetFeatureImagesResponse> getFeatureIMAGES(@Body GetFeatureImagesRequest getFeatureImagesRequest);

    @GET("filters/{subcategory_id}/")
    Call<FabricFilterListResponse> getFilterList(@Path("subcategory_id") int i);

    @GET("category/{type_id}/")
    Call<StylelookDataResponse> getImages(@Path("type_id") int i);

    @GET("lining_filters/{piece_id}/")
    Call<LiningFilterOptionResponse> getLiningFilter(@Path("piece_id") int i);

    @POST("api/measurement/getmeasurement/")
    Call<GETMSMeasurementResponse> getMSMeasurement(@Body GetMSMeasurementRequest getMSMeasurementRequest);

    @GET("order/{order_id}")
    Call<OrderDetailResponse> getOrderData(@Path("order_id") int i);

    @GET("recent_orders/")
    Call<OrderListResponse> getOrderlist();

    @GET("piece_types/{piece_id}/")
    Call<FabricSelectionGetPieceTypesResponse> getPieceTYPE(@Path("piece_id") int i);

    @GET("item_details/{item_id}/")
    Call<ProductDescriptionRes> getProductDes(@Path("item_id") Integer num);

    @GET("region/{country_id}/")
    Call<GetStateResponse> getRegionList(@Path("country_id") String str);

    @GET("piece/{subcategory_id}/")
    Call<PieceSelectionResponse> getStylePiece(@Path("subcategory_id") Integer num);

    @GET("style/{piece_id}/")
    Call<StyleResponse> getStyledata(@Path("piece_id") int i);

    @GET("subcategory/{parent_id}/")
    Call<SubCategoryResponse> getSubCategoryData(@Path("parent_id") Integer num);

    @GET("types/")
    Call<TypeResponse> getTYPE();

    @POST("piece_types/{piece_id}/")
    Call<PieceResponse> getTabListData(@Path("piece_id") int i);

    @GET("measurements/")
    Call<MeasurementHistoryResponse> getmeasurementHistory();

    @GET("customer_info/")
    Call<GetUserProfileResponse> getuserProfile();

    @POST("payment/")
    Call<ChangePasswordResponse> paymentCAlling(@Body PlaceOrderRequestBody placeOrderRequestBody);

    @POST("place_order/")
    Call<ChangePasswordResponse> placeOrder(@Body PlaceOrderRequestBody placeOrderRequestBody);

    @POST("resend_otp/")
    Call<SignUpResponse> resendUserOTP(@Body SignupRequest signupRequest);

    @POST("measurement/")
    Call<SETmeasurementResponse> setUserMeasurement(@Body Map<String, Object> map);

    @POST("update_account_info/")
    Call<UpdateUserProfileResponse> upateUserProfile(@Body UpdateUserProfileRequest updateUserProfileRequest);

    @POST("quantity/")
    Call<UpdateCartQuantityResponse> updateCart(@Body UpdateCartQuantityRequest updateCartQuantityRequest);

    @PUT("default_address/")
    Call<UpdateDefaultAddressResponse> updateDefaultAddress(@Body UpdateDefaultAddressRequest updateDefaultAddressRequest);

    @PUT("address/{address_id}")
    Call<UpdateAddressResponse> updateUserAddress(@Body AddAndUpdateAddressRequest addAndUpdateAddressRequest, @Path("address_id") int i);

    @POST("signin/")
    Call<SignUpResponse> userLogin(@Body SignupRequest signupRequest);

    @POST("signup/")
    Call<SignUpResponse> userSignUp(@Body SignupRequest signupRequest);

    @POST("verify_otp/")
    Call<SignUpResponse> verifyForgotUserOtp(@Body ForgotPassOTPRequestBody forgotPassOTPRequestBody);

    @POST("verify_otp/")
    Call<SignUpResponse> verifyUserOtp(@Body VerifyOtpRequest verifyOtpRequest);
}
