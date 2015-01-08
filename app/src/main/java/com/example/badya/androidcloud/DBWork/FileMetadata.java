package com.example.badya.androidcloud.DBWork;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

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


    private ArrayList<FileMetadata> containFiles = new ArrayList<FileMetadata>();

    public FileMetadata(Long id, String storageName, String name, String storagePath, Integer isDir, long size,
                        String mimeType, String lastModified) {
        this.id = id;
        this.storageName = storageName;
        this.name = name;
        this.storagePath = storagePath;
        this.isDir = isDir;
        this.size = size;
        this.mimeType = mimeType;
        this.lastModified = lastModified;
    }

    public Long save(DBHelper db){
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

        return db.ReplaceOneRow(DBHelper.FileMetaData.TABLE_NAME, cv);
    }

    public static FileMetadata getFromDB(DBHelper db, String selection, String[] args) {
        String[] projection = {DBHelper.FileMetaData.COLUMN_STORAGENAME,
                DBHelper.FileMetaData.COLUMN_ISDIR,
                DBHelper.FileMetaData.COLUMN_LASTMODIFIED,
                DBHelper.FileMetaData.COLUMN_MIMETYPE,
                DBHelper.FileMetaData.COLUMN_NAME,
                DBHelper.FileMetaData.COLUMN_SIZE,
                DBHelper.FileMetaData.COLUMN_STORAGEPATH};

        Cursor c = db.SelectFileMetaData(projection, selection, args);
        if (c == null)
            return null;

        Long id = c.getLong(c.getColumnIndex(DBHelper.FileMetaData._ID));
        String storage_name = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_STORAGENAME));
        Integer is_dir = c.getInt(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_ISDIR));
        String lastModified = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_LASTMODIFIED));
        String mimeType = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_MIMETYPE));
        String name = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_NAME));
        Long size = c.getLong(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_SIZE));
        String storagePath = c.getString(c.getColumnIndex(DBHelper.FileMetaData.COLUMN_STORAGEPATH));

        return new FileMetadata(id, storage_name, name, storagePath, is_dir, size,  mimeType, lastModified);
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

    public void addContainFile(FileMetadata file) {
        containFiles.add(file);
    }

    public ArrayList<FileMetadata> getContainFiles() {
        return containFiles;
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

    @Override
    public String toString() {
        return name;
    }
}