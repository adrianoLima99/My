package com.br.myprofission.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.br.myprofission.R;
import com.br.myprofission.adapter.ContatoAdapter;
import com.br.myprofission.adapter.ContatoConexoesAdapter;
import com.br.myprofission.dao.BD;
import com.br.myprofission.dao.BDConexoesContatos;
import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.dao.Contato;
import com.br.myprofission.dao.ContatoConexoes;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LENOVO on 22/04/2016.
 */
public class FragmentContato extends Fragment {
    public Activity activity;
    private List<ContatoConexoes> c = new ArrayList<ContatoConexoes>();
    private ListView lv ;
    private ContatoConexoesAdapter adapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_contato,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        BDConexoesContatos bd = new BDConexoesContatos(activity);
        //faz a listagem das conexoes
        List<ContatoConexoes> list = bd.buscar();
        TextView txt_vazio = (TextView) activity.findViewById(R.id.txt_vazio);
        lv = (ListView) activity.findViewById(R.id.listagemContato);
        if(list.size()>0) {
            adapter = new ContatoConexoesAdapter(activity, list);
            lv.setAdapter(adapter);
        }else{
            lv.setVisibility(View.GONE);
            txt_vazio.setVisibility(View.VISIBLE);
            txt_vazio.setText("Seus contatos ainda não estão usando o Buskr");
            txt_vazio.setTextSize(13);
        }
        bd.fecharConexao();
    }
}
