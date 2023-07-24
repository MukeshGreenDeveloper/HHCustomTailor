package com.ms.hht.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

public class ImageUtils {
    public static Bitmap compressImage(Bitmap image) {
        Log.d("keyss,,,", "Size b4 compression==>" + image.toString().length());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 100baos
        int options = 100;
        while (baos.toByteArray().length / 1024 > 60) { // 100kb,
            baos.reset();// baosbaos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// options%baos
            options -= 10;// 10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(
                baos.toByteArray());// baosByteArrayInputStream
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ByteArrayInputStream
        Log.d("keyss,,,", "Size a4 compression==>" + bitmap.toString().length());
        return bitmap;
    }

    public static String convertCm_Inch(String value) {
        final double INCH = 0.394;
        return String.valueOf(new DecimalFormat("#.##").format(Double.parseDouble(value) * INCH));
    }
}
