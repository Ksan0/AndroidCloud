package com.example.badya.androidcloud.Api.core;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import com.example.badya.androidcloud.Api.usage.StorageApiFront;

import java.lang.reflect.Method;



public class StorageApiService extends IntentService {

    StorageApiBack storageApiBack;

    public StorageApiService(Activity activity) {
        super("StorageApiService");

        storageApiBack = new StorageApiBack(activity);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        StorageApiIntentParams params = intent.getParcelableExtra(StorageApiFront.class.getName());
        Object objectParams[] = params.getParams();

        Class classParams[] = new Class[objectParams.length];
        for (int i = 0; i < objectParams.length; i++) {
            classParams[i] = objectParams[i].getClass();
        }

        Method method;
        try {
            method = storageApiBack.getClass().getMethod(params.getMethodName(), classParams);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }

        Object objectReturn[];
        try {
            objectReturn = (Object[]) method.invoke(storageApiBack, objectParams);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        StorageApiIntentParams paramsReturn = new StorageApiIntentParams();
        paramsReturn.setMethodName(params.getMethodName());
        if (objectReturn != null) {
            paramsReturn.setParams(objectReturn);
        }

        Intent answerIntent = new Intent(StorageApiBack.class.getCanonicalName());
        answerIntent.putExtra(StorageApiBack.class.getName(), paramsReturn);

        sendBroadcast(answerIntent);
    }
}
