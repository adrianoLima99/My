package com.br.myprofission.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
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
public class ContatoAdapter  extends BaseAdapter {
    private AlertDialog alerta;
    private Activity activity;
    private LayoutInflater inflater;
    private List<Contato> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ContatoAdapter(Activity activity, List<Contato> movieItems) {
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
            convertView = inflater.inflate(R.layout.contatos, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        /*NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
*/
        TextView nome = (TextView) convertView.findViewById(R.id.nome);
        TextView numero = (TextView) convertView.findViewById(R.id.numero);
        TextView profissao = (TextView) convertView.findViewById(R.id.profissao);
        //Button btn_detalhes = (Button)convertView.findViewById(R.id.btn_detalhes);
        ImageView img_detalhes= (ImageView) convertView.findViewById(R.id.img_detalhes);
        //ImageView img_msg= (ImageView) convertView.findViewById(R.id.img_msg);
        ImageView img_ligar= (ImageView) convertView.findViewById(R.id.img_ligar);

        final Contato c = movieItems.get(position);

        CircularNetworkImageView image = (CircularNetworkImageView) convertView.findViewById(R.id.image);

        image.setImageUrl(c.getFoto(), imageLoader);

        nome.setText(c.getNome());
        numero.setText(c.getNumero());
        profissao.setText(c.profissao.getProfissao());

        img_detalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Utilitaria.janelaPoupUp(activity,c.getNome(),c.profissao.getProfissao(),c.getNumero(),"","");
                }});

        img_ligar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chamada(c.getNumero(),activity);
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


