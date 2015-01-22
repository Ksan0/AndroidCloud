package com.example.badya.androidcloud.Api.usage;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.badya.androidcloud.DBWork.DBHelper;
import com.example.badya.androidcloud.DBWork.FileMetadata;


public class StorageApiCallback {

    private static final String TAG = "StorageApiCallback";

    private Activity activity;

    public StorageApiCallback(Activity activity) {
        this.activity = activity;
    }

    public void oauth2(String storageName, String accessToken) {

    }

    public void getMetadata(FileMetadata metadata) {

    }

    public void getFile(String storageName, String storagePath, String filePath, String hash) {
        /*Log.d("____path", filePath);
        StringBuilder b = new StringBuilder();

        try {
            FileInputStream stream = new FileInputStream(filePath);

            byte buffer[] = new byte[1024];
            int read;
            while((read = stream.read(buffer)) != -1) {
                b.append(new String(buffer, 0, read));
            }

            Log.d("____d", b.toString());
            stream.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }*/

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
        Cursor c = db.selectFileMetaData(proj, whereClause, whereArgs, null, null, null);
        ContentValues values = new ContentValues();
        values.put(DBHelper.FileMetaData._ID, c.getLong(c.getColumnIndex(DBHelper.FileMetaData._ID)));
        values.put(DBHelper.FileMetaData.COLUMN_MD5, hash);
        db.replaceOneRow(DBHelper.FileMetaData.TABLE_NAME, values);
    }

    public void putFile(String storageName, String webPath, String filePath) {

    }
}
