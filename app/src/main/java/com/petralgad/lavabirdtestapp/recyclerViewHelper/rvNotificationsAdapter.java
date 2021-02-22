package com.petralgad.lavabirdtestapp.recyclerViewHelper;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.petralgad.lavabirdtestapp.model.MyNotificationModel;

import java.util.ArrayList;

public class rvNotificationsAdapter extends ListAdapter<MyNotificationModel, NotificationViewHolder> {

    public rvNotificationsAdapter(@NonNull DiffUtil.ItemCallback<MyNotificationModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return NotificationViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        MyNotificationModel current = getItem(position);
        holder.bind(current);
    }

    public static class WordDiff extends DiffUtil.ItemCallback<MyNotificationModel>{

        @Override
        public boolean areItemsTheSame(@NonNull MyNotificationModel oldItem, @NonNull MyNotificationModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MyNotificationModel oldItem, @NonNull MyNotificationModel newItem) {
            return oldItem.getNotificationText().equals(newItem.getNotificationText());
        }
    }
}
