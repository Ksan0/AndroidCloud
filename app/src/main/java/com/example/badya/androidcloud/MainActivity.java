package com.example.badya.androidcloud;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.badya.androidcloud.Fragments.FileListFragment;
import com.example.badya.androidcloud.Fragments.SplashFragment;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements FragmentsController {
    List<WeakReference<Fragment>> fragmentsList = new ArrayList<WeakReference<Fragment>>();
    public static final String FRAGMENT_FILE_LIST_TAG = "fragment_file_list";
    private FileListFragment fileListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            fileListFragment = (FileListFragment) getFragmentManager().findFragmentByTag(FRAGMENT_FILE_LIST_TAG);
            if (fileListFragment != null) {
                setFragment(fileListFragment, true);
            } else {
                updateFragment();
            }
        } else {
            updateFragment();
        }
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
        if (fragment instanceof FileListFragment) {
            transaction
                    .replace(R.id.activity_main, fragment, FRAGMENT_FILE_LIST_TAG)
                    .commit();
        } else {
            transaction
                    .replace(R.id.activity_main, fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Activity getController() {
        return this;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        fragmentsList.add(new WeakReference<Fragment>(fragment));
    }

    public void updateFragment() {
        // тут бы было круто смотреть есть ли локальный список файлов и сразу открывать его при наличии
        Fragment fragment;
        List<Fragment> frags = getActiveFragments();
        if (frags.isEmpty()) {
            fragment = new SplashFragment();
        } else {
            fragment = frags.get(0);
            frags.remove(0); // ?? а надо ли ??
        }
        setFragment(fragment, true);
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

    public void openSettings(MenuItem item) {
        Intent intent = new Intent(this, UserSettingsActivity.class);
        startActivity(intent);
    }

}
