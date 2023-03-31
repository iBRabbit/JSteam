package com.google.jsteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.jsteam.GameDetailActivity;
import com.google.jsteam.R;
import com.google.jsteam.model.Game;

import java.util.Vector;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    Context context;
    Vector<Game> gameVector;

    public GameAdapter(Context context) {
        this.context = context;
    }

    public void setGameVector(Vector<Game> gameVector) {
        this.gameVector = gameVector;
    }

    @NonNull
    @Override
    public GameAdapter.GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.GameViewHolder holder, int position) {

        holder.gameNameTextView.setText(gameVector.get(position).getName());
        holder.gameGenreTextView.setText(gameVector.get(position).getGenre());
        holder.gamePriceTextView.setText(gameVector.get(position).getFormattedPrice());

        String imageName = gameVector.get(position).getValidatedImage();

        int imageID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        holder.gameImageView.setImageResource(imageID);

        holder.gameCardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            intent.putExtra("gameID", gameVector.get(position).getID());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return gameVector.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        TextView    gameNameTextView,
                    gamePriceTextView,
                    gameGenreTextView;
        ImageView   gameImageView;
        CardView    gameCardView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameNameTextView = itemView.findViewById(R.id.gameNameTextView);
            gamePriceTextView = itemView.findViewById(R.id.gamePriceTextView);
            gameCardView = itemView.findViewById(R.id.gameCardView);
            gameGenreTextView = itemView.findViewById(R.id.gameGenreTextView);
            gameImageView = itemView.findViewById(R.id.gameImageView);
        }
    }
}
