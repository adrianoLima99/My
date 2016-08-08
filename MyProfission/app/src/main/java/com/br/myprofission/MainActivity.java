package com.br.myprofission;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.br.myprofission.adapter.UsuarioAdapter;
import com.br.myprofission.adapter.ViewPagerAdapter;
import com.br.myprofission.dao.BD;
import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.dao.Contato;
import com.br.myprofission.dao.Usuario;
import com.br.myprofission.fragment.FragmentContato;
import com.br.myprofission.fragment.FragmentConversa;
import com.br.myprofission.fragment.FragmentPerfil;
import com.br.myprofission.fragment.FragmentInicio;
import com.br.myprofission.util.Utilitaria;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by LENOVO on 09/07/2016.
 */
public class MainActivity extends AppCompatActivity {


    private List<Usuario> usuario = new ArrayList<Usuario>();
//    private  ListView lv ;
    private UsuarioAdapter adapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BDUsuario bd_user;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      /*  new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                    }
                });
                FirebaseInstanceIDService t = new FirebaseInstanceIDService();
                t.onTokenRefresh();

            }
        }).start();*/


        bd_user = new BDUsuario(this);

        if(bd_user.buscar().size()==0){//chama a atividade de cadastro de usuario
            Intent i= new Intent(this,UsuarioActivity.class);
            startActivity(i);
            finish();
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager= (ViewPager) findViewById(R.id.viewPager);
        configurarViewPager(viewPager);

        tabLayout= (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



        //verifica as permissoes de acesso ao contato do usuario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {
                    // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                    // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                    Toast.makeText(MainActivity.this, "OPS!! vc negou a permissao de acesso a seu contatos", Toast.LENGTH_SHORT).show();
                    /*Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:com.br.myprofission"));
                    startActivity(i);*/
                } else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},0);
                }
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_NETWORK_STATE)) {
                    // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                    // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                    Toast.makeText(this, "OPS!! vc negou a permissao de acesso ao status da internet", Toast.LENGTH_SHORT).show();
                    /*Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:com.br.myprofission"));
                    startActivity(i);*/
                } else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},0);
                }
            }

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                    // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                    Toast.makeText(this, "OPS!! vc negou a permissao de acesso ao status da internet", Toast.LENGTH_SHORT).show();
                    /*Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:com.br.myprofission"));
                    startActivity(i);*/
                } else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
                }
            }
        }


        try {
            String[] PROJECTION = new String[] { ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };

            //Cursor c = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);

            Cursor c= getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);

            if (c.moveToFirst()) {
                String clsPhonename = null;
                String clsphoneNo = null;

                do {
                    clsPhonename =       c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    clsphoneNo = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    clsphoneNo =clsphoneNo.replaceAll("\\(","").replaceAll("\\)","").replaceAll("-","").replaceAll("\\+","").replaceAll(" ","");
                    //  System.out.println(clsPhonename+"  -  "+clsphoneNo.replaceAll("\\(","").replaceAll("\\)","").replaceAll("-","").replaceAll("\\+","").replaceAll(" ",""));
                    BD bd= new BD(this);
                    if(bd.buscarUm(clsphoneNo)!=true) {//verifica se o telefone ja existe
                        Contato contato = new Contato();
                        contato.setNome(clsPhonename);
                        contato.setNumero(clsphoneNo);
                        bd.inserir(contato);

                    }

                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void configurarViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentPerfil(),"Perfil");
        viewPagerAdapter.addFragment(new FragmentInicio(),"Busca");
        viewPagerAdapter.addFragment(new FragmentConversa(),"Conversas");
        viewPagerAdapter.addFragment(new FragmentContato(),"Contatos");
        viewPager.setAdapter(viewPagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_atualizar) {
            atualizaConexoesContato();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void atualizaConexoesContato(){
        if(Utilitaria.Conectado(MainActivity.this)) {
            BDUsuario bdUsuario = new BDUsuario(MainActivity.this);
            bdUsuario.conexoes(MainActivity.this);
        }

    }
    /*public void startContatosConexoes(){//inicia brodcast q sera o responsavel de verificar de tempos em tempos se o usuario possui contato q usam o app
        long milisegundo;
        boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("VERIFICACONEXOESCONTATOS"),
                PendingIntent.FLAG_NO_CREATE) == null);

        if (alarmeAtivo) {//verifica se alarme ta ativo
            milisegundo=60*60*1000;
            Intent intent = new Intent("VERIFICACONEXOESCONTATOS");
            PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            //c.add(Calendar.SECOND, 3);

            AlarmManager alarme =(AlarmManager)getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),milisegundo,p);
        } else {
            // Toast.makeText(this, "alarme rodando", Toast.LENGTH_SHORT).show();
            Log.i("Script", "Alarme ja ativo");
        }
    }*/
}


