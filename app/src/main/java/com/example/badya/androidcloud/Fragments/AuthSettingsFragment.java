package com.example.badya.androidcloud.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.example.badya.androidcloud.Fragments.Elements.ItemElement;
import com.example.badya.androidcloud.Fragments.Elements.ItemElementAdapter;
import com.example.badya.androidcloud.FragmentsController;
import com.example.badya.androidcloud.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Badya on 22/01/15.
 */
public class AuthSettingsFragment extends Fragment implements AbsListView.OnItemClickListener {
    private FragmentsController fragmentsController;
    private AbsListView absListView;
    private ListAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        listAdapter = new ItemElementAdapter(getActivity(), getData());
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_settings_list, container, false);

        absListView = (AbsListView) view.findViewById(android.R.id.list); // может быть надо искать по тегу
        absListView.setAdapter(listAdapter);
        absListView.setOnItemClickListener(this);

        return view;
    }

    private ArrayList<ItemElement> getData() {
        ArrayList<ItemElement> itemElements = new ArrayList<ItemElement>();

        //add existing account here

        itemElements.add(new ItemElement(android.R.drawable.btn_plus, "Add account", false));
        return itemElements;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentsController = (FragmentsController) activity;
        } catch (ClassCastException e) {
            Log.w("AuthSettingsFragment", "FragmentsController cast exception!" + e.getMessage());
        }
    }

    @Override
    public void onDetach() {
        fragmentsController = null;
        super.onDetach();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listAdapter.getCount() - 1 == position && fragmentsController != null) {
            fragmentsController.setFragment(new AuthFragment(), true);
        }
    }
}
