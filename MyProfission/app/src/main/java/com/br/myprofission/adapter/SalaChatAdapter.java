package com.br.myprofission.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.br.myprofission.R;
import com.br.myprofission.app.AppController;
import com.br.myprofission.dao.Contato;
import com.br.myprofission.dao.SalaChat;
import com.br.myprofission.util.CircularNetworkImageView;
import com.br.myprofission.util.Utilitaria;

import java.util.List;

/**
 * Created by LENOVO on 09/07/2016.
 */
public class SalaChatAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<SalaChat> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public SalaChatAdapter(Activity activity, List<SalaChat> movieItems) {
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
            convertView = inflater.inflate(R.layout.sala_chat, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        /*NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
*/
        TextView nome = (TextView) convertView.findViewById(R.id.txt_nome);
        TextView nome_exibicao = (TextView) convertView.findViewById(R.id.txt_nome_exibicao);


        final SalaChat s = movieItems.get(position);

        CircularNetworkImageView image = (CircularNetworkImageView) convertView.findViewById(R.id.image);

        image.setImageUrl(s.getCaminhoImg(), imageLoader);

        nome.setText("caminho="+s.getCaminhoImg());
        //nome.setText(s.getNome());
        nome_exibicao.setText(s.getNomeExibicao());


        return convertView;
    }


}


