package com.example.badya.androidcloud.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.badya.androidcloud.Api.storages.Storage;
import com.example.badya.androidcloud.DBWork.DBHelper;
import com.example.badya.androidcloud.DBWork.TokenDAO;
import com.example.badya.androidcloud.FragmentsController;
import com.example.badya.androidcloud.R;

/**
 * Created by Badya on 11/12/14.
 */
public class SplashFragment extends Fragment {
    private FragmentsController fragmentsController;
    DBHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        db = new DBHelper(fragmentsController.getController());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_splash, container, false);
        if (!TokenDAO.getTokensList(db, Storage.STORAGES).isEmpty()) {
            fragmentsController.setFragment(new FileListFragment(), false);
        }
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
