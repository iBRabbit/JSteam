package com.google.jsteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.jsteam.function.GlobalFunction;
import com.google.jsteam.helper.UserHelper;
import com.google.jsteam.model.User;

public class OTPActivity extends AppCompatActivity {

    Integer authID;
    UserHelper userDB;
    User user;
    GlobalFunction func = new GlobalFunction();
    TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        userDB = new UserHelper(this);
        userDB.open();

        authID = func.getAuthID(func.safeGetContext(this));
        user = userDB.getData("id", authID);

        descriptionTextView = findViewById(R.id.description2TitleTextView);
        descriptionTextView.setText(user.getPhoneNumber());

        userDB.close();
        finish();
    }
}