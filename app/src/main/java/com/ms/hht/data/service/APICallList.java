package com.ms.hht.data.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
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
import com.ms.hht.ui.measure.EnterDetails;
import com.ms.hht.ui.measure.PosePreviewAct;
import com.ms.hht.ui.payment.PlaceOrderRequestBody;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.HHLogger;

import io.reactivex.disposables.CompositeDisposable;

import java.lang.annotation.Annotation;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class APICallList {
    public static CompositeDisposable disposable = new CompositeDisposable();
    public static APIService service;

    public static void getType(final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getTYPE().enqueue(new Callback<TypeResponse>() {
            public void onResponse(Call<TypeResponse> call, Response<TypeResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(TypeResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<TypeResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getCustomerInfo(final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getCustomerInfo().enqueue(new Callback<GetCustomerInfoResponse>() {
            public void onResponse(Call<GetCustomerInfoResponse> call, Response<GetCustomerInfoResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>> of getCustomerInfo in api call list " + response.errorBody());
                        System.out.println("response code ==>> of getCustomerInfo in api call list " + response.code());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(GetCustomerInfoResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<GetCustomerInfoResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void userSignUp(SignupRequest signupRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.userSignUp(signupRequest).enqueue(new Callback<SignUpResponse>() {
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SignUpResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void userLogin(SignupRequest signupRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.userLogin(signupRequest).enqueue(new Callback<SignUpResponse>() {
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SignUpResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void userLoginByToken(String token, final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<SignUpResponse> login_service = service.userLoginWithToken(token);
        Log.d("keyss...", "Trying to login2 token received in userLoginByToken()==>" + token);
        HHLogger.getINSTANCE(context).REQUEST("EnterDetails", Constants.LOGIN_WITH_TOKEN,token,null);
        login_service.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {
                Log.d("keyss...", "Response received in onResponse==>" + token);

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]);
                            SignUpResponse errorBody = (SignUpResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            HHLogger.getINSTANCE(context).RESPONSE("EnterDetails", Constants.LOGIN_WITH_TOKEN,new Gson().toJson(errorBody),
                                    null,response.code());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        HHLogger.getINSTANCE(context).RESPONSE("EnterDetails", Constants.LOGIN_WITH_TOKEN,new Gson().toJson(response.body()),
                                null,response.code());
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    HHLogger.getINSTANCE(context).EXCEPTION("EnterDetails", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                    HHLogger.getINSTANCE(context).RESPONSE("EnterDetails", Constants.LOGIN_WITH_TOKEN,t.toString(),
                            null,500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void deleteAccount(final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.deleteUserAccount().enqueue(new Callback<AddToCartResponse>() {
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(AddToCartResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<AddToCartResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void forgotPassword(SignupRequest signupRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.forgotPassword(signupRequest).enqueue(new Callback<SignUpResponse>() {
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SignUpResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void resendOTP(SignupRequest signupRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.resendUserOTP(signupRequest).enqueue(new Callback<SignUpResponse>() {
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SignUpResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void generatePassword(GeneratePasswordRequest generatePasswordRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.generateNewPassword(generatePasswordRequest).enqueue(new Callback<SignUpResponse>() {
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SignUpResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getCatImages(int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getImages(i).enqueue(new Callback<StylelookDataResponse>() {
            public void onResponse(Call<StylelookDataResponse> call, Response<StylelookDataResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(StylelookDataResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<StylelookDataResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getCustImages(Integer num, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getSubCategoryData(num).enqueue(new Callback<SubCategoryResponse>() {
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SubCategoryResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SubCategoryResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getProductDescp(Integer num, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getProductDes(num).enqueue(new Callback<ProductDescriptionRes>() {
            public void onResponse(Call<ProductDescriptionRes> call, Response<ProductDescriptionRes> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(ProductDescriptionRes.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ProductDescriptionRes> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getPiece(Integer num, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getStylePiece(num).enqueue(new Callback<PieceSelectionResponse>() {
            public void onResponse(Call<PieceSelectionResponse> call, Response<PieceSelectionResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(PieceSelectionResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<PieceSelectionResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void verifyOTP(VerifyOtpRequest verifyOtpRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.verifyUserOtp(verifyOtpRequest).enqueue(new Callback<SignUpResponse>() {
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SignUpResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void ForgotUserOTP(ForgotPassOTPRequestBody forgotPassOTPRequestBody, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.verifyForgotUserOtp(forgotPassOTPRequestBody).enqueue(new Callback<SignUpResponse>() {
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SignUpResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getMeasurementHistoryList(final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getmeasurementHistory().enqueue(new Callback<MeasurementHistoryResponse>() {
            public void onResponse(Call<MeasurementHistoryResponse> call, Response<MeasurementHistoryResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                    } else {
                        disposableData.onSuccess(str, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<MeasurementHistoryResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getUserProfileData(final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getuserProfile().enqueue(new Callback<GetUserProfileResponse>() {
            public void onResponse(Call<GetUserProfileResponse> call, Response<GetUserProfileResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(GetUserProfileResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<GetUserProfileResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void updateUserProfile(UpdateUserProfileRequest updateUserProfileRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.upateUserProfile(updateUserProfileRequest).enqueue(new Callback<UpdateUserProfileResponse>() {
            public void onResponse(Call<UpdateUserProfileResponse> call, Response<UpdateUserProfileResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(UpdateUserProfileResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<UpdateUserProfileResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getCountry(final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getCountry().enqueue(new Callback<GetCountryResponse>() {
            public void onResponse(Call<GetCountryResponse> call, Response<GetCountryResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(GetCountryResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<GetCountryResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getRegion(String str, final String str2, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getRegionList(str).enqueue(new Callback<GetStateResponse>() {
            public void onResponse(Call<GetStateResponse> call, Response<GetStateResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str2, APIClient.retrofit.responseBodyConverter(GetStateResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str2, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<GetStateResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void addAddress(AddAndUpdateAddressRequest addAndUpdateAddressRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.addUserAddress(addAndUpdateAddressRequest).enqueue(new Callback<AddAddressResponse>() {
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(AddAddressResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<AddAddressResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getAddressList(final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getAddList().enqueue(new Callback<AddressListResponse>() {
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(AddressListResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<AddressListResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void UpdateAddress(AddAndUpdateAddressRequest addAndUpdateAddressRequest, int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.updateUserAddress(addAndUpdateAddressRequest, i).enqueue(new Callback<UpdateAddressResponse>() {
            public void onResponse(Call<UpdateAddressResponse> call, Response<UpdateAddressResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(UpdateAddressResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<UpdateAddressResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void UpdateDefaultAddress(UpdateDefaultAddressRequest updateDefaultAddressRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.updateDefaultAddress(updateDefaultAddressRequest).enqueue(new Callback<UpdateDefaultAddressResponse>() {
            public void onResponse(Call<UpdateDefaultAddressResponse> call, Response<UpdateDefaultAddressResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(UpdateDefaultAddressResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<UpdateDefaultAddressResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void DeleteAddress(int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.deleteAddress(i).enqueue(new Callback<DeleteAddressResponse>() {
            public void onResponse(Call<DeleteAddressResponse> call, Response<DeleteAddressResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(DeleteAddressResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<DeleteAddressResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void ChangePassword(ChangePassRequestBody changePassRequestBody, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.changePasseord(changePassRequestBody).enqueue(new Callback<ChangePasswordResponse>() {
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(ChangePasswordResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ChangePasswordResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getfabricList(FabricListRequest fabricListRequest, int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getFabricFilter(fabricListRequest, i).enqueue(new Callback<FabricFilterResponse>() {
            public void onResponse(Call<FabricFilterResponse> call, Response<FabricFilterResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(FabricFilterResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<FabricFilterResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getfilterList(int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getFilterList(i).enqueue(new Callback<FabricFilterListResponse>() {
            public void onResponse(Call<FabricFilterListResponse> call, Response<FabricFilterListResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(FabricFilterListResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<FabricFilterListResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getOrderLIST(final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getOrderlist().enqueue(new Callback<OrderListResponse>() {
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(OrderListResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<OrderListResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getorderDETAIL(int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getOrderData(i).enqueue(new Callback<OrderDetailResponse>() {
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(OrderDetailResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<OrderDetailResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getcartList(final String str, final DisposableData disposableData, Context context) {
        service = (APIService) APIClient.getClient(context).create(APIService.class);
        System.out.println("service inside getcartList in Api call list " + service);
        service.getCartList().enqueue(new Callback<CartListResponse>() {
            public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(CartListResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<CartListResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void checkProcessId(MeasureMeRequest measureMeRequest, final String str, final DisposableData disposableData, Context context) {
        service = (APIService) APIClient.getClientUrl2(context).create(APIService.class);
        System.out.println("service inside getcartList in Api call list " + service);
        service.checkProcessId(measureMeRequest).enqueue(new Callback<CheckMeasureMe>() {
            public void onResponse(Call<CheckMeasureMe> call, Response<CheckMeasureMe> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(CheckMeasureMe.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<CheckMeasureMe> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void deleteCartList(DeleteCartItemRequest deleteCartItemRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.deleteCart(deleteCartItemRequest).enqueue(new Callback<DeleteCartResponse>() {
            public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(DeleteCartResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<DeleteCartResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getMSMeasurement(GetMSMeasurementRequest getMSMeasurementRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClientUrl2(context).create(APIService.class);
        service = aPIService;
        HHLogger.getINSTANCE(context).REQUEST("PosePreviewAct", "api/ms_measurement_process",new Gson().toJson(getMSMeasurementRequest),null);
        aPIService.getMSMeasurement(getMSMeasurementRequest).enqueue(new Callback<GETMSMeasurementResponse>() {
            public void onResponse(Call<GETMSMeasurementResponse> call, Response<GETMSMeasurementResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        HHLogger.getINSTANCE(context).RESPONSE("PosePreviewAct", "api/ms_measurement_process","Failure",null,response.code());
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        HHLogger.getINSTANCE(context).RESPONSE("PosePreviewAct", "api/ms_measurement_process",response.errorBody().toString(),
                                null,response.code());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(GETMSMeasurementResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    HHLogger.getINSTANCE(context).RESPONSE("PosePreviewAct", "api/ms_measurement_process",new Gson().toJson(response.body()),
                            null,response.code());
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    HHLogger.getINSTANCE(context).EXCEPTION("PosePreviewAct", e.getMessage());
                }
            }

            public void onFailure(Call<GETMSMeasurementResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                    HHLogger.getINSTANCE(context).RESPONSE("PosePreviewAct","api/ms_measurement_process",th.getMessage(),null,500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setUserMeasurement(Map<String, Object> map, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        HHLogger.getINSTANCE(context)
                .REQUEST("PosePreviewAct","measurement/",null,map);
        aPIService.setUserMeasurement(map).enqueue(new Callback<SETmeasurementResponse>() {
            public void onResponse(Call<SETmeasurementResponse> call, Response<SETmeasurementResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        HHLogger.getINSTANCE(context)
                                .RESPONSE("PosePreviewAct","measurement/","Failed",null,response.code());
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        HHLogger.getINSTANCE(context)
                                .RESPONSE("PosePreviewAct","measurement/",response.errorBody().toString(),null,response.code());
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SETmeasurementResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    HHLogger.getINSTANCE(context)
                            .RESPONSE("PosePreviewAct","measurement/",new Gson().toJson(response.body()),null,response.code());
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    HHLogger.getINSTANCE(context)
                            .EXCEPTION("PosePreviewAct",e.getMessage());
                }
            }

            public void onFailure(Call<SETmeasurementResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                    HHLogger.getINSTANCE(context)
                            .RESPONSE("PosePreviewAct","measurement/",th.getMessage(),null,500);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void ReorderAddToCart(ReorderCartRequest reorderCartRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.ReORDERaddToCart(reorderCartRequest).enqueue(new Callback<AddToCartResponse>() {
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(AddToCartResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<AddToCartResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void updateCartQuantity(UpdateCartQuantityRequest updateCartQuantityRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.updateCart(updateCartQuantityRequest).enqueue(new Callback<UpdateCartQuantityResponse>() {
            public void onResponse(Call<UpdateCartQuantityResponse> call, Response<UpdateCartQuantityResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(UpdateCartQuantityResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<UpdateCartQuantityResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getPIECETYPE(int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getPieceTYPE(i).enqueue(new Callback<FabricSelectionGetPieceTypesResponse>() {
            public void onResponse(Call<FabricSelectionGetPieceTypesResponse> call, Response<FabricSelectionGetPieceTypesResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        disposableData.onError("Server Error");
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<FabricSelectionGetPieceTypesResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getFabricImages(GetFeatureImagesRequest getFeatureImagesRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getFeatureIMAGES(getFeatureImagesRequest).enqueue(new Callback<GetFeatureImagesResponse>() {
            public void onResponse(Call<GetFeatureImagesResponse> call, Response<GetFeatureImagesResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(GetFeatureImagesResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<GetFeatureImagesResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getLiningFilter(int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getLiningFilter(i).enqueue(new Callback<LiningFilterOptionResponse>() {
            public void onResponse(Call<LiningFilterOptionResponse> call, Response<LiningFilterOptionResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(LiningFilterOptionResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<LiningFilterOptionResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getDataOfLiningFilter(LiningFilterListRequest liningFilterListRequest, int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getDataToSetLiningFilter(liningFilterListRequest, i).enqueue(new Callback<LiningResponse>() {
            public void onResponse(Call<LiningResponse> call, Response<LiningResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(LiningResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<LiningResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getStyleData(int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getStyledata(i).enqueue(new Callback<StyleResponse>() {
            public void onResponse(Call<StyleResponse> call, Response<StyleResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(StyleResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<StyleResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getAccentData(int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getAccentData(i).enqueue(new Callback<AccentResponse>() {
            public void onResponse(Call<AccentResponse> call, Response<AccentResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(AccentResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<AccentResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getFabricData(int i, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.getAccentData(i).enqueue(new Callback<AccentResponse>() {
            public void onResponse(Call<AccentResponse> call, Response<AccentResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(AccentResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<AccentResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void LiningProductAddToCart(LiningAddToCartRequest liningAddToCartRequest, final String str, final DisposableData disposableData, Context context) {
        service = (APIService) APIClient.getClient(context).create(APIService.class);
        System.out.println("Request body of add to cart in api calll list " + new Gson().toJson((Object) liningAddToCartRequest));
        System.out.println("service of addd to cart of add to cart in api calll list " + service);
        Call<AddToCartResponse> LiningAddToCart = service.LiningAddToCart(liningAddToCartRequest);
        System.out.println("service of addd to cart of add to cart in api calll list " + LiningAddToCart);
        LiningAddToCart.enqueue(new Callback<AddToCartResponse>() {
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    System.out.println("code 408 response )__________________________________________________________________________");
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                }
                                System.out.println("code below 408 response )__________________________________________________________________________");
                                disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                return;
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(AddToCartResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    System.out.println("success response )__________________________________________________________________________");
                    System.out.println("success response )__________________________________________________________________________" + response.body());
                    System.out.println("success response )__________________________________________________________________________" + new Gson().toJson((Object) response.body()));
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<AddToCartResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void processID(ProcessIDRequest processIDRequest, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClientUrl2(context).create(APIService.class);
        service = aPIService;
        HHLogger.getINSTANCE(context).REQUEST("EnterDetails", "api/ms_initialize_user",new Gson().toJson(processIDRequest),null);
        aPIService.generateProcess(processIDRequest).enqueue(new Callback<ProcessResponse>() {
            public void onResponse(Call<ProcessResponse> call, Response<ProcessResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        HHLogger.getINSTANCE(context).RESPONSE("EnterDetails", "api/ms_initialize_user","Failed API",
                                null,response.code());
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        ProcessResponse convert = (ProcessResponse) APIClient.retrofit.responseBodyConverter(ProcessResponse.class, new Annotation[0]).convert(response.errorBody());
                        System.out.println("ERROR: ==>>" + convert.getMessage());
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, convert);
                        return;
                    }
                    HHLogger.getINSTANCE(context).RESPONSE("EnterDetails", "api/ms_initialize_user",new Gson().toJson(response.body()),
                            null,response.code());
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    HHLogger.getINSTANCE(context).EXCEPTION("EnterDetails", e.getMessage());
                }
            }

            public void onFailure(Call<ProcessResponse> call, Throwable th) {
                try {
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                    HHLogger.getINSTANCE(context).RESPONSE("EnterDetails", "api/ms_initialize_user",th.getMessage(),
                            null,500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getPaymentStatusAPi(PlaceOrderRequestBody placeOrderRequestBody, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.paymentCAlling(placeOrderRequestBody).enqueue(new Callback<ChangePasswordResponse>() {
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(ChangePasswordResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ChangePasswordResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void placeOrderAPi(PlaceOrderRequestBody placeOrderRequestBody, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.placeOrder(placeOrderRequestBody).enqueue(new Callback<ChangePasswordResponse>() {
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(ChangePasswordResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ChangePasswordResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void appStatus(final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClientUrl3(context).create(APIService.class);
        service = aPIService;
        aPIService.getAppStatus("HH_TAILOR", "MEASUREMENT", "ANDROID").enqueue(new Callback<AppStatusResponse>() {
            public void onResponse(Call<AppStatusResponse> call, Response<AppStatusResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (!(response.code() == 400 || response.code() == 401)) {
                            if (response.code() != 500) {
                                if (response.code() == 408) {
                                    disposableData.onError("Request timed out check your internet connection and try again");
                                    return;
                                } else {
                                    disposableData.onError("Server is down for maintenance sorry for the inconvenience.");
                                    return;
                                }
                            }
                        }
                        System.out.println("error body2==>>" + response.errorBody());
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<AppStatusResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
