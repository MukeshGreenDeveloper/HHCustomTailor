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
import com.ms.hht.ui.payment.PlaceOrderRequestBody;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class APICallList {

    public static CompositeDisposable disposable = new CompositeDisposable();
    public static APIService service;

    public static void getType(final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<TypeResponse> process_service = service.getTYPE();
        process_service.enqueue(new Callback<TypeResponse>() {
            @Override
            public void onResponse(Call<TypeResponse> call, retrofit2.Response<TypeResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(TypeResponse.class, new Annotation[0]);
                            TypeResponse errorBody = (TypeResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TypeResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getCustomerInfo(final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<GetCustomerInfoResponse> process_service = service.getCustomerInfo();
        process_service.enqueue(new Callback<GetCustomerInfoResponse>() {
            @Override
            public void onResponse(Call<GetCustomerInfoResponse> call, retrofit2.Response<GetCustomerInfoResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(GetCustomerInfoResponse.class, new Annotation[0]);
                            GetCustomerInfoResponse errorBody = (GetCustomerInfoResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>> of getCustomerInfo in api call list " + response.errorBody());
                            System.out.println("response code ==>> of getCustomerInfo in api call list " + response.code());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetCustomerInfoResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void userSignUp(SignupRequest signupRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<SignUpResponse> process_service = service.userSignUp(signupRequest);
        process_service.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]);
                            SignUpResponse errorBody = (SignUpResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void userLogin(SignupRequest signupRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<SignUpResponse> login_service = service.userLogin(signupRequest);
        login_service.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]);
                            SignUpResponse errorBody = (SignUpResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        }
                        else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        }
                        else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    }
                    else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void userLoginByToken(String token, final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<SignUpResponse> login_service = service.userLoginWithToken(token);
        Log.d("keyss...","Trying to login2 token received in userLoginByToken()==>"+token);
        login_service.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {
                Log.d("keyss...","Response received in onResponse==>"+token);

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]);
                            SignUpResponse errorBody = (SignUpResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        }
                        else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        }
                        else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    }
                    else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void deleteAccount(final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<AddToCartResponse> delete_service = service.deleteUserAccount();
        delete_service.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, retrofit2.Response<AddToCartResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(AddToCartResponse.class, new Annotation[0]);
                            AddToCartResponse errorBody = (AddToCartResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        }
                        else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        }
                        else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    }
                    else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void forgotPassword(SignupRequest signupRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<SignUpResponse> process_service = service.forgotPassword(signupRequest);
        process_service.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]);
                            SignUpResponse errorBody = (SignUpResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void resendOTP(SignupRequest signupRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<SignUpResponse> process_service = service.resendUserOTP(signupRequest);
        process_service.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]);
                            SignUpResponse errorBody = (SignUpResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void generatePassword(GeneratePasswordRequest generatePasswordRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<SignUpResponse> process_service = service.generateNewPassword(generatePasswordRequest);
        process_service.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]);
                            SignUpResponse errorBody = (SignUpResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getCatImages(int typeId, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<StylelookDataResponse> process_service = service.getImages(typeId);
        process_service.enqueue(new Callback<StylelookDataResponse>() {
            @Override
            public void onResponse(Call<StylelookDataResponse> call, retrofit2.Response<StylelookDataResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(StylelookDataResponse.class, new Annotation[0]);
                            StylelookDataResponse errorBody = (StylelookDataResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StylelookDataResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getCustImages(Integer entityId, String item, DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<SubCategoryResponse> process_service = service.getSubCategoryData(entityId);
        process_service.enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, retrofit2.Response<SubCategoryResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SubCategoryResponse.class, new Annotation[0]);
                            SubCategoryResponse errorBody = (SubCategoryResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void getProductDescp(Integer itemId, String item, DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<ProductDescriptionRes> process_service = service.getProductDes(itemId);
        process_service.enqueue(new Callback<ProductDescriptionRes>() {
            @Override
            public void onResponse(Call<ProductDescriptionRes> call, retrofit2.Response<ProductDescriptionRes> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(ProductDescriptionRes.class, new Annotation[0]);
                            ProductDescriptionRes errorBody = (ProductDescriptionRes) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductDescriptionRes> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static void getPiece(Integer subcategory_id, String item, DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<PieceSelectionResponse> process_service = service.getStylePiece(subcategory_id);
        process_service.enqueue(new Callback<PieceSelectionResponse>() {
            @Override
            public void onResponse(Call<PieceSelectionResponse> call, retrofit2.Response<PieceSelectionResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(PieceSelectionResponse.class, new Annotation[0]);
                            PieceSelectionResponse errorBody = (PieceSelectionResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PieceSelectionResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static void verifyOTP(VerifyOtpRequest verifyOtpRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<SignUpResponse> process_service = service.verifyUserOtp(verifyOtpRequest);
        process_service.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]);
                            SignUpResponse errorBody = (SignUpResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void ForgotUserOTP(ForgotPassOTPRequestBody ForgpotUserverifyOtpRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<SignUpResponse> process_service = service.verifyForgotUserOtp(ForgpotUserverifyOtpRequest);
        process_service.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(SignUpResponse.class, new Annotation[0]);
                            SignUpResponse errorBody = (SignUpResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getMeasurementHistoryList(final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<MeasurementHistoryResponse> measureService = service.getmeasurementHistory();
        measureService.enqueue(new Callback<MeasurementHistoryResponse>() {
            @Override
            public void onResponse(Call<MeasurementHistoryResponse> call, retrofit2.Response<MeasurementHistoryResponse> response) {

                try {
                    if (!response.isSuccessful()) {
//                        res.onError(response.errorBody().toString());
                        res.onError("Server is down for maintenance sorry for inconvenience.");
//                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
//                            Converter<ResponseBody, Object> errorConverter =
//                                    APIClient.retrofit.responseBodyConverter(MeasurementHistoryResponse.class, new Annotation[0]);
//                            MeasurementHistoryResponse errorBody = (MeasurementHistoryResponse) errorConverter.convert(response.errorBody());
////                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
//                            System.out.println("error body2==>>" + response.errorBody());
//                            res.onSuccess(item, response.errorBody());
//                        } else if (response.code() >= 200 && response.code() < 301) {
//                            MeasurementHistoryResponse errorBody = (MeasurementHistoryResponse) errorConverter.convert(response.errorBody());
////                            System.out.println("ERROR: ==>>" + errorBody.getMessage())
//                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MeasurementHistoryResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getUserProfileData(final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<GetUserProfileResponse> process_service = service.getuserProfile();
        process_service.enqueue(new Callback<GetUserProfileResponse>() {
            @Override
            public void onResponse(Call<GetUserProfileResponse> call, retrofit2.Response<GetUserProfileResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(GetUserProfileResponse.class, new Annotation[0]);
                            GetUserProfileResponse errorBody = (GetUserProfileResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetUserProfileResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void updateUserProfile(UpdateUserProfileRequest updateUserProfileRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<UpdateUserProfileResponse> process_service = service.upateUserProfile(updateUserProfileRequest);
        process_service.enqueue(new Callback<UpdateUserProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateUserProfileResponse> call, retrofit2.Response<UpdateUserProfileResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(UpdateUserProfileResponse.class, new Annotation[0]);
                            UpdateUserProfileResponse errorBody = (UpdateUserProfileResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserProfileResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getCountry(final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<GetCountryResponse> process_service = service.getCountry();
        process_service.enqueue(new Callback<GetCountryResponse>() {
            @Override
            public void onResponse(Call<GetCountryResponse> call, retrofit2.Response<GetCountryResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(GetCountryResponse.class, new Annotation[0]);
                            GetCountryResponse errorBody = (GetCountryResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetCountryResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getRegion(String countryId, String item, DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<GetStateResponse> process_service = service.getRegionList(countryId);
        process_service.enqueue(new Callback<GetStateResponse>() {
            @Override
            public void onResponse(Call<GetStateResponse> call, retrofit2.Response<GetStateResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(GetStateResponse.class, new Annotation[0]);
                            GetStateResponse errorBody = (GetStateResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetStateResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static void addAddress(AddAndUpdateAddressRequest addAndUpdateAddressRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<AddAddressResponse> process_service = service.addUserAddress(addAndUpdateAddressRequest);
        process_service.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, retrofit2.Response<AddAddressResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(AddAddressResponse.class, new Annotation[0]);
                            AddAddressResponse errorBody = (AddAddressResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getAddressList(String item, DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<AddressListResponse> process_service = service.getAddList();
        process_service.enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, retrofit2.Response<AddressListResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(AddressListResponse.class, new Annotation[0]);
                            AddressListResponse errorBody = (AddressListResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static void UpdateAddress(AddAndUpdateAddressRequest addAndUpdateAddressRequest, final int addressId, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<UpdateAddressResponse> process_service = service.updateUserAddress(addAndUpdateAddressRequest, addressId);
        process_service.enqueue(new Callback<UpdateAddressResponse>() {
            @Override
            public void onResponse(Call<UpdateAddressResponse> call, retrofit2.Response<UpdateAddressResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(UpdateAddressResponse.class, new Annotation[0]);
                            UpdateAddressResponse errorBody = (UpdateAddressResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpdateAddressResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void UpdateDefaultAddress(UpdateDefaultAddressRequest defaultAddressRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<UpdateDefaultAddressResponse> process_service = service.updateDefaultAddress(defaultAddressRequest);
        process_service.enqueue(new Callback<UpdateDefaultAddressResponse>() {
            @Override
            public void onResponse(Call<UpdateDefaultAddressResponse> call, retrofit2.Response<UpdateDefaultAddressResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(UpdateDefaultAddressResponse.class, new Annotation[0]);
                            UpdateDefaultAddressResponse errorBody = (UpdateDefaultAddressResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpdateDefaultAddressResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void DeleteAddress(final int address_id, final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<DeleteAddressResponse> process_service = service.deleteAddress(address_id);
        process_service.enqueue(new Callback<DeleteAddressResponse>() {
            @Override
            public void onResponse(Call<DeleteAddressResponse> call, retrofit2.Response<DeleteAddressResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(DeleteAddressResponse.class, new Annotation[0]);
                            DeleteAddressResponse errorBody = (DeleteAddressResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DeleteAddressResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void ChangePassword(ChangePassRequestBody changePassRequestBody, final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<ChangePasswordResponse> process_service = service.changePasseord(changePassRequestBody);
        process_service.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, retrofit2.Response<ChangePasswordResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(ChangePasswordResponse.class, new Annotation[0]);
                            ChangePasswordResponse errorBody = (ChangePasswordResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getfabricList(FabricListRequest fabricListRequest, int subCategoryID, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<FabricFilterResponse> fabric_service = service.getFabricFilter(fabricListRequest, subCategoryID);
        fabric_service.enqueue(new Callback<FabricFilterResponse>() {
            @Override
            public void onResponse(Call<FabricFilterResponse> call, retrofit2.Response<FabricFilterResponse> response) {

                try {

                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(FabricFilterResponse.class, new Annotation[0]);
                            FabricFilterResponse errorBody = (FabricFilterResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FabricFilterResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getfilterList(int subCategoryID, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<FabricFilterListResponse> process_service = service.getFilterList(subCategoryID);
        process_service.enqueue(new Callback<FabricFilterListResponse>() {
            @Override
            public void onResponse(Call<FabricFilterListResponse> call,
                                   retrofit2.Response<FabricFilterListResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(FabricFilterListResponse.class, new Annotation[0]);
                            FabricFilterListResponse errorBody = (FabricFilterListResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FabricFilterListResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getOrderLIST(String item, DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<OrderListResponse> process_service = service.getOrderlist();
        process_service.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, retrofit2.Response<OrderListResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(OrderListResponse.class, new Annotation[0]);
                            OrderListResponse errorBody = (OrderListResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static void getorderDETAIL(int orderid, String item, DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<OrderDetailResponse> process_service = service.getOrderData(orderid);
        process_service.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, retrofit2.Response<OrderDetailResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(OrderDetailResponse.class, new Annotation[0]);
                            OrderDetailResponse errorBody = (OrderDetailResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getcartList(String item, DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        System.out.println("service inside getcartList in Api call list " + service);
        Call<CartListResponse> process_service = service.getCartList();
        process_service.enqueue(new Callback<CartListResponse>() {
            @Override
            public void onResponse(Call<CartListResponse> call, retrofit2.Response<CartListResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(CartListResponse.class, new Annotation[0]);
                            CartListResponse errorBody = (CartListResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CartListResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void checkProcessId(MeasureMeRequest measureMeRequest, String item, DisposableData res, Context context) {
        service = APIClient.getClientUrl2(context).create(APIService.class);
        System.out.println("service inside getcartList in Api call list " + service);
        Call<CheckMeasureMe> process_service = service.checkProcessId(measureMeRequest);
        process_service.enqueue(new Callback<CheckMeasureMe>() {
            @Override
            public void onResponse(Call<CheckMeasureMe> call, retrofit2.Response<CheckMeasureMe> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(CheckMeasureMe.class, new Annotation[0]);
                            CheckMeasureMe errorBody = (CheckMeasureMe) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CheckMeasureMe> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void deleteCartList(DeleteCartItemRequest deleteCartItemRequest, String item, DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<DeleteCartResponse> process_service = service.deleteCart(deleteCartItemRequest);
        process_service.enqueue(new Callback<DeleteCartResponse>() {
            @Override
            public void onResponse(Call<DeleteCartResponse> call, retrofit2.Response<DeleteCartResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(DeleteCartResponse.class, new Annotation[0]);
                            DeleteCartResponse errorBody = (DeleteCartResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DeleteCartResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public static void getMSMeasurement(GetMSMeasurementRequest getMSMeasurementRequest, final String str,
                                        final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClientUrl2(context).create(APIService.class);
        service = aPIService;
        aPIService.getMSMeasurement(getMSMeasurementRequest).enqueue(new Callback<GETMSMeasurementResponse>() {
            public void onResponse(Call<GETMSMeasurementResponse> call, Response<GETMSMeasurementResponse> response) {
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
                        disposableData.onSuccess(str, APIClient.retrofit.responseBodyConverter(GETMSMeasurementResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<GETMSMeasurementResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void setUserMeasurement(Map<String, Object> map, final String str, final DisposableData disposableData, Context context) {
        APIService aPIService = (APIService) APIClient.getClient(context).create(APIService.class);
        service = aPIService;
        aPIService.setUserMeasurement(map).enqueue(new Callback<SETmeasurementResponse>() {
            public void onResponse(Call<SETmeasurementResponse> call, Response<SETmeasurementResponse> response) {
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
                        disposableData.onSuccess(str, APIClient.retrofit
                                .responseBodyConverter(SETmeasurementResponse.class, new Annotation[0]).convert(response.errorBody()));
                        return;
                    }
                    disposableData.onSuccess(str, response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<SETmeasurementResponse> call, Throwable th) {
                try {
                    System.out.println("ERRORRRRR*****" + th.toString());
                    disposableData.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void ReorderAddToCart(ReorderCartRequest reorderCartRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<AddToCartResponse> process_service = service.ReORDERaddToCart(reorderCartRequest);
        process_service.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, retrofit2.Response<AddToCartResponse> response) {

                try {

                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(AddToCartResponse.class, new Annotation[0]);
                            AddToCartResponse errorBody = (AddToCartResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void updateCartQuantity(UpdateCartQuantityRequest updateCartQuantityRequest, String item, DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<UpdateCartQuantityResponse> process_service = service.updateCart(updateCartQuantityRequest);
        process_service.enqueue(new Callback<UpdateCartQuantityResponse>() {
            @Override
            public void onResponse(Call<UpdateCartQuantityResponse> call, retrofit2.Response<UpdateCartQuantityResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(UpdateCartQuantityResponse.class, new Annotation[0]);
                            UpdateCartQuantityResponse errorBody = (UpdateCartQuantityResponse) errorConverter.convert(response.errorBody());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpdateCartQuantityResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getPIECETYPE(int piece_id, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<FabricSelectionGetPieceTypesResponse> process_service = service.getPieceTYPE(piece_id);

        process_service.enqueue(new Callback<FabricSelectionGetPieceTypesResponse>() {
            @Override
            public void onResponse(Call<FabricSelectionGetPieceTypesResponse> call,
                                   retrofit2.Response<FabricSelectionGetPieceTypesResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            res.onError("Server Error");
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FabricSelectionGetPieceTypesResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getFabricImages(GetFeatureImagesRequest getFeatureImagesRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<GetFeatureImagesResponse> process_service = service.getFeatureIMAGES(getFeatureImagesRequest);

        process_service.enqueue(new Callback<GetFeatureImagesResponse>() {
            @Override
            public void onResponse(Call<GetFeatureImagesResponse> call,
                                   retrofit2.Response<GetFeatureImagesResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(GetFeatureImagesResponse.class, new Annotation[0]);
                            GetFeatureImagesResponse errorBody = (GetFeatureImagesResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetFeatureImagesResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getLiningFilter(int piece_id, final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<LiningFilterOptionResponse> process_service = service.getLiningFilter(piece_id);
        process_service.enqueue(new Callback<LiningFilterOptionResponse>() {
            @Override
            public void onResponse(Call<LiningFilterOptionResponse> call,
                                   retrofit2.Response<LiningFilterOptionResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(LiningFilterOptionResponse.class, new Annotation[0]);
                            LiningFilterOptionResponse errorBody = (LiningFilterOptionResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LiningFilterOptionResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getDataOfLiningFilter(LiningFilterListRequest liningFilterListRequest, int piece_id, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<LiningResponse> fabric_service = service.getDataToSetLiningFilter(liningFilterListRequest, piece_id);
        fabric_service.enqueue(new Callback<LiningResponse>() {
            @Override
            public void onResponse(Call<LiningResponse> call, retrofit2.Response<LiningResponse> response) {

                try {

                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(LiningResponse.class, new Annotation[0]);
                            LiningResponse errorBody = (LiningResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LiningResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getStyleData(int piece_id, final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<StyleResponse> process_service = service.getStyledata(piece_id);
        process_service.enqueue(new Callback<StyleResponse>() {
            @Override
            public void onResponse(Call<StyleResponse> call,
                                   retrofit2.Response<StyleResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(StyleResponse.class, new Annotation[0]);
                            StyleResponse errorBody = (StyleResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StyleResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getAccentData(int piece_id, final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<AccentResponse> process_service = service.getAccentData(piece_id);
        process_service.enqueue(new Callback<AccentResponse>() {
            @Override
            public void onResponse(Call<AccentResponse> call,
                                   retrofit2.Response<AccentResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(AccentResponse.class, new Annotation[0]);
                            AccentResponse errorBody = (AccentResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AccentResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getFabricData(int piece_id, final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<AccentResponse> process_service = service.getAccentData(piece_id);
        process_service.enqueue(new Callback<AccentResponse>() {
            @Override
            public void onResponse(Call<AccentResponse> call,
                                   retrofit2.Response<AccentResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(AccentResponse.class, new Annotation[0]);
                            AccentResponse errorBody = (AccentResponse) errorConverter.convert(response.errorBody());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AccentResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void LiningProductAddToCart(LiningAddToCartRequest liningAddToCartRequest, final String item, final DisposableData res, Context context) {

        service = APIClient.getClient(context).create(APIService.class);
        Call<AddToCartResponse> process_service = service.LiningAddToCart(liningAddToCartRequest);
        System.out.println("service of addd to cart of add to cart in api calll list "+ process_service);
        process_service.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, retrofit2.Response<AddToCartResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(AddToCartResponse.class, new Annotation[0]);
                            AddToCartResponse errorBody = (AddToCartResponse) errorConverter.convert(response.errorBody());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                try {
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void placeOrderAPi(PlaceOrderRequestBody paymentRequestObject, final String item, final DisposableData res, Context context) {
        service = APIClient.getClient(context).create(APIService.class);
        Call<ChangePasswordResponse> process_service = service.placeOrder(paymentRequestObject);
        process_service.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, retrofit2.Response<ChangePasswordResponse> response) {

                try {
                    if (!response.isSuccessful()) {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            Converter<ResponseBody, Object> errorConverter =
                                    APIClient.retrofit.responseBodyConverter(ChangePasswordResponse.class, new Annotation[0]);
                            ChangePasswordResponse errorBody = (ChangePasswordResponse) errorConverter.convert(response.errorBody());
//                            System.out.println("ERROR: ==>>" + errorBody.getMessage());
                            System.out.println("error body2==>>" + response.errorBody());
                            res.onSuccess(item, errorBody);
                        } else if (response.code() == 408) {
                            res.onError("Request timed out check your internet connection and try again");
                        } else {
                            res.onError("Server is down for maintenance sorry for the inconvenience.");
                        }
                    } else {
                        res.onSuccess(item, response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                try {
                    System.out.println("ERRORRRRR*****" + t.toString());
                    res.onError("Server is down for maintenance sorry for inconvenience.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}