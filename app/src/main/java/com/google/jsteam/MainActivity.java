package com.google.jsteam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.jsteam.function.GlobalFunction;
import com.google.jsteam.helper.GameHelper;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mainRegisterButton, mainLoginButton;
    SharedPreferences settings;

    GlobalFunction func = new GlobalFunction();
    GameHelper gameDB = new GameHelper(this);
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRegisterButton = findViewById(R.id.mainRegisterButton);
        mainLoginButton = findViewById(R.id.mainLoginButton);
        mainRegisterButton.setOnClickListener(this);
        mainLoginButton.setOnClickListener(this);

        func.setAuthID(this, -1);

        // Load semua game dulu ke db
        gameDB.open();
        gameDB.fetchAndInsertDataFromJSON(this);
        gameDB.close();

    }

    @Override
    protected void onStart() {
        super.onStart();
        func.authCheck(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if(view == mainRegisterButton){
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        if(view == mainLoginButton){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}