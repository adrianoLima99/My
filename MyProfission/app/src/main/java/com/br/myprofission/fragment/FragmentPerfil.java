package com.br.myprofission.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.br.myprofission.ChatActivity;
import com.br.myprofission.FotoPerfilActivity;
import com.br.myprofission.MainActivity;
import com.br.myprofission.R;
import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.dao.Usuario;
import com.br.myprofission.util.Utilitaria;

import java.io.IOException;
import java.util.List;


/**
 * Created by LENOVO on 22/04/2016.
 */
public class FragmentPerfil extends Fragment {

    private Activity activity;
    private List<Usuario> lista;
    private EditText edt_nome;
    private EditText edt_sobre;
    private EditText edt_tel;
    private EditText edt_email;
    private EditText edt_profissao;
    private EditText edt_latitude;
    private EditText edt_longitude;
    private EditText edt_endereco;
    private EditText edt_cidade;
    private EditText edt_uf;
    private EditText edt_pais;
    private TextInputLayout input_nome;
    private TextInputLayout input_email;
    private TextInputLayout input_sobre;
    private TextInputLayout input_tel;
    private TextInputLayout input_profissao;

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
        return inflater.inflate(R.layout.fragment_perfil,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edt_nome= (EditText) activity.findViewById(R.id.edt_nome);
        edt_sobre= (EditText) activity.findViewById(R.id.edt_sobre);
        edt_tel= (EditText) activity.findViewById(R.id.edt_tel);
        edt_email= (EditText) activity.findViewById(R.id.edt_email);
        edt_profissao= (EditText) activity.findViewById(R.id.edt_profissao);
        edt_endereco= (EditText) activity.findViewById(R.id.edt_endereco);
        edt_uf= (EditText) activity.findViewById(R.id.edt_uf);
        edt_cidade= (EditText) activity.findViewById(R.id.edt_cidade);
        edt_pais= (EditText) activity.findViewById(R.id.edt_pais);
        input_nome= (TextInputLayout) activity.findViewById(R.id.input_nome);
        input_email= (TextInputLayout) activity.findViewById(R.id.input_email);
        input_profissao= (TextInputLayout) activity.findViewById(R.id.input_profissao);
        input_tel= (TextInputLayout) activity.findViewById(R.id.input_tel);
        input_sobre= (TextInputLayout) activity.findViewById(R.id.input_sobre);

        //geolocalizacao

        edt_latitude = (EditText) activity.findViewById(R.id.edt_latitude);
        edt_longitude = (EditText) activity.findViewById(R.id.edt_longitude);

        //verificaGPS();//se verifica se gps esta ligado, senão sera ligado
        startGPS();//inici a captura das coordenadas


        ImageView img_perfil= (ImageView) activity.findViewById(R.id.img_perfil);
        Button btn_salvar= (Button) activity.findViewById(R.id.btn_salvar);
        Button btn_localizacao= (Button) activity.findViewById(R.id.btn_localizacao);
        /*Button btn_atualiza= (Button) activity.findViewById(R.id.btn_teste);

        btn_atualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ChatActivity.class);
                startActivity(i);
            }
        });
*/
        // Se não possui permissão
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                    // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                } else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                }
            } else {
                //se tiver permissao para acessar e criar pastas
            }


        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_NETWORK_STATE)) {
                    // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                    // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                    Toast.makeText(activity, "OPS!! vc negou a permissao de acesso ao status da internet", Toast.LENGTH_SHORT).show();
                    /*Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:com.br.myprofission"));
                    startActivity(i);*/
                } else {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},0);
                }
            }
        }




        final BDUsuario bd= new BDUsuario(activity);
        lista = bd.buscar();

        for(int i=0;i<lista.size();i++) {//for para preencgimnto dos campos do formulario
            String caminho = Environment.getExternalStorageDirectory() +
                    "/MyProfession/"+Utilitaria.retornaEmail(getContext())+".png";

            if(!Utilitaria.diretorioVazio(caminho)){
                ///seta a foto perfil salva no direotio
                final Bitmap bitmap1 = BitmapFactory.decodeFile(caminho);
                img_perfil.setImageBitmap(bitmap1);
            }

            edt_nome.setText(lista.get(i).getNome());
            edt_sobre.setText(lista.get(i).getSobre());
            edt_tel.setText(lista.get(i).getNumero());
            edt_profissao.setText(lista.get(i).getProfissao());
            edt_email.setText(lista.get(i).getEmail());
            edt_cidade.setText(lista.get(i).getCidade());
            edt_uf.setText(lista.get(i).getUf());
            edt_pais.setText(lista.get(i).getPais());
            edt_endereco.setText(lista.get(i).getLogradouro());
            edt_latitude.setText(""+lista.get(i).getLatitude());
            edt_longitude.setText(""+lista.get(i).getLongitude());
           try {
                Address endereco;
                if(lista.get(i).getLatitude()!=null && lista.get(i).getLatitude()!=0.0  && lista.get(i).getLongitude()!=null && lista.get(i).getLongitude()!=0.0) {
                   // Toast.makeText(activity, "pegos da lista", Toast.LENGTH_SHORT).show();
                    endereco = buscaEndereco(lista.get(i).getLatitude(), lista.get(i).getLongitude());
                    edt_cidade.setText(endereco.getLocality());
                    edt_uf.setText(endereco.getAdminArea());
                    edt_pais.setText(endereco.getCountryName());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submitForm()) {
                   if(Utilitaria.Conectado(activity)) {
                       Double longi=0.0;
                       Double lati=0.0;
                       Usuario u = new Usuario();
                       u.setNome(edt_nome.getText().toString());
                       u.setSobre(edt_sobre.getText().toString());
                       u.setProfissao(edt_profissao.getText().toString());
                       u.setEmail(edt_email.getText().toString());
                       u.setNumero(edt_tel.getText().toString());
                       if(edt_longitude.getText().toString()!=null && !edt_longitude.getText().toString().isEmpty()) {
                           longi = Double.parseDouble(edt_longitude.getText().toString());
                           //Log.i("long",""+longi);
                       }
                       if(edt_latitude.getText().toString()!=null && !edt_latitude.getText().toString().isEmpty() ) {
                           lati = Double.parseDouble(edt_latitude.getText().toString());
                           //Log.i("lat",""+lati);
                       }
                       u.setLongitude(longi);
                       u.setLatitude(lati);
                       u.setCidade(edt_cidade.getText().toString());
                       u.setUf(edt_uf.getText().toString());
                       u.setPais(edt_pais.getText().toString());
                       u.setLogradouro(edt_endereco.getText().toString());
                       bd.atualizar(u);
                       bd.editarServidor(activity, edt_nome.getText().toString(), edt_tel.getText().toString(), edt_email.getText().toString(), edt_sobre.getText().toString(), edt_profissao.getText().toString(),lati,longi,edt_endereco.getText().toString(),edt_cidade.getText().toString(),edt_uf.getText().toString(),edt_pais.getText().toString());
                   }else{
                       Toast.makeText(activity, "Operação não pode ser realizada\nVocê não esta conectado a Internet", Toast.LENGTH_SHORT).show();
                   }
                }

            }
        });
        btn_localizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaGPS();
            }
        });
        img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, FotoPerfilActivity.class);
                startActivity(i);
            }
        });

    }
    /**
     * Validating form
     */
    private boolean submitForm() {
        if (!validateName()) {
            return false;
        }

        if (!validateEmail()) {
            return false;
        }

        return true;
    }

    private boolean validateName() {
        if (edt_nome.getText().toString().trim().isEmpty()) {
            input_nome.setError(getString(R.string.err_msg_name));
            requestFocus(edt_nome);
            return false;
        } else {
            input_nome.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = edt_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            input_email.setError(getString(R.string.err_msg_email));
            requestFocus(edt_email);
            return false;
        } else {
            input_email.setErrorEnabled(false);
        }

        return true;
    }

    /*private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }*/

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edt_nome:
                    validateName();
                    break;
                case R.id.edt_email:
                    validateEmail();
                    break;

            }
        }
    }

    //Método que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS() {
        LocationManager lManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
                updateView(locat);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);
    }


    //  Método que faz a atualização da tela para o usuário.
    public void updateView(Location locat){
        Double latitude = locat.getLatitude();
        Double longitude = locat.getLongitude();


       /* BDUsuario bd= new BDUsuario(activity);
        Usuario u= new Usuario();
        u.setLatitude(latitude);
        u.setLongitude(longitude);
        bd.atualizarLanLon(u);

        if(Utilitaria.Conectado(activity)){//se conectado
            bd.atualizarLatLongServidor(activity,latitude,longitude,Utilitaria.retornaEmail(activity));
        }*/

        edt_latitude.setText(latitude.toString());
        edt_longitude.setText(longitude.toString());
    }
    public Address buscaEndereco(double latitude ,double longitude)throws IOException{

        Geocoder geocoder;
        Address address=null;
        List<Address> addresses;

        geocoder=new Geocoder(getActivity());
        addresses=geocoder.getFromLocation(latitude,longitude,1);
        if(addresses.size()>0){
            address=addresses.get(0);
        }
        return address;
    }
    public void  verificaGPS(){
        AlertDialog alerta;
        //verifica se gps está destivado
        LocationManager manager = (LocationManager) activity.getSystemService( Context.LOCATION_SERVICE );
        boolean isOn = manager.isProviderEnabled( LocationManager.GPS_PROVIDER);

        if(!isOn)
        {
            //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            //define o titulo
            builder.setTitle("GPS desativado!");
            //define a mensagem
            builder.setMessage("Deseja ativa-lo?");
            //define um botão como positivo
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            //define um botão como negativo.
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();

        }else{
            startGPS();
            recuperaLocalizacao();
        }
    }
    private void recuperaLocalizacao(){
        try {
            Address endereco;
             if(!edt_latitude.getText().toString().isEmpty() && !edt_longitude.getText().toString().isEmpty()){
                endereco = buscaEndereco(Double.parseDouble(edt_latitude.getText().toString()), Double.parseDouble(edt_longitude.getText().toString()));
                edt_cidade.setText(endereco.getLocality());
                edt_uf.setText(endereco.getAdminArea());
                edt_pais.setText(endereco.getCountryName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

