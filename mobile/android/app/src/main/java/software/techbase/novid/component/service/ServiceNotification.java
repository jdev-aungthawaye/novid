package software.techbase.novid.component.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import software.techbase.novid.R;
import software.techbase.novid.component.android.notifications.XNotificationConstants;
import software.techbase.novid.ui.activity.MainActivity;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class ServiceNotification {

    public static Notification setNotification(Context context) {

        // Flag indicating that if the described PendingIntent already exists, the current one should be canceled before generating a new one.
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        return new NotificationCompat.Builder(context, XNotificationConstants.SERVICE_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.app_name))
                .setTicker(context.getString(R.string.app_name))
                .setContentText("Novid is running.")
                .setSmallIcon(R.drawable.ic_my_location_black)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
    }
}
