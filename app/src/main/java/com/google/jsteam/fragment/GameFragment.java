package com.google.jsteam.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.jsteam.R;
import com.google.jsteam.adapter.GameAdapter;
import com.google.jsteam.helper.GameHelper;
import com.google.jsteam.model.Game;

import java.util.Vector;


public class GameFragment extends Fragment {

    Game game;
    GameHelper gameDB;
    Vector <Game> gameVector;
    GameAdapter gameAdapter;
    RecyclerView gameRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        if(view == null)
            return null;

        gameRecyclerView = view.findViewById(R.id.gameRecyclerView);
        gameDB = new GameHelper(getContext());
        gameDB.open();
        gameDB.gameFactory();

        gameVector = new Vector<>();
        gameVector = gameDB.getAndZipAllData();

        gameAdapter = new GameAdapter(getContext());

        gameAdapter.setGameVector(gameVector);
        gameRecyclerView.setAdapter(gameAdapter);
        gameRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        gameDB.close();
        return view;
    }

    void printAllGameInLog(){
        Cursor cursor = gameDB.getAllData();
        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Log.i("GameFragment", "id: " + cursor.getInt(0));
            Log.i("GameFragment", "name: " + cursor.getString(1));
            Log.i("GameFragment", "rating: " + cursor.getFloat(2));
            Log.i("GameFragment", "price: " + cursor.getInt(3));
            Log.i("GameFragment", "description: " + cursor.getString(4));
            Log.i("GameFragment", "image: " + cursor.getString(5));
        }
    }
}