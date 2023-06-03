package com.ms.hht.data.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.hht.R;
import com.ms.hht.data.request.UpdateDefaultAddressRequest;
import com.ms.hht.data.response.AddressListResponse;
import com.ms.hht.data.response.DeleteAddressResponse;
import com.ms.hht.data.response.UpdateDefaultAddressResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.ui.payment.PlaceOrder_billing_address;
import com.ms.hht.ui.payment.PlaceOrder_shipping_address;
import com.ms.hht.ui.profile.AddAddress;
import com.ms.hht.ui.profile.AddressListAct;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.Constants;
import com.ms.hht.utils.InternetConnection;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {

    DeleteAddressResponse deleteAddressResponse;
    UpdateDefaultAddressResponse updateDefaultAddressResponse;
    Context context;
    List<AddressListResponse.DataItem> data;

    public AddressListAdapter(Context context, List<AddressListResponse.DataItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflatert = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = layoutInflatert.inflate(R.layout.address_list_recy, parent, false);

        return new MyViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.address.setText(data.get(position).getFirstname() + " " +
                data.get(position).getLastname() + "\n" +
                data.get(position).getStreet() + "\n" +
                data.get(position).getCity() + ", " + data.get(position).getRegion() + " " +
                data.get(position).getPostcode() + "\n" +
                data.get(position).getCountry() + "\n" +
                "Phone no. " + data.get(position).getTelephone());

        holder.editAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAddress.class);
                AddAddress.ComingToAddressFrom = "Edit Address";
                AddAddress.AddressIdofUpdatingAddress = data.get(position).getId();

                AddAddress.EditAddFirstName = data.get(position).getFirstname();
                AddAddress.EditAddlastname = data.get(position).getLastname();
                AddAddress.EditAddcity = data.get(position).getCity();
                AddAddress.EditAddcountry_Name = data.get(position).getCountry();
                AddAddress.EditAddcountry_id = data.get(position).getCountryId();
                AddAddress.EditAddpostcode = data.get(position).getPostcode();
                AddAddress.EditAddregion = data.get(position).getRegion();
                AddAddress.EditAddregion_id = data.get(position).getRegionId();
                AddAddress.EditAddstreet = data.get(position).getStreet();
                AddAddress.EditAddtelephone = data.get(position).getTelephone();
                AddAddress.EditAddis_default_shipping = data.get(position).getIsDefaultShipping();
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        holder.deleteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnection.isConnected(context)) {
                    CommFunc.ShowProgressbar(context);
                    APICallList.DeleteAddress(data.get(position).getId(), "delete address", response, context);
                } else {
                    CommFunc.ShowStatusPop(context,"No internet connection. Please check your internet connection. ", false);
                }
            }
        });

        holder.AddListcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AddressListAct.AddressListActivityComingFrom.equalsIgnoreCase("cart ship address")) {

                    Constants.shippingAddress_VALUE = (
                            data.get(position).getFirstname() + " " +
                                    data.get(position).getLastname() + "\n\n" +
                                    data.get(position).getStreet() + "\n" +
                                    data.get(position).getCity() + ", " +
                                    data.get(position).getRegion() + " " +
                                    data.get(position).getPostcode() + "\n" +
                                    data.get(position).getCountry() + "\n\n" +
                                    "Phone no. " + data.get(position).getTelephone());

                    PlaceOrder_shipping_address placeOrderShippingAddress = new PlaceOrder_shipping_address(
                            data.get(position).getId(),
                            data.get(position).getCity(),
                            data.get(position).getCountryId(),
                            data.get(position).getPostcode(),
                            data.get(position).getRegion(),
                            data.get(position).getRegionId(),
                            data.get(position).getStreet(),
                            data.get(position).getTelephone(),0);

                    Constants.shippingAddress_VALUE_ObJ_TYPE = placeOrderShippingAddress;

                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }

                else if (AddressListAct.AddressListActivityComingFrom.equalsIgnoreCase("cart bill address")) {

                    Constants.billingAddress_VALUE = (
                            data.get(position).getFirstname() + " " +
                                    data.get(position).getLastname() + "\n\n" +
                                    data.get(position).getStreet() + "\n" +
                                    data.get(position).getCity() + ", " +
                                    data.get(position).getRegion() + " " +
                                    data.get(position).getPostcode() + "\n" +
                                    data.get(position).getCountry() + "\n\n" +
                                    "Phone no. " + data.get(position).getTelephone());

                    PlaceOrder_billing_address placeOrder_billing_address = new PlaceOrder_billing_address(
                            data.get(position).getId(),
                            data.get(position).getCity(),
                            data.get(position).getCountryId(),
                            data.get(position).getPostcode(),
                            data.get(position).getRegion(),
                            data.get(position).getRegionId(),
                            data.get(position).getStreet(),
                            data.get(position).getTelephone(),0);

                    Constants.billingAddress_VALUE_OBJ_TYPE = placeOrder_billing_address;

                    ((Activity) context).finish();
                    ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }

                else {
                       CommFunc.ShowProgressbar(context);
                        UpdateDefaultAddressRequest defaultAddressRequest = new UpdateDefaultAddressRequest(data.get(position).getId(),
                                data.get(position).getId());
                        APICallList.UpdateDefaultAddress(defaultAddressRequest, "set default address", response, context);
                }

            }
        });

        if (AddressListAct.AddressListActivityComingFrom.equalsIgnoreCase("cart bill address")) {

        }
        else {
            if (data.get(position).getIsDefaultShipping() == 1) {
                holder.radioBtn.setImageResource(R.drawable.radio);
            } else if (data.get(position).getIsDefaultShipping() == 0) {
                holder.radioBtn.setImageResource(R.drawable.radio_blank);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        RelativeLayout AddListcard;
        ImageView editAdd, deleteAdd, radioBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.addTV);
            editAdd = itemView.findViewById(R.id.editIMG);
            deleteAdd = itemView.findViewById(R.id.deleteIMG);
            radioBtn = itemView.findViewById(R.id.radioBtn);
            AddListcard = itemView.findViewById(R.id.AddListcard);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {

            if (url_type.equalsIgnoreCase("delete address")) {
                CommFunc.DismissDialog();
                deleteAddressResponse = (DeleteAddressResponse) o;
                if (deleteAddressResponse != null) {
                    if (deleteAddressResponse.getCode() == 1) {
                        ShowSuccessPop(context, deleteAddressResponse.getMessage(), true);
                    } else {
                        ShowSuccessPop(context, deleteAddressResponse.getMessage(), false);
                    }
                } else {
                    ShowSuccessPop(context, context.getResources().getString(R.string.try_again), false);
                }
            }

            else if (url_type.equalsIgnoreCase("set default address")) {
                CommFunc.DismissDialog();
                updateDefaultAddressResponse = (UpdateDefaultAddressResponse) o;

                if (updateDefaultAddressResponse != null) {
                    if (updateDefaultAddressResponse.getCode() == 1) {
                        ShowSuccessPop(context, updateDefaultAddressResponse.getMessage(), true);
                    } else {
                        ShowSuccessPop(context, updateDefaultAddressResponse.getMessage(), false);
                    }
                } else {
                    ShowSuccessPop(context, context.getResources().getString(R.string.try_again), false);
                }
            }

            if (url_type.equalsIgnoreCase("get address list")) {
                CommFunc.DismissDialog();
                AddressListAct.addressListResponse = (AddressListResponse) o;

                if (AddressListAct.addressListResponse != null) {

                    if (AddressListAct.addressListResponse.getCode() == 1) {

                        data = AddressListAct.addressListResponse.getData();
                        notifyDataSetChanged();

                        if (AddressListAct.AddressListActivityComingFrom.equalsIgnoreCase("cart ship address")) {

                            for(int i=0 ; i<AddressListAct.addressListResponse.getData().size(); i++){

                                if (AddressListAct.addressListResponse.getData().get(i).getIsDefaultShipping() == 1) {

                                    Constants.shippingAddress_VALUE = (AddressListAct.addressListResponse.getData().get(i).getFirstname() + " " +
                                            AddressListAct.addressListResponse.getData().get(i).getLastname() + "\n\n" +
                                            AddressListAct.addressListResponse.getData().get(i).getStreet() + "\n" +
                                            AddressListAct.addressListResponse.getData().get(i).getCity() + ", " +
                                            AddressListAct.addressListResponse.getData().get(i).getRegion() + " " +
                                            AddressListAct.addressListResponse.getData().get(i).getPostcode() + "\n" +
                                            AddressListAct.addressListResponse.getData().get(i).getCountry() + "\n\n" +
                                            "Phone no. " + AddressListAct.addressListResponse.getData().get(i).getTelephone());

                                    PlaceOrder_shipping_address placeOrderShippingAddress = new PlaceOrder_shipping_address(
                                            AddressListAct.addressListResponse.getData().get(i).getId(),
                                            AddressListAct.addressListResponse.getData().get(i).getCity(),
                                            AddressListAct.addressListResponse.getData().get(i).getCountryId(),
                                            AddressListAct.addressListResponse.getData().get(i).getPostcode(),
                                            AddressListAct.addressListResponse.getData().get(i).getRegion(),
                                            AddressListAct.addressListResponse.getData().get(i).getRegionId(),
                                            AddressListAct.addressListResponse.getData().get(i).getStreet(),
                                            AddressListAct.addressListResponse.getData().get(i).getTelephone(),0);

                                    Constants.shippingAddress_VALUE_ObJ_TYPE = placeOrderShippingAddress;
                                }
                            }
                        }
                    }
                    else {
                        data.clear();
                        notifyDataSetChanged();
                        Constants.shippingAddress_VALUE = "";
                        CommFunc.ShowStatusPop(context, AddressListAct.addressListResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(context, "Data not found. Please try again.", false);
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(context, message, false);
        }
    };

    private void ShowSuccessPop(Context context, String description, boolean status) {
        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.pop_up_status);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView popheaderImage = dialog.findViewById(R.id.successPop_Header_image);
        TextView poperrorDescription = dialog.findViewById(R.id.successPop_error_description);
        Button submit = dialog.findViewById(R.id.successPop_done_button);

        poperrorDescription.setText(description);

        if (!status) {
            popheaderImage.setImageResource(R.drawable.oopsimage);
        } else {
            popheaderImage.setImageResource(R.drawable.ok);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddressList();
//                ((AddressListAct) context).getAddressList();
                dialog.dismiss();
            }
        });
    }

    public void getAddressList() {
        if (InternetConnection.isConnected(context)) {
            CommFunc.ShowProgressbar(context);
            APICallList.getAddressList("get address list", response, context);
        } else {
            CommFunc.ShowStatusPop(context, "No internet connection. Please try again.", false);
        }
    }

}
