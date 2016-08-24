package com.br.myprofission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.util.Utilitaria;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_email;
    private EditText edt_senha;
    private Button btn_logar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email= (EditText) findViewById(R.id.edt_email);
        edt_senha= (EditText) findViewById(R.id.edt_senha);
        btn_logar= (Button) findViewById(R.id.btn_logar);


    }

    @Override
    protected void onResume() {
        super.onResume();

        btn_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilitaria.Conectado(LoginActivity.this)) {
                    BDUsuario bd = new BDUsuario(LoginActivity.this);
                    bd.login(LoginActivity.this, edt_email.getText().toString(), edt_senha.getText().toString());

                } else {
                    Toast.makeText(LoginActivity.this, "Sem conexão com internet", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
    public  void lembra(View v){
        Intent i = new Intent(LoginActivity.this,LembraSenhaActivity.class);
        startActivity(i);

    }
}
