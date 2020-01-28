package com.example.creditreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class OnNotificationClickActivity extends AppCompatActivity {

    NotificationManagerCompat notificationManagerCompat;
    NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_notification_click);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        } else {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        Intent intent = getIntent();
        if (intent.getBooleanExtra("Payment", false) == true) {
            TextView textView = findViewById(R.id.textView);
            textView.setText("Нажата кнопка оплачено");
            notificationManagerCompat.cancelAll();
        }
        if (intent.getBooleanExtra("ViewCredits", false) == true) {
            TextView textView = findViewById(R.id.textView);
            textView.setText("Нажата кнопка Просмотреть платежи");
            notificationManagerCompat.cancelAll();
        }

    }
}
