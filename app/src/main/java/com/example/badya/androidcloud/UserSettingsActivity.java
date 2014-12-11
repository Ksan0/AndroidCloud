package com.example.badya.androidcloud;

import android.app.Activity;
import android.os.Bundle;

import com.example.badya.androidcloud.Fragments.SettingsFragment;

/**
 * Created by Badya on 11/12/14.
 */
public class UserSettingsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().
                replace(android.R.id.content, new SettingsFragment()).
                commit();
    }
}
