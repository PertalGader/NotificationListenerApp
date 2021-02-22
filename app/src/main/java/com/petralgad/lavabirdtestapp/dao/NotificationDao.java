package com.petralgad.lavabirdtestapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.petralgad.lavabirdtestapp.model.MyNotificationModel;

import java.util.List;
@Dao
public interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MyNotificationModel notification);

    @Query("SELECT * FROM notifications_table ORDER BY notificationTime ASC")
    LiveData<List<MyNotificationModel>> getAllNotifications();

    @Query("DELETE FROM notifications_table")
    void deleteAll();

}
