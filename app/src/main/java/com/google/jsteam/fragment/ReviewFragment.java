package com.google.jsteam.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.jsteam.R;
import com.google.jsteam.adapter.ReviewAdapter;
import com.google.jsteam.function.GlobalFunction;
import com.google.jsteam.helper.ReviewHelper;
import com.google.jsteam.model.Review;

import java.util.Vector;

public class ReviewFragment extends Fragment {

    Review review;
    ReviewHelper reviewDB;
    Vector <Review> reviewVector;
    ReviewAdapter reviewAdapter;
    RecyclerView reviewRecyclerView;
    GlobalFunction func = new GlobalFunction();
    Integer authID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        if(view == null)
            return null;

        reviewRecyclerView = view.findViewById(R.id.reviewRecyclerView);
        reviewDB = new ReviewHelper(getContext());
        reviewDB.open();

        authID = func.getAuthID(func.safeGetContext(getContext()));

        reviewVector = new Vector<>();
        reviewVector = reviewDB.getAndZipAllDataByUserID(authID);

        reviewAdapter = new ReviewAdapter(getContext());
        reviewAdapter.setReviewVector(reviewVector);
        reviewRecyclerView.setAdapter(reviewAdapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reviewDB.close();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reviewDB.open();
        reviewVector = new Vector<>();
        reviewVector = reviewDB.getAndZipAllDataByUserID(authID);

        reviewAdapter = new ReviewAdapter(getContext());
        reviewAdapter.setReviewVector(reviewVector);
        reviewRecyclerView.setAdapter(reviewAdapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewDB.close();
    }

    void printAllReviewInLog(){
        reviewDB.open();
        reviewVector = reviewDB.getAndZipAllDataByUserID(authID);
        reviewDB.close();

        for(int i = 0; i < reviewVector.size(); i++) {
            Log.i("ReviewFragment","====================");
            Log.i("ReviewFragment", "Review ID: " + reviewVector.get(i).getID());
            Log.i("ReviewFragment", "Review User ID: " + reviewVector.get(i).getUserID());
            Log.i("ReviewFragment", "Review Game ID: " + reviewVector.get(i).getGameID());
            Log.i("ReviewFragment","====================");
        }
    }
}