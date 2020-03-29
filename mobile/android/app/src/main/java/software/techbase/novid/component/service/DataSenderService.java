package software.techbase.novid.component.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Objects;

import software.techbase.novid.R;
import software.techbase.novid.component.android.xlogger.XLoggerKt;
import software.techbase.novid.component.ble.BLEDevices;
import software.techbase.novid.ui.activity.MainActivity;
import software.techbase.novid.util.CurrentLocation;
import software.techbase.novid.util.LocationUtils;

/**
 * Created by Wai Yan on 3/29/20.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class DataSenderService extends Service {

    private Handler handler;
    private Runnable runnable;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Objects.requireNonNull(intent.getAction()).contains(Constants.ACTION.START_ACTION)) {

            super.startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, updateNotification());

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {

                    //Current Location
                    CurrentLocation.getCurrentLocation(getApplicationContext(), location -> {

                        XLoggerKt.debug("Your are now in : " + LocationUtils.getAddressName(getApplicationContext(), location.getLatitude(), location.getLongitude()));
                        //TODO Send data to server
                    });

                    //Nearby devices
                    BLEDevices.scanDevices(getApplicationContext(), macAddress -> {

                        XLoggerKt.debug("Nearby device : " + macAddress);
                        //TODO Send data to server
                    });

                    handler.postDelayed(this, 5000);
                }
            };
            handler.post(runnable);
        } else {
            handler.removeCallbacks(runnable);
            super.stopForeground(true);
            super.stopSelf();
        }
        return Service.START_STICKY;
    }

    private Notification updateNotification() {

        Context context = getApplicationContext();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, "NovidChannel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Novid tracking.");
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        // Flag indicating that if the described PendingIntent already exists, the current one should be canceled before generating a new one.
        PendingIntent action = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID);

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
