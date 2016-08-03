package com.br.myprofission;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.dao.Usuario;

public class UsuarioActivity extends AppCompatActivity {
    private TextView passos;
    private EditText numero;
    private EditText nome;
    private EditText profissao;
    private EditText email;
    private EditText senha;
    private Button btn_prox;
    private Button btn_salvar;
    private Button btn_ja_cadastrado;
    private TextView txt_msg;
    private TextInputLayout input_nome;
    private TextInputLayout input_email;
    private TextInputLayout input_tel;
    private TextInputLayout input_senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        passos= (TextView) findViewById(R.id.conta_passos);

        input_nome= (TextInputLayout) findViewById(R.id.input_nome);
        input_email= (TextInputLayout) findViewById(R.id.input_email);
        input_tel= (TextInputLayout) findViewById(R.id.input_tel);
        input_senha= (TextInputLayout) findViewById(R.id.input_senha);
        nome= (EditText) findViewById(R.id.edt_nome);
        numero= (EditText) findViewById(R.id.edt_tel);
        profissao= (EditText) findViewById(R.id.edt_profissao);
        email= (EditText) findViewById(R.id.edt_email);
        senha= (EditText) findViewById(R.id.edt_senha);
        txt_msg= (TextView) findViewById(R.id.txtmsg);
        btn_prox= (Button) findViewById(R.id.btn_proximo);
        btn_salvar= (Button) findViewById(R.id.btn_salvar);
        btn_ja_cadastrado= (Button) findViewById(R.id.btn_ja_cadastrado);

        btn_prox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampo1Parte()){
                    //some campo nome e numero
                    email.setVisibility(View.GONE);
                    senha.setVisibility(View.GONE);
                    btn_prox.setVisibility(View.GONE);
                    passos.setText("2 de 2");
                    //aparece campo profissao e botao salvar
                    numero.setVisibility(View.VISIBLE);
                    nome.setVisibility(View.VISIBLE);
                    profissao.setVisibility(View.VISIBLE);
                    btn_salvar.setVisibility(View.VISIBLE);
                    txt_msg.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampo2Parte()) {
                    BDUsuario bd= new BDUsuario(UsuarioActivity.this);
                    Usuario u= new Usuario();
                    u.setNome(nome.getText().toString());
                    u.setNumero(numero.getText().toString());
                    u.setProfissao(profissao.getText().toString());
                    u.setEmail(email.getText().toString());
                    u.setSenha(senha.getText().toString());
                    bd.inserir(u);

                    bd.salvaServidor(UsuarioActivity.this,nome.getText().toString(),numero.getText().toString(),email.getText().toString(),"",profissao.getText().toString(),senha.getText().toString());

                    bd.fecharConexao();

                    Toast.makeText(UsuarioActivity.this, "Feito com sucesso", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UsuarioActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        btn_ja_cadastrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(UsuarioActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public boolean validaCampo1Parte(){


        if( email.getText().toString().isEmpty() || email.getText().toString()==null || !isValidEmail(email.getText().toString().trim())){
            requestFocus(email);
            input_email.setError("Email invalido ou vazio");
            return false;
        }else{
            input_email.setErrorEnabled(false);
        }
        if( senha.getText().toString().isEmpty() || senha.getText().toString()==null) {
            requestFocus(senha);
            input_senha.setError("Campo obrigatorio");
            return false;
        }else{
            input_senha.setErrorEnabled(false);
        }


        return true;
    }
    public boolean validaCampo2Parte(){

        if( nome.getText().toString().isEmpty() || nome.getText().toString()==null){
            requestFocus(nome);
            input_nome.setError("Campo obrigatorio");
            return false;
        }else{
            input_nome.setErrorEnabled(false);
        }

        if( numero.getText().toString().isEmpty() || numero.getText().toString()==null){
            requestFocus(numero);
            input_tel.setError("Campo obrigatorio");
            return false;
        }else{
            input_tel.setErrorEnabled(false);
        }


        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
