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
import com.ms.hht.utils.Constants;

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

    @POST(Constants.SIGN_UP)
    Call<SignUpResponse> userSignUp(@Body SignupRequest signupRequest);

    @POST(Constants.LOGIN)
    Call<SignUpResponse> userLogin(@Body SignupRequest signupRequest);

    @DELETE(Constants.DELETE_ACCOUNT)
    Call<AddToCartResponse> deleteUserAccount();

    @POST(Constants.FORGOT_PASSWORD)
    Call<SignUpResponse> forgotPassword(@Body SignupRequest signupRequest);

    @POST(Constants.RESENT_OTP)
    Call<SignUpResponse> resendUserOTP(@Body SignupRequest signupRequest);

    @POST(Constants.GENERATE_PASSWORD)
    Call<SignUpResponse> generateNewPassword(@Body GeneratePasswordRequest signupRequest);

    @POST(Constants.VERIFY_OTP)
    Call<SignUpResponse> verifyUserOtp(@Body VerifyOtpRequest verifyOtpRequest);

    @POST(Constants.VERIFY_OTP)
    Call<SignUpResponse> verifyForgotUserOtp(@Body ForgotPassOTPRequestBody verifyOtpRequest);

    @GET(Constants.CUSTOMER_INFO)
    Call<GetCustomerInfoResponse> getCustomerInfo();

    @GET(Constants.TYPE)
    Call<TypeResponse> getTYPE();

    @GET(Constants.CATEGORY)
    Call<StylelookDataResponse> getImages(
            @Path("type_id") int type_id);

    @GET(Constants.SUB_CATEGORY_ID)
    Call<SubCategoryResponse> getSubCategoryData(
            @Path("parent_id") Integer category_id);

    @GET(Constants.PRODUCT_DESCP)
    Call<ProductDescriptionRes> getProductDes(
            @Path("item_id") Integer item_id);

    @GET(Constants.STYLE_PIECE)
    Call<PieceSelectionResponse> getStylePiece(
            @Path("subcategory_id") Integer subcategory_id);

    @GET(Constants.MEASUREMRNT_HISTORY)
    Call<MeasurementHistoryResponse> getmeasurementHistory();

    @GET(Constants.USER_PROFILE)
    Call<GetUserProfileResponse> getuserProfile();

    @POST(Constants.UPDATE_PROFILE)
    Call<UpdateUserProfileResponse> upateUserProfile(@Body UpdateUserProfileRequest updateUserProfileRequest);

    @GET(Constants.GET_COUNTRY)
    Call<GetCountryResponse> getCountry();

    @GET(Constants.GET_REGION)
    Call<GetStateResponse> getRegionList(
            @Path("country_id") String country_id);

    @POST(Constants.ADD_ADDRESS)
    Call<AddAddressResponse> addUserAddress(@Body AddAndUpdateAddressRequest addAndUpdateAddressRequest);

    @POST(Constants.CHANGE_PASSWORD)
    Call<ChangePasswordResponse> changePasseord(@Body ChangePassRequestBody changePassRequestBody);

    @GET(Constants.ADD_ADDRESS)
    Call<AddressListResponse> getAddList();

    @PUT(Constants.UPDATE_ADDRESS)
    Call<UpdateAddressResponse> updateUserAddress(@Body AddAndUpdateAddressRequest addAndUpdateAddressRequest,
                                                  @Path("address_id") int address_id);

    @PUT(Constants.UPDATE_DEFAULT_ADDRESS)
    Call<UpdateDefaultAddressResponse> updateDefaultAddress(@Body UpdateDefaultAddressRequest updateDefaultAddressRequest);

    @DELETE(Constants.UPDATE_ADDRESS)
    Call<DeleteAddressResponse> deleteAddress(@Path("address_id") int address_id);

    @POST(Constants.FABRIC_FILTER)
    Call<FabricFilterResponse> getFabricFilter(@Body FabricListRequest fabricListRequest, @Path("subcategory_id") int subcategory_id);

    @POST(Constants.TAB_LIST)
    Call<PieceResponse> getTabListData(@Path("piece_id") int subcategory_id);

    @GET(Constants.FABRIC_FILTER)
    Call<FabricFilterListResponse> getFilterList(@Path("subcategory_id") int subcategory_id);

    @GET(Constants.ORDER_LIST)
    Call<OrderListResponse> getOrderlist();

    @GET(Constants.ORDER_DETAIL)
    Call<OrderDetailResponse> getOrderData(@Path("order_id") int order_id);

    @GET(Constants.CART_LIST)
    Call<CartListResponse> getCartList();

    @POST(Constants.CHECK_PROCESS_ID)
    Call<CheckMeasureMe> checkProcessId(@Body MeasureMeRequest req);

    @HTTP(method = "DELETE", path = Constants.CART_LIST, hasBody = true)
    Call<DeleteCartResponse> deleteCart(@Body DeleteCartItemRequest deleteCartItemRequest);

    @POST(Constants.REORDER_LIST)
    Call<AddToCartResponse> ReORDERaddToCart(@Body ReorderCartRequest reorderCartRequest);

    @POST(Constants.CART_QUANTITY)
    Call<UpdateCartQuantityResponse> updateCart(@Body UpdateCartQuantityRequest updateCartQuantityRequest);

    @GET(Constants.GET_PIECE_TYPE)
    Call<FabricSelectionGetPieceTypesResponse> getPieceTYPE(@Path("piece_id") int piece_id);

    @POST(Constants.GET_FEATURE_IMAGE)
    Call<GetFeatureImagesResponse> getFeatureIMAGES(@Body GetFeatureImagesRequest getFeatureImagesRequest);

    @GET(Constants.GET_LINING)
    Call<LiningFilterOptionResponse> getLiningFilter(@Path("piece_id") int piece_id);

    @POST(Constants.GET_LINING)
    Call<LiningResponse> getDataToSetLiningFilter(@Body LiningFilterListRequest liningFilterListRequest, @Path("piece_id") int piece_id);

    @GET(Constants.GET_STYLE)
    Call<StyleResponse> getStyledata(@Path("piece_id") int piece_id);

    @GET(Constants.GET_ACCENT)
    Call<AccentResponse> getAccentData(@Path("piece_id") int piece_id);

    @POST(Constants.CART_LIST)
    Call<AddToCartResponse> LiningAddToCart(@Body LiningAddToCartRequest liningAddToCartRequest);

    @POST(Constants.PLACE_ORDER)
    Call<ChangePasswordResponse> placeOrder(@Body PlaceOrderRequestBody paymentReqBody);

}
