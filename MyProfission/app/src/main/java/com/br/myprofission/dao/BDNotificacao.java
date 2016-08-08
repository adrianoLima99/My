package com.br.myprofission.dao;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.myprofission.MainActivity;
import com.br.myprofission.util.Utilitaria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by LENOVO on 09/07/2016.
 */
public class BDNotificacao {
    private SQLiteDatabase bd;
    ProgressDialog pDialog;
    public BDNotificacao(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public String notificacao(String token, String emissor, String receptador){


        OkHttpClient client = new OkHttpClient();
        RequestBody body= new FormBody.Builder()
                .add("token",token)
                .add("emissor",emissor)
                .add("receptador",receptador)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://www.seriadosweb.biz/precciso/enviaNotificacao.php")
                .post(body)
                .build();
        try{
            okhttp3.Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code" + response.toString());
            return response.body().string();

        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
