package com.google.jsteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.jsteam.function.GlobalFunction;
import com.google.jsteam.helper.GameHelper;
import com.google.jsteam.helper.ReviewHelper;
import com.google.jsteam.model.Game;
import com.squareup.picasso.Picasso;

public class GameDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    Integer gameID, authID;
    Game game;
    GameHelper gameDB;

    TextView    detailGameNameTextView,
                detailGameGenreTextView,
                detailGamePriceTextView,
                detailGameRatingTextView,
                detailGameDescriptionTextView;

    EditText    detailGameCommentEditText;

    ImageView   detailGameImageView;
    ImageButton detailGameCommentButton;

    String      detailGameName,
                detailGameGenre,
                detailGamePrice,
                detailGameRating,
                detailGameDescription;

    int        detailGameImageID;

    ReviewHelper reviewDB;
    GlobalFunction func = new GlobalFunction();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        gameID = getIntent().getIntExtra("gameID", 0);
        authID = func.getAuthID(this);

        if(gameID == 0) {
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        gameDB = new GameHelper(this);
        reviewDB = new ReviewHelper(this);
        gameDB.open();

        game = gameDB.getGameByID(gameID);

        if(game == null) {
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        detailGameNameTextView = findViewById(R.id.detailGameNameTextView);
        detailGameGenreTextView = findViewById(R.id.detailGameGenreTextView);
        detailGamePriceTextView = findViewById(R.id.detailGamePriceTextView);
        detailGameDescriptionTextView = findViewById(R.id.detailGameDescriptionTextView);
        detailGameImageView = findViewById(R.id.detailGameImageView);
        detailGameRatingTextView = findViewById(R.id.detailGameRatingTextView);
        detailGameCommentButton = findViewById(R.id.detailGameCommentButton);
        detailGameCommentEditText = findViewById(R.id.detailGameCommentEditText);

        assignDetailGameData();

        gameDB.close();
        detailGameCommentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if(view == detailGameCommentButton) {
            intent = new Intent(this, HomeActivity.class);

            if(detailGameCommentEditText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Review comment is empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            reviewDB.open();
            reviewDB.insert(authID, gameID, detailGameCommentEditText.getText().toString());
            reviewDB.close();

            Toast.makeText(this, "Review successfully added!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }


    void assignDetailGameData() {
        detailGameName = game.getName();
        detailGameGenre = game.getGenre();
        detailGamePrice = game.getFormattedPrice();
        detailGameDescription = game.getDescription();
        detailGameRating = String.valueOf(game.getRating());

        Picasso.get().load(game.getImage()).into(detailGameImageView);

        detailGameNameTextView.setText(detailGameName);
        detailGameGenreTextView.setText(detailGameGenre);
        detailGamePriceTextView.setText(detailGamePrice);
        detailGameRatingTextView.setText(detailGameRating);
        detailGameDescriptionTextView.setText(detailGameDescription);
    }
}