package com.google.jsteam.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.jsteam.LoginActivity;
import com.google.jsteam.MainActivity;
import com.google.jsteam.R;
import com.google.jsteam.function.GlobalFunction;
import com.google.jsteam.helper.UserHelper;
import com.google.jsteam.model.User;


public class ProfileFragment extends Fragment implements View.OnClickListener{
    Integer authID;
    UserHelper userDB;
    User user;

    TextView    profileUsernameTextView,
                profileRegionTextView,
                profilePhoneTextView,
                profileEmailTextView;

    Button profileLogoutButton;
    GlobalFunction func = new GlobalFunction();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userDB = new UserHelper(getContext());
        userDB.open();

        if(view == null)
            return null;

        authID = func.getAuthID(func.safeGetContext(getContext()));
        user = userDB.getData("id", authID);

        profileUsernameTextView = view.findViewById(R.id.profileUsernameTextView);
        profileRegionTextView = view.findViewById(R.id.profileRegionTextView);
        profilePhoneTextView = view.findViewById(R.id.profilePhoneTextView);
        profileEmailTextView = view.findViewById(R.id.profileEmailTextView);
        profileLogoutButton = view.findViewById(R.id.profileLogoutButton);
        profileLogoutButton.setOnClickListener(this);

        assignProfileData(user);
        userDB.close();

        Log.i("ProfileFragment", "authID: " + authID);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == profileLogoutButton) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            new AlertDialog.Builder(getContext())
                    .setTitle("Confirm Logout")
                    .setPositiveButton("Logout", (dialog, which) -> {
                        func.setAuthID(func.safeGetContext(getContext()), -1);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .show();
        }
    }

    void assignProfileData(User user) {
        profileUsernameTextView.setText(user.getUsername());
        profileRegionTextView.setText(user.getRegion());
        profilePhoneTextView.setText(user.getPhoneNumber());
        profileEmailTextView.setText(user.getEmail());
    }
}