package com.ms.hht.ui.customization.Product_Zoom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.response.GetFeatureImagesResponse;
import com.ms.hht.databinding.ActivityViewImageBinding;
import com.ms.hht.ui.customization.CustomizationProcess.Activity.CustomizationAct;

public class ViewImageActivity extends AppCompatActivity {

    public static GetFeatureImagesResponse.Data FEATURE_IMAGE_DATA;
    public static String COMING_FROM = "";
    ActivityViewImageBinding viewImageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewImageBinding = ActivityViewImageBinding.inflate(getLayoutInflater());
        setContentView(viewImageBinding.getRoot());
        viewImageBinding.viewImageScreen.removeAllViews();
        if (COMING_FROM.equalsIgnoreCase("front image")){
            for (int i = 0; i<= FEATURE_IMAGE_DATA.getFront().size()-1;i++) {
                RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                ImageView image1 = new ImageView(this);
                image1.setLayoutParams(vp);
                viewImageBinding.viewImageScreen.addView(image1);
                Glide.with(ViewImageActivity.this).load(FEATURE_IMAGE_DATA.getFront().get(i).getImage()).into(image1);
            }
        }
        else {
            for (int i = 0; i<= FEATURE_IMAGE_DATA.getRear().size()-1;i++) {
                RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                ImageView image1 = new ImageView(this);
                image1.setLayoutParams(vp);
                viewImageBinding.viewImageScreen.addView(image1);
                Glide.with(ViewImageActivity.this).load(FEATURE_IMAGE_DATA.getRear().get(i).getImage()).into(image1);
            }
        }

        viewImageBinding.backbtn.setOnClickListener(view -> {
            CustomizationAct.isZoom = false;
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomizationAct.isZoom = false;
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}