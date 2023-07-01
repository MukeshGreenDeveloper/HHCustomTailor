package com.ms.hht.ui.measure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.ms.hht.R;
import com.ms.hht.data.adapter.DisplayMeasurementResultAdapter;
import com.ms.hht.data.adapter.SpaceItemDecoration;
import com.ms.hht.data.response.GETMSMeasurementResponse;
import com.ms.hht.databinding.ActMeasurementResultBinding;
import com.ms.hht.utils.SessionManager;
import java.util.List;

public class MeasurementResult extends AppCompatActivity implements View.OnClickListener {
    public static List<GETMSMeasurementResponse.DataItem> MEASUREMENT;
    public static String MeasurementHistoryActivityComingFrom;
    GridLayoutManager layoutManager;
    ActMeasurementResultBinding resultBinding;
    SessionManager sessionManager;

    public void onBackPressed() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActMeasurementResultBinding inflate = ActMeasurementResultBinding.inflate(getLayoutInflater());
        this.resultBinding = inflate;
        setContentView((View) inflate.getRoot());
        this.resultBinding.conBtn.setOnClickListener(this);
        this.resultBinding.retakeBtn.setOnClickListener(this);
        this.sessionManager = new SessionManager(this);
        List<GETMSMeasurementResponse.DataItem> list = MEASUREMENT;
        if (list != null && list.size() > 0) {
            this.layoutManager = new GridLayoutManager(this, 1);
            this.resultBinding.measurementRecyclerView.setLayoutManager(this.layoutManager);
            this.resultBinding.measurementRecyclerView.addItemDecoration(new SpaceItemDecoration(30));
            this.resultBinding.measurementRecyclerView.setAdapter(new DisplayMeasurementResultAdapter(this, MEASUREMENT));
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.conBtn) {
            Intent intent = new Intent(this, MeasurementHistoryAct.class);
            MeasurementHistoryAct.MeasurementHistoryActivityComingFrom = "cart";
            finish();
            startActivity(intent);
        } else if (id == R.id.retakeBtn) {
            startActivity(new Intent(this, CameraAct.class));
        }
    }
}
