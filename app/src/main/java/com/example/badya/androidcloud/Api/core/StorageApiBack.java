package com.example.badya.androidcloud.Api.core;


import android.os.Environment;
import android.util.Log;

import com.example.badya.androidcloud.Api.storages.Storage;
import com.example.badya.androidcloud.DBWork.FileMetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class StorageApiBack {
    private static final String TAG = "StorageApiBack";

    public StorageApiBack() {
    }

    public Object[] getMetadata(String storageName, String accessToken, String path) {
        Storage storage = Storage.create(storageName);

        FileMetadata metadata = storage.getMetadata(accessToken, path);

        return new Object[] {metadata};
    }

    public Object[] getFile(String storageName, String accessToken, String storagePath) {
        Storage storage = Storage.create(storageName);

        String pathElems[] = storagePath.split(File.separator);
        String currentPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Storage.EXTERNAL_STORAGE_NAME + "/" + storage.getHumanReadName();

        for (int i = 0; i < pathElems.length; i++) {
            if (pathElems[i].isEmpty())
                continue;

            currentPath += "/" + pathElems[i];
            File f = new File(currentPath);
            if (i < pathElems.length - 1 && !f.isDirectory()) {
                if (!f.mkdir()) {
                    Log.e("tpcloud_api", "cannot create dir " + f.getAbsolutePath());
                }
            }
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(currentPath);
            boolean resStatus = storage.getFile(accessToken, storagePath, fileOutputStream);
            fileOutputStream.close();

            if (resStatus) {
                String hash = FileMetadata.getMD5(currentPath);
                return new Object[]{storageName, storagePath, currentPath, hash};
            }
        } catch (Exception e) {
           Log.e(TAG, e.toString());
        }

        return new Object[] {storageName, null, null, null};
    }

    public Object[] putFile(String storageName, String accessToken, String putPath, String filePath) {
        Storage storage = Storage.create(storageName);

        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            boolean resStatus = storage.putFile(accessToken, putPath, fileInputStream, file.length());
            fileInputStream.close();

            if (resStatus) {
                return new Object[]{storageName, putPath, filePath};
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return new Object[] {storageName, null, null};
    }

}
