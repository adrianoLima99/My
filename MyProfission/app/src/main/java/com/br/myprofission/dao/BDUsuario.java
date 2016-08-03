package com.br.myprofission.dao;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.br.myprofission.LoginActivity;
import com.br.myprofission.MainActivity;
import com.br.myprofission.adapter.UsuarioAdapter;
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
public class BDUsuario {
    private SQLiteDatabase bd;
    ProgressDialog pDialog;
    public BDUsuario(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }




    public List<Usuario> buscar() {

        List<Usuario> list = new ArrayList<Usuario>();
        String[] colunas = new String[] { "_id","nome", "profissao", "numero","email","sobre","latitude","longitude","cidade","pais","uf","logradouro"};
       // String where = condicao ;
        Cursor cursor = bd.query("usuario", colunas,null, null, null, null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Usuario u = new Usuario();
                u.setId(cursor.getLong(0));
                u.setNome(cursor.getString(1));
                u.setProfissao(cursor.getString(2));
                u.setNumero(cursor.getString(3));
                u.setEmail(cursor.getString(4));
                u.setSobre(cursor.getString(5));
                u.setLatitude(cursor.getDouble(6));
                u.setLongitude(cursor.getDouble(7));
                u.setCidade(cursor.getString(8));
                u.setPais(cursor.getString(9));
                u.setUf(cursor.getString(10));
                u.setLogradouro(cursor.getString(11));
                list.add(u);

            } while (cursor.moveToNext());
        }
        //bd.close();
        return (list);
    }
    public double retornaLat() {

        double lat=0.0;
        String[] colunas = new String[] { "latitude"};
        // String where = condicao ;
        Cursor cursor = bd.query("usuario", colunas,null, null, null, null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                lat = cursor.getDouble(0);

            } while (cursor.moveToNext());
        }
        //bd.close();
        return lat;
    }
    public double retornaLong() {

        double longi=0.0;
        String[] colunas = new String[] { "longitude"};
        // String where = condicao ;
        Cursor cursor = bd.query("usuario", colunas,null, null, null, null,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                longi = cursor.getDouble(0);

            } while (cursor.moveToNext());
        }
        //bd.close();
        return longi;
    }
    public void inserir(Usuario u) {
		ContentValues valores = new ContentValues();
        valores.put("nome", u.getNome());
        valores.put("numero", u.getNumero());
        valores.put("profissao", u.getProfissao());
        valores.put("email", u.getEmail());
        valores.put("senha", u.getSenha());
        valores.put("latitude",u.getLatitude());
        valores.put("longitude",u.getLongitude());
        valores.put("pais",u.getPais());
        valores.put("cidade",u.getCidade());
        valores.put("logradouro",u.getLogradouro());
        valores.put("uf",u.getUf());
        bd.insert("usuario", null, valores);

	}

    public void atualizar(Usuario u) {
        ContentValues valores = new ContentValues();

        valores.put("nome", u.getNome());
        valores.put("numero", u.getNumero());
        valores.put("profissao", u.getProfissao());
        valores.put("sobre", u.getSobre());
        valores.put("email", u.getEmail());
        valores.put("latitude",u.getLatitude());
        valores.put("longitude",u.getLongitude());
        valores.put("pais",u.getPais());
        valores.put("cidade",u.getCidade());
        valores.put("logradouro",u.getLogradouro());
        valores.put("uf",u.getUf());
        //valores.put("")
        bd.update("usuario", valores, null, null);
    }

    public void deletar(Usuario c) {
		bd.delete("usuario", "_id = " + c.getId(), null);
	}


    public  void fecharConexao(){
        bd.close();
    }


    public void salvaServidor(final Context context,final String nome,final String tel,final String email,final String sobre,final String profissao,final String senha ){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url="http://www.seriadosweb.biz/precciso/controller/controllerUsuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   hidePDialog();
                        pDialog.dismiss();
                        Log.i("response",response);
                        if(response.trim().equals("nao")) {
                            Toast.makeText(context, "Operação falhou!", Toast.LENGTH_SHORT).show();

                        } else {
                            Utilitaria.gravaEmail(context,email);///salvar email sharepreference
                            //Utilitaria.sharedPrefernces(context,Utilitaria.removerEspacoVazio(response));
                            Toast.makeText(context, "resposta =" + response.trim(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "erro="+ error, Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("acao","inserir");//vai para metodo q verifica se o usuario ja possui registro e ja tem creditos criados , assim em vez de cria uma nova tupla ele apenas atualiza a existente
                params.put("nome",nome);
                params.put("email",email);
                params.put("tel",tel);
                params.put("sobre",sobre);
                params.put("profissao",profissao);
                params.put("senha",senha);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    public void alterarFoto(final Context context,final String email,final String foto ){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url="http://www.seriadosweb.biz/precciso/controller/controllerUsuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   hidePDialog();
                        pDialog.dismiss();
                        if(response.trim().equals("nao")) {
                            Toast.makeText(context, "Operação falhou!", Toast.LENGTH_SHORT).show();
                            Log.i("erro",response);
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
                        pDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("acao","alterarFoto");//vai para metodo q verifica se o usuario ja possui registro e ja tem creditos criados , assim em vez de cria uma nova tupla ele apenas atualiza a existente
                params.put("email",email);
                params.put("foto",foto);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    public void editarServidor(final Context context,final String nome,final String tel,final String email,final String sobre,final String profissao,final Double latitude,final Double longitude,final String logradouro,final String cidade,final String uf,final String pais  ){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url="http://www.seriadosweb.biz/precciso/controller/controllerUsuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   hidePDialog();
                        pDialog.dismiss();
                        if(response.trim().equals("nao")) {
                            //Toast.makeText(context, "Operação falhou!", Toast.LENGTH_SHORT).show();

                        } else {

                            //Utilitaria.sharedPrefernces(context,Utilitaria.removerEspacoVazio(response));
                          //  Toast.makeText(context, "resposta =" + response.trim(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Houve 1 erro!\nTente novamente"+ error, Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("acao","editar");//vai para metodo q verifica se o usuario ja possui registro e ja tem creditos criados , assim em vez de cria uma nova tupla ele apenas atualiza a existente
                params.put("nome",nome);
                params.put("email",email);
                params.put("tel",tel);
                params.put("sobre",sobre);
                params.put("profissao",profissao);
                params.put("latitude",""+latitude);
                params.put("longitude",""+longitude);
                params.put("logradouro",logradouro);
                params.put("cidade",cidade);
                params.put("uf",uf);
                params.put("pais",pais);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void lembraSenha(final Context context,final String email){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url="http://www.seriadosweb.biz/precciso/controller/controllerUsuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        if(response.trim().equals("nao")) {
                            Toast.makeText(context, "Email não encontrado!\n verifique se você digitou corretamente", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(context, "Uma nova senha foi enviada para seu email" + response.trim(), Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Houve 1 erro!\nTente novamente"+ error, Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("acao","recuperaSenha");//vai para metodo q verifica se o usuario ja possui registro e ja tem creditos criados , assim em vez de cria uma nova tupla ele apenas atualiza a existente
                params.put("email",email);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void login(final Context context,final String email,final String senha  ){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url="http://www.seriadosweb.biz/precciso/controller/controllerUsuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        if(response.trim().equals("nao")) {
                            Toast.makeText(context, "Operação falhou!", Toast.LENGTH_SHORT).show();

                        } else {
                            if(response.length()>0){
                                JSONArray json= null;
                                try {
                                    json = new JSONArray(response);
                                    BDUsuario bd = new BDUsuario(context);
                                    for(int i=0;i<json.length();i++) {
                                        JSONObject obj = json.getJSONObject(i);
                                        Usuario u = new Usuario();
                                        u.setNome(obj.getString("nome"));
                                        u.setNumero(obj.getString("tel"));
                                        u.setProfissao(obj.getString("profissao"));
                                        u.setEmail(obj.getString("email"));
                                        u.setUf(obj.getString("uf"));
                                        u.setCidade(obj.getString("cidade"));
                                        u.setProfissao(obj.getString("profissao"));
                                        u.setSenha(obj.getString("senha"));
                                        u.setLongitude(obj.getDouble("longitude"));
                                        u.setLatitude(obj.getDouble("latitude"));
                                        u.setLogradouro(obj.getString("logradouro"));
                                        u.setCidade(obj.getString("cidade"));
                                        u.setUf(obj.getString("uf"));
                                        u.setPais(obj.getString("pais"));

                                        bd.inserir(u);
                                        Utilitaria.gravaEmail(context,email);//gravar email sharepreference
                                        Intent intent = new Intent(context, MainActivity.class);
                                        context.startActivity(intent);
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
                        pDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("acao","login");//vai para metodo q verifica se o usuario ja possui registro e ja tem creditos criados , assim em vez de cria uma nova tupla ele apenas atualiza a existente
                params.put("usuario",email);
                params.put("senha",senha);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    public void conexoes(final Context context){
       /* pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
*/
        BD bd= new BD(context);
        List<Contato> list = bd.buscar();

            for (int i = 0; i < bd.buscar().size(); i++) {

                String url = "http://www.seriadosweb.biz/precciso/controller/controllerUsuario.php?acao=conexoesUsuario&tel=" + list.get(i).getNumero();
                Log.i("url", url);
                //Log.i("telefone",url);
                JsonArrayRequest jsonRequest = new JsonArrayRequest
                        (url, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // pDialog.dismiss();
                                try {

                                    //output.setText(response.toString());
                                    //Toast.makeText(MainActivity.this, "tam="+response.length(), Toast.LENGTH_SHORT).show();
                                    //Log.i("rsponse",response.toString());
                                    if (response.length() > 0) {
                                        for (int i = 0; i < response.length(); i++) {
                                            //  Toast.makeText(ListActivity.this, "i="+i, Toast.LENGTH_SHORT).show();
                                            JSONObject obj = response.getJSONObject(i);

                                            ContatoConexoes c = new ContatoConexoes();
                                        /*Log.i("nome",obj.getString("nome"));
                                        Log.i("email",obj.getString("email"));
                                        Log.i("tel",obj.getString("tel"));
                                        Log.i("desc",obj.getString("descricao"));
                                        Log.i("prof",obj.getString("profissao"));
                                        Log.i("foto",obj.getString("foto"));*/
                                            c.setNome(obj.getString("nome"));
                                            c.setEmail(obj.getString("email"));
                                            c.setNumero(obj.getString("tel"));
                                            c.setSobre(obj.getString("descricao"));
                                            c.setProfissao(obj.getString("profissao"));
                                            c.setFoto(obj.getString("foto"));

                                            BDConexoesContatos bd = new BDConexoesContatos(context);
                                            if (bd.buscarEmail(obj.getString("email")) == false) {//verifica se ja fois cadastro esse email se foi ele não sera mais inserido...
                                                bd.inserir(c);
                                            } else {
                                                bd.atualizar(c);
                                            }

                                        }
                                    } else {
                                        //Toast.makeText(MainActivity.this, "Nenhum resultado encontrado", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // pDialog.dismiss();
                                error.printStackTrace();
                            }
                        });

                Volley.newRequestQueue(context).add(jsonRequest);
            }


    }
}
