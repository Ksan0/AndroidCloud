package com.example.badya.androidcloud.Api.usage;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.badya.androidcloud.Api.core.StorageApiBack;
import com.example.badya.androidcloud.Api.core.StorageApiIntentParams;
import com.example.badya.androidcloud.Api.core.StorageApiService;
import com.example.badya.androidcloud.Api.storages.Storage;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

public class StorageApiFront {

    Activity activity;
    BroadcastReceiver broadcastReceiver;
    StorageApiCallback storageApiCallback;
    private static final String TAG = "StorageApiFront";

    public StorageApiFront(Activity activity) {
        this.activity = activity;

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                StorageApiIntentParams params = intent.getParcelableExtra(StorageApiBack.class.getName());
                Object objectParams[] = params.getParams();

                Class classParams[] = new Class[objectParams.length];
                for (int i = 0; i < objectParams.length; i++) {
                    classParams[i] = objectParams[i].getClass();
                }

                Method method;
                try {
                    method = storageApiCallback.getClass().getMethod(params.getMethodName(), classParams);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, e.toString());
                    return;
                }

                try {
                    method.invoke(storageApiCallback, objectParams);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        };
        activity.registerReceiver(broadcastReceiver, new IntentFilter(StorageApiBack.class.getCanonicalName()));

        storageApiCallback = new StorageApiCallback(activity);
    }

    public void shutdown() {
        activity.unregisterReceiver(broadcastReceiver);
    }

    private void startService(String methodName, Object objectParams[]) {
        StorageApiIntentParams params = new StorageApiIntentParams();
        params.setMethodName(methodName);
        params.setParams(objectParams);

        Intent intent = new Intent(activity, StorageApiService.class);
        intent.putExtra(StorageApiFront.class.getName(), params);

        activity.startService(intent);
    }


    // *** API METHODS BEGIN ***

    public void oauth2(final String storageName, WebView webView) {
        final Storage oauth2Storage = Storage.create(storageName);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.indexOf(oauth2Storage.getAuthRedirectUrl()) != 0) {
                    return false;  // let's load this url
                }

                HashMap<String, String> getParameters = new HashMap<String, String>();
                try {
                     List<NameValuePair> getParametersPairs = URLEncodedUtils.parse(new URI(url.replace('#', '?')), "UTF-8");
                    for (NameValuePair p: getParametersPairs) {
                        getParameters.put(p.getName(), p.getValue());
                    }
                } catch(Exception e) {
                    Log.e("SigningService", e.toString());
                    return false;
                }

                storageApiCallback.oauth2(storageName, getParameters.get("access_token"));

                return true;
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(oauth2Storage.getAuthUrl());
    }

    public void getMetadata(String storageName, String accessToken, String path) {
        startService("getMetadata", new Object[] {storageName, accessToken, path});
    }

    public void getFile(String storageName, String accessToken, String path) {
        startService("getFile", new Object[] {storageName, accessToken, path});
    }

    public void putFile(String storageName, String accessToken, String putPath, String filePath) {
        startService("putFile", new Object[] {storageName, accessToken, putPath, filePath});
    }

    // *** API METHODS END ***
}
