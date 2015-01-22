package com.example.badya.androidcloud.DBWork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Ruslan on 07.01.2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "AndroidCloud.db";
    final static int DB_VER = 1;

    private static final String TEXT_TYPE_NOT_NULL = " text not null";
    private static final String INTEGER_TYPE_NOT_NULL = " integer";
    private static final String COMMA_SEP= ", ";
    private static final String TAG = "DBHelper";

    public static abstract class FileMetaData implements BaseColumns {
        public static final String TABLE_NAME = "FileMetaData";
        public static final String COLUMN_STORAGENAME = "storage_name";
        public static final String COLUMN_STORAGEPATH = "storage_path";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ISDIR = "is_dir";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_MIMETYPE = "mime_type";
        public static final String COLUMN_LASTMODIFIED = "last_modified";
        public static final String COLUMN_PARENT = "parent";
        public static final String COLUMN_MD5 = "md5";
    }

    public static abstract class Token implements BaseColumns {
        public static final String TABLE_NAME= "Token";
        public static final String COLUMN_STORAGE_NAME = "storage_name";
        public static final String COLUMN_TOKEN = "token";
    }

    public static abstract class Setting implements BaseColumns {
        public static final String TABLE_NAME= "Settings";
        public static final String COLUMN_SETTING = "setting";
        public static final String COLUMN_VALUE = "value";
    }

    private static final String CREATE_TABLE_FILEMETADATA = "create table "
            + FileMetaData.TABLE_NAME + "("
            + FileMetaData._ID + " integer primary key autoincrement, "
            + FileMetaData.COLUMN_STORAGENAME + TEXT_TYPE_NOT_NULL + COMMA_SEP
            + FileMetaData.COLUMN_STORAGEPATH + TEXT_TYPE_NOT_NULL + COMMA_SEP
            + FileMetaData.COLUMN_NAME + TEXT_TYPE_NOT_NULL + COMMA_SEP
            + FileMetaData.COLUMN_ISDIR + INTEGER_TYPE_NOT_NULL + COMMA_SEP
            + FileMetaData.COLUMN_SIZE + INTEGER_TYPE_NOT_NULL + COMMA_SEP
            + FileMetaData.COLUMN_MIMETYPE + TEXT_TYPE_NOT_NULL + COMMA_SEP
            + FileMetaData.COLUMN_LASTMODIFIED + TEXT_TYPE_NOT_NULL + COMMA_SEP
            + FileMetaData.COLUMN_PARENT + INTEGER_TYPE_NOT_NULL
            + FileMetaData.COLUMN_MD5 + TEXT_TYPE_NOT_NULL
            + ");";

    private static final String CREATE_TABLE_TOKEN =  "create table "
                + Token.TABLE_NAME + "("
                + Token._ID + " integer primary key autoincrement, "
                + Token.COLUMN_STORAGE_NAME + TEXT_TYPE_NOT_NULL + COMMA_SEP
                + Token.COLUMN_TOKEN + TEXT_TYPE_NOT_NULL
                + ");";

    private static final String CREATE_TABLE_SETTING =  "create table "
            + Setting.TABLE_NAME + "("
            + Setting._ID + " integer primary key autoincrement, "
            + Setting.COLUMN_SETTING + TEXT_TYPE_NOT_NULL + COMMA_SEP
            + Setting.COLUMN_VALUE + TEXT_TYPE_NOT_NULL
            + ");";

    private static final String DROP_TABLE_FILEMETADATA =  "drop table if exists "
            + FileMetaData.TABLE_NAME;

    private static final String DROP_TABLE_TOKEN =  "drop table if exists " + Token.TABLE_NAME;

    private static final String DROP_TABLE_SETTING =  "drop table if exists " + Setting.TABLE_NAME;

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory,
                    int version, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, version, errorHandler);
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DROP_TABLE_FILEMETADATA);
        db.execSQL(DROP_TABLE_TOKEN);
        db.execSQL(DROP_TABLE_SETTING);
        db.execSQL(CREATE_TABLE_FILEMETADATA);
        db.execSQL(CREATE_TABLE_TOKEN);
        db.execSQL(CREATE_TABLE_SETTING);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    protected long insertOneRow(String table_name, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ret;
        try {
            ret =  db.insert(table_name, null, values);
        } catch (SQLiteException e) {
            Log.e(TAG, e.toString());
            db.close();
            return -1;
        }
        db.close();
        return ret;
    }

    public long replaceOneRow(String table_name, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ret;
        try {
            ret =  db.replace(table_name, null, values);
        } catch (SQLiteException e) {
            Log.e(TAG, e.toString());
            db.close();
            return -1;
        }
        db.close();
        return ret;
    }

    protected int deleteOneRow(String table_name, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        int ret;
        try {
            ret =  db.delete(table_name, whereClause, whereArgs);
        } catch (SQLiteException e) {
            Log.e(TAG, e.toString());
            db.close();
            return -1;
        }
        db.close();
        return ret;
    }

    public Cursor selectFileMetaData(String[] projection, String whereClause, String[] whereArgs,
                                     String groupBy, String having, String orderBy){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = null;
        try {
             c = db.query(
                    FileMetaData.TABLE_NAME,              // The table to query
                    projection,                           // The columns to return
                    whereClause,                          // The columns for the WHERE clause
                    whereArgs,                            // The values for the WHERE clause
                    groupBy,                              // group the rows
                    having,                               // don't filter by row groups
                    orderBy                               // The sort order
            );
        } catch (SQLiteException e) {
            Log.e(TAG, e.toString());
        }
        finally {
            db.close();
            return c;
        }
    }

    public Cursor selectTokens(String[] storages){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = null;
        try {
            String where = "(";
            for (int i = 0; i < storages.length - 1; i++) {
                where += storages[i] + ", ";
            }
            where += storages[storages.length - 1] + ")";
            c = db.rawQuery("SELECT * FROM " + DBHelper.Token.TABLE_NAME + " WHERE " +
                    Token.COLUMN_STORAGE_NAME + " IN " + where, null);
        } catch (SQLiteException e) {
            Log.e(TAG, e.toString());
        }
        finally {
            db.close();
            return c;
        }
    }

    public Cursor selectSettings(String[] settings) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = null;
        try {
            String where = "(";
            for (int i = 0; i < settings.length - 1; i++) {
                where += settings[i] + ", ";
            }
            where += settings[settings.length - 1] + ")";
            c = db.rawQuery("SELECT * FROM " + Setting.TABLE_NAME + " WHERE " +
                    Setting.COLUMN_SETTING + " IN " + where, null);
        } catch (SQLiteException e) {
            Log.e(TAG, e.toString());
        }
        finally {
            db.close();
            return c;
        }
    }
}
