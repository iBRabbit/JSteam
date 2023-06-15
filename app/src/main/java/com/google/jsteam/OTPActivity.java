package com.google.jsteam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.jsteam.function.GlobalFunction;
import com.google.jsteam.helper.UserHelper;
import com.google.jsteam.model.User;

import java.util.Random;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    Integer authID;
    UserHelper userDB;
    User user;
    GlobalFunction func = new GlobalFunction();
    TextView descriptionTextView, resendTextView;
    Button btnVerify;
    EditText otp1EditText, otp2EditText, otp3EditText, otp4EditText, otp5EditText, otp6EditText;
    SmsManager smsManager;
    int sendSMSPermission;
    TelephonyManager telephonyManager;

    //  countdown resend
    boolean resendEnable = false;
    int resendTime = 60;
    int selectedEditTextPos = 0;
    String otp = "";

    public final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {
                if (selectedEditTextPos == 0) {
                    selectedEditTextPos = 1;
                    showKeyboard(otp2EditText);
                } else if (selectedEditTextPos == 1) {
                    selectedEditTextPos = 2;
                    showKeyboard(otp3EditText);
                } else if (selectedEditTextPos == 2) {
                    selectedEditTextPos = 3;
                    showKeyboard(otp4EditText);
                } else if (selectedEditTextPos == 3) {
                    selectedEditTextPos = 4;
                    showKeyboard(otp5EditText);
                } else if (selectedEditTextPos == 4) {
                    selectedEditTextPos = 5;
                    showKeyboard(otp6EditText);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        userDB = new UserHelper(this);
        userDB.open();

        Intent getintent = getIntent();
        int id = getintent.getIntExtra("id",-1);
        user = userDB.getData("id", id);

        btnVerify = findViewById(R.id.verifyOTPButton);
        descriptionTextView = findViewById(R.id.description2TitleTextView);
        descriptionTextView.setText(user.getPhoneNumber());
        resendTextView = findViewById(R.id.resendOTPTextView);
        otp1EditText = findViewById(R.id.otpInput1);
        otp2EditText = findViewById(R.id.otpInput2);
        otp3EditText = findViewById(R.id.otpInput3);
        otp4EditText = findViewById(R.id.otpInput4);
        otp5EditText = findViewById(R.id.otpInput5);
        otp6EditText = findViewById(R.id.otpInput6);
        smsManager = SmsManager.getDefault();
        sendSMSPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (sendSMSPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        requestPermissions(new String[]{Manifest.permission.READ_PHONE_NUMBERS},102);

        resendTextView.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
        userDB.close();

        otp1EditText.addTextChangedListener(textWatcher);
        otp2EditText.addTextChangedListener(textWatcher);
        otp3EditText.addTextChangedListener(textWatcher);
        otp4EditText.addTextChangedListener(textWatcher);
        otp5EditText.addTextChangedListener(textWatcher);
        otp6EditText.addTextChangedListener(textWatcher);

        showKeyboard(otp1EditText);
        otp = func.sendAndGetOTP(this, smsManager, user.getPhoneNumber());
        countDownTime();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view == btnVerify) {
            String checkOTP = otp1EditText.getText().toString() +
                    otp2EditText.getText().toString() +
                    otp3EditText.getText().toString() +
                    otp4EditText.getText().toString() +
                    otp5EditText.getText().toString() +
                    otp6EditText.getText().toString();
//            if (checkOTP.length() == 6) {
            if (true) {
//                if(otp.equals(checkOTP)){
                if(true){
                    Intent getintent = getIntent();
                    String username = getintent.getStringExtra("username");
                    func.setAuthID(this, userDB.getIDByUsername(username));
                    Toast.makeText(this, "Succesfully Login", Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, HomeActivity.class);
                    func.authCheck(this);
                    startActivity(intent);
                }
            }
            else{
                Toast.makeText(this, "Fill the OTP column", Toast.LENGTH_SHORT).show();
            }
        }
        if (view == resendTextView) {
            if (resendEnable) {
                otp = func.sendAndGetOTP(this, smsManager, user.getPhoneNumber());
                countDownTime();
            }
        }
    }

    public void showKeyboard(EditText otpEditText){
        otpEditText.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void countDownTime(){
        resendEnable = false;
        resendTextView.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime * 1000, 1000) {

            @Override
            public void onTick(long l){
                resendTextView.setText("Resend OTP in "+(l / 1000)+" s");
            }

            @Override
            public void onFinish(){
                resendEnable = true;
                resendTextView.setText("Resend OTP");
                resendTextView.setTextColor(Color.parseColor("#B53D66"));
            }
        }.start();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DEL){
            if(selectedEditTextPos == 5){
                selectedEditTextPos = 4;
                showKeyboard(otp5EditText);
            }else if(selectedEditTextPos == 4){
                selectedEditTextPos = 3;
                showKeyboard(otp4EditText);
            }else if(selectedEditTextPos == 3){
                selectedEditTextPos = 2;
                showKeyboard(otp3EditText);
            }else if(selectedEditTextPos == 2){
                selectedEditTextPos = 1;
                showKeyboard(otp2EditText);
            }else if(selectedEditTextPos == 1){
                selectedEditTextPos = 0;
                showKeyboard(otp1EditText);
            }
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }
    }
}