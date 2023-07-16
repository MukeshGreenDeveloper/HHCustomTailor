package com.ms.hht.ui.measure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ms.hht.R;
import com.ms.hht.data.response.MeasurementHistoryResponse;
import com.ms.hht.data.service.APICallList;
import com.ms.hht.data.service.APIClient;
import com.ms.hht.data.service.APIService;
import com.ms.hht.data.service.DisposableData;
import com.ms.hht.databinding.ActMeasurementHistoryBinding;
import com.ms.hht.ui.customization.Fabric_Select.order.CartDetailsActivity;
import com.ms.hht.ui.customization.Fabric_Select.order.OrderCartAct;
import com.ms.hht.ui.payment.PaymentAct;
import com.ms.hht.utils.CommFunc;
import com.ms.hht.utils.InternetConnection;
import com.ms.hht.utils.RecyclerTouchListener;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class MeasurementHistoryAct extends AppCompatActivity implements View.OnClickListener {
    ActMeasurementHistoryBinding historyBinding;
    APIService apiService3;
    MeasurementHistoryResponse getMeasurementHistoryResponse;
    MeasurementHistoryAdapter measurementHistoryAdapter;
    public static int SelectedMeasurementId = -1;
    public static String MeasurementHistoryActivityComingFrom = "";
    int measurementCapturedNow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding = ActMeasurementHistoryBinding.inflate(getLayoutInflater());
        setContentView(historyBinding.getRoot());

        if(getIntent()!=null && getIntent().getIntExtra("MeasurementIDCapturedNow",0)>0) {
            measurementCapturedNow = getIntent().getIntExtra("MeasurementIDCapturedNow", 0);
        }
        historyBinding.back.setOnClickListener(this);
        historyBinding.retakeBtn.setOnClickListener(this);
        apiService3 = APIClient.getClient(MeasurementHistoryAct.this).create(APIService.class);


        getData();

        if (MeasurementHistoryActivityComingFrom.equalsIgnoreCase("measurement process")) {
            historyBinding.retakeBtn.setVisibility(View.VISIBLE);
            historyBinding.retakeBtn.setText("Retake");
        } else if (MeasurementHistoryActivityComingFrom.equalsIgnoreCase("cart")) {
            historyBinding.retakeBtn.setVisibility(View.VISIBLE);
            historyBinding.retakeBtn.setText("Proceed");
        } else {
            historyBinding.retakeBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                backFunction();
                break;

            case R.id.retakeBtn:
                if (MeasurementHistoryActivityComingFrom.equalsIgnoreCase("measurement process")) {
                    Intent retakeIntent = new Intent(MeasurementHistoryAct.this, EnterDetails.class);
                    startActivity(retakeIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                } else if (MeasurementHistoryActivityComingFrom.equalsIgnoreCase("cart")) {

                    if (SelectedMeasurementId<0){
                        CommFunc.ShowStatusPop(MeasurementHistoryAct.this,"Please select measurement to proceed.",false);
                    }
                    else {

//                        Intent i1 = new Intent(MeasurementHistoryAct.this, MeasurementDetailListActivity.class);
                        Intent i1 = new Intent(MeasurementHistoryAct.this, CartDetailsActivity.class);
                        MeasurementDetailListActivity.measurementDetailList = getMeasurementHistoryResponse.getData().get(SelectedMeasurementId).getMeasurement();
                        MeasurementDetailListActivity.MeasurementHistoryActivityComingFrom = MeasurementHistoryActivityComingFrom;
                        MeasurementDetailListActivity.SelectedMeasurementId = getMeasurementHistoryResponse.getData().get(SelectedMeasurementId).getId();
                        PaymentAct.cart_measurement_id_in_paymentAct = getMeasurementHistoryResponse.getData().get(SelectedMeasurementId).getId();
                        startActivity(i1);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backFunction();
    }

    private void getData() {
        if (InternetConnection.isConnected(MeasurementHistoryAct.this)) {
            CommFunc.ShowProgressbar(this);
            APICallList.getMeasurementHistoryList("Measurement List", response, MeasurementHistoryAct.this);
        } else {
            CommFunc.ShowStatusPop(this, getResources().getString(R.string.internet), false);
        }
    }

    private final DisposableData response = new DisposableData() {
        @Override
        public void onSuccess(String url_type, Object o) throws Exception {
            if (url_type.equalsIgnoreCase("Measurement List")) {
                CommFunc.DismissDialog();
                getMeasurementHistoryResponse = (MeasurementHistoryResponse) o;
                if (getMeasurementHistoryResponse != null) {
                    if (getMeasurementHistoryResponse.getCode() == 1) {
                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MeasurementHistoryAct.this, RecyclerView.VERTICAL, false);
                        historyBinding.measRecy.setLayoutManager(layoutManager1);
                        if(measurementCapturedNow>0){
                            for(int i=0;i<getMeasurementHistoryResponse.getData().size();i++){
                                MeasurementHistoryResponse.DataItem dataItem = getMeasurementHistoryResponse.getData().get(i);
                                if(getMeasurementHistoryResponse.getData().get(i).getId() == measurementCapturedNow){
                                    MeasurementHistoryAct.this.SelectedMeasurementId = i;
                                    dataItem.setChecked(true);
                                    getMeasurementHistoryResponse.getData().set(i,dataItem);
                                    break;
                                }
                            }
                        }
                        measurementHistoryAdapter = new MeasurementHistoryAdapter(MeasurementHistoryAct.this, getMeasurementHistoryResponse.getData()/*,measurementCapturedNow*/);
                        historyBinding.measRecy.setAdapter(measurementHistoryAdapter);
                        historyBinding.measRecy.setHasFixedSize(true);
                        historyBinding.measRecy.setItemViewCacheSize(20);
                        historyBinding.measRecy.setDrawingCacheEnabled(true);
                        historyBinding.measRecy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                    } else {
                        CommFunc.ShowStatusPop(MeasurementHistoryAct.this, getMeasurementHistoryResponse.getMessage(), false);
                    }
                } else {
                    CommFunc.ShowStatusPop(MeasurementHistoryAct.this, getResources().getString(R.string.try_again), false);
                }

            }
        }

        @Override
        public void onError(String message) throws Exception {
            CommFunc.DismissDialog();
            CommFunc.ShowStatusPop(MeasurementHistoryAct.this, message, false);
        }
    };

    private void backFunction() {
        if (MeasurementHistoryActivityComingFrom.equalsIgnoreCase("cart")) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

}