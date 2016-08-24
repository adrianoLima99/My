package com.br.myprofission.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.myprofission.util.Utilitaria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LENOVO on 09/07/2016.
 */
public class BDExperiencia {
    private SQLiteDatabase bd;

    public BDExperiencia(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public List<Experiencia> buscar() {

        List<Experiencia> list = new ArrayList<Experiencia>();
        String[] colunas = new String[] { "_id", "empresa", "inicio","terminio","obs"};
       // String where = condicao ;
        Cursor cursor = bd.query("experiencia", colunas,null, null, null, null, "empresa");
        try{
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Experiencia e = new Experiencia();
                e.setId(cursor.getLong(0));
                e.setEmpresa(cursor.getString(1));
                e.setInicio(cursor.getString(2));
                e.setTerminio(cursor.getString(3));
                e.setObs(cursor.getString(4));
                list.add(e);

            } while (cursor.moveToNext());
        }
        }finally {
            cursor.close();
        }
        //bd.close();
        return (list);
    }

    public void inserir(Experiencia e) {
		ContentValues valores = new ContentValues();
		valores.put("empresa", e.getEmpresa());
        valores.put("inicio", e.getInicio());
        valores.put("terminio", e.getTerminio());
        valores.put("obs", e.getObs());

        bd.insert("experiencia", null, valores);
	}

    public void atualizar(Experiencia e) {
        ContentValues valores = new ContentValues();
        valores.put("empresa", e.getEmpresa());
        valores.put("inicio", e.getInicio());
        valores.put("terminio", e.getTerminio());
        valores.put("obs", e.getObs());

        bd.update("experiencia", valores, "_id = ?", new String[] { "" + e.getId() });


    }
    public void deletar() {
		bd.delete("experiencia", null, null);
	}


    public  void fecharConexao(){
        bd.close();
    }

    public void salvaServidor(final Context context, final String empresa, final String inicio, final String terminio ){


        String url="http://www.seriadosweb.biz/precciso/controller/controllerExperiencia.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.i("response",response);
                        if(response.trim().equals("nao")) {
                            Toast.makeText(context, "Operação falhou!", Toast.LENGTH_SHORT).show();

                        } else {
                            //Utilitaria.sharedPrefernces(context,Utilitaria.removerEspacoVazio(response));
                            Toast.makeText(context, "resposta =" + response.trim(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "erro="+ error, Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("acao","inserir");//vai para metodo q verifica se o usuario ja possui registro e ja tem creditos criados , assim em vez de cria uma nova tupla ele apenas atualiza a existente
                params.put("empresa",empresa);
                params.put("inicio",inicio);
                params.put("terminio",terminio);
                params.put("emailUsuario", Utilitaria.retornaEmail(context));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
