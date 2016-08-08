package com.br.myprofission.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.br.myprofission.ChatActivity;
import com.br.myprofission.R;
import com.br.myprofission.adapter.UsuarioAdapter;
import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.dao.Usuario;
import com.br.myprofission.util.Utilitaria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LENOVO on 22/04/2016.
 */
public class FragmentInicio extends Fragment implements  SwipeRefreshLayout.OnRefreshListener
{
    private Activity activity;
    private List<Usuario> usuario = new ArrayList<Usuario>();
    private UsuarioAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog pDialog = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicial,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pDialog = new ProgressDialog(activity);
        ListView lv = (ListView) activity.findViewById(R.id.lv_profissionais);
        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swiperefresh);
        ImageButton imgBtn= (ImageButton) activity.findViewById(R.id.btn_encontrar);
        adapter = new UsuarioAdapter(activity, usuario);
        lv.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(FragmentInicio.this);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog.show();
                lista();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object email = usuario.get(position).getEmail();
                Object img= usuario.get(position).getFoto();

                Intent i = new Intent(activity, ChatActivity.class);
                i.putExtra("email",""+email);//email para quem vai ser enviado a msg
                i.putExtra("img",""+img);
                startActivity(i);
            }
        });


    }

    public void lista(){

        BDUsuario bd_user = new BDUsuario(activity);
        EditText edt_precisa = (EditText) activity.findViewById(R.id.precisa);//campo de pesquisa
        String condicao = "";
        //recuperar a longitude e latitude paa poder pesquisa por pessoas proximas

        if ((edt_precisa.getText().toString() != "") && edt_precisa.getText().toString() != null) {
            condicao = edt_precisa.getText().toString();
        }
        if (usuario.size() > 0) {//caso se faça uma nova consulta sera limpo o LIST para ser preenchido com dados novos
            usuario.clear();
        }
        String url = "http://www.seriadosweb.biz/precciso/controller/controllerUsuario.php?acao=listar&condicao=" + condicao+"&email="+ Utilitaria.retornaEmail(activity)+"&lat="+bd_user.retornaLat()+"&long="+bd_user.retornaLong();

        /*final ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Loading...");
        pDialog.show();
*/

        JsonArrayRequest jsonRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pDialog.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                        try {

                            //output.setText(response.toString());
                            //Toast.makeText(MainActivity.this, "tam="+response.length(), Toast.LENGTH_SHORT).show();
                            if( response.length()>0) {
                                for (int i = 0; i < response.length(); i++) {
                                    //  Toast.makeText(ListActivity.this, "i="+i, Toast.LENGTH_SHORT).show();
                                    JSONObject obj = response.getJSONObject(i);

                                    Usuario u = new Usuario();
                                    u.setNome(obj.getString("nome"));
                                    u.setEmail(obj.getString("email"));
                                    u.setNumero(obj.getString("tel"));
                                    u.setSobre(obj.getString("descricao"));
                                    u.setProfissao(obj.getString("profissao"));
                                    u.setFoto(obj.getString("foto"));
                                    u.setDistanciaEntreUsuarios(obj.getString("distancia"));
                                    u.setCidade(obj.getString("cidade"));
                                    u.setUf(obj.getString("uf"));
                                    usuario.add(u);
                                }
                            }else{
                                Toast.makeText(activity, "Nenhum resultado encontrado", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        error.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        Volley.newRequestQueue(activity).add(jsonRequest);

    }

    @Override
    public void onRefresh() {
        lista();
    }
}
