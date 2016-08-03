package com.br.myprofission.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.br.myprofission.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LENOVO on 12/07/2016.
 */
public class Utilitaria {


    public static void janelaPoupUp(Activity activity, String nome,String profissao,String tel,String email,String descricao){
        AlertDialog alerta = null;
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = activity.getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.janela_detalhes, null);
        //definimos para o botão do layout um clickListener
        TextView txt_nome= (TextView) view.findViewById(R.id.txt_nome);
        TextView txt_profissao= (TextView) view.findViewById(R.id.txt_profissao);
        TextView txt_tel= (TextView) view.findViewById(R.id.txt_tel);
        TextView txt_email= (TextView) view.findViewById(R.id.txt_email);
        TextView txt_descricao= (TextView) view.findViewById(R.id.txt_descricao);

        txt_nome.setText(""+nome);
        txt_profissao.setText(""+profissao);
        txt_tel.setText(""+tel);
        txt_email.setText(email);
        txt_descricao.setText(descricao);


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Detalhes");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();

    }
    public static String seVazio(String valor,String tipo){
        if(valor=="" || valor==null){
            return tipo;
        }
        return valor;
    }
    public static boolean diretorioVazio(String caminho) {

        File dir = new File(caminho);
        if (!dir.exists()){//verifica se o diretorio existe se não existe sera criado
            return true;
        }
        return false;
    }

    public static int uploadFile(String sourceFileUri,Context context) {


        int serverResponseCode = 0;


        String   upLoadServerUri = "http://www.seriadosweb.biz/precciso/uploadFile.php";

        /**********  File Path *************/
        final String uploadFilePath = Environment.getExternalStorageDirectory() +
                "/MyProfession/";
        final String uploadFileName = Utilitaria.retornaEmail(context)+".png";


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath + "" + uploadFileName);
            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename=" + fileName + ""+ lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){
                    Log.i("info","upload completado");
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                e.printStackTrace();
                Log.e("errro", "Exception : "+ e.getMessage(), e);
            }
            return serverResponseCode;

        } // End else block
    }
    public static boolean Conectado(Context context) {
        boolean conectado = false;
        ConnectivityManager cm;
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                //Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                conectado=true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                // Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                conectado=true;
            }
        } else {
            // not connected to the internet
            conectado=false;
        }
        return conectado;
    }

    public  static void gravaEmail(Context context,String response){//salva saldo sharepreferend

        SharedPreferences preferences = context.getSharedPreferences("PREF_ACAD", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",response);
        editor.commit();
    }
    public static String retornaEmail(Context context){
        SharedPreferences preferences=context.getSharedPreferences("PREF_ACAD", 0);
        String email=preferences.getString("email", "");
        return email;
    }

    public static double calculaDistancia(double lat1, double lng1, double lat2, double lng2) {
        //double earthRadius = 3958.75;//miles
        double earthRadius = 6371;//kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist * 1000; //em metros
    }

}
