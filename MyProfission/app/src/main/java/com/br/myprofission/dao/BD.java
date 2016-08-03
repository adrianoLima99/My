package com.br.myprofission.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 09/07/2016.
 */
public class BD {
    private SQLiteDatabase bd;

    public BD(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }




    public List<Contato> buscar() {

        List<Contato> list = new ArrayList<Contato>();
        String[] colunas = new String[] { "_id", "nome", "numero"};
       // String where = condicao ;
        Cursor cursor = bd.query("contato", colunas,null, null, null, null, "nome ");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Contato c = new Contato();
                c.setId(cursor.getLong(0));
                c.setNome(cursor.getString(1));
                c.setNumero(cursor.getString(2));

                list.add(c);

            } while (cursor.moveToNext());
        }
        //bd.close();
        return (list);
    }

    public boolean buscarUm(String numero) {

        String[] colunas = new String[] { "_id"};
        String where =" numero='"+numero+"'" ;
        Cursor cursor = bd.query("contato", colunas,where, null, null, null, "nome ");

        if (cursor.getCount() > 0) {
         return true;
        }
        //bd.close();
        return false;
    }

    public void inserir(Contato c) {
		ContentValues valores = new ContentValues();
		valores.put("nome", c.getNome());
		valores.put("numero", c.getNumero());

		bd.insert("contato", null, valores);
	}

    public void atualizar(Contato c) {
        ContentValues valores = new ContentValues();
        valores.put("nome", c.getNome());
        valores.put("numero",c.getNumero());


        bd.update("contato", valores, "_id = ?", new String[] { "" + c.getId() });


    }
    public void deletar(Contato c) {
		bd.delete("contato", "_id = " + c.getId(), null);
	}


    public  void fecharConexao(){
        bd.close();
    }
}
