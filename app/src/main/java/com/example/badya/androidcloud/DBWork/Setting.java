package com.example.badya.androidcloud.DBWork;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruslan on 22.01.2015.
 */
public class Setting {
    public static final HashMap listOptions = new HashMap();
    private final String setting;
    private final String value;

    public Setting(Cursor c) {
        setting = c.getString(c.getColumnIndex(DBHelper.Settings.COLUMN_SETTING));
        value = c.getString(c.getColumnIndex(DBHelper.Settings.COLUMN_VALUE));
    }

    public void setSetting(DBHelper db, String setting, Object value) {

    }

    public ArrayList<Setting> getSetting(DBHelper db, String[] settings) {
        String selection = DBHelper.Settings.COLUMN_SETTING + "=?";

        ArrayList<Setting> arr = new ArrayList<>();
        Cursor c = db.selectSettings(settings);
        do {
            arr.add(new Setting(c));
        }
        while (c.moveToNext() != false);
        return arr;
    }
}
