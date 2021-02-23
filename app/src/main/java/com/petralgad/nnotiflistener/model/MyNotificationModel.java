package com.petralgad.nnotiflistener.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notifications_table")
public class MyNotificationModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "appIcon")
    private String appIcon;

    @ColumnInfo(name = "appIconId")
    private String appIconId;

    @ColumnInfo(name = "appName")
    private String appName;

    @ColumnInfo(name = "notificationTime")
    private String notificationTime;

    @ColumnInfo(name = "notificationText")
    private String notificationText;

    public MyNotificationModel(String appIcon, String appName, String notificationTime, String notificationText, String appIconId) {
        this.appIcon = appIcon;
        this.appName = appName;
        this.notificationTime = notificationTime;
        this.notificationText = notificationText;
        this.appIconId = appIconId;
    }

    public String getAppIconId() {
        return appIconId;
    }

    public void setAppIconId(String appIconId) {
        this.appIconId = appIconId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}
