package com.example.badya.androidcloud.Fragments.Elements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.badya.androidcloud.R;

import java.util.ArrayList;

/**
 * Created by Badya on 11/12/14.
 */
public class ItemElementAdapter extends ArrayAdapter<ItemElement> {
    private final Context context;
    private final ArrayList<ItemElement> itemElementArrayList;

    public ItemElementAdapter(Context context, ArrayList<ItemElement> itemElementArrayList) {
        super(context, R.layout.file_item, itemElementArrayList);
        this.context = context;
        this.itemElementArrayList = itemElementArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = null;
        if (!itemElementArrayList.get(position).isGroupHeader()) {
            rowView = inflater.inflate(R.layout.file_item, parent, false);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.item_icon);
            TextView nameView = (TextView) rowView.findViewById(R.id.item_name);

            imageView.setImageResource(itemElementArrayList.get(position).getIcon());
            nameView.setText(itemElementArrayList.get(position).getName());

        } else {
            rowView = inflater.inflate(R.layout.group_header, parent, false);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.cloud_icon);
            TextView nameView = (TextView) rowView.findViewById(R.id.cloud_name);

        }
        return rowView;
    }
}
