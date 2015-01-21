package com.example.badya.androidcloud.DBWork;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Ruslan on 22.01.2015.
 */
public class Token {
    private long id;
    private String storageName;
    private String token;

    public Token() {
    }

    public Token(long id, String storageName, String token) {
        this.storageName = storageName;
        this.token = token;
    }

    public Token(Cursor c) {
        id = c.getLong(c.getColumnIndex(DBHelper.FileMetaData._ID));
        storageName = c.getString(c.getColumnIndex(DBHelper.Token.COLUMN_STORAGE_NAME));
        token = c.getString(c.getColumnIndex(DBHelper.Token.COLUMN_TOKEN));
    }

    public ArrayList<Token> getTokensFromDB(DBHelper db, String[] storageNames) {

        ArrayList<Token> arr = new ArrayList<>();

        Cursor c = db.selectTokens(storageNames);
        if (c == null) return null;

        do {
            arr.add(new Token(c));
        } while (c.moveToNext() != false);

        return arr;
    }

    public long save(DBHelper db) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.Token._ID, id);
        cv.put(DBHelper.Token.COLUMN_STORAGE_NAME, storageName);
        cv.put(DBHelper.Token.COLUMN_TOKEN, token);
        id = db.replaceOneRow(DBHelper.FileMetaData.TABLE_NAME, cv);
        return id;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }
}
