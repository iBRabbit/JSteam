package com.google.jsteam.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "JSteam_asg.db";
    public final static int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase database;

    GameHelper gameDB;
    UserHelper userDB;
    ReviewHelper reviewDB;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        database = this.getReadableDatabase();
        Log.i("DatabaseHelper", String.format("constructor: constructor %s called", DATABASE_NAME));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,username TEXT, password TEXT, region TEXT, phoneNumber TEXT, email TEXT)";
        sqLiteDatabase.execSQL(query);
        Log.i("UserTable: ", "onCreate: " + query);

        query = "CREATE TABLE games (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, genre TEXT, rating FLOAT, price INTEGER, description TEXT, image TEXT)";
        sqLiteDatabase.execSQL(query);
        Log.i("GameTable: ", "onCreate: " + query);

        query = "CREATE TABLE reviews (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, game_id INTEGER, user_id INTEGER, review TEXT, FOREIGN KEY (game_id) REFERENCES games(id) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(query);
        Log.i("ReviewTable: ", "onCreate: " + query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS users";
        sqLiteDatabase.execSQL(query);
        Log.i("UserTable: ", "onUpgrade: " + query);

        query = "DROP TABLE IF EXISTS games";
        sqLiteDatabase.execSQL(query);
        Log.i("GameTable: ", "onUpgrade: " + query);

        query = "DROP TABLE IF EXISTS reviews";
        sqLiteDatabase.execSQL(query);
        Log.i("ReviewTable: ", "onUpgrade: " + query);

        onCreate(sqLiteDatabase);
    }

    public Cursor query(String query) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
        } catch (Exception e) {
            Log.e("DatabaseHelper: ", String.format("query error: %s", e.getMessage()));
            return null;
        }

        Log.i("DatabaseHelper: ", "query: " + query);
        return null;
    }

    public Cursor getData(String dataType, String str, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        try {
            String query = String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, dataType, str);
            cursor = db.rawQuery(query, null);
        } catch (Exception e){
            Log.e("UserTable: ", String.format("getData: %s", e.getMessage()));
            return null;
        }

        return cursor;
    }

    public Cursor getData(String dataType, Integer num, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        try {
            String query = String.format("SELECT * FROM %s WHERE %s = %d", tableName, dataType, num);
            cursor = db.rawQuery(query, null);
        } catch (Exception e){
            Log.e("UserTable: ", String.format("getData: %s", e.getMessage()));
            return null;
        }

        return cursor;
    }

    public Cursor getDataWithQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }
}
