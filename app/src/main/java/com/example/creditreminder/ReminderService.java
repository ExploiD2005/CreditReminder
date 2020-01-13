package com.example.creditreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.creditreminder.App.CHANNEL_1_ID;

public class ReminderService extends Service {
    ReminderBinder binder = new ReminderBinder();
    Timer timer;
    TimerTask timerTask;
    long interval = 1000;
    NotificationManagerCompat nm;
    private final int NOTIFICATION_ID = 111;

    final String LOG_TAG = "Reminder";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind: ");
        //showNotification();
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        //scheduleTask();
        nm = NotificationManagerCompat.from(getApplicationContext());// getSystemService(NOTIFICATION_SERVICE);
        //nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        //showNotification();
        Log.d(LOG_TAG, "onCreate: ");
    }

    private void scheduleTask() {
        if (timerTask != null) timerTask.cancel();
        if (interval > 0) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    //
                }
            };
            timer.schedule(timerTask, 1000, interval);
        }
        Log.d(LOG_TAG, "scheduleTask: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        showNotification();
        Log.d(LOG_TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification() {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_1_ID);
        // NotificationCompat.Builder builder = new NotificationCompat(getApplicationContext(), CHANNEL_1_ID);
        Intent intent = new Intent(getApplicationContext(), OnNotificationClickActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        notification
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.ic_launcher_foreground))
                .setTicker("Напоминание о платеже")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Сбербанк")
                .setContentText("Оплатить до 12.01.2020");
        //Notification notification = builder.build();
        nm.notify(NOTIFICATION_ID, notification.build());
        Log.d(LOG_TAG, "showNotification: ");
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy: ");
        super.onDestroy();
    }

    class  ReminderBinder extends Binder {
        ReminderService getService() {
            return ReminderService.this;
        }

    }

    long upInterval(long gap) {
        interval = interval + gap;
        scheduleTask();
        return interval;
    }
}
