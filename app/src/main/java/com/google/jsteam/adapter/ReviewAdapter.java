package com.google.jsteam.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.jsteam.GameDetailActivity;
import com.google.jsteam.HomeActivity;
import com.google.jsteam.R;
import com.google.jsteam.fragment.ReviewFragment;
import com.google.jsteam.function.GlobalFunction;
import com.google.jsteam.helper.GameHelper;
import com.google.jsteam.helper.ReviewHelper;
import com.google.jsteam.helper.UserHelper;
import com.google.jsteam.model.Game;
import com.google.jsteam.model.Review;
import com.google.jsteam.model.User;

import java.util.Vector;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    Context context;
    Vector<Review> reviewVector;
    GlobalFunction func;
    GameHelper gameDB;
    Game game;
    UserHelper userDB;
    User user;
    Integer authID;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public void setReviewVector(Vector<Review> reviewVector) {
        this.reviewVector = reviewVector;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        gameDB = new GameHelper(context);
        userDB = new UserHelper(context);
        func = new GlobalFunction();

        gameDB.open();
        userDB.open();

        authID = func.getAuthID(func.safeGetContext(context));

        user = userDB.getData("id", authID);
        game = gameDB.getGameByID(reviewVector.get(position).getGameID());

        if(game == null){
            Log.d("ReviewAdapter", "onBindViewHolder: game is null");
            return;
        }

        holder.reviewUsernameTextView.setText(user.getUsername());
        holder.reviewGameNameTextView.setText(game.getName());
        holder.reviewReviewTextView.setText(reviewVector.get(position).getReview());

        String imageName = game.getValidatedImage();
        int imageID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        holder.reviewGameImageView.setImageResource(imageID);

        gameDB.close();
        userDB.close();

        holder.reviewCardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            intent.putExtra("gameID", reviewVector.get(position).getID());
            context.startActivity(intent);
        });

        holder.reviewUpdateButton.setOnClickListener(v -> {

            EditText updateReviewEditText = new EditText(context);
            updateReviewEditText.setHint("Update your review...");
            updateReviewEditText.setInputType(InputType.TYPE_CLASS_TEXT);

            new AlertDialog.Builder(context)
                    .setTitle("Update Review")
                    .setView(updateReviewEditText)
                    .setPositiveButton("Update", (dialog, which) -> {
                        String review = updateReviewEditText.getText().toString();
                        if(review.isEmpty()){
                            Toast.makeText(context, "Review cannot be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ReviewHelper reviewDB = new ReviewHelper(context);
                        reviewDB.open();
                        reviewDB.update(reviewVector.get(position).getID(), review);
                        reviewDB.close();

                        Toast.makeText(context, "Review updated", Toast.LENGTH_SHORT).show();
                        refreshFragment();

                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .show();
        });

        holder.reviewDeleteButton.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Delete Review")
                    .setMessage("Are you sure you want to delete this review?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        ReviewHelper reviewDB = new ReviewHelper(context);
                        reviewDB.open();
                        reviewDB.delete(reviewVector.get(position).getID());
                        reviewDB.close();

                        Toast.makeText(context, "Review deleted", Toast.LENGTH_SHORT).show();
                        refreshFragment();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return reviewVector.size();
    }

    public class ReviewViewHolder extends  RecyclerView.ViewHolder{

        TextView    reviewUsernameTextView,
                    reviewGameNameTextView,
                    reviewReviewTextView;

        ImageView   reviewGameImageView;

        Button      reviewUpdateButton,
                    reviewDeleteButton;

        CardView  reviewCardView;


        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewUsernameTextView = itemView.findViewById(R.id.reviewUsernameTextView);
            reviewGameNameTextView = itemView.findViewById(R.id.reviewGameNameTextView);
            reviewReviewTextView = itemView.findViewById(R.id.reviewReviewTextView);
            reviewGameImageView = itemView.findViewById(R.id.reviewGameImageView);
            reviewUpdateButton = itemView.findViewById(R.id.reviewUpdateButton);
            reviewDeleteButton = itemView.findViewById(R.id.reviewDeleteButton);
            reviewCardView = itemView.findViewById(R.id.reviewCardView);
        }
    }

    public void refreshFragment() {
        FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.homeFrameLayout, new ReviewFragment())
                .commit();

    }
}
