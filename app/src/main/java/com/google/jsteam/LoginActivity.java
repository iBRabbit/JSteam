package com.google.jsteam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.jsteam.function.GlobalFunction;
import com.google.jsteam.helper.UserHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText loginUsernameEditText, loginPasswordEditText;
    Button loginSubmitButton;
    TextView loginDirectToRegisterTextView;
    UserHelper userDB;
    Integer authID;

    GlobalFunction func = new GlobalFunction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsernameEditText = findViewById(R.id.loginUsernameEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        loginSubmitButton = findViewById(R.id.loginSubmitButton);
        loginDirectToRegisterTextView = findViewById(R.id.loginDirectToRegisterTextView);

        loginSubmitButton.setOnClickListener(this);
        loginDirectToRegisterTextView.setOnClickListener(this);

//        debugAutoFill();

        userDB = new UserHelper(this);

        authID = func.getAuthID(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        String username = loginUsernameEditText.getText().toString();
        String password = loginPasswordEditText.getText().toString();

        if(view == loginDirectToRegisterTextView){
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }

        if(view == loginSubmitButton) {
            userDB.open();
            if(!userDB.isUserExists("username", username)) {
                Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!userDB.auth(username, password)) {
                Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                return;
            }

            intent = new Intent(this, OTPActivity.class);
            intent.putExtra("username",username);
            intent.putExtra("id",userDB.getIDByUsername(username));

            Log.i("AuthID",authID.toString());

            Log.i("LoginActivity", "User authenticated, authID" + userDB.getIDByUsername(username));

            userDB.close();
            startActivity(intent);
            finish();
        }

    }

    void debugAutoFill(){
        loginUsernameEditText.setText("admin");
        loginPasswordEditText.setText("admin");
    }
}