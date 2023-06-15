package com.google.jsteam.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.jsteam.R;


public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        Fragment fragment = new MapFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.map_layout,fragment).commit();
//        getParentFragmentManager().beginTransaction().replace(R.id.map_layout,fragment).commit();
        return view;
    }
}