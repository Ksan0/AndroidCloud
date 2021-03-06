package com.example.badya.androidcloud.Api.storages;


import com.example.badya.androidcloud.DBWork.FileMetaDataDAO;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Storage {

    public static final String EXTERNAL_STORAGE_NAME = "tpcloud_api";

    public static final String STORAGE_DROPBOX = "STORAGE_DROPBOX";
    public static final String STORAGE_YANDEX = "STORAGE_YANDEX";
    public static final String[] STORAGES = {Storage.STORAGE_DROPBOX, Storage.STORAGE_YANDEX};

    public static Storage create(String storageName) {
        if (storageName.equals(STORAGE_DROPBOX))
            return new StorageDropbox();
        if (storageName.equals(STORAGE_YANDEX))
            return new StorageYandex();
        return null;
    }

    public abstract String getHumanReadName();

    public abstract String getAuthUrl();
    public abstract String getAuthRedirectUrl();

    public abstract FileMetaDataDAO getMetadata(String accessToken, String path);
    public abstract boolean getFile(String accessToken, String path, OutputStream stream);
    public abstract boolean putFile(String accessToken, String path, InputStream stream, long streamLength);

}
