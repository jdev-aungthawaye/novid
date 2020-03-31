package software.techbase.novid.component.android.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import software.techbase.novid.R;
import software.techbase.novid.ui.activity.MainActivity;

/**
 * Created by Wai Yan on 12/4/18.
 */
public class XNotificationManager {

    private NotificationCompat.Builder builder;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context context,
                                                 String channelId,
                                                 String channelName,
                                                 String description) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setShowBadge(true);
        channel.setVibrationPattern(new long[]{500});

        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void notify(Context context,
                              String title,
                              String content,
                              String channelId,
                              int notificationId,
                              Intent resultIntent) {

        Intent backIntent = new Intent(context, MainActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivities(context, notificationId, new Intent[]{backIntent, resultIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert mNotificationManager != null;
        mNotificationManager.notify(notificationId, builder.build());
    }

    public static void cancelAll(Context context) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();
    }
}
