package com.ms.hht.data.adapter;

import android.annotation.SuppressLint;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ms.hht.R;
import com.ms.hht.data.request.DeleteCartItemRequest;
import com.ms.hht.data.request.UpdateCartQuantityRequest;
import com.ms.hht.data.response.CartListResponse;
import com.ms.hht.data.response.DeleteCartResponse;
import com.ms.hht.data.response.UpdateCartQuantityResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.ui.customization.Fabric_Select.order.CartProdDesc;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderCartAct;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.SessionManager;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {
    Context context;
    List<CartListResponse.Data.QuoteItemItem> cartItemList;
    DeleteCartResponse deleteCartResponse;
    UpdateCartQuantityResponse updateCartQuantityResponse;
    Double ProductPRICE = 0.0;
    int productQuantity = 1;
    int QuoteItemID = -1;
    int adapterPositionForQuantity = -1;
    int adapterPositionForDelete = -1;
    SessionManager sessionManager;

    public CartListAdapter(Context context, List<CartListResponse.Data.QuoteItemItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.sessionManager = new SessionManager(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3 = inflater.inflate(R.layout.cart_list_item_file, parent, false);
        return new MyViewHolder(view3);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        QuoteItemID = cartItemList.get(position).getQuoteItemId();
        ProductPRICE = cartItemList.get(position).getItemTotal();
        productQuantity = cartItemList.get(position).getQuantity();

        holder.quantityText.setText(String.valueOf(productQuantity));

        holder.ProductName.setText(cartItemList.get(position).getItemName());
        holder.ProductPrice.setText("$ " + ProductPRICE);

       try{
           holder.productImage.removeAllViews();
           for (CartListResponse.Data.QuoteItemItem.ItemDataItem.FeatureImages.FrontItem frontItem : cartItemList.get(position).getItemData().get(0).getFeatureImages().getFront()) {
               RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
               ImageView image = new ImageView(context);
               image.setLayoutParams(vp);
               holder.productImage.addView(image);
               Glide.with(context).load(frontItem.getImage()).into(image);

           }
       }catch (Exception e){
           e.printStackTrace();
       }

        int qty = Integer.parseInt(holder.quantityText.getText().toString());

       holder.CArtListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemDesc  = new Intent(context, CartProdDesc.class);
                CartProdDesc.CartProdDescList = cartItemList.get(position).getItemData();
                context.startActivity(itemDesc);

            }
        });

        holder.deleteCartItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterPositionForDelete = holder.getAdapterPosition();
                StatusPop(cartItemList.get(position).getQuoteItemId(),cartItemList.get(position).getItemTotal());
            }
        });

        holder.plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (qty>0) {
                    adapterPositionForQuantity = holder.getAdapterPosition();
                    productQuantity = cartItemList.get(adapterPositionForQuantity).getQuantity();
                    productQuantity++;
                    holder.quantityText.setText(String.valueOf(productQuantity));
                    upadateItemQuantity(adapterPositionForQuantity, productQuantity);
                }
            }
        });

        holder.minusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty>1) {
                    adapterPositionForQuantity = holder.getAdapterPosition();
                    productQuantity = cartItemList.get(adapterPositionForQuantity).getQuantity();
                    productQuantity--;
                    holder.quantityText.setText(String.valueOf(productQuantity));
                    upadateItemQuantity(adapterPositionForQuantity, productQuantity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ProductName, ProductPrice, quantityText;
        ImageView deleteCartItemImg, plusImage, minusImage;
        RelativeLayout productImage, CArtListCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            ProductName = itemView.findViewById(R.id.ProductName);
            ProductPrice = itemView.findViewById(R.id.ProductPrice);
            deleteCartItemImg = itemView.findViewById(R.id.deleteCartItemImg);
            plusImage = itemView.findViewById(R.id.plusText);
            minusImage = itemView.findViewById(R.id.minusText);
            quantityText = itemView.findViewById(R.id.quantityText);
            CArtListCard = itemView.findViewById(R.id.CArtListCard);
        }
    }

    private void upadateItemQuantity(int Position, int productQnty) {
        if (InternetConnection.isConnected(context)) {
            CommFunc.ShowProgressbar(context);
            UpdateCartQuantityRequest updateCartQuantityRequest = new UpdateCartQuantityRequest(cartItemList.get(Position).getQuoteItemId(), productQnty);
            APICallList.updateCartQuantity(updateCartQuantityRequest, "update cart quantity", response, context);
        } else {
            CommFunc.ShowStatusPop(context, "No internet connection", false);
        }
    }

    private void deleteitem(int QuoteItemID, double ProductPRICE) {
        if (InternetConnection.isConnected(context)) {
            CommFunc.ShowProgressbar(context);
            DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest("single", QuoteItemID, ProductPRICE);
            APICallList.deleteCartList(deleteCartItemRequest, "delete cart item", response, context);
        } else {
            CommFunc.ShowStatusPop(context, "No internet connection", false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("delete cart item")) {
                CommFunc.DismissDialog();
                deleteCartResponse = (DeleteCartResponse) o;
                if (deleteCartResponse != null) {
                    if (deleteCartResponse.getCode() == 1) {

                        callingGetCartListApi();

                    } else {
                        CommFunc.ShowStatusPop(context, deleteCartResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(context, "NO ERROR", false);
                }
            }
            else if (url_type.equalsIgnoreCase("update cart quantity")) {

                updateCartQuantityResponse = (UpdateCartQuantityResponse) o;
                if (updateCartQuantityResponse != null) {
                    if (updateCartQuantityResponse.getCode() == 1) {

                        cartItemList.get(adapterPositionForQuantity).setQuantity(productQuantity);
//                        notifyDataSetChanged();
                        callingGetCartListApi();
                    } else {
                        CommFunc.ShowStatusPop(context, updateCartQuantityResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(context, "NO ERROR", false);
                }
            }
            else   if (url_type.equalsIgnoreCase("cart details data")) {
                CommFunc.DismissDialog();
                OrderCartAct.cartListResponse = (CartListResponse) o;
                if (OrderCartAct.cartListResponse != null) {
                    if (OrderCartAct.cartListResponse.getCode() == 1) {
                        cartItemList = OrderCartAct.cartListResponse.getData().getQuoteItem();
                        sessionManager.setCartCount(String.valueOf(OrderCartAct.cartListResponse.getData().getQuoteItem().size()));
                        notifyDataSetChanged();
                    }
                    else {
                        cartItemList.clear();
                        sessionManager.setCartCount(String.valueOf(0));
                        notifyDataSetChanged();
                        CommFunc.ShowStatusPop(context, OrderCartAct.cartListResponse.getMessage(), false);
                    }
                }
                else {
                    CommFunc.ShowStatusPop(context, "Something went wrong. Please try again. ", false);
                }
            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(context, message, false);
        }
    };

    private void callingGetCartListApi(){
        if (InternetConnection.isConnected(context)) {
            APICallList.getcartList( "cart details data", response, context);
        } else {
            CommFunc.ShowStatusPop(context, context.getResources().getString(R.string.internet), false);
        }
    }

    private void StatusPop(int QuoteItemID, double ProductPRICE) {
        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.delete_popup);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView popheaderImage = dialog.findViewById(R.id.successPop_Header_image);
        TextView poperrorDescription = dialog.findViewById(R.id.successPop_error_description);
        Button submit = dialog.findViewById(R.id.successPop_done_button);
        Button no_button = dialog.findViewById(R.id.pop_no_button);

        poperrorDescription.setText(context.getResources().getString(R.string.delete_content));

        popheaderImage.setImageResource(R.drawable.oopsimage);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                deleteitem(QuoteItemID, ProductPRICE);
            }
        });

        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
