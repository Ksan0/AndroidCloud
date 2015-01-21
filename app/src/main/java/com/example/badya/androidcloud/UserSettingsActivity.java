package com.example.badya.androidcloud;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.example.badya.androidcloud.Fragments.AuthFragment;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Badya on 11/12/14.
 */
public class UserSettingsActivity extends PreferenceActivity implements FragmentsController {
    private List<WeakReference<Fragment>> fragmentsList = new ArrayList<WeakReference<Fragment>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //recreate all the stuff we lost if needed
        }

    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preferences, target);
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void setFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction = transaction.addToBackStack(null);
        }
        transaction
                .replace(R.xml.preferences, fragment)
                .commitAllowingStateLoss();
    }

    public List<Fragment> getActiveFragments() {
        ArrayList<Fragment> ret = new ArrayList<Fragment>();
        for (WeakReference<Fragment> ref : fragmentsList) {
            Fragment f = ref.get();
            if (f != null && f.isVisible()) {
                ret.add(f);
            }
        }
        return ret;
    }

    @Override
    public Activity getController() {
        return this;
    }

    public void auth(MenuItem item) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.xml.preferences, new AuthFragment()).commitAllowingStateLoss();
    }
}
