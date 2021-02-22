package com.petralgad.lavabirdtestapp.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.petralgad.lavabirdtestapp.db.NotificationsRepository;
import com.petralgad.lavabirdtestapp.model.MyNotificationModel;

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
