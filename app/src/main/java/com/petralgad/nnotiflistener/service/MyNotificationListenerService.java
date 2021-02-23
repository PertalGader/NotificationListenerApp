package com.petralgad.nnotiflistener.service;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.petralgad.nnotiflistener.model.MyNotificationModel;

public class MyNotificationListenerService extends NotificationListenerService {

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Intent intent = new Intent(getPackageName());
        MyNotificationModel notificationModel = new MyNotificationModel(sbn.getPackageName(),
                sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TITLE).toString(),
                "time",
                sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString(),
                String.valueOf(sbn.getNotification().icon));
        
        intent.putExtra("notification",notificationModel);

        sendBroadcast(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    }
}
