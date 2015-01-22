package com.example.badya.androidcloud.Api.helpers;


import com.example.badya.androidcloud.Api.storages.Storage;
import com.example.badya.androidcloud.DBWork.FileMetaDataDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


// it isn't copy-paste in code
// because clouds can use different architectures


public class JSONHelper {

    public static String parseGetFileResponseYandex(String str) {
        try {
            JSONObject json = new JSONObject(str);
            return json.getString("href");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static FileMetaDataDAO parseMetadataDropbox(String str) {
        try {
            JSONObject json = new JSONObject(str);
            FileMetaDataDAO file = new FileMetaDataDAO();

            fillFileMetadataDropbox(file, json);

            if (json.has("contents")) {
                JSONArray contentsArray = json.getJSONArray("contents");
                for (int i = 0; i < contentsArray.length(); i++) {
                    JSONObject contentsJson = contentsArray.getJSONObject(i);
                    FileMetaDataDAO contentsFile = new FileMetaDataDAO();
                    fillFileMetadataDropbox(contentsFile, contentsJson);
                    //TODO: file.addContainFile(contentsFile); Теперь нет такого поля
                }
            }

            return file;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static FileMetaDataDAO parseMetadataYandex(String str) {
        try {
            JSONObject json = new JSONObject(str);
            FileMetaDataDAO file = new FileMetaDataDAO();

            fillFileMetadataYandex(file, json);

            if (json.has("_embedded")) {
                JSONObject embedded = json.getJSONObject("_embedded");

                JSONArray contentsArray = embedded.getJSONArray("items");
                for (int i = 0; i < contentsArray.length(); i++) {
                    JSONObject contentsJson = contentsArray.getJSONObject(i);
                    FileMetaDataDAO contentsFile = new FileMetaDataDAO();
                    fillFileMetadataYandex(contentsFile, contentsJson);
                    //TODO: file.addContainFile(contentsFile); Теперь нет такого поля
                }
            }

            return file;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static void fillFileMetadataDropbox(FileMetaDataDAO file, JSONObject json)
            throws JSONException
    {
        file.setStorageName(Storage.STORAGE_DROPBOX);

        file.setStoragePath(json.getString("path"));

        if (json.has("bytes")) {
            file.setSize(json.getLong("bytes"));
        }

        file.setDir(json.getBoolean("is_dir") ? 1 : 0);

        if (json.has("mime_type")) {
            file.setMimeType(json.getString("mime_type"));
        }

        if (json.has("modified")) {
            file.setLastModified(json.getString("modified"));
        }
    }


    private static void fillFileMetadataYandex(FileMetaDataDAO file, JSONObject json)
            throws JSONException
    {
        file.setStorageName(Storage.STORAGE_YANDEX);

        file.setStoragePath(json.getString("path"));

        if (json.has("size")) {
            file.setSize(json.getLong("size"));
        }

        file.setDir(json.getString("type").equals("dir") ? 1 : 0);

        if (json.has("mime_type")) {
            file.setMimeType(json.getString("mime_type"));
        }

        if (json.has("modified")) {
            file.setLastModified(json.getString("modified"));
        }
    }
}
