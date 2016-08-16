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
public class BDConexoesContatos {
    private SQLiteDatabase bd;

    public BDConexoesContatos(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }




    public List<ContatoConexoes> buscar() {

        List<ContatoConexoes> list = new ArrayList<ContatoConexoes>();
        String[] colunas = new String[] { "_id", "nome", "numero","profissao","uf","email","sobre","foto"};
       // String where = condicao ;
        Cursor cursor = bd.query("contatoConexoes", colunas,null, null, null, null, "nome");
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {

                    ContatoConexoes c = new ContatoConexoes();
                    c.setId(cursor.getLong(0));
                    c.setNome(cursor.getString(1));
                    c.setNumero(cursor.getString(2));
                    c.setProfissao(cursor.getString(3));
                    c.setUf(cursor.getString(4));
                    c.setEmail(cursor.getString(5));
                    c.setSobre(cursor.getString(6));
                    c.setFoto(cursor.getString(7));
                    list.add(c);

                } while (cursor.moveToNext());
            }
        }finally {
            cursor.close();
        }
        //bd.close();
        return (list);
    }

    public boolean buscarEmail(String email) {

        String[] colunas = new String[] { "_id"};
         String where = " email='"+email+"'" ;
        Cursor cursor = bd.query("contatoConexoes", colunas,where, null, null, null, "nome");
        try {
            if (cursor.getCount() > 0) {
                return true;
            }
        }finally {
            cursor.close();
        }
        return false;
    }

    public void inserir(ContatoConexoes c) {
		ContentValues valores = new ContentValues();
		valores.put("nome", c.getNome());
		valores.put("numero", c.getNumero());
        valores.put("email", c.getEmail());
        valores.put("sobre", c.getSobre());
        valores.put("uf", c.getUf());
        valores.put("foto", c.getFoto());
        valores.put("profissao", c.getProfissao());

        bd.insert("contatoConexoes", null, valores);
	}

    public void atualizar(ContatoConexoes c) {
        ContentValues valores = new ContentValues();
        valores.put("nome", c.getNome());
        valores.put("numero", c.getNumero());
        valores.put("email", c.getEmail());
        valores.put("sobre", c.getSobre());
        valores.put("uf", c.getUf());
        valores.put("foto", c.getFoto());
        valores.put("profissao", c.getProfissao());
        bd.update("contatoConexoes", valores, "email = ?", new String[] { "" + c.getEmail() });
    }
    /*public void atualizar(Contato c) {
        ContentValues valores = new ContentValues();
        valores.put("nome", c.getNome());
        valores.put("numero",c.getNumero());


        bd.update("contato", valores, "_id = ?", new String[] { "" + c.getId() });

    }*/
    public void deletar(ContatoConexoes c) {
		bd.delete("contatoConexoes", "_id = " + c.getId(), null);
	}


    public  void fecharConexao(){
        bd.close();
    }
}
