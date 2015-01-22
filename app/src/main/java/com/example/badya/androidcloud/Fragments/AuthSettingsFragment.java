package com.example.badya.androidcloud.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.badya.androidcloud.Api.storages.Storage;
import com.example.badya.androidcloud.DBWork.DBHelper;
import com.example.badya.androidcloud.DBWork.Token;
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
        listAdapter = new ItemElementAdapter(fragmentsController.getController(), getAuthList());
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_list, container, false);

        absListView = (AbsListView) view.findViewById(android.R.id.list);
        absListView.setAdapter(listAdapter);
        absListView.setOnItemClickListener(this);

        return view;
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
            showCreateDialog();

        }
    }

    private void showCreateDialog(){
        final Dialog dialog = new Dialog(fragmentsController.getController());
        LayoutInflater inflater = fragmentsController.getController().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_list, null);
        ListView listView = (ListView)view.findViewById(android.R.id.list);
        ItemElementAdapter adapter = new ItemElementAdapter(fragmentsController.getController(), getAvailableServices());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemElement clicked = (ItemElement) parent.getItemAtPosition(position);
                dialog.hide();
                fragmentsController.setFragment(new AuthFragment(clicked.getName()), true);
            }
        });
        dialog.setContentView(view);
        dialog.show();

    }

    private ArrayList<ItemElement> getAvailableServices() {
        ArrayList<ItemElement> itemElements = new ArrayList<ItemElement>();
        itemElements.add(new ItemElement(android.R.drawable.sym_def_app_icon, "dropbox", false));
        itemElements.add(new ItemElement(android.R.drawable.sym_def_app_icon, "yandex", false));
        return itemElements;
    }

    private ArrayList<ItemElement> getAuthList() {
        ArrayList<ItemElement> itemElements = new ArrayList<ItemElement>();

        String[] storages = {Storage.STORAGE_DROPBOX, Storage.STORAGE_YANDEX};
        DBHelper db = new DBHelper(getActivity());
        ArrayList<Token> tokens = new Token().getFromDB(db, storages);
        for (Token token : tokens) {
            itemElements.add(new ItemElement(android.R.drawable.sym_def_app_icon, token.getStorageName(), false));
        }

        itemElements.add(new ItemElement(android.R.drawable.btn_plus, "Add account", false));
        return itemElements;
    }
}
