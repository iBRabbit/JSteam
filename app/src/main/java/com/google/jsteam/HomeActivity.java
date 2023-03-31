package com.google.jsteam;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.jsteam.fragment.AboutFragment;
import com.google.jsteam.fragment.GameFragment;
import com.google.jsteam.fragment.ProfileFragment;
import com.google.jsteam.fragment.ReviewFragment;
import com.google.jsteam.function.GlobalFunction;

public class HomeActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    GlobalFunction func = new GlobalFunction();

    Boolean isAppPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        frameLayout = findViewById(R.id.homeFrameLayout);
        bottomNavigationView = findViewById(R.id.homeBottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.game)
                    return openFragment(new GameFragment());

                if(item.getItemId() == R.id.profile)
                    return openFragment(new ProfileFragment());

                if(item.getItemId() == R.id.review)
                    return openFragment(new ReviewFragment());

                if(item.getItemId() == R.id.about)
                    return openFragment(new AboutFragment());

                return false;
            }
        });
        openFragment(new GameFragment());
    }

    @Override
    protected void onPause() {
        super.onPause();
        isAppPaused = true;
        Log.i("HomeActivity", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isAppPaused) {
            func.authCheck(this);
            isAppPaused = false;
        }

        Log.i("HomeActivity", "onResume");
    }

    Boolean openFragment(Fragment fragment) {
        if(fragment == null)
            return false;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFrameLayout, fragment)
                .commit();

        return true;
    }

}