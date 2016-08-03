package com.br.myprofission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.br.myprofission.dao.BD;
import com.br.myprofission.dao.BDConexoesContatos;
import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.dao.Contato;
import com.br.myprofission.dao.ContatoConexoes;
import com.br.myprofission.util.Utilitaria;

import java.util.List;

public class AtualizaConexoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_conexoes);
        //pesquisaNumero();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utilitaria.Conectado(AtualizaConexoesActivity.this)) {
            BDUsuario bdUsuario = new BDUsuario(AtualizaConexoesActivity.this);
            bdUsuario.conexoes(AtualizaConexoesActivity.this);
        }

    }

}
