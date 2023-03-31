package com.google.jsteam.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.jsteam.model.Game;

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

    public void insert(String name, String genre, Float rating, Integer price, String description, String image) {
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

    public void gameFactory() {
        open();

        if(getAllData().getCount() > 0) {
            Log.d("GameHelper", String.format("GameFactory %s: Data already filled.", TABLE_NAME));
            return;
        }

        insert("Angry Birds",
                "Adventure",
                3.5F,
                150000,
                "Angry Bird Game is throwing bird game to enemy.",
                "image_1"
        );

        insert("Plant vs Zombie",
                "Strategy",
                4.5F,
                250000,
                "Plant vs Zombie is throwing plant to zombie.",
                "image_2"
        );

        insert("Need for Asphalt",
                "Racing",
                5.0F,
                350000,
                "This game you race you win.",
                "image_3"
        );

        insert("Game 4",
                "Genre 4",
                3.0F,
                450000,
                "Desc 4",
                ""
        );

        close();

    }

    public Cursor getAllData() {
        return dbh.getDataWithQuery("SELECT * FROM " + TABLE_NAME);
    }

    public Vector<Game> getAndZipAllData() {
        Vector<Game> gameVector = new Vector<>();
        Cursor cursor = getAllData();
        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int     idIndex = cursor.getColumnIndex("id"),
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
        Vector <Game> gameVector = getAndZipAllData();
        for (Game game : gameVector) {
            if (game.getID() == id)
                return game;
        }
        return null;
    }
}
