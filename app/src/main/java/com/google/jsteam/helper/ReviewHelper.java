package com.google.jsteam.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.jsteam.model.Review;

import java.util.Vector;


public class ReviewHelper {

    private static final String TABLE_NAME = "reviews";
    private DatabaseHelper dbh;
    private final Context context;

    public ReviewHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbh = new DatabaseHelper(context);
        dbh.getReadableDatabase();
        Log.i("ReviewHelper", String.format("Open %s: Open method called", TABLE_NAME));
    }

    public void close() throws SQLException {
        dbh.close();
        Log.i("ReviewHelper", String.format("Close %s: Close method called", TABLE_NAME));
    }

    public void reviewFactory() {
        open();

        dbh.query("DELETE FROM " + TABLE_NAME);

        insert(1, 65, "Review Pertama yang agak panjang dan lebar. Review ini sangat panjang dan sangat lebar sehinga panjang dan lebar.");
        insert(1, 66, "Review Kedua");
        insert(1, 67, "Review Ketiga");
        insert(2, 66, "Review Keenam untu ID 2");
        insert(2, 66, "Review Ketujuh untu ID 2");
        insert(2, 66, "Review Kelapan untu ID 2");
        insert(1, 68, "Review Keempat");
        insert(1, 65, "Review Kelima untuk menunjukan satu orang itu bisa lebih dari satu review game");
        insert(2, 67, "Review Kesembilan untu ID 2");

        close();
    }

    public Cursor getAllData() {
        return dbh.getDataWithQuery("SELECT * FROM " + TABLE_NAME);
    }

    public void insert(int userID, int gameID, String review) {
        SQLiteDatabase database = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", userID);
        values.put("game_id", gameID);
        values.put("review", review);
        database.insert(TABLE_NAME, null, values);

        Log.d("ReviewHelper", String.format("Insert %s: Insert method called", TABLE_NAME));
    }

    public void update(int reviewID, String review) {
        String query = String.format("UPDATE %s SET review = '%s' WHERE id = %d", TABLE_NAME, review, reviewID);

        try {
            dbh.query(query);
        } catch (Exception e) {
            Log.e("ReviewHelper", String.format("Update %s: Error", TABLE_NAME));
        }
    }

    public void delete(int reviewID) {
        String query = String.format("DELETE FROM %s WHERE id = %d", TABLE_NAME, reviewID);

        try {
            dbh.query(query);
        } catch (Exception e) {
            Log.e("ReviewHelper", String.format("Delete %s: Error", TABLE_NAME));
        }
    }

    public Vector<Review> getAndZipAllDataByUserID(int userID) {
        Vector<Review> reviewVector = new Vector<>();
        Cursor cursor = getAllData();
        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int     idIndex = cursor.getColumnIndex("id"),
                    userIDIndex = cursor.getColumnIndex("user_id"),
                    gameIDIndex = cursor.getColumnIndex("game_id"),
                    reviewIndex = cursor.getColumnIndex("review");

            if(cursor.getInt(userIDIndex) == userID) {
                reviewVector.add(new Review(
                        cursor.getInt(idIndex),
                        cursor.getInt(userIDIndex),
                        cursor.getInt(gameIDIndex),
                        cursor.getString(reviewIndex)
                ));
            }

        }
        return reviewVector;
    }

}
