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
import androidx.lifecycle.ViewModelProviders;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.creditreminder.App.CHANNEL_1_ID;
import static com.example.creditreminder.App.CHANNEL_2_ID;

public class ReminderService extends Service {
    ReminderBinder binder = new ReminderBinder();
    Timer timer;
    TimerTask timerTask;
    long interval = 1000;
    NotificationManagerCompat nm;
    List<Credit> credits;
    //ExecutorService es;
    //private final int NOTIFICATION_ID = 111;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = NotificationManagerCompat.from(getApplicationContext());// getSystemService(NOTIFICATION_SERVICE);
        //timer = new Timer();
        //es = Executors.newFixedThreadPool(1);
        //scheduleTask();
        //Intent intent = getIIntent.getIntent();
        //credits = (Credit) intent.getSerializableExtra(Credit.class.getSimpleName());
        //nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        //showNotification();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //showNotification();
        return binder;
    }



    //private void scheduleTask() {
    //    if (timerTask != null) timerTask.cancel();
    //    if (interval > 0) {
    //        timerTask = new TimerTask() {
    //            @Override
    //            public void run() {
                    //
    //            }
    //        };
    //        timer.schedule(timerTask, 1000, interval);
    //    }
    //}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        showNotificationOnChannel1(1, "Напоминание", "Сбербанк: Оплатить до 20.01.2020", "Потяните вниз чтобы увидеть еще платежи");
        showNotificationOnChannel2(2, "Напоминание", "Тинькоф", "Оплатить до 17.02.2020");
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotificationOnChannel1(int notification_id, String ticker, String title, String text) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_1_ID);
        String long_text = "Тинькоф: Оплатить до 20.01.2020 \n" +
                            "Альфабанк: Оплатить до 03.02.2020 \n" +
                            "Халва: Оплатить до 26.01.2020";
        // NotificationCompat.Builder builder = new NotificationCompat(getApplicationContext(), CHANNEL_1_ID);
        Intent intent = new Intent(getApplicationContext(), OnNotificationClickActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        notification
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_schedule)
                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.ic_launcher_foreground))
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(long_text));
        //Notification notification = builder.build();
        nm.notify(notification_id, notification.build());
    }

    private void showNotificationOnChannel2(int notification_id, String ticker, String title, String text) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_2_ID);
        // NotificationCompat.Builder builder = new NotificationCompat(getApplicationContext(), CHANNEL_1_ID);
        Intent intent = new Intent(getApplicationContext(), OnNotificationClickActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        notification
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_schedule)
                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.ic_launcher_foreground))
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(text);
        //Notification notification = builder.build();
        nm.notify(notification_id, notification.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class  ReminderBinder extends Binder {
        ReminderService getService() {
            return ReminderService.this;
        }

    }

    //long upInterval(long gap) {
    //    interval = interval + gap;
    //    scheduleTask();
    //    return interval;
    //}

    public void setCredit(List<Credit> credits) {
        this.credits = credits;
    }

    //class getData implements Runnable {

    //}
}
