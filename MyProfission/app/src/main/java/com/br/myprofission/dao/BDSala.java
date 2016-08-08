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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.br.myprofission.MainActivity;
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
public class BDSala {
    private SQLiteDatabase bd;

    public BDSala(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public boolean jaExiste(String emailUsuario,String emailProfissional) {

        String[] colunas = new String[] { "_id,nome"};
        String where =" (email_origem='"+emailUsuario+"' AND email_destino='"+emailProfissional+"') OR (email_origem='"+emailProfissional+"' AND email_destino='"+emailUsuario+"') ";
        Cursor cursor = bd.query("sala_chat", colunas,where, null, null, null, "nome ");

        if (cursor.getCount() > 0) {
            return true;
        }
        //bd.close();
        return false;
    }

    public List<SalaChat> buscar() {

        List<SalaChat> list = new ArrayList<SalaChat>();
        String[] colunas = new String[] { "_id", "nome", "nome_exibicao"};
       // String where = condicao ;
        Cursor cursor = bd.query("sala_chat", colunas,null, null, null, null, "nome ");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                SalaChat s = new SalaChat();
                s.setId(cursor.getLong(0));
                s.setNome(cursor.getString(1));
                s.setNomeExibicao(cursor.getString(2));
                list.add(s);

            } while (cursor.moveToNext());
        }
        //bd.close();
        return (list);
    }
    public String retornaSala(String email_origem,String email_destino) {//retorna o nome da sala independement de quem seja o emissor ou receptor se os usuarios ja tem uma conexao outra não sera criada

        String sala=null;
        String[] colunas = new String[] { "_id", "nome"};
        String where = " (email_origem='"+email_origem+"' AND email_destino='"+email_destino+"') OR ( email_destino='"+email_origem+"' AND email_origem='"+email_destino+"')" ;
        Cursor cursor = bd.query("sala_chat", colunas,where, null, null, null, "nome ");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

                sala=cursor.getString(1);
        }
        //bd.close();
        return sala;
    }


    public void inserir(SalaChat s) {
		ContentValues valores = new ContentValues();
		valores.put("nome", s.getNome());
        valores.put("nome_exibicao", s.getNomeExibicao());
        valores.put("email_origem", s.getEmailorigem());
        valores.put("email_destino", s.getEmailDestino());
        valores.put("img_exibicao",s.getCaminhoImg());
		bd.insert("sala_chat", null, valores);
	}

    public void atualizar(SalaChat s) {
        ContentValues valores = new ContentValues();
        valores.put("nome", s.getNome());
        valores.put("nome_exibicao", s.getNomeExibicao());
        bd.update("sala_chat", valores, "_id = ?", new String[] { "" + s.getId() });


    }
    public void deletar() {
		bd.delete("sala_chat", null, null);
	}


    public  void fecharConexao(){
        bd.close();
    }

    public void salvaServidor(final Context context, final String nome, final String emailDestino, final String emailOrigem ){

        Toast.makeText(context, "nome="+nome, Toast.LENGTH_SHORT).show();
        String url="http://www.seriadosweb.biz/precciso/controller/controllerSalaChat.php";
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
                params.put("nome",nome);
                params.put("origem",emailOrigem);
                params.put("destino",emailDestino);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void verificaSala(final Context context,final String email){///pesquisa para saber se alguma chat foi criado endereçado ao usuario


        String url="http://www.seriadosweb.biz/precciso/controller/controllerSalaChat.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equals("nao")) {
                            Toast.makeText(context, "Operação falhou!", Toast.LENGTH_SHORT).show();

                        } else {
                            if(response.length()>0){
                                JSONArray json= null;
                                try {
                                    json = new JSONArray(response);
                                    BDSala bd = new BDSala(context);
                                    for(int i=0;i<json.length();i++) {
                                        JSONObject obj = json.getJSONObject(i);

                                        SalaChat s = new SalaChat();

                                        s.setNome(obj.getString("nome"));
                                        s.setNomeExibicao(obj.getString("nome_exibicao"));
                                        s.setEmailorigem(obj.getString("origem"));
                                        s.setEmailDestino(obj.getString("destino"));

                                        //if(!bd.jaExiste(obj.getString("nome")))
                                          //  bd.inserir(s);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                            Toast.makeText(context, "resposta =" + response.trim(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Houve 1 erro!\nTente novamente"+ error, Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("acao","verificaSala");//vai para metodo q verifica se o usuario ja possui registro e ja tem creditos criados , assim em vez de cria uma nova tupla ele apenas atualiza a existente
                params.put("email",email);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

}
