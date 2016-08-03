package com.br.myprofission;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.br.myprofission.dao.BDUsuario;
import com.br.myprofission.util.Utilitaria;

import java.io.File;
import java.io.FileOutputStream;

public class FotoPerfilActivity extends AppCompatActivity {
    private static final int IMAGEM_INTERNA=12;
    private ImageView img_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_perfil);
        img_perfil= (ImageView) findViewById(R.id.img_perfil);




        String caminho = Environment.getExternalStorageDirectory() +
                "/MyProfession/"+Utilitaria.retornaEmail(FotoPerfilActivity.this)+".png";

        if(!Utilitaria.diretorioVazio(caminho)){
            ///seta a foto perfil salva no direotio
            final Bitmap bitmap1 = BitmapFactory.decodeFile(caminho);
            img_perfil.setImageBitmap(bitmap1);
        }

    }

    public void alteraFoto(View v){
       if(Utilitaria.Conectado(FotoPerfilActivity.this)) {//verifica se esta conestado a internet
           Intent i = new Intent(Intent.ACTION_GET_CONTENT);
           i.setType("image/");
           startActivityForResult(i, IMAGEM_INTERNA);
       }else{
           Toast.makeText(FotoPerfilActivity.this, "Operação não pode ser realizada\nVocê não esta conectado a Internet", Toast.LENGTH_LONG).show();
       }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

       if (requestCode == IMAGEM_INTERNA) {
            if (resultCode == RESULT_OK) {
                Uri imagemSelecionada = intent.getData();
                String[] colunas = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imagemSelecionada, colunas, null, null, null);
                cursor.moveToFirst();

                int indexColuna = cursor.getColumnIndex(colunas[0]);
                String pathing = cursor.getString(indexColuna);

                cursor.close();

                final Bitmap bitmap1 = BitmapFactory.decodeFile(pathing);
                img_perfil.setImageBitmap(bitmap1);
                salvarFoto(bitmap1);
            }
        }
    }
    public void salvarFoto(Bitmap foto){

        String file_path = Environment.getExternalStorageDirectory() +
                "/MyProfession/";

        File dir = new File(file_path);
        if (!dir.exists())//verifica se o diretorio existe se não existe sera criado
            dir.mkdirs();
        //File file = new File(dir, "pensativo.jpg");
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file_path+ Utilitaria.retornaEmail(FotoPerfilActivity.this)+".png");

            foto.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            uploadFoto();

            //img.setImageBitmap(moldura);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void uploadFoto(){

        /**********  File Path *************/
        final String uploadFilePath = Environment.getExternalStorageDirectory() +
                "/MyProfession/";
        final String uploadFileName = Utilitaria.retornaEmail(FotoPerfilActivity.this)+".png";
        Toast.makeText(FotoPerfilActivity.this, ""+uploadFileName, Toast.LENGTH_SHORT).show();
        BDUsuario bd= new BDUsuario(FotoPerfilActivity.this);
        bd.alterarFoto(FotoPerfilActivity.this,Utilitaria.retornaEmail(FotoPerfilActivity.this),uploadFileName);
        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });

                Utilitaria.uploadFile(uploadFilePath + "" + uploadFileName,FotoPerfilActivity.this);
            }
        }).start();

        }
    }
