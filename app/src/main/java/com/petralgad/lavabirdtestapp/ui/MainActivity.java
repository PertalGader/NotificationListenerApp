package com.petralgad.lavabirdtestapp.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.petralgad.lavabirdtestapp.R;
import com.petralgad.lavabirdtestapp.model.MyNotificationModel;
import com.petralgad.lavabirdtestapp.recyclerViewHelper.rvNotificationsAdapter;
import com.petralgad.lavabirdtestapp.recyclerViewHelper.EndlessRecyclerViewScrollListener;
import com.petralgad.lavabirdtestapp.service.MyNotificationListenerService;
import com.petralgad.lavabirdtestapp.viewModels.NotificationViewModel;

import java.io.Serializable;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    Toolbar toolbar;
    RecyclerView rvNotifications;
    TextView tvNotificationsMissing;
    Button btTracking;

    rvNotificationsAdapter rvNotificationsAdapter;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecoration;
    NotificationViewModel notificationViewModel;

    NotificationReceivedBroadcastReceiver notificationReceiver;
    MyNotificationListenerService notificationListenerService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //init all views
        initViews();

        //checking if there exists notifications
        checkWhetherNotificationsExists();

        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        rvNotificationsAdapter = new rvNotificationsAdapter(new rvNotificationsAdapter.WordDiff());
        linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_24dp));
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void show() {
                btTracking.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) btTracking.getLayoutParams();
                btTracking.animate().translationY(btTracking.getHeight()+lp.bottomMargin).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        };

        rvNotifications.setLayoutManager(linearLayoutManager);
        rvNotifications.addOnScrollListener(endlessRecyclerViewScrollListener);
        rvNotifications.addItemDecoration(dividerItemDecoration);
        rvNotifications.setAdapter(rvNotificationsAdapter);

        notificationViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(NotificationViewModel.class);

        notificationViewModel.getAllWords().observe(this, myNotificationModels -> rvNotificationsAdapter.submitList(myNotificationModels));



        notificationReceiver = new NotificationReceivedBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getPackageName());
        registerReceiver(notificationReceiver, intentFilter);


    }

    private void startOrStopService(){
        Intent service = new Intent(this, NotificationListenerService.class);

        startService(service);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btTracking.setOnClickListener(v -> startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS)));
        if(isNotificationServiceEnabled()){
            btTracking.setText("Stop");
        }else{
            btTracking.setText("Start");
        }
    }

    private static boolean isNotificationListenerServiceEnabled(Context context) {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);
        if (packageNames.contains(context.getPackageName())) {
            return true;
        }
        return false;
    }

    private void initViews(){
        toolbar = findViewById(R.id.toolbar);
        rvNotifications = findViewById(R.id.rvnotifications);
        tvNotificationsMissing = findViewById(R.id.tv_notifications_missing);
        btTracking = findViewById(R.id.bt_stop_start_tracking);
    }

    private void checkWhetherNotificationsExists(){
        if(true){
            rvNotifications.setVisibility(View.VISIBLE);
            tvNotificationsMissing.setVisibility(View.GONE);
        }else{
            rvNotifications.setVisibility(View.GONE);
            tvNotificationsMissing.setVisibility(View.VISIBLE);
        }
    }
    private boolean isNotificationServiceEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public class NotificationReceivedBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //insert into db
            MyNotificationModel sbn = (MyNotificationModel)intent.getSerializableExtra("notification");
            notificationViewModel.insert(sbn);
            Log.e("NotificationRec","sbn.getNotification().tickerText.toString()");
        }
    }
}
