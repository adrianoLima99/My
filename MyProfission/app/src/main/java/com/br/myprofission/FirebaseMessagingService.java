package com.br.myprofission;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.br.myprofission.util.Utilitaria;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by LENOVO on 02/08/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        ShowNotification(remoteMessage.getData().get("message"));

    }

    private void ShowNotification(String message) {
        Log.i("msg",message);
        String conteudo[]=message.trim().split("-");
        String sala=conteudo[0];
        String emissor=conteudo[1];
        String receptador=conteudo[2];
       // Toast.makeText(FirebaseMessagingService.this, "emissor="+emissor+"\nreceptador="+receptador, Toast.LENGTH_SHORT).show();
        if(receptador.equals(Utilitaria.retornaEmail(FirebaseMessagingService.this))) {//se o receptador for este usuario
            //Log.i("receptaor",receptador);
            Intent i = new Intent(this, ChatActivity.class);
            i.putExtra("email",emissor);//o email do emissor
            i.putExtra("nomeSala",sala);//recebe o emissor e receptador pra saber qual sala abri
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("Você tem 1 nova mensagem")
                    .setContentText(emissor +" lhe enviou uma mensagem")
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }
    }
}
