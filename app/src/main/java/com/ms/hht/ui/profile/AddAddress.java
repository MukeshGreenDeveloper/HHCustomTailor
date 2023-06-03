package com.ms.hht.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.hht.R;
import com.ms.hht.data.adapter.CountryAdapterClass;
import com.ms.hht.data.adapter.StateAdapterClass;
import com.ms.hht.data.request.AddAndUpdateAddressRequest;
import com.ms.hht.data.response.AddAddressResponse;
import com.ms.hht.data.response.GetCountryResponse;
import com.ms.hht.data.response.GetStateResponse;
import com.ms.hht.data.response.UpdateAddressResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActAddAddressBinding;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.RecyclerTouchListener;

import java.util.List;


public class AddAddress extends AppCompatActivity implements View.OnClickListener {

    ActAddAddressBinding addressBinding;
    AddAddressResponse addAddressResponse;
    UpdateAddressResponse updateAddressResponse;
    GetCountryResponse getCountryResponse;
    GetStateResponse getRegionResponse;
    String countryID = "";
    String countryName = "";
    int regionID = -1;
    String regionName = "";
    Boolean isDefaultAddress = false;
    public static String ComingToAddressFrom = "";
    public static int AddressIdofUpdatingAddress = -1;

    public static String EditAddFirstName = "";
    public static String EditAddlastname = "";
    public static String EditAddcity = "";
    public static String EditAddcountry_Name = "";
    public static String EditAddcountry_id = "";
    public static String EditAddpostcode = "";
    public static String EditAddregion = "";
    public static int EditAddregion_id = -1;
    public static String EditAddstreet = "";
    public static String EditAddtelephone = "";
    public static int EditAddis_default_shipping = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressBinding = ActAddAddressBinding.inflate(getLayoutInflater());
        setContentView(addressBinding.getRoot());

        addressBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        addressBinding.saveDetailBtn.setOnClickListener(this);
        addressBinding.counteryEdtxt.setOnClickListener(this);
//        addressBinding.stateEdtxt.setOnClickListener(this);
        addressBinding.stateTextView.setOnClickListener(this);


//        CommFunc.ShowProgressbar(this);
//        APICallList.getCountry("get country",response,AddAddress.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ComingToAddressFrom.equalsIgnoreCase("Add Address")){
            addressBinding.addHeading.setText("Add Address");
        }else{
            addressBinding.addHeading.setText("Edit Address");
            addressBinding.saveDetailBtn.setText("Update");

            addressBinding.FirstnameEditTxt1.setText(EditAddFirstName);
            addressBinding.LastEditTxt1.setText(EditAddlastname);
            addressBinding.cityEdtxt.setText(EditAddcity);
            addressBinding.counteryEdtxt.setText(EditAddcountry_Name);
            countryID = EditAddcountry_id;
            addressBinding.postalEdtxt.setText(EditAddpostcode);
            addressBinding.stateEdtxt.setText(EditAddregion);
            addressBinding.stateTextView.setText(EditAddregion);
            regionID = EditAddregion_id;
            addressBinding.addrs1Edtxt.setText(EditAddstreet);
            addressBinding.phoneNo.setText(EditAddtelephone);

            if (EditAddis_default_shipping == 1){
                addressBinding.defaultAddCheck.setChecked(true);
            }else if (EditAddis_default_shipping == 0){
                addressBinding.defaultAddCheck.setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.saveDetailBtn:
                Validation();
                break;
            case R.id.counteryEdtxt:

                if (InternetConnection.isConnected(AddAddress.this)) {
                    CommFunc.ShowProgressbar(this);
                    APICallList.getCountry("get country",response,AddAddress.this);
                } else {
                    CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
                }
                break;
            case R.id.stateTextView:
                if (!addressBinding.counteryEdtxt.getText().toString().isEmpty()) {
                    if (!countryID.isEmpty()){
                        CommFunc.ShowProgressbar(this);
                        APICallList.getRegion(countryID, "get region", response, AddAddress.this);
                    }
                }
                else {
                    CommFunc.ShowStatusPop(this, "Please select country first.", false);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void Validation(){

        if(addressBinding.FirstnameEditTxt1.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(AddAddress.this,getResources().getString(R.string.nameError),false);
        }
        else if(addressBinding.phoneNo.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(AddAddress.this,getResources().getString(R.string.phoneError),false);
        }
        else if(addressBinding.counteryEdtxt.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(AddAddress.this,getResources().getString(R.string.countryError),false);
        }
        else if(addressBinding.stateEdtxt.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(AddAddress.this,getResources().getString(R.string.stateError),false);
        }
        else if (addressBinding.cityEdtxt.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(AddAddress.this,getResources().getString(R.string.cityError),false);
        }
        else if(addressBinding.postalEdtxt.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(AddAddress.this,getResources().getString(R.string.postalError),false);
        }
        else if(addressBinding.addrs1Edtxt.getText().toString().isEmpty()) {
            CommFunc.ShowStatusPop(AddAddress.this,getResources().getString(R.string.addLaneError),false);
        }
        else {
            addAndUpdateUserAddress();
        }
    }

    private void addAndUpdateUserAddress(){
//        call post api and send the request body if user tapped on Add address Btn on Address List then add address api will call
        // and if user is tapped Edit Add btn in add list adapter class then update address api will call,
        // Both the api have same  request body but different calling method Post and Put respectively
        if (InternetConnection.isConnected(AddAddress.this)) {
            CommFunc.ShowProgressbar(this);

            if (getRegionResponse!= null && getRegionResponse.getData() != null && getRegionResponse.getData().size()<1){
                regionName = addressBinding.stateEdtxt.getText().toString();
            }

            AddAndUpdateAddressRequest addAndUpdateAddressRequest = new AddAndUpdateAddressRequest(
                    addressBinding.FirstnameEditTxt1.getText().toString(),
                    addressBinding.LastEditTxt1.getText().toString() ,
                    addressBinding.cityEdtxt.getText().toString(), countryID,
                    addressBinding.postalEdtxt.getText().toString(),regionName, regionID,
                    addressBinding.addrs1Edtxt.getText().toString(),
                    addressBinding.phoneNo.getText().toString(),
                    addressBinding.defaultAddCheck.isChecked());
            if (ComingToAddressFrom.equalsIgnoreCase("Add Address")) {
                APICallList.addAddress(addAndUpdateAddressRequest, "add address", response, AddAddress.this);
            }else {
                APICallList.UpdateAddress(addAndUpdateAddressRequest,AddressIdofUpdatingAddress, "update address", response, AddAddress.this);
            }
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }

    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if(url_type.equalsIgnoreCase("get country")){
                CommFunc.DismissDialog();
                getCountryResponse = (GetCountryResponse) o;
                if (getCountryResponse != null) {
                    if (getCountryResponse.getCode() == 1) {
                        countryPopUp(getCountryResponse.getData());
                    } else {
                        CommFunc.ShowStatusPop(AddAddress.this, getCountryResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(AddAddress.this, getResources().getString(R.string.try_again), false);
                }
            }
            else if(url_type.equalsIgnoreCase("get region")){
                CommFunc.DismissDialog();
                getRegionResponse = (GetStateResponse) o;
                if (getRegionResponse != null) {
                    if (getRegionResponse.getCode() == 1) {
                        if (  getRegionResponse.getData().size() <= 0 ){
                            addressBinding.stateTextView.setVisibility(View.GONE);
                            addressBinding.stateEdtxt.setFocusable(true);
//                            addressBinding.stateEdtxt.setFocusableInTouchMode(true);
//                            addressBinding.stateEdtxt.setEnabled(true);
                            addressBinding.stateEdtxt.requestFocus();
                        }
                        else {
                            addressBinding.stateTextView.setVisibility(View.VISIBLE);
//                            addressBinding.stateEdtxt.setFocusable(false);
                            regionPopUp(getRegionResponse.getData());
                        }
                    } else {
                        CommFunc.ShowStatusPop(AddAddress.this, getRegionResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(AddAddress.this, getResources().getString(R.string.try_again), false);
                }
            }
            else if(url_type.equalsIgnoreCase("add address")){
                CommFunc.DismissDialog();
                addAddressResponse = (AddAddressResponse) o;
                if (addAddressResponse != null) {
                    if (addAddressResponse.getCode() == 1) {
                        ShowSuccessPopUP(AddAddress.this, addAddressResponse.getMessage(), true);
                    } else {
                        CommFunc.ShowStatusPop(AddAddress.this, addAddressResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(AddAddress.this, getResources().getString(R.string.try_again), false);
                }
            }
            else if(url_type.equalsIgnoreCase("update address")){
                CommFunc.DismissDialog();
                updateAddressResponse = (UpdateAddressResponse) o;
                if (updateAddressResponse != null) {
                    if (updateAddressResponse.getCode() == 1) {
                        ShowSuccessPopUP(AddAddress.this, updateAddressResponse.getMessage(), true);
                    } else {
                        CommFunc.ShowStatusPop(AddAddress.this, updateAddressResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(AddAddress.this, getResources().getString(R.string.try_again), false);
                }
            }
        }
        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(AddAddress.this, message, false);
        }
    };

    private void countryPopUp(List<GetCountryResponse.DataItem> data) {

        final Dialog dialog = new Dialog(AddAddress.this);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.country_state_recy_view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        RecyclerView countryRecyView = dialog.findViewById(R.id.countryStateRecy);
        EditText searchEditText = dialog.findViewById(R.id.searchLayout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddAddress.this);
        countryRecyView.setLayoutManager(linearLayoutManager);
        CountryAdapterClass countryAdapterClass = new CountryAdapterClass(AddAddress.this, data);
        countryRecyView.setAdapter(countryAdapterClass);
        countryRecyView.setHasFixedSize(true);
        countryRecyView.setItemViewCacheSize(20);
        countryRecyView.setDrawingCacheEnabled(true);
        countryRecyView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        countryRecyView.setNestedScrollingEnabled(false);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countryAdapterClass.getFilter().filter(searchEditText.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        countryRecyView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                countryRecyView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                countryID = data.get(position).getCountryId();
                countryName = data.get(position).getName();
                addressBinding.counteryEdtxt.setText(countryName);
                addressBinding.stateEdtxt.setText("");
                addressBinding.stateTextView.setText("");
                addressBinding.stateTextView.setVisibility(View.VISIBLE);
                regionID = -1;
                regionName = "";
//                CommFunc.ShowProgressbar(AddAddress.this);
//                APICallList.getRegion(countryID, "get region", response, AddAddress.this);
                dialog.dismiss();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void regionPopUp(List<GetStateResponse.DataItem> data) {

        final Dialog dialog = new Dialog(AddAddress.this);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.country_state_recy_view);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        RecyclerView stateRecyView = dialog.findViewById(R.id.countryStateRecy);
        EditText searchEditText = dialog.findViewById(R.id.searchLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddAddress.this);
        stateRecyView.setLayoutManager(linearLayoutManager);
        StateAdapterClass stateAdapterClass = new StateAdapterClass(AddAddress.this, data);
        stateRecyView.setAdapter(stateAdapterClass);
        stateRecyView.setHasFixedSize(true);
        stateRecyView.setItemViewCacheSize(20);
        stateRecyView.setDrawingCacheEnabled(true);
        stateRecyView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        stateRecyView.setNestedScrollingEnabled(false);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                stateAdapterClass.getSateFilter().filter(searchEditText.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        stateRecyView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                stateRecyView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                regionID = data.get(position).getRegionId();
                regionName = data.get(position).getName();
                addressBinding.stateEdtxt.setText(regionName);
                addressBinding.stateTextView.setText(regionName);
                dialog.dismiss();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void ShowSuccessPopUP(Context context, String description, boolean status) {
        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.pop_up_status);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

//        GifImageView popheaderImage = dialog.findViewById(R.id.successPop_Header_image);
        TextView poperrorDescription = dialog.findViewById(R.id.successPop_error_description);
        Button submit = dialog.findViewById(R.id.successPop_done_button);

        poperrorDescription.setText(description);
//
//        if (!status) {
//            popheaderImage.setImageResource(R.drawable.oopsimage);
//        } else {
//            popheaderImage.setImageResource(R.drawable.ok);
//        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                Intent inten=new Intent(AddAddress.this,AddressListAct.class);
//                startActivity(inten);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
    }

}