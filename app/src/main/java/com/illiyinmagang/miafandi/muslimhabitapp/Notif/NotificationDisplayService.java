package com.illiyinmagang.miafandi.muslimhabitapp.Notif;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.MainActivity;

public class NotificationDisplayService extends Service {
    final int NOTIFICATION_ID = 16;
    public NotificationDisplayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        displayNotification("Notifikasi Sholat","Allah meminta hambanya untuk mensegerakan sholat");
        return super.onStartCommand(intent, flags, startId);
    }

    public void displayNotification (String title, String text){

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this).
                setContentTitle(title).
                setContentText(text).
                setSmallIcon(R.drawable.iconshlat).
                setColor(getResources().getColor(R.color.colorPrimary)).
                setVibrate(new long[]{0,300,300,300}).
                //setDefaults(145000).
                setTimeoutAfter(145000).
                setSound(Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.adzan)).
                setLights(Color.WHITE,1000,145000).
                //setWhen(System.currentTimeMillis())
                setContentIntent(pendingIntent).
                setAutoCancel(true).
                setPriority(NotificationCompat.PRIORITY_MAX).
                setStyle(new NotificationCompat.BigTextStyle().bigText(text));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,notification.build());
    }
}
