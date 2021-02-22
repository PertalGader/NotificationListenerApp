package com.petralgad.lavabirdtestapp.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.petralgad.lavabirdtestapp.dao.NotificationDao;
import com.petralgad.lavabirdtestapp.model.MyNotificationModel;

import java.util.List;

public class NotificationsRepository {
    private NotificationDao notificationDao;
    private LiveData<List<MyNotificationModel>> notifications;

    public NotificationsRepository(Application application) {
        NotificationsRoomDatabase db = NotificationsRoomDatabase.getDatabase(application);
        notificationDao = db.notificationDao();
        notifications = notificationDao.getAllNotifications();
    }

    public LiveData<List<MyNotificationModel>> getAllNotifications() {
        return notifications;
    }

    public void insert(MyNotificationModel notification) {
        NotificationsRoomDatabase.databaseWriteExecutor.execute(() -> {
            notificationDao.insert(notification);
        });
    }
}
