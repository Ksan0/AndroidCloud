package com.example.badya.androidcloud.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;

import com.example.badya.androidcloud.FragmentsController;

/**
 * Created by Badya on 11/12/14.
 */
public class AuthFragment extends Fragment {

    private FragmentsController fragmentsController;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentsController = (FragmentsController) activity;
        } catch (ClassCastException e) {
            Log.w("AuthFragment", "FragmentsController cast exception!" + e.getMessage());
        }
    }

    @Override
    public void onDetach() {
        fragmentsController = null;
    }
}
