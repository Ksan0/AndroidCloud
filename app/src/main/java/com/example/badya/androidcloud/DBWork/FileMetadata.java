package com.example.badya.androidcloud.DBWork;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileMetadata implements Serializable {
    private long id;
    private String storageName;
    private String name;
    private String storagePath;
    private Integer isDir;
    private long size;
    private String mimeType;
    private String lastModified;
    private long parent;
    private String md5;

    private static final String DATE_FORMAT =   "EEE MMM dd HH:mm:ss 'GMT' yyyy"; // UTC time
    private static final String TAG = "FileMetadata";

    public FileMetadata() {
    }

    public FileMetadata(long id, String storageName, String name, String storagePath, Integer isDir, long size,
                        String mimeType, String lastModified, long parent, String md5) {
        this.id = id;
        this.storageName = storageName;
        this.name = name;
        this.storagePath = storagePath;
        this.isDir = isDir;
        this.size = size;
        this.mimeType = mimeType;
        this.lastModified = lastModified;
        this.parent = parent;
        this.md5 = md5;
    }

    public FileMetadata(Cursor c) {
        if (c == null)
            return;
        id = c.getLong(c.getColumnIndex(DBHelper.FileMetaData._ID));
        storageName = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_STORAGENAME));
        isDir = c.getInt(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_ISDIR));
        lastModified = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_LASTMODIFIED));
        mimeType = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_MIMETYPE));
        name = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_NAME));
        size = c.getLong(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_SIZE));
        storagePath = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_STORAGEPATH));
        parent = c.getLong(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_PARENT));
        md5 = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_STORAGEPATH));
    }

    public long save(DBHelper db){
        ContentValues cv = new ContentValues();
        if (mimeType == null || isDir == null || lastModified == null || name == null || size < 0)
            return (long) -1;

        cv.put(DBHelper.FileMetaData._ID, id);
        cv.put(DBHelper.FileMetaData.COLUMN_STORAGENAME, storageName);
        cv.put(DBHelper.FileMetaData.COLUMN_ISDIR, isDir);
        cv.put(DBHelper.FileMetaData.COLUMN_LASTMODIFIED, lastModified);
        cv.put(DBHelper.FileMetaData.COLUMN_MIMETYPE, mimeType);
        cv.put(DBHelper.FileMetaData.COLUMN_NAME, name);
        cv.put(DBHelper.FileMetaData.COLUMN_SIZE, size);
        cv.put(DBHelper.FileMetaData.COLUMN_STORAGEPATH, storagePath);
        cv.put(DBHelper.FileMetaData.COLUMN_PARENT, storagePath);
        cv.put(DBHelper.FileMetaData.COLUMN_MD5, md5);

        id = db.ReplaceOneRow(DBHelper.FileMetaData.TABLE_NAME, cv);
        return id;
    }

    public long delete(DBHelper db){
        if (id < 0)
            return -1;
        return db.DeleteOneRow(DBHelper.FileMetaData.TABLE_NAME, DBHelper.FileMetaData._ID + "=" + Long.toString(id), null);
    }

    public static String getMD5(String path_to_file) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.toString());
            return null;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path_to_file);
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        byte[] dataBytes = new byte[1024];

        int nread = 0;
        try {
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        ;
        byte[] mdbytes = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static Date parseDate(String date, String format) throws ParseException {
        if (date == null || date.length() == 0) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    public ArrayList getContainFiles(DBHelper db){
        String[] projection = {
                DBHelper.FileMetaData._ID,
                DBHelper.FileMetaData.COLUMN_STORAGENAME,
                DBHelper.FileMetaData.COLUMN_STORAGEPATH,
                DBHelper.FileMetaData.COLUMN_NAME,
                DBHelper.FileMetaData.COLUMN_ISDIR,
                DBHelper.FileMetaData.COLUMN_SIZE,
                DBHelper.FileMetaData.COLUMN_MIMETYPE,
                DBHelper.FileMetaData.COLUMN_LASTMODIFIED,
                DBHelper.FileMetaData.COLUMN_PARENT,
                DBHelper.FileMetaData.COLUMN_MD5
        };
        String where = "parent=?";
        String[] whereArgs = {Long.toString(this.id)};
        Cursor c = db.SelectFileMetaData(projection, where, whereArgs, null, null, null);
        ArrayList containFiles = new ArrayList();
        do {
            containFiles.add(new FileMetadata(c));
        }
        while (c.moveToNext() != false);
        return containFiles;
    }

    public static FileMetadata getFromDB(DBHelper db, String selection, String[] args) {
        String[] projection = {DBHelper.FileMetaData.COLUMN_STORAGENAME,
                DBHelper.FileMetaData.COLUMN_ISDIR,
                DBHelper.FileMetaData.COLUMN_LASTMODIFIED,
                DBHelper.FileMetaData.COLUMN_MIMETYPE,
                DBHelper.FileMetaData.COLUMN_NAME,
                DBHelper.FileMetaData.COLUMN_SIZE,
                DBHelper.FileMetaData.COLUMN_STORAGEPATH,
                DBHelper.FileMetaData.COLUMN_PARENT,
                DBHelper.FileMetaData.COLUMN_MD5
        };

        Cursor c = db.SelectFileMetaData(projection, selection, args, null, null, null);
        return new FileMetadata(c);
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStoragePath(String path) {
        storagePath = path;
        int pos = path.lastIndexOf('/');
        name = path.substring(pos+1);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setDir(Integer isDir) {
        this.isDir = isDir;
    }

    public int isDir() {
        return isDir;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public long getId() {
        return id;
    }

    public String getMd5() {
        return md5;
    }

    public void SetMd5(String MD5) {
        md5 = MD5;
    }
}