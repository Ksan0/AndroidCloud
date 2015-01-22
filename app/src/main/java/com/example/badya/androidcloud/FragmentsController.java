package com.example.badya.androidcloud;

import android.app.Activity;
import android.app.Fragment;

import com.example.badya.androidcloud.Api.usage.StorageApiFront;

/**
 * Created by Badya on 11/12/14.
 */
public interface FragmentsController {
    public void setFragment(Fragment fragment, boolean addToBackStack);

    public Activity getController();
    public StorageApiFront getApiFront();
}
