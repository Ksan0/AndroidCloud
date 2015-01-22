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
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.file_item, null, true);
            holder = new ViewHolder();
            holder.imageView = (ImageView) rowView.findViewById(R.id.item_icon);
            holder.nameView = (TextView) rowView.findViewById(R.id.item_name);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.nameView.setText(itemElementArrayList.get(position).getName());
        holder.imageView.setImageResource(itemElementArrayList.get(position).getIcon());

        return rowView;
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView nameView;
    }

}
