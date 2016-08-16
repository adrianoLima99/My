package com.br.myprofission;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.br.myprofission.adapter.ChatListAdapter;
import com.br.myprofission.app.AppController;
import com.br.myprofission.dao.BDNotificacao;
import com.br.myprofission.dao.BDSala;
import com.br.myprofission.dao.Chat;
import com.br.myprofission.dao.SalaChat;
import com.br.myprofission.util.CircularNetworkImageView;
import com.br.myprofission.util.Utilitaria;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Random;

import okhttp3.internal.Util;

public class ChatActivity extends ListActivity {

    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://chatbusk.firebaseio.com/";

    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;
    private String emailProfissional;
    private String imgExibicao;
    private String[] emails;
    private String salaBatePapo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        mUsername=Utilitaria.retornaEmail(ChatActivity.this);
        CircularNetworkImageView image = (CircularNetworkImageView) findViewById(R.id.image);
        TextView txt_titulo= (TextView) findViewById(R.id.txt_titulo);
        ImageView img_voltar= (ImageView) findViewById(R.id.img_voltar);

        BDSala sl= new BDSala(ChatActivity.this);
        Intent intent=getIntent();
        if(intent!=null){

            Bundle params= intent.getExtras();
            if(params!=null) {
                emailProfissional = params.getString("email");
                imgExibicao=params.getString("img");
                Toast.makeText(ChatActivity.this, "emailProfissiona-" + emailProfissional, Toast.LENGTH_SHORT).show();
                Toast.makeText(ChatActivity.this, "img-" + imgExibicao, Toast.LENGTH_SHORT).show();
                //}
                // if(params.getString("nomeSala")!=null){//vem da lista de conversas ja criada
                salaBatePapo = params.getString("nomeSala");
                //}else{//aqui se criara uma nova conversa

                emails = Utilitaria.retornaEmail(ChatActivity.this).split("@");
                salaBatePapo = emails[0];
                emails = emailProfissional.split("@");
                salaBatePapo += emails[0];
                //sl.salvaServidor(ChatActivity.this, salaBatePapo, emailProfissional, Utilitaria.retornaEmail(this));
                //   }
                if(!sl.jaExiste(Utilitaria.retornaEmail(ChatActivity.this),emailProfissional)) {//se ja existe esse nome nao pode ser mais cadastrado
                    SalaChat s = new SalaChat();
                    s.setNome(salaBatePapo);
                    s.setNomeExibicao(emailProfissional);
                    s.setEmailDestino(emailProfissional);
                    s.setEmailorigem(Utilitaria.retornaEmail(this));
                    s.setCaminhoImg(imgExibicao);
                    sl.inserir(s);
                    sl.salvaServidor(ChatActivity.this,salaBatePapo,emailProfissional, Utilitaria.retornaEmail(this));
                }else{
                    salaBatePapo = sl.retornaSala(Utilitaria.retornaEmail(ChatActivity.this),emailProfissional);
                }
            }
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.tb_topo);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        if(imgExibicao!=null)
            image.setImageUrl(imgExibicao, imageLoader);

        txt_titulo.setText(emailProfissional);

        img_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        // Setup our Firebase mFirebaseRef

        mFirebaseRef = new Firebase(FIREBASE_URL).child(salaBatePapo);

        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatActivity.this, "para: "+emailProfissional, Toast.LENGTH_SHORT).show();
                sendMessage();
                //Toast.makeText(ChatActivity.this, "to enviando\n"+FirebaseInstanceId.getInstance().getToken()+","+Utilitaria.retornaEmail(ChatActivity.this)+"-"+emailProfissional, Toast.LENGTH_SHORT).show();
               /// Log.i("enviando","to enviando\n"+FirebaseInstanceId.getInstance().getToken()+","+Utilitaria.retornaEmail(ChatActivity.this)+"-"+emailProfissional);
                // Toast.makeText(ChatActivity.this, "token="+FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(ChatActivity.this, Utilitaria.retornaEmail(ChatActivity.this), Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                            }
                        });

                        BDNotificacao bd= new BDNotificacao(ChatActivity.this);
                        bd.notificacao(FirebaseInstanceId.getInstance().getToken(),Utilitaria.retornaEmail(ChatActivity.this),emailProfissional);

                    }
                }).start();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = getListView();
        // Tell our list adapter that we only want 50 messages at a time
        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_message, mUsername);
        listView.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(ChatActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

    /*private void setupUsername() {
        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        if (mUsername == null) {
            Random r = new Random();
            // Assign a random user name if we don't have one saved.
            mUsername = "JavaUser" + r.nextInt(100000);
            prefs.edit().putString("username", mUsername).commit();
        }
    }*/

    private void sendMessage() {
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername);///seta a msg e autor
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            inputText.setText("");
        }
    }

}
