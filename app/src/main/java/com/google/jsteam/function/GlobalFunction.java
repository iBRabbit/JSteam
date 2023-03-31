package com.google.jsteam.function;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.google.jsteam.HomeActivity;
import com.google.jsteam.R;

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
}
