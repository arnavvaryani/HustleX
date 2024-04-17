package com.psa.hustlex.arnav.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.psa.hustlex.R;
import com.psa.hustlex.arnav.models.Reminders;
import com.psa.hustlex.arnav.database.AppDatabase;
import com.psa.hustlex.arnav.screens.ReminderScreen;

import java.util.Date;

public class NotifierAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);

        Intent notificationIntent = new Intent(context, ReminderScreen.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_channel_01")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Reminder")
                .setContentText(intent.getStringExtra("Message"))
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel(Context context) {
            CharSequence name = "Reminder Notifications";
            String description = "Includes all the reminders set for tasks";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("my_channel_01", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
    }
}
