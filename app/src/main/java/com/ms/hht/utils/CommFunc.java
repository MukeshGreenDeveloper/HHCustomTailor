package com.ms.hht.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.hht.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommFunc {

    public static Dialog dialog;
    public static void ShowStatusPop(Context context, String description, boolean status) {
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
        submit.setOnClickListener(v -> dialog.dismiss());
    }

    public static void DismissDialog() {
        dialog.dismiss();
    }

    public static void ShowProgressbar(Context context) {
        dialog = new Dialog(context);
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.progress_layout);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static boolean Password_Validation(String password)
    {

        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            //Pattern eight = Pattern.compile (".{8}");


            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }
}
