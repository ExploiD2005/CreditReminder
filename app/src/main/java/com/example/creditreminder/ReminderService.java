package com.example.creditreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;

import java.text.DateFormat;
import java.util.Date;
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
    long interval = 10000;
    NotificationManagerCompat notificationManagerCompat;
    NotificationManager notificationManager;
    List<Credit> credits;
    private String[] creditsString;

    //ExecutorService es;
    //private final int NOTIFICATION_ID = 111;

    @Override
    public void onCreate() {
        //timer = new Timer();
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        } else {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        //es = Executors.newFixedThreadPool(1);
        //nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        //showNotification();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //showNotification();
        //scheduleTask(intent);
        return binder;
    }


    private void scheduleTask(Intent intent) {
        timer = new Timer();
        if (timerTask != null) timerTask.cancel();
        credits = (List<Credit>) intent.getSerializableExtra(Credit.class.getSimpleName());
        if (interval > 0) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    String ticker = "0", titleNotification = "0", textNotification = "0", fullNotification, longTextNotification = "0";
                    //credits = (List<Credit>) intent.getSerializableExtra(Credit.class.getSimpleName());
                    int sizeOfCredits = credits.size();
                    boolean firstRun = true;
                    if (!credits.isEmpty()) {
                        //creditsString = new String[credits.size()];
                        for (int i = 0; i < sizeOfCredits; i++) {
                            int id = credits.get(i).getId();
                            String title = credits.get(i).getTitle();
                            Date last_pay_day = credits.get(i).getLast_pay_date();
                            double full_ammount_of_payment = credits.get(i).getFull_amount_of_payment();
                            double min_ammount_of_payment = credits.get(i).getMin_amount_of_payment();
                            if (sizeOfCredits == 1) {
                                titleNotification = title;
                                textNotification = "Оплатить до " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_day.getTime()) + " " + full_ammount_of_payment + "р.";
                                ticker = title +  ": Оплатить до " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_day.getTime());
                                showNormalNotificationOnChannel1(1, ticker, titleNotification, textNotification);
                            }
                            if (sizeOfCredits > 1) {
                                //longTextNotification = longTextNotification + "\n";
                                if (!firstRun) {
                                    //if (i == sizeOfCredits) {
                                    //    longTextNotification = longTextNotification + title + ": Оплатить до " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_day.getTime()) + " " + full_ammount_of_payment + "р.";
                                    //} else {
                                        longTextNotification = longTextNotification + title + ": Оплатить до " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_day.getTime()) + " " + full_ammount_of_payment + "р.\n";
                                    //}
                                }
                                if (firstRun) {
                                    titleNotification = title +  ": Оплатить до " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_day.getTime()) + " " + full_ammount_of_payment + "р.";
                                    longTextNotification = "";
                                    ticker = "Несколько платежей";
                                    firstRun = false;
                                }
                                showLongNotificationOnChannel1(1, ticker, titleNotification, longTextNotification);
                            }


                            //if(i == 0) {

                            //}
                        }
                    }
                }
            };
            timer.schedule(timerTask, 5000, interval);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //String ticker = "0", titleNotification = "0", textNotification = "0", fullNotification, longTextNotification = "0";
        //credits = (List<Credit>) intent.getSerializableExtra(Credit.class.getSimpleName());
        scheduleTask(intent);
        //int sizeOfCredits = credits.size();
        //boolean firstRun = true;
        //if (!credits.isEmpty()) {
            //creditsString = new String[credits.size()];
        //    for (int i = 0; i < sizeOfCredits; i++) {
        //        int id = credits.get(i).getId();
        //        String title = credits.get(i).getTitle();
        //        Date last_pay_day = credits.get(i).getLast_pay_date();
        //        double full_ammount_of_payment = credits.get(i).getFull_amount_of_payment();
        //        double min_ammount_of_payment = credits.get(i).getMin_amount_of_payment();
        //        if (sizeOfCredits == 1) {
        //            titleNotification = title;
        //            textNotification = "Оплатить до " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_day.getTime()) + " " + full_ammount_of_payment + "р.";
        //            ticker = title +  ": Оплатить до " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_day.getTime());
        //            showNormalNotificationOnChannel1(1, ticker, titleNotification, textNotification);
        //        }
        //        if (sizeOfCredits > 1) {
        //            if (!firstRun) {
        //                longTextNotification = longTextNotification + title +  ": Оплатить до " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_day.getTime()) + " " + full_ammount_of_payment + "р. \n";
        //            }
        //            if (firstRun) {
        //                titleNotification = title +  ": Оплатить до " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(last_pay_day.getTime()) + " " + full_ammount_of_payment + "р.";
        //                longTextNotification = "";
        //                ticker = "Несколько платежей";
        //                firstRun = false;
        //            }
        //            showLongNotificationOnChannel1(1, ticker, titleNotification, longTextNotification);
        //        }


                //if(i == 0) {

                //}
        //    }
        //}
        //showNotificationOnChannel1(1, "Напоминание", "Сбербанк: Оплатить до 20.01.2020", "Потяните вниз чтобы увидеть еще платежи");
        //showNotificationOnChannel2(2, "Напоминание", "Тинькоф", "Оплатить до 17.02.2020");
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNormalNotificationOnChannel1(int notification_id, String ticker, String title, String text) {
        NotificationCompat.Builder notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder(this, CHANNEL_1_ID);
        } else {
            notification = new NotificationCompat.Builder(this);
        }
        //Intent intent = new Intent(getApplicationContext(), OnNotificationClickActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Intent intentPayment = new Intent(getApplicationContext(), OnNotificationClickActivity.class);
        intentPayment.putExtra("Payment", true);
        PendingIntent pendingIntentPayment = PendingIntent.getActivity(getApplicationContext(), 0, intentPayment, PendingIntent.FLAG_CANCEL_CURRENT);

        notification
                //.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_schedule)
                //.setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.ic_schedule))
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(text)
                .addAction(R.drawable.ic_payment, "Оплачено", pendingIntentPayment);
                //.setContentInfo("test")
                //.setOngoing(true)
                //.setVibrate(long[] pattern)
                //.setPriority(-2 2)
                //--------------------------
                //.setGroup(String GROUP_KEY)
                //.setGroupSummary(true)
                //.setSortKey("")
                //--------------------------
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(long_text));
        //Notification notification = builder.build();
        notificationManagerCompat.notify(notification_id, notification.build());
        //notificationManagerCompat.cancel(notification_id);
        //notificationManagerCompat.cancelAll();
    }

    private void showLongNotificationOnChannel1(int notification_id, String ticker, String title, String long_text) {
        NotificationCompat.Builder notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder(this, CHANNEL_1_ID);
        } else {
            notification = new NotificationCompat.Builder(this);
        }
        //Intent intent = new Intent(getApplicationContext(), OnNotificationClickActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Intent intentPayment = new Intent(getApplicationContext(), OnNotificationClickActivity.class);
        intentPayment.putExtra("ViewCredits", true);
        PendingIntent pendingIntentPayment = PendingIntent.getActivity(getApplicationContext(), 1, intentPayment, PendingIntent.FLAG_CANCEL_CURRENT);

        notification
                //.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_schedule)
                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.ic_schedule))
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText("Потяните вниз чтобы увидеть все платежи")
                .addAction(R.drawable.ic_payment, "Просмотреть платежи", pendingIntentPayment)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(long_text));
        //.setContentInfo("test")
        //.setOngoing(true)
        //.setVibrate(long[] pattern)
        //.setPriority(-2 2)
        //--------------------------
        //.setGroup(String GROUP_KEY)
        //.setGroupSummary(true)
        //.setSortKey("")
        //--------------------------

        /*notification
                //.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_schedule)
                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.ic_schedule))
                .setTicker(ticker)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(text)
                .addAction(R.drawable.ic_payment, "Оплачено", pendingIntentPayment)
                //.setContentInfo("test")
                //.setOngoing(true)
                //.setVibrate(long[] pattern)
                //.setPriority(-2 2)
                //--------------------------
                //.setGroup(String GROUP_KEY)
                //.setGroupSummary(true)
                //.setSortKey("")
                //--------------------------
                .setStyle(new NotificationCompat.BigTextStyle().bigText(long_text));*/
        //Notification notification = builder.build();
        notificationManagerCompat.notify(notification_id, notification.build());
        //notificationManagerCompat.cancel(notification_id);
        //notificationManagerCompat.cancelAll();
    }

    /*private void showNotificationOnChannel2(int notification_id, String ticker, String title, String text) {
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
    }*/

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
