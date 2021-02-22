package com.petralgad.lavabirdtestapp.recyclerViewHelper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petralgad.lavabirdtestapp.R;
import com.petralgad.lavabirdtestapp.model.MyNotificationModel;

import org.w3c.dom.Text;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    private final ImageView ivAppIcon;
    private final TextView tvAppName, tvNotificationMessage, tvNotificationTime, tvNotificationDate;
    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        ivAppIcon = itemView.findViewById(R.id.iv_app_icon);
        tvAppName = itemView.findViewById(R.id.tv_app_name);
        tvNotificationMessage = itemView.findViewById(R.id.tv_notification_message);
        tvNotificationTime = itemView.findViewById(R.id.tv_notification_time);
        tvNotificationDate = itemView.findViewById(R.id.tv_notification_date);
    }
    public void bind(MyNotificationModel notification){
        tvAppName.setText(notification.getAppName());
        tvNotificationDate.setText(notification.getNotificationTime());
        tvNotificationTime.setText(notification.getNotificationTime());
        tvNotificationMessage.setText(notification.getNotificationText());

        String pack = notification.getAppIcon();
        Context remotePackageContext = null;
        Bitmap bmp = null;
        try {
            PackageManager manager = itemView.getContext().getPackageManager();
            Resources resources = manager.getResourcesForApplication(pack);
            Log.e("pack",pack);
            Drawable icon = resources.getDrawable(Integer.parseInt(notification.getAppIconId()));
            if(icon != null) {
                bmp = ((BitmapDrawable) icon).getBitmap();
                ivAppIcon.setImageBitmap(bmp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static NotificationViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent,false);
        return new NotificationViewHolder(view);
    }
}
