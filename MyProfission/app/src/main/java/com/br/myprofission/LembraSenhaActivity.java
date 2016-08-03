package com.br.myprofission;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.br.myprofission.dao.BDUsuario;

/**
 * Created by LENOVO on 28/07/2016.
 */
public class LembraSenhaActivity  extends AppCompatActivity{
    private EditText edt_email;
    private Button btn_lembra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembra_senha);

        edt_email= (EditText) findViewById(R.id.edt_email);
        btn_lembra= (Button) findViewById(R.id.btn_enviar);

        btn_lembra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDUsuario bd = new BDUsuario(LembraSenhaActivity.this);
                bd.lembraSenha(LembraSenhaActivity.this,edt_email.getText().toString());
            }
        });

    }
}
