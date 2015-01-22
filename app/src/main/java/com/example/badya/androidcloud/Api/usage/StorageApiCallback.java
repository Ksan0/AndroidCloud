package com.example.badya.androidcloud.Api.usage;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.badya.androidcloud.DBWork.DBHelper;
import com.example.badya.androidcloud.DBWork.FileMetadata;
import com.example.badya.androidcloud.DBWork.Token;
import com.example.badya.androidcloud.Fragments.FileListFragment;
import com.example.badya.androidcloud.Fragments.SplashFragment;
import com.example.badya.androidcloud.FragmentsController;

import java.util.ArrayList;


public class StorageApiCallback {

    private static final String TAG = "StorageApiCallback";

    private Activity activity;

    public StorageApiCallback(Activity activity) {
        this.activity = activity;
    }

    public void oauth2(String storageName, String accessToken) {
        DBHelper db = new DBHelper(activity);
        Token token = new Token(storageName, accessToken);
        token.save(db);
        FragmentsController controller = (FragmentsController) activity;
        controller.setFragment(new SplashFragment(), false);
    }

    public void getMetadata(FileMetadata metadata) {
        DBHelper db = new DBHelper(activity);
        metadata.save(db);
    }

    public void getFile(String storageName, String storagePath, String filePath, String hash) {
        DBHelper db = new DBHelper(activity);
        String[] proj = {
                DBHelper.FileMetaData._ID,
                DBHelper.FileMetaData.COLUMN_MD5
        };
        String whereClause = DBHelper.FileMetaData.COLUMN_STORAGEPATH + "=? and "
                + DBHelper.FileMetaData.COLUMN_STORAGENAME + "=?";
        String[] whereArgs = {
                storagePath,
                storageName
        };
        ArrayList<FileMetadata> files = db.selectFileMetaData(proj, whereClause, whereArgs, null, null, null);
        if (files != null && !files.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(DBHelper.FileMetaData._ID, files.get(0).getId());
            values.put(DBHelper.FileMetaData.COLUMN_MD5, hash);
            db.replaceOneRow(DBHelper.FileMetaData.TABLE_NAME, values);
        }
    }

    public void putFile(String storageName, String webPath, String filePath) {

    }
}
