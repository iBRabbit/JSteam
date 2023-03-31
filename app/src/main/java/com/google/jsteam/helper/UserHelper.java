package com.google.jsteam.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.jsteam.model.User;

public class UserHelper {

    private static final String TABLE_NAME = "users";
    private DatabaseHelper dbh;
    private final Context context;

    public UserHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbh = new DatabaseHelper(context);
        dbh.getReadableDatabase();
        Log.i("UserHelper", String.format("Open %s: Open method called", TABLE_NAME));
    }

    public void close() throws SQLException {
        dbh.close();
        Log.i("UserHelper", String.format("Close %s: Close method called", TABLE_NAME));
    }

    public Boolean isUserExists(String dataType, String str) {
        Cursor cursor;

        try {
            cursor = dbh.getData(dataType, str, TABLE_NAME);
        } catch (Exception e) {
            Log.e("UserTable: ", String.format("Error in isDataAlreadyExists: %s", e.getMessage()));
            return false;
        }

        return cursor.getCount() > 0;
    }
    public Integer getIDByUsername(String username) {
        Cursor cursor;

        cursor = dbh.getData("username", username, TABLE_NAME);
        if(cursor.getCount() <= 0)
            return -1;

        cursor.moveToFirst();

        int idIdx = cursor.getColumnIndex("id");
        return cursor.getInt(idIdx);
    }

    public void insert(String username, String password, String region, String phoneNumber, String email) {
        SQLiteDatabase database = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("region", region);
        values.put("phoneNumber", phoneNumber);
        values.put("email", email);

        database.insert(TABLE_NAME, null, values);
        Log.d("UserTable: ", String.format("insert: Data %s [id: %d] has been inserted into %s", username, getIDByUsername(username) ,TABLE_NAME));
    }

    public Boolean auth(String username, String password) {
        String query = String.format("SELECT * FROM %s WHERE username = '%s' AND password = '%s'", TABLE_NAME, username, password);
        return dbh.getDataWithQuery(query).getCount() > 0;
    }

    public User getData(String dataType, Integer num) {
        Cursor cursor = dbh.getData(dataType, num, TABLE_NAME);
        if(cursor.getCount() == 0)
            return null;

        cursor.moveToFirst();

        int idColIndex = cursor.getColumnIndex("id"),
                usernameColIndex = cursor.getColumnIndex("username"),
                passwordColIndex = cursor.getColumnIndex("password"),
                regionColIndex = cursor.getColumnIndex("region"),
                phoneNumberColIndex = cursor.getColumnIndex("phoneNumber"),
                emailColIndex = cursor.getColumnIndex("email");

        return new User(
                cursor.getInt(idColIndex),
                cursor.getString(usernameColIndex),
                cursor.getString(passwordColIndex),
                cursor.getString(regionColIndex),
                cursor.getString(phoneNumberColIndex),
                cursor.getString(emailColIndex)
        );

    }

}
