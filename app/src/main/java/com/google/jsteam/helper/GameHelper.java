package com.google.jsteam.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.jsteam.model.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class GameHelper {

    private static final String TABLE_NAME = "games";
    private DatabaseHelper dbh;
    private final Context context;

    public GameHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        dbh = new DatabaseHelper(context);
        dbh.getReadableDatabase();
        Log.i("GameHelper", String.format("Open %s: Open method called", TABLE_NAME));
    }

    public void close() throws SQLException {
        dbh.close();
        Log.i("GameHelper", String.format("Close %s: Close method called", TABLE_NAME));
    }

    public void insert(String name, String genre, Double rating, Integer price, String description, String image) {
        SQLiteDatabase database = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("image", image);
        values.put("price", price);
        values.put("rating", rating);
        values.put("genre", genre);
        database.insert(TABLE_NAME, null, values);
        Log.d("GameHelper", String.format("Insert %s: Insert method called", TABLE_NAME));
    }

    public Cursor getAllData() {
        return dbh.getDataWithQuery("SELECT * FROM " + TABLE_NAME);
    }

    public Vector<Game> getAndZipAllData() {
        Vector<Game> gameVector = new Vector<>();
        Cursor cursor = getAllData();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int idIndex = cursor.getColumnIndex("id"),
                    nameIndex = cursor.getColumnIndex("name"),
                    descriptionIndex = cursor.getColumnIndex("description"),
                    imageIndex = cursor.getColumnIndex("image"),
                    priceIndex = cursor.getColumnIndex("price"),
                    ratingIndex = cursor.getColumnIndex("rating"),
                    genreIndex = cursor.getColumnIndex("genre");

            gameVector.add(new Game(
                    cursor.getInt(idIndex),
                    cursor.getString(nameIndex),
                    cursor.getString(descriptionIndex),
                    cursor.getString(imageIndex),
                    cursor.getInt(priceIndex),
                    cursor.getDouble(ratingIndex),
                    cursor.getString(genreIndex)
            ));
        }
        return gameVector;
    }

    public Game getGameByID(Integer id) {
        Vector<Game> gameVector = getAndZipAllData();
        for (Game game : gameVector) {
            if (game.getID() == id)
                return game;
        }
        return null;
    }

    public void fetchAndInsertDataFromJSON(Context context) {
        RequestQueue rq = Volley.newRequestQueue(context);
        String url = "https://mocki.io/v1/6b7306e9-5c3b-4341-8efa-601bbb9b3a94";

        if(getAllData().getCount() > 0)
            return;

        // Ga bisa langsung masukin ke vector gara2 asyncronous, jd masukin db aja dulu baru diambil
        JsonObjectRequest jObjReq = new JsonObjectRequest(url, null, response -> {
            try {
                JSONArray gameArr = response.getJSONArray("games");
                for (int i = 0; i < gameArr.length(); i++) {
                    JSONObject obj = gameArr.getJSONObject(i);

                    String tmpPrice = obj.getString("price");
                    tmpPrice = tmpPrice
                                    .replace("Rp", "")
                                    .replace(".", "")
                                    .replace(" ", "");
                    int price = Integer.parseInt(tmpPrice);

                    insert(
                            obj.getString("name"),
                            obj.getString("genre"),
                            obj.getDouble("rating"),
                            price,
                            obj.getString("description"),
                            obj.getString("image")
                    );

                }
            } catch (JSONException e) {
                Log.e("GameHelper", "onResponseError: " + e.getMessage());
            }
        }, error -> Log.e("GameHelper", "onErrorResponse: " + error.getMessage()));

        rq.add(jObjReq);
    }

}