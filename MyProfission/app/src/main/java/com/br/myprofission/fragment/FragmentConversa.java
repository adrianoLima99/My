package com.br.myprofission.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.br.myprofission.ChatActivity;
import com.br.myprofission.R;
import com.br.myprofission.adapter.ContatoConexoesAdapter;
import com.br.myprofission.adapter.SalaChatAdapter;
import com.br.myprofission.dao.BDConexoesContatos;
import com.br.myprofission.dao.BDSala;
import com.br.myprofission.dao.ContatoConexoes;
import com.br.myprofission.dao.SalaChat;
import com.br.myprofission.util.Utilitaria;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LENOVO on 22/04/2016.
 */
public class FragmentConversa extends Fragment {
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

       View view= inflater.inflate(R.layout.fragment_conversa,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        final ListView lv = (ListView) activity.findViewById(R.id.lv_sala);
        Button btn_delete= (Button) activity.findViewById(R.id.btn_delete);


        BDSala bd=  new BDSala(activity);
        bd.verificaSala(activity, Utilitaria.retornaEmail(activity));
        final List<SalaChat> list= bd.buscar();

        final SalaChatAdapter adapter = new SalaChatAdapter(activity, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object nomeSala = list.get(position).getNome();

                Object nomeExibicao = list.get(position).getNomeExibicao();

                Object imgExibicao = list.get(position).getCaminhoImg();
                //Toast.makeText(activity, "sala="+nomeSala+"\n nomeExibicao="+nomeExibicao, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(activity, ChatActivity.class);
                i.putExtra("nomeSala",""+nomeSala);
                i.putExtra("email",""+nomeExibicao);//email para quem vai ser enviado a msg
                i.putExtra("img",""+imgExibicao);
                startActivity(i);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BDSala bd = new BDSala(activity);
                bd.deletar();
            }
        });

    }
}
