package com.example.badya.androidcloud.DBWork;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Ruslan on 22.01.2015.
 */
public class Setting {
    private String setting;
    private String value;
    private long id;

    public Setting(Cursor c) {
        id = c.getLong(c.getColumnIndex(DBHelper.Setting._ID));
        setting = c.getString(c.getColumnIndex(DBHelper.Setting.COLUMN_SETTING));
        value = c.getString(c.getColumnIndex(DBHelper.Setting.COLUMN_VALUE));
    }

    public void setSettingName(String setting) {
        this.setting = setting;
    }

    public String getSettingName() {
        return setting;
    }

    public void setSettingValue(String value) {
        this.value = value;
    }

    public String getSettingValue() {
        return value;
    }
    public ArrayList<Setting> getSettings(DBHelper db, String[] settings) {
        String selection = DBHelper.Setting.COLUMN_SETTING + "=?";

        ArrayList<Setting> arr = new ArrayList<>();
        Cursor c = db.selectSettings(settings);
        if (c == null) return null;
        do {
            arr.add(new Setting(c));
        }
        while (c.moveToNext() != false);
        return arr;
    }

    public long save(DBHelper db) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.Setting.COLUMN_SETTING, setting);
        cv.put(DBHelper.Setting.COLUMN_VALUE, value);
        id = db.replaceOneRow(DBHelper.FileMetaData.TABLE_NAME, cv);
        return id;
    }

    public long delete(DBHelper db){
        if (id < 0)
            return -1;
        return db.deleteOneRow(DBHelper.FileMetaData.TABLE_NAME, DBHelper.FileMetaData._ID + "=" + Long.toString(id), null);
    }
}
