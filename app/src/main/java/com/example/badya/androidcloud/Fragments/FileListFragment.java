package com.example.badya.androidcloud.Fragments;

import android.app.Activity;
import android.app.ListFragment;
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
 * Created by Badya on 11/12/14.
 */
public class FileListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private FragmentsController fragmentsController;

    private AbsListView absListView;
    private ListAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (fragmentsController != null) {
            listAdapter = new ItemElementAdapter(fragmentsController.getController(), getData());
        }
    }

    private ArrayList<ItemElement> getData() {
        ArrayList<ItemElement> itemElements = new ArrayList<ItemElement>();
        itemElements.add(new ItemElement(1, "Some name", true)); //Cloud name here with icon
        itemElements.add(new ItemElement(2, "File1", false));
        // Дергаем список запихуем и выводим, возможно добавим еще какой фигни
        return itemElements;
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
        try {
            fragmentsController = (FragmentsController) activity;
        } catch (ClassCastException e) {
            Log.w("FileListFragment", "FragmentsController cast exception!" + e.getMessage());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentsController = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (fragmentsController != null) {
            //тут надо писать логику обработки выбора файла из списка
        }
    }
}
