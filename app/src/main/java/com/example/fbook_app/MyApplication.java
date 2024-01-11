package com.example.fbook_app;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {
    public static final String ID="push_notification_id";

    @Override
    public void onCreate() {
        super.onCreate();

        createChannelNotification();
    }

    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(ID,"PushNotification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
