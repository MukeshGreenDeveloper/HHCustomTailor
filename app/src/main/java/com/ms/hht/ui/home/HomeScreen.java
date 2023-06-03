package com.ms.hht.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.ms.hht.R;
import com.ms.hht.data.adapter.HeaderAdapter;
import com.ms.hht.data.adapter.HomeAdapter;
import com.ms.hht.data.response.AddToCartResponse;
import com.ms.hht.data.response.GetCustomerInfoResponse;
import com.ms.hht.data.response.StylelookDataResponse;
import com.ms.hht.data.response.TypeResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.APIClient;
import com.ms.hht.data.service.APIService;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActivityHomeScreenBinding;
import com.ms.hht.ui.SplashAct;
import com.ms.hht.ui.login.LoginActivity;
import com.ms.hht.ui.measure.MeasurementHistoryAct;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderCartAct;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderList;
import com.ms.hht.ui.profile.AddressListAct;
import com.ms.hht.ui.profile.ChangePassword;
import com.ms.hht.ui.profile.ContactUs;
import com.ms.hht.ui.profile.EditProfile;
import com.ms.hht.ui.profile.FAQActivity;
import com.ms.hht.ui.profile.PrivacyPolicy;
import com.ms.hht.utils.ComUserProfileData;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.RecyclerTouchListener;
import com.ms.hht.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    NavigationView navigationView;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    Toolbar toolbar;

    SessionManager sessionManager;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    APIService apiService;
    GetCustomerInfoResponse getCustomerInfoResponse;
    TypeResponse typeDataResponse;
    StylelookDataResponse categoryDataResponse;

    HeaderAdapter headerAdapter;
    int TYPE_ID_POS = 0;

    ActivityHomeScreenBinding homeScreenBinding;
    Dialog dialog;
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenBinding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(homeScreenBinding.getRoot());
        dialog = new Dialog(this);
        toolbar = findViewById(R.id.toolbar);
        expandableListView = findViewById(R.id.expandableListView);
        sessionManager = new SessionManager(HomeScreen.this);
        apiService = APIClient.getClient(HomeScreen.this).create(APIService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowProgressbar();
        email = sessionManager.getUserEmail();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamberg);
        navigationView = findViewById(R.id.nav_view);

        homeScreenBinding.appBarMain.cartImage.setOnClickListener(this);

        if (email.isEmpty()){
            getTypeAPi();
        }
        else {
            getCUSTOMERINFO();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cartImage:

                if (email.isEmpty()){
                    ShowResultPopUp();
                }
                else {
                    Intent intent2 = new Intent(HomeScreen.this, OrderCartAct.class);
                    OrderCartAct.ACTIVITY_FROM = "";
                    startActivity(intent2);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
    }



    private void getCUSTOMERINFO() {
        if (InternetConnection.isConnected(HomeScreen.this)) {
            APICallList.getCustomerInfo("get customer info", response, HomeScreen.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }

    }

    private void getTypeAPi() {
        this.typeDataResponse = new TypeResponse();
        if (InternetConnection.isConnected(HomeScreen.this)) {
            APICallList.getType("TYPE", response, HomeScreen.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private void getCategoryAPi(int type_Id) {
        if (InternetConnection.isConnected(HomeScreen.this)) {
            ShowProgressbar();
            APICallList.getCatImages(type_Id, "category data", response, HomeScreen.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    public void ShowProgressbar() {
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.progress_layout);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void ShowResultPopUp() {
        final Dialog dialog = new Dialog(HomeScreen.this);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.pop_up_status);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView popheaderImage = dialog.findViewById(R.id.successPop_Header_image);
        TextView poperrorDescription = dialog.findViewById(R.id.successPop_error_description);
        Button submit = dialog.findViewById(R.id.successPop_done_button);

        popheaderImage.setImageResource(R.drawable.oopsimage);
        poperrorDescription.setText(getString(R.string.Not_registered_user));

        submit.setOnClickListener(v ->{
            dialog.dismiss();
            Intent i  = new Intent(HomeScreen.this,LoginActivity.class);
//            LoginActivity.from = "HomeScreen";
            Constants.Login_From = "HomeScreen";
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    public void DismissDialog() {
        dialog.dismiss();
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("get customer info")) {

                getCustomerInfoResponse = (GetCustomerInfoResponse) o;
                if (getCustomerInfoResponse != null) {
                    typeDataResponse = null;
                    getTypeAPi();
                    setUserInfo(getCustomerInfoResponse.getData());
                    if (getCustomerInfoResponse.getCode() == 1) {

                        homeScreenBinding.appBarMain.userName.setText("Hi "+getCustomerInfoResponse.getData().getFirstname());
                        sessionManager.setUserFirstName(getCustomerInfoResponse.getData().getFirstname());

                        if (getCustomerInfoResponse.getData().getCartCount()>0) {
                            sessionManager.setCartCount(String.valueOf(getCustomerInfoResponse.getData().getCartCount()));
                            homeScreenBinding.appBarMain.cartCount.setVisibility(View.VISIBLE);
                            homeScreenBinding.appBarMain.cartCount.setText(sessionManager.getcartCount());

                        } else {
                            homeScreenBinding.appBarMain.cartCount.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        DismissDialog();
                        CommFunc.ShowStatusPop(HomeScreen.this, getCustomerInfoResponse.getMessage(), false);
                    }
                }
            }
            else if (url_type.equalsIgnoreCase("TYPE")) {
                DismissDialog();
                typeDataResponse = (TypeResponse) o;
                if (typeDataResponse != null && typeDataResponse.getCode() == 1 && typeDataResponse.getData().size() > 0) {
                    setTypeData();
                    getCategoryAPi(typeDataResponse.getData().get(TYPE_ID_POS).getId());
                    sessionManager.setTypeId(String.valueOf(typeDataResponse.getData().get(TYPE_ID_POS).getId()));
                    System.out.println("------------------------------------------>"+ (typeDataResponse.getData().get(TYPE_ID_POS).getId()));
                } else {
                    CommFunc.ShowStatusPop(HomeScreen.this, typeDataResponse.getMessage(), false);
                }
            }
            else if (url_type.equalsIgnoreCase("category data")) {
                DismissDialog();
                categoryDataResponse = (StylelookDataResponse) o;
                if (categoryDataResponse != null) {
                    if (1 == categoryDataResponse.getCode()) {
                        if (categoryDataResponse.getData().size() > 0) {
                            setCategoryData(); // set category adapter
                        }
                    } else {
                        CommFunc.ShowStatusPop(HomeScreen.this, categoryDataResponse.getMessage(), false);
                    }
                }
            }
            else if (url_type.equalsIgnoreCase("delete account")){
                AddToCartResponse delteResponse = (AddToCartResponse) o;
                if (delteResponse != null){
                    if (delteResponse.getCode() == 1){
                        Intent i8 = new Intent(HomeScreen.this, SplashAct.class);

                        Constants.billingAddress_VALUE = "";
                        Constants.shippingAddress_VALUE = "";
                        Constants.billingAddress_VALUE_OBJ_TYPE = null;
                        Constants.shippingAddress_VALUE_ObJ_TYPE = null;
                        sessionManager.logoutUser();
                        startActivity(i8);
                        finishAffinity();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }else {
                        CommFunc.ShowStatusPop(HomeScreen.this, delteResponse.getMessage(), false);
                    }
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            DismissDialog();
            CommFunc.ShowStatusPop(HomeScreen.this, message, false);
        }
    };

    private void setUserInfo(GetCustomerInfoResponse.Data data) {

        sessionManager.setCartCount(String.valueOf(data.getCartCount()));
        sessionManager.setUserGender(data.getGender());
        ComUserProfileData.setEmail(data.getGender());
    }

    private void setTypeData() {
        typeDataResponse.getData().get(TYPE_ID_POS).setSelected(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(HomeScreen.this, RecyclerView.HORIZONTAL, false);
        homeScreenBinding.appBarMain.contentMainLay.headerRecycler.setLayoutManager(layoutManager1);
        headerAdapter = new HeaderAdapter(HomeScreen.this, typeDataResponse.getData());
        homeScreenBinding.appBarMain.contentMainLay.headerRecycler.setAdapter(headerAdapter);
        homeScreenBinding.appBarMain.contentMainLay.headerRecycler.setHasFixedSize(true);
        homeScreenBinding.appBarMain.contentMainLay.headerRecycler.setItemViewCacheSize(20);
        homeScreenBinding.appBarMain.contentMainLay.headerRecycler.setDrawingCacheEnabled(true);
        homeScreenBinding.appBarMain.contentMainLay.headerRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        homeScreenBinding.appBarMain.contentMainLay.headerRecycler.addOnItemTouchListener(new RecyclerTouchListener(HomeScreen.this,
                homeScreenBinding.appBarMain.contentMainLay.headerRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                for (int i=0; i < typeDataResponse.getData().size(); i++) {
                    if (i == position) {
                        TYPE_ID_POS = position;
                        typeDataResponse.getData().get(position).setSelected(true);
                    } else {
                        typeDataResponse.getData().get(i).setSelected(false);
                    }
                    headerAdapter.notifyDataSetChanged();
                }
                sessionManager.setTypeId(String.valueOf(typeDataResponse.getData().get(TYPE_ID_POS).getId()));
                System.out.println("------------------------------------------>"+ (typeDataResponse.getData().get(TYPE_ID_POS).getId()));
                getCategoryAPi(typeDataResponse.getData().get(TYPE_ID_POS).getId());
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void setCategoryData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeScreen.this);
        homeScreenBinding.appBarMain.contentMainLay.categoryList.setLayoutManager(layoutManager);
        HomeAdapter categoryAdapter = new HomeAdapter(HomeScreen.this, categoryDataResponse.getData());
        homeScreenBinding.appBarMain.contentMainLay.categoryList.setAdapter(categoryAdapter);
        homeScreenBinding.appBarMain.contentMainLay.categoryList.setHasFixedSize(true);
        homeScreenBinding.appBarMain.contentMainLay.categoryList.setItemViewCacheSize(20);
        homeScreenBinding.appBarMain.contentMainLay.categoryList.setDrawingCacheEnabled(true);
        homeScreenBinding.appBarMain.contentMainLay.categoryList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

    private void prepareMenuData() {

        headerList.clear();
        MenuModel menuModel = new MenuModel(getResources().getString(R.string.my_profile), true, false);
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(getResources().getString(R.string.recent_orders), true, false);
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(getResources().getString(R.string.my_measurements), true, false);
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(getResources().getString(R.string.manage_address), true, false);
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(getResources().getString(R.string.manage_password), true, false);
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(getResources().getString(R.string.contactUs), true, false);
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(getResources().getString(R.string.privacy_policy), true, false);
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(getResources().getString(R.string.faqs), true, false);
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        if (email.isEmpty()){
            menuModel = new MenuModel(getResources().getString(R.string.login), true, false);
        }
        else {
            menuModel = new MenuModel(getResources().getString(R.string.logout), true, false);
        }
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }


        menuModel = new MenuModel(getResources().getString(R.string.delete_account), true, false);
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
    }

    private void populateExpandableList() {
        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            if (headerList.get(groupPosition).isGroup) {
                if (groupPosition == 0) {
                    if (email.isEmpty()){
                        ShowResultPopUp();
                    }
                    else {
                        Intent i4 = new Intent(HomeScreen.this, EditProfile.class);
                        startActivity(i4);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
                else if (groupPosition == 1) {
                    if (email.isEmpty()){
                        ShowResultPopUp();
                    }
                    else {
                        Intent i5 = new Intent(HomeScreen.this, OrderList.class);
                        startActivity(i5);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
                else if (groupPosition == 2) {
                    if (email.isEmpty()){
                        ShowResultPopUp();
                    }
                    else {
                        Intent i3 = new Intent(HomeScreen.this, MeasurementHistoryAct.class);
                        MeasurementHistoryAct.MeasurementHistoryActivityComingFrom="";
                        startActivity(i3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
                else if (groupPosition == 4) {
                    if (email.isEmpty()){
                        ShowResultPopUp();
                    }
                    else {
                        Intent i6 = new Intent(HomeScreen.this, ChangePassword.class);
                        startActivity(i6);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
                else if (groupPosition == 3) {
                    if (email.isEmpty()){
                        ShowResultPopUp();
                    }
                    else {
                        Intent icoupon = new Intent(HomeScreen.this, AddressListAct.class);
                        AddressListAct.AddressListActivityComingFrom = "menu";
                        startActivity(icoupon);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                } else if (groupPosition == 5) {
                    Intent i5 = new Intent(HomeScreen.this, ContactUs.class);
                    startActivity(i5);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }  else if (groupPosition == 6) {
                    Intent i6 = new Intent(HomeScreen.this, PrivacyPolicy.class);
                    startActivity(i6);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }  else if (groupPosition == 7) {
                    Intent i7 = new Intent(HomeScreen.this, FAQActivity.class);
                    startActivity(i7);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else if (groupPosition == 8) {

                    if (email.isEmpty()){
                        Intent i7 = new Intent(HomeScreen.this, LoginActivity.class);
//                        LoginActivity.from = "HomeScreen";
                        Constants.Login_From = "HomeScreen";
                        startActivity(i7);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    else {
                        logoutPopUp(HomeScreen.this,getResources().getString(R.string.logout_popup_msg));
                    }
                    }
                else if (groupPosition == 9){
                    if (email.isEmpty()){
                        ShowResultPopUp();
                    }
                    else {
                        deletePopUp(HomeScreen.this);
                    }
                }
            }
            return false;
        });
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void logoutPopUp(Context context, String description) {

        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.logout_popup);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView popheaderImage = dialog.findViewById(R.id.successPop_Header_image);
        TextView poperrorDescription = dialog.findViewById(R.id.successPop_error_description);
        Button submit = dialog.findViewById(R.id.successPop_done_button);
        Button pop_no_button = dialog.findViewById(R.id.pop_no_button);

        poperrorDescription.setText(description);

        popheaderImage.setImageResource(R.drawable.oopsimage);

        pop_no_button.setOnClickListener(v -> dialog.dismiss());
        submit.setOnClickListener(v -> {
            dialog.dismiss();
            Intent i8 = new Intent(HomeScreen.this, SplashAct.class);

            Constants.billingAddress_VALUE = "";
            Constants.shippingAddress_VALUE = "";
            Constants.billingAddress_VALUE_OBJ_TYPE = null;
            Constants.shippingAddress_VALUE_ObJ_TYPE = null;
            sessionManager.logoutUser();
            startActivity(i8);
            finishAffinity();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    private void deletePopUp(Context context) {

        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.delete_account_popup);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Button Delete = dialog.findViewById(R.id.delete_popup_delete_button);
        Button Cancel = dialog.findViewById(R.id.delete_popup_cancel_button);

        Cancel.setOnClickListener(v -> dialog.dismiss());

        Delete.setOnClickListener(v -> {
            dialog.dismiss();

            if (InternetConnection.isConnected(HomeScreen.this)) {
                CommFunc.ShowProgressbar(this);

                APICallList.deleteAccount("delete account",response,HomeScreen.this);
            } else {
                CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
            }

        });

    }
}