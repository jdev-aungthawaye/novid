package software.techbase.novid.component.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import software.techbase.novid.R;
import software.techbase.novid.ui.activity.MainActivity;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class ServiceNotification {

    public static final String NOTIFICATION_CHANNEL_ID = "software.techbase.novid.tracking";

    public static Notification setNotification(Context context) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NovidChannel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Novid tracking.");
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        // Flag indicating that if the described PendingIntent already exists, the current one should be canceled before generating a new one.
        PendingIntent action = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);

        return builder
                .setContentTitle(context.getString(R.string.app_name))
                .setTicker(context.getString(R.string.app_name))
                .setContentText("Novid is running.")
                .setSmallIcon(R.drawable.ic_my_location_black)
                .setContentIntent(action)
                .setOngoing(true)
                .build();
    }
}
