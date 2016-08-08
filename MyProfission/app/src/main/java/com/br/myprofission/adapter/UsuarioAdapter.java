package com.br.myprofission.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.br.myprofission.ChatActivity;
import com.br.myprofission.R;
import com.br.myprofission.app.AppController;
import com.br.myprofission.dao.Contato;
import com.br.myprofission.dao.Usuario;
import com.br.myprofission.util.CircularNetworkImageView;
import com.br.myprofission.util.Utilitaria;

import java.util.List;

/**
 * Created by LENOVO on 09/07/2016.
 */
public class UsuarioAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Usuario> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public UsuarioAdapter(Activity activity, List<Usuario> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.usuarios, null);


        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        /*NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
*/
        TextView nome = (TextView) convertView.findViewById(R.id.nome);
        TextView numero = (TextView) convertView.findViewById(R.id.numero);
        TextView profissao = (TextView) convertView.findViewById(R.id.profissao);
        TextView distancia = (TextView) convertView.findViewById(R.id.distancia);
        TextView cid_uf = (TextView) convertView.findViewById(R.id.cid_uf);

        ImageView img_detalhes= (ImageView) convertView.findViewById(R.id.img_detalhes);
        ImageView img_ligar= (ImageView) convertView.findViewById(R.id.img_ligar);

        final Usuario u = movieItems.get(position);

        CircularNetworkImageView image = (CircularNetworkImageView) convertView.findViewById(R.id.image);

        image.setImageUrl(u.getFoto(), imageLoader);

        nome.setText(u.getNome().trim());
        numero.setText(u.getNumero());
        profissao.setText(u.getProfissao().trim());
        distancia.setText("Distante: "+u.getDistanciaEntreUsuarios()+" KM");
        if(!u.getCidade().isEmpty()) {
            cid_uf.setVisibility(View.VISIBLE);
            cid_uf.setText(u.getCidade() + "-" + u.getUf());
        }


        // thumbnail image
        //thumbNail.setImageUrl(u.getFoto(), imageLoader);
        img_detalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilitaria.janelaPoupUp(activity,u.getNome().trim(),u.getProfissao().trim(),u.getNumero(),u.getEmail(),u.getSobre());
            }});

        img_ligar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chamada(u.getNumero(),activity);
            }
        });

        return convertView;
    }
    public void Chamada(String tel, Context context) {//chama o discador do telefone

        Uri call = Uri.parse("tel:"+tel);
        Intent i = new Intent(Intent.ACTION_DIAL, call);

        context.startActivity(i);
    }
}


