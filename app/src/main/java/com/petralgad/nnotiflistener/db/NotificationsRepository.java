package com.petralgad.nnotiflistener.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.petralgad.nnotiflistener.dao.NotificationDao;
import com.petralgad.nnotiflistener.model.MyNotificationModel;

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
