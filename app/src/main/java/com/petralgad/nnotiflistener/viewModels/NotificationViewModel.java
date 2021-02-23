package com.petralgad.nnotiflistener.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.petralgad.nnotiflistener.db.NotificationsRepository;
import com.petralgad.nnotiflistener.model.MyNotificationModel;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {
    private NotificationsRepository notificationsRepository;
    private final LiveData<List<MyNotificationModel>> notifications;

    public NotificationViewModel (Application application) {
        super(application);
        notificationsRepository = new NotificationsRepository(application);
        notifications = notificationsRepository.getAllNotifications();
    }

    public LiveData<List<MyNotificationModel>> getAllWords() { return notifications; }

    public void insert(MyNotificationModel notification) { notificationsRepository.insert(notification); }
}
