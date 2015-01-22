package com.example.badya.androidcloud.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.badya.androidcloud.Api.storages.Storage;
import com.example.badya.androidcloud.Api.usage.StorageApiFront;
import com.example.badya.androidcloud.FragmentsController;
import com.example.badya.androidcloud.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Badya on 11/12/14.
 */
public class AuthFragment extends Fragment {
    private FragmentsController fragmentsController;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        StorageApiFront front = new StorageApiFront(fragmentsController.getController());
        webView = (WebView) view.findViewById(R.id.web_view);
        front.oauth2(Storage.STORAGE_DROPBOX, webView);
        return view;
    }

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
        super.onDetach();
    }
}
