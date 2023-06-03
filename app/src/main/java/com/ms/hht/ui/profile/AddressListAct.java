package com.ms.hht.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ms.hht.R;
import com.ms.hht.data.adapter.AddressListAdapter;
import com.ms.hht.data.request.UpdateDefaultAddressRequest;
import com.ms.hht.data.response.AddressListResponse;
import com.ms.hht.data.response.UpdateDefaultAddressResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActAddressListBinding;
import com.ms.hht.ui.payment.PlaceOrder_billing_address;
import com.ms.hht.ui.payment.PlaceOrder_shipping_address;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.RecyclerTouchListener;

import java.util.List;

public class AddressListAct extends AppCompatActivity implements View.OnClickListener {

    ActAddressListBinding listBinding;
    public static AddressListResponse addressListResponse;
    UpdateDefaultAddressResponse updateDefaultAddressResponse;
    public static String AddressListActivityComingFrom = "";
    Dialog progressDialog, popUpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBinding = ActAddressListBinding.inflate(getLayoutInflater());
        progressDialog = new Dialog(AddressListAct.this);
        popUpDialog = new Dialog(AddressListAct.this);
        setContentView(listBinding.getRoot());
        listBinding.back.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
        listBinding.addAddressBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addAddressBtn:
                Intent intent = new Intent(AddressListAct.this, AddAddress.class);
                AddAddress.ComingToAddressFrom = "Add Address";
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    public void getAddressList() {
        if (InternetConnection.isConnected(AddressListAct.this)) {
            ShowProgressbar();
            APICallList.getAddressList("get address list", response, AddressListAct.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {

            if (url_type.equalsIgnoreCase("get address list")) {
                DismissProgress();
                addressListResponse = (AddressListResponse) o;
                if (addressListResponse != null) {
                    if (addressListResponse.getCode() == 1) {

                        if (AddressListActivityComingFrom.equalsIgnoreCase("cart ship address")) {

                        for(int i=0 ; i<addressListResponse.getData().size(); i++){

                                if (addressListResponse.getData().get(i).getIsDefaultShipping() == 1) {

                                    Constants.shippingAddress_VALUE = (addressListResponse.getData().get(i).getFirstname() + " " +
                                            addressListResponse.getData().get(i).getLastname() + "\n\n" +
                                            addressListResponse.getData().get(i).getStreet() + "\n" +
                                            addressListResponse.getData().get(i).getCity() + ", " +
                                            addressListResponse.getData().get(i).getRegion() + " " +
                                            addressListResponse.getData().get(i).getPostcode() + "\n" +
                                            addressListResponse.getData().get(i).getCountry() + "\n\n" +
                                            "Phone no. " + addressListResponse.getData().get(i).getTelephone());

                                    PlaceOrder_shipping_address placeOrderShippingAddress = new PlaceOrder_shipping_address(
                                            addressListResponse.getData().get(i).getId(),
                                            addressListResponse.getData().get(i).getCity(),
                                            addressListResponse.getData().get(i).getCountryId(),
                                            addressListResponse.getData().get(i).getPostcode(),
                                            addressListResponse.getData().get(i).getRegion(),
                                            addressListResponse.getData().get(i).getRegionId(),
                                            addressListResponse.getData().get(i).getStreet(),
                                            addressListResponse.getData().get(i).getTelephone(),0);

                                    Constants.shippingAddress_VALUE_ObJ_TYPE = placeOrderShippingAddress;
                                }
                            }
                        }
                        setAddressList(addressListResponse.getData());
                    }
                    else {
                        Constants.shippingAddress_VALUE = "";
                        CommFunc.ShowStatusPop(AddressListAct.this, addressListResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(AddressListAct.this, getResources().getString(R.string.try_again), false);
                }
            }

//            else if (url_type.equalsIgnoreCase("set default address")) {
//                DismissProgress();
//                updateDefaultAddressResponse = (UpdateDefaultAddressResponse) o;
//                if (updateDefaultAddressResponse != null) {
//                    if (updateDefaultAddressResponse.getCode() == 1) {
//                        showSuccessPopUP(AddressListAct.this, updateDefaultAddressResponse.getMessage(), true);
//                    } else {
//                        CommFunc.ShowStatusPop(AddressListAct.this, updateDefaultAddressResponse.getMessage(), false);
//                    }
//                } else {
//                    CommFunc.ShowStatusPop(AddressListAct.this, getResources().getString(R.string.try_again), false);
//                }
//            }
        }

        @Override
        public void onError(String message) throws Exception {
            DismissProgress();
            CommFunc.ShowStatusPop(AddressListAct.this, message, false);
        }
    };

    private void setAddressList(List<AddressListResponse.DataItem> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddressListAct.this);
        listBinding.addressListRcy.setLayoutManager(layoutManager);
        AddressListAdapter addressListAdapter = new AddressListAdapter(AddressListAct.this, data);
        listBinding.addressListRcy.setAdapter(addressListAdapter);
        listBinding.addressListRcy.setHasFixedSize(true);
        listBinding.addressListRcy.setItemViewCacheSize(20);
        listBinding.addressListRcy.setDrawingCacheEnabled(true);
        listBinding.addressListRcy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        listBinding.addressListRcy.setNestedScrollingEnabled(false);

//        listBinding.addressListRcy.addOnItemTouchListener(new RecyclerTouchListener(AddressListAct.this,
//                listBinding.addressListRcy, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {

//                if (AddressListActivityComingFrom.equalsIgnoreCase("cart ship address")) {
//
//                    Constants.shippingAddress_VALUE = (
//                            data.get(position).getFirstname() + " " +
//                            data.get(position).getLastname() + "\n\n" +
//                            data.get(position).getStreet() + "\n" +
//                            data.get(position).getCity() + ", " +
//                            data.get(position).getRegion() + " " +
//                            data.get(position).getPostcode() + "\n" +
//                            data.get(position).getCountry() + "\n\n" +
//                            "Phone no. " + data.get(position).getTelephone());
//
//                    PlaceOrder_shipping_address placeOrderShippingAddress = new PlaceOrder_shipping_address(
//                            data.get(position).getId(),
//                            data.get(position).getCity(),
//                            data.get(position).getCountryId(),
//                            data.get(position).getPostcode(),
//                            data.get(position).getRegion(),
//                            data.get(position).getRegionId(),
//                            data.get(position).getStreet(),
//                            data.get(position).getTelephone(),0);
//
//                    Constants.shippingAddress_VALUE_ObJ_TYPE = placeOrderShippingAddress;
//
//                    finish();
//                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                }
//
//                else if (AddressListActivityComingFrom.equalsIgnoreCase("cart bill address")) {
//
//                    Constants.billingAddress_VALUE = (
//                            data.get(position).getFirstname() + " " +
//                            data.get(position).getLastname() + "\n\n" +
//                            data.get(position).getStreet() + "\n" +
//                            data.get(position).getCity() + ", " +
//                            data.get(position).getRegion() + " " +
//                            data.get(position).getPostcode() + "\n" +
//                            data.get(position).getCountry() + "\n\n" +
//                            "Phone no. " + data.get(position).getTelephone());
//
//                    PlaceOrder_billing_address placeOrder_billing_address = new PlaceOrder_billing_address(
//                            data.get(position).getId(),
//                            data.get(position).getCity(),
//                            data.get(position).getCountryId(),
//                            data.get(position).getPostcode(),
//                            data.get(position).getRegion(),
//                            data.get(position).getRegionId(),
//                            data.get(position).getStreet(),
//                            data.get(position).getTelephone(),0);
//
//                    Constants.billingAddress_VALUE_OBJ_TYPE = placeOrder_billing_address;
//
//                    finish();
//                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                }
//
//                else {
//                    if (InternetConnection.isConnected(AddressListAct.this)) {
//                        ShowProgressbar();
//                        UpdateDefaultAddressRequest defaultAddressRequest = new UpdateDefaultAddressRequest(data.get(position).getId(),
//                                data.get(position).getId());
//                        APICallList.UpdateDefaultAddress(defaultAddressRequest, "set default address", response, AddressListAct.this);
//                    }
//                    else {
//                        CommFunc.ShowStatusPop(AddressListAct.this, getResources().getString( R.string.internet), false);
//                    }
//                }
//            }
//            @Override
//            public void onLongClick(View view, int position) {
//            }
//        }));

    }

    private void showSuccessPopUP(Context context, String description, boolean status) {
        Window window = popUpDialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popUpDialog.setContentView(R.layout.pop_up_status);
        popUpDialog.setCancelable(false);
        popUpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpDialog.show();

        ImageView popheaderImage = popUpDialog.findViewById(R.id.successPop_Header_image);
        TextView poperrorDescription = popUpDialog.findViewById(R.id.successPop_error_description);
        Button submit = popUpDialog.findViewById(R.id.successPop_done_button);

        poperrorDescription.setText(description);

        if (!status) {
            popheaderImage.setImageResource(R.drawable.oopsimage);
        } else {
            popheaderImage.setImageResource(R.drawable.ok);
        }
        submit.setOnClickListener(view -> {
            popUpDialog.dismiss();
            getAddressList();
        });
    }

    private void ShowProgressbar() {
        Window window = progressDialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
    }

    private void DismissProgress(){
        progressDialog.dismiss();
    }

}