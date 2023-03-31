package com.google.jsteam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.jsteam.helper.UserHelper;
import com.google.jsteam.function.RegisterFunction;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText registerUsernameEditText,
            registerPasswordEditText,
            registerRegionEditText,
            registerPhoneNumberEditText,
            registerEmailEditText;

    TextView registerDirectToLoginTextView;
    Button registerSubmitButton;
    RegisterFunction registerFunc = new RegisterFunction();

    UserHelper userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsernameEditText = findViewById(R.id.registerUsernameEditText);
        registerPasswordEditText = findViewById(R.id.registerPasswordEditText);
        registerEmailEditText = findViewById(R.id.registerEmailEditText);
        registerRegionEditText = findViewById(R.id.registerRegionEditText);
        registerPhoneNumberEditText = findViewById(R.id.registerPhoneNumberEditText);
        registerSubmitButton = findViewById(R.id.registerSubmitButton);
        registerDirectToLoginTextView = findViewById(R.id.registerDirectToLoginTextView);

        registerSubmitButton.setOnClickListener(this);
        registerDirectToLoginTextView.setOnClickListener(this);

        userDB = new UserHelper(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        String  username = registerUsernameEditText.getText().toString(),
                password = registerPasswordEditText.getText().toString(),
                region = registerRegionEditText.getText().toString(),
                phoneNumber = registerPhoneNumberEditText.getText().toString(),
                email = registerEmailEditText.getText().toString();

        if(view == registerSubmitButton) {
            userDB.open();

            if(!registerFunc.isRegisterFormInputValid(username, password, region, phoneNumber, email)) {
                Toast.makeText(this, registerFunc.getRegisterFormErrorMessage(username, password, region, phoneNumber, email), Toast.LENGTH_SHORT).show();
                return;
            }

            if(userDB.isUserExists("username", username)) {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            userDB.insert(username, password, region, phoneNumber, email);

            Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

            userDB.close();
            finish();
        }

        if(view == registerDirectToLoginTextView) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}