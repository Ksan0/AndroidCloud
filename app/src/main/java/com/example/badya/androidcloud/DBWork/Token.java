package com.example.badya.androidcloud.DBWork;

import android.database.Cursor;

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

    Token getTokenFromDB(DBHelper db, String storageName) {
        String[] proj = {
            DBHelper.Token._ID,
            DBHelper.Token.COLUMN_TOKEN,
            DBHelper.Token.COLUMN_STORAGE_NAME
        };
        String selection = DBHelper.Token.COLUMN_STORAGE_NAME + "=?";
        String[] args = {storageName};
        return new Token(db.selectToken(proj, selection, args));
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
