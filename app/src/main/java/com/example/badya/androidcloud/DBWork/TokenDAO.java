package com.example.badya.androidcloud.DBWork;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.badya.androidcloud.Api.storages.Storage;

import java.util.ArrayList;

/**
 * Created by Ruslan on 22.01.2015.
 */
public class TokenDAO implements DAO {
    private long id;
    private String storageName;
    private String token;

    public TokenDAO() {
    }

    public TokenDAO(String storageName, String token) {
        this.storageName = storageName;
        this.token = token;
    }

    public TokenDAO(Cursor c) {
        id = c.getLong(c.getColumnIndex(DBHelper.FileMetaData._ID));
        storageName = c.getString(c.getColumnIndex(DBHelper.Token.COLUMN_STORAGE_NAME));
        token = c.getString(c.getColumnIndex(DBHelper.Token.COLUMN_TOKEN));
    }

    public static ArrayList<TokenDAO> getTokensList(DBHelper db, String[] storageNames) {
        return db.selectTokens(storageNames);
    }

    public long save(DBHelper db) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.Token._ID, id);
        cv.put(DBHelper.Token.COLUMN_STORAGE_NAME, storageName);
        cv.put(DBHelper.Token.COLUMN_TOKEN, token);
        id = db.replaceOneRow(DBHelper.Token.TABLE_NAME, cv);
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

    public long delete(DBHelper db){
        return db.deleteOneRow(DBHelper.Token.TABLE_NAME, DBHelper.Token.COLUMN_TOKEN + "=?", new String[] {getToken()});
        //return db.deleteOneRow(DBHelper.FileMetaData.TABLE_NAME, DBHelper.FileMetaData._ID + "=" + Long.toString(id), null);
    }
}
