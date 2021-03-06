package com.br.myprofission.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LENOVO on 09/07/2016.
 */

public class BDCore extends SQLiteOpenHelper {
    private static final String NOME_BD = "profissionaisON";
    private static final int VERSAO_BD = 11;

    public BDCore(Context ctx) {
        super(ctx, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table usuario(_id integer primary key autoincrement,nome text not null, numero  text not null,profissao text,pais text,sobre text,email text,senha text not null,latitude double,longitude double,logradouro text,cidade text,uf text);");
        bd.execSQL("create table experiencia(_id integer primary key autoincrement,empresa text not null,inicio text not null,terminio text not null,obs text)");
        contato(bd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
       bd.execSQL("drop table contato;");
       bd.execSQL("drop table if exists contatoConexoes;");
       bd.execSQL("drop table if exists sala_chat;");
       bd.execSQL("drop table if exists experiencia;");
       contato(bd);
        //onCreate(bd);
    }
    public void contato(SQLiteDatabase bd){
        bd.execSQL("create table contato(_id integer primary key autoincrement,nome text not null,numero text not null);");
        bd.execSQL("create table contatoConexoes(_id integer primary key autoincrement,nome text not null,numero text not null, profissao  text,email text,sobre text,uf text,foto text,latitude double,longitude double,logradouro text,cidade text,pais text);");
        bd.execSQL("create table sala_chat(_id integer primary key autoincrement,nome text not null,nome_exibicao text not null,img_exibicao text ,email_destino text, email_origem text not null);");
    }
}