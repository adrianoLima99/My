package com.br.myprofission;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by LENOVO on 02/08/2016.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    final String tokenPreferenceKey = "fcm_token";

    final static String infoTopicName = "info";

    @Override
    public void onTokenRefresh() {
       // super.onTokenRefresh();
        /* SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(tokenPreferenceKey, FirebaseInstanceId.getInstance().getToken()).apply();

        FirebaseMessaging.getInstance().subscribeToTopic(infoTopicName);*/
        String token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
    }

    private void registerToken(String token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body= new FormBody.Builder()
                .add("Token",token)
                .build();
        Request request = new Request.Builder()
                .url("http://www.seriadosweb.biz/precciso/register.php")
                .post(body)
                .build();
        try{
            client.newCall(request).execute();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
