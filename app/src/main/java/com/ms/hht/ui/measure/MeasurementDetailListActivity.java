package com.ms.hht.ui.measure;

import static com.ms.hht.utils.Constants.PAYPAL_LIVE_CLIENT_ID;
import static com.ms.hht.utils.Constants.PAYPAL_SANDBOX_CLIENT_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ms.hht.R;
import com.ms.hht.data.response.MeasurementHistoryResponse;
import com.ms.hht.databinding.ActivityMeasurementDetailListBinding;
import com.ms.hht.ui.payment.PaymentAct;
import com.ms.hht.utils.CommFunc;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

import java.util.List;

public class MeasurementDetailListActivity extends AppCompatActivity{

    public static List<MeasurementHistoryResponse.MeasurementItem> measurementDetailList;
    public static String MeasurementHistoryActivityComingFrom = "";
    public static int SelectedMeasurementId = 0;

    ActivityMeasurementDetailListBinding measurementDetailBinding;
    MeasurementDetailAdapter measurementListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        measurementDetailBinding = ActivityMeasurementDetailListBinding.inflate(getLayoutInflater());
        setContentView(measurementDetailBinding.getRoot());

        if (MeasurementHistoryActivityComingFrom.equalsIgnoreCase("cart")) {
            measurementDetailBinding.retakeBtn.setVisibility(View.VISIBLE);
            measurementDetailBinding.retakeBtn.setText("Proceed");
        } else {
            measurementDetailBinding.retakeBtn.setVisibility(View.INVISIBLE);
        }

        measurementDetailBinding.retakeBtn.setOnClickListener(v->{
            if (MeasurementHistoryActivityComingFrom.equalsIgnoreCase("cart")) {

                if (SelectedMeasurementId<1){
                    CommFunc.ShowStatusPop(MeasurementDetailListActivity.this,"Please select measurement to proceed.",false);
                }
                else {
                    Intent payIntent = new Intent(MeasurementDetailListActivity.this, PaymentAct.class);

                    PayPalCheckout.setConfig(new CheckoutConfig(
                            getApplication(),
                            PAYPAL_LIVE_CLIENT_ID,
                            Environment.LIVE,
                            CurrencyCode.USD,
                            UserAction.PAY_NOW
                    ));

                    PaymentAct.cart_measurement_id_in_paymentAct = SelectedMeasurementId;
                    startActivity(payIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        measurementDetailBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMeasurementDetailList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setMeasurementDetailList() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MeasurementDetailListActivity.this, RecyclerView.VERTICAL, false);
        measurementDetailBinding.MeasurementDetailRecy.setLayoutManager(layoutManager1);
        measurementListAdapter = new MeasurementDetailAdapter(MeasurementDetailListActivity.this,measurementDetailList);
        measurementDetailBinding.MeasurementDetailRecy.setAdapter(measurementListAdapter);
        measurementDetailBinding.MeasurementDetailRecy.setHasFixedSize(true);
        measurementDetailBinding.MeasurementDetailRecy.setItemViewCacheSize(20);
        measurementDetailBinding.MeasurementDetailRecy.setDrawingCacheEnabled(true);
        measurementDetailBinding.MeasurementDetailRecy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }
}