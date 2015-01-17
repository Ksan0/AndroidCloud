package com.example.badya.androidcloud.DBWork;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

public class FileMetadata implements Serializable {
    private long id;
    private String storageName;
    private String name;
    private String storagePath;
    private Integer isDir;
    private long size;
    private String mimeType;
    private String lastModified; // you can use it like hash
    private long parent; // you can use it like hash

    public FileMetadata() {
    }

    public FileMetadata(long id, String storageName, String name, String storagePath, Integer isDir, long size,
                        String mimeType, String lastModified, long parent) {
        this.id = id;
        this.storageName = storageName;
        this.name = name;
        this.storagePath = storagePath;
        this.isDir = isDir;
        this.size = size;
        this.mimeType = mimeType;
        this.lastModified = lastModified;
        this.parent = parent;
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
    }


    public long save(DBHelper db){
        ContentValues cv = new ContentValues();
        if (isDir == null || lastModified == null || name == null || size < 0)
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

        id = db.ReplaceOneRow(DBHelper.FileMetaData.TABLE_NAME, cv);
        return id;
    }

    public long delete(DBHelper db){
        if (id < 0)
            return -1;
        return db.DeleteOneRow(DBHelper.FileMetaData.TABLE_NAME, DBHelper.FileMetaData._ID + "=" + Long.toString(id), null);
    }

    public ArrayList getContainFiles(DBHelper db){
        String[] projection = new String[9];
        projection[0] = DBHelper.FileMetaData._ID;
        projection[1] = DBHelper.FileMetaData.COLUMN_STORAGENAME;
        projection[2] = DBHelper.FileMetaData.COLUMN_STORAGEPATH;
        projection[3] = DBHelper.FileMetaData.COLUMN_NAME;
        projection[4] = DBHelper.FileMetaData.COLUMN_ISDIR;
        projection[5] = DBHelper.FileMetaData.COLUMN_SIZE;
        projection[6] = DBHelper.FileMetaData.COLUMN_MIMETYPE;
        projection[7] = DBHelper.FileMetaData.COLUMN_LASTMODIFIED;
        projection[8] = DBHelper.FileMetaData.COLUMN_PARENT;
        String where = "parent=?";
        String[] whereArgs = {DBHelper.FileMetaData._ID};
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
                DBHelper.FileMetaData.COLUMN_PARENT
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

    public Integer isDir() {
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
}