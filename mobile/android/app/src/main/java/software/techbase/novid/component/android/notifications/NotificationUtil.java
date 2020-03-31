package software.techbase.novid.component.android.notifications;

import android.content.Context;

import software.techbase.novid.component.ui.base.BaseActivity;

/**
 * Created by Wai Yan on 3/26/20.
 */
public class NotificationUtil {

    public static void sendNotification(String messageTitle,
                                        String messageBody,
                                        int notificationId,
                                        Context context,
                                        Class<? extends BaseActivity> openingActivity) {

        XNotificationManager.notify(
                context,
                XNotificationManager.CHAT_CHANNEL_ID,
                messageTitle,
                messageBody,
                notificationId,
                openingActivity);
    }
}
