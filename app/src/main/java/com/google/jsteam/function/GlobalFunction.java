package com.google.jsteam.function;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.jsteam.HomeActivity;
import com.google.jsteam.R;

import java.util.Random;

public class GlobalFunction {


    public Context safeGetContext(Context context) {
        return context == null ? null : context.getApplicationContext();
    }

    public void setAuthID(Context context, Integer authID) {
        SharedPreferences preference = context.getSharedPreferences("authID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt("authID", authID);
        editor.apply();
    }

    public Integer getAuthID(Context context) {
        SharedPreferences preference = context.getSharedPreferences("authID", Context.MODE_PRIVATE);
        return preference.getInt("authID", -1);
    }

    public void authCheck(Context context) {
        SharedPreferences preference = context.getSharedPreferences("authID", Context.MODE_PRIVATE);
        int authID = preference.getInt("authID", -1);

        if(authID == -1)
            return;

        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public Boolean isStringAlphaNumeric(String string) {
        return string.matches("[a-zA-Z0-9]+");
    }

    public String sendAndGetOTP (Context ctx, SmsManager smsManager, String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        String otp = "";
        for (int i=0;i<6;i++){
            Random r = new Random();
            otp += r.nextInt(9-1) + 1;
        }
        smsManager.sendTextMessage(
                phoneNumber,
                null,
                otp,
                null,
                null);
        return otp;
    }
}
