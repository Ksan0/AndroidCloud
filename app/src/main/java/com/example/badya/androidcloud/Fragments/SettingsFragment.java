package com.example.badya.androidcloud.Fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.badya.androidcloud.R;

/**
 * Created by Badya on 11/12/14.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
