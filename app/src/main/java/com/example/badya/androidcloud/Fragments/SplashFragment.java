package com.example.badya.androidcloud.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.badya.androidcloud.FragmentsController;
import com.example.badya.androidcloud.R;

/**
 * Created by Badya on 11/12/14.
 */
public class SplashFragment extends Fragment {
    private FragmentsController fragmentsController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentsController = (FragmentsController) activity;
        } catch (ClassCastException e) {
            Log.w("SplashFragment", "FragmentsController cast exception!" + e.getMessage());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentsController = null;
    }
}
