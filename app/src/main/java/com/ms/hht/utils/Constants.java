package com.ms.hht.utils;

import com.ms.hht.R;
import com.ms.hht.ui.payment.PlaceOrder_billing_address;
import com.ms.hht.ui.payment.PlaceOrder_shipping_address;

public class Constants {

    public static final String BASE_URL = "https://api.hhcustomtailor.com/api/v1/";
    public static final String base_url = "https://api.mysize.mirrorsize.com/";
    public static final String base_url2 = "https://api.services.mirrorsize.com/";
    public static final String BASE_URL2 = "https://fitdetection.msways.com/getFitType";

    public static String shippingAddress_VALUE = "";
    public static String billingAddress_VALUE = "";
    public static PlaceOrder_shipping_address shippingAddress_VALUE_ObJ_TYPE  ;
    public static PlaceOrder_billing_address billingAddress_VALUE_OBJ_TYPE ;

    public static String Login_From = "";

    public static String PAYPAL_LIVE_CLIENT_ID = "AZitRVDZCaKLmSXMV8WZSfM1qu1tYnhBpgkjCzza8RtS2umtQ61JIrFJd2uX99-bxENL6za_k-8Ccn4I";
    public static String PAYPAL_SANDBOX_CLIENT_ID = "AXfpchZSvEkdvv9LqWwj4IO8dkhjQgKHTzqxPyscuSnvx3z_AOe7mCCpbIGxuV1OHPc3ykoNGTRfTy3f";


    public static final String SIGN_UP = "signup/";
    public static final String LOGIN = "signin/";
    public static final String LOGIN_WITH_TOKEN = "signin_with_token/";
    public static final String FORGOT_PASSWORD = "forgot_password/";
    public static final String RESENT_OTP = "resend_otp/";
    public static final String GENERATE_PASSWORD = "generate_new_password/";
    public static final String VERIFY_OTP = "verify_otp/";
    public static final String TYPE = "types/";
    public static final String CUSTOMER_INFO = "customer_info/";
    public static final String CATEGORY = "category/{type_id}/";
    public static final String SUB_CATEGORY_ID = "subcategory/{parent_id}/";
    public static final String PRODUCT_DESCP = "item_details/{item_id}/";
    public static final String MEASUREMRNT_HISTORY = "measurements/";

    public static final String USER_PROFILE = "customer_info/";
    public static final String UPDATE_PROFILE = "update_account_info/";
    public static final String CHANGE_PASSWORD = "change_password/";

    public static final String GET_COUNTRY = "country/";
    public static final String GET_REGION = "region/{country_id}/";
    public static final String ADD_ADDRESS = "address/";
    public static final String UPDATE_ADDRESS = "address/{address_id}";
    public static final String UPDATE_DEFAULT_ADDRESS = "default_address/";
    public static final String FABRIC_FILTER = "filters/{subcategory_id}/";
    public static final String ORDER_LIST = "recent_orders/";
    public static final String ORDER_DETAIL = "order/{order_id}";

    public static final String CART_LIST = "cart/";
    public static final String CHECK_PROCESS_ID = "api/user/checkprocessid";
    public static final String GET_PROCESS_ID = "api/ms_initialize_user";
    public static final String TAB_LIST = "piece_types/{piece_id}/";
    public static final String GET_PROCESS_URL = "https://api.mysize.mirrorsize.com/api/ms_initialize_user/";
    public static final String GET_MS_MEASUREMENT_URL = "https://api.mysize.mirrorsize.com/api/measurement/getmeasurement/";
    public static final String SET_MEASUREMENT_URL = "measurement/";
    public static final String GET_MEASUREMENT_URL = "api/measurement/getmeasurement/";
    public static final String CART_QUANTITY = "quantity/";
    public static final String REORDER_LIST = "reorder/";
    public static final String GET_PIECE_TYPE = "piece_types/{piece_id}/";
    public static final String STYLE_PIECE = "piece/{subcategory_id}/";
    public static final String GET_FEATURE_IMAGE = "feature_images/";
    public static final String GET_LINING = "lining_filters/{piece_id}/";
    public static final String GET_STYLE = "style/{piece_id}/";
    public static final String GET_ACCENT = "accent/{piece_id}/";


    public static final String PLACE_ORDER = "place_order/";
    public static final String DELETE_ACCOUNT = "delete_account/";
    public static final String APP_STATUS = "api/app-status";

}


