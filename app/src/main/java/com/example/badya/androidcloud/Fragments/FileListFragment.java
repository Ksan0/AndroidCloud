package com.example.badya.androidcloud.Fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.example.badya.androidcloud.Api.storages.Storage;
import com.example.badya.androidcloud.Api.usage.StorageApiFront;
import com.example.badya.androidcloud.DBWork.DBHelper;
import com.example.badya.androidcloud.DBWork.FileMetaDataDAO;
import com.example.badya.androidcloud.DBWork.TokenDAO;
import com.example.badya.androidcloud.Fragments.Elements.ItemElement;
import com.example.badya.androidcloud.Fragments.Elements.ItemElementAdapter;
import com.example.badya.androidcloud.FragmentsController;
import com.example.badya.androidcloud.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Badya on 11/12/14.
 */
public class FileListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private FragmentsController fragmentsController;

    private AbsListView absListView;
    private ListAdapter listAdapter;
    private DBHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        db = new DBHelper(fragmentsController.getController());
        downloadMetaData("");
        listAdapter = new ItemElementAdapter(fragmentsController.getController(), getFiles());

    }

    private void downloadMetaData(String path) {
        ArrayList<TokenDAO> tokens = TokenDAO.getTokensList(db, Storage.STORAGES);
        StorageApiFront front = fragmentsController.getApiFront();
        if (front != null) {
            for (TokenDAO token : tokens) {
                front.getMetadata(token.getStorageName(), token.getToken(), path);
            }
        }
    }

    private void getFiles() {
        pushArrayList<ItemElement>
        String[] args = {"0"};
        ArrayList<FileMetaDataDAO> metas = FileMetaDataDAO.getFromDB(db, DBHelper.FileMetaData.COLUMN_PARENT + "=? ", args);
        for (FileMetaDataDAO file: metas) {

        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);

        absListView = (AbsListView) view.findViewById(android.R.id.list); // может быть надо искать по тегу
        absListView.setAdapter(listAdapter);
        absListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
