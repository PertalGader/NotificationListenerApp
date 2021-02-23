package com.petralgad.nnotiflistener.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.petralgad.nnotiflistener.dao.NotificationDao;
import com.petralgad.nnotiflistener.model.MyNotificationModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MyNotificationModel.class}, version = 1, exportSchema = false)
public abstract class NotificationsRoomDatabase extends RoomDatabase {

    public abstract NotificationDao notificationDao();
    private static volatile NotificationsRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
    static NotificationsRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (NotificationsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotificationsRoomDatabase.class, "notifications_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                NotificationDao dao = INSTANCE.notificationDao();
                dao.deleteAll();

                MyNotificationModel notificationModel = new MyNotificationModel(null,"Test","Test", "Test","Test");
                dao.insert(notificationModel);
            });
        }
    };
}
