package com.br.myprofission.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.br.myprofission.R;
import com.br.myprofission.dao.Chat;
import com.firebase.client.Query;

/**
 * Created by LENOVO on 22/07/2016.
 */
public  class ChatListAdapter extends FirebaseListAdapter<Chat> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Chat.class, layout, activity);
        this.mUsername = mUsername;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Chat chat) {


        // Map a Chat object to an entry in our listview

        String author = chat.getAuthor();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        TextView txt_msg = (TextView) view.findViewById(R.id.message);
        authorText.setText(author + ": ");
        // If the message was sent by this user, color it differently
        if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.WHITE);
            //authorText.setLayoutParams(params);

            authorText.setBackgroundColor(Color.rgb(30,144,255));
            txt_msg.setBackgroundColor(Color.rgb(30,144,255));
            txt_msg.setTextColor(Color.WHITE);

        } else {
            authorText.setTextColor(Color.BLACK);
            authorText.setBackgroundColor(Color.rgb(211,211,211));
            txt_msg.setBackgroundColor(Color.rgb(211,211,211));
            txt_msg.setTextColor(Color.BLACK);
           // authorText.setGravity(View.FOCUS_RIGHT);
        }
        //((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());


       txt_msg.setText(chat.getMessage());

    }


}
