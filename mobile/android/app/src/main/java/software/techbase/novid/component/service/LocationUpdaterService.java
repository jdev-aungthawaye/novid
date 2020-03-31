package software.techbase.novid.component.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;

import java.util.Objects;

import software.techbase.novid.R;
import software.techbase.novid.component.android.broadcast.NetworkStatusBroadcastReceiver;
import software.techbase.novid.component.android.notifications.XNotificationConstants;
import software.techbase.novid.component.android.notifications.XNotificationManager;
import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.domain.location.CurrentLocation;
import software.techbase.novid.domain.location.LocationUtils;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class LocationUpdaterService extends Service {

    private Handler handler;
    private Runnable runnable;
    private final long DATA_SEND_PERIOD = 3000;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Objects.requireNonNull(intent.getAction()).contains(Constants.ACTION.START_ACTION)) {

            super.startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, ServiceNotification.setNotification(getApplicationContext()));

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {

                    CurrentLocation.getCurrentLocation(getApplicationContext(), location -> {

                        XLogger.debug(this.getClass(), "Your are now in : " + LocationUtils.getAddressName(getApplicationContext(), location.getLatitude(), location.getLongitude()));

                        sendData(getApplicationContext(), location);
                    });
                    handler.postDelayed(this, DATA_SEND_PERIOD);
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

    private void sendData(Context mContext, Location location) {

        if (NetworkStatusBroadcastReceiver.isInternetAvailable(mContext)) {
            //SendData
        } else {
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            XNotificationManager.notify(mContext,
                    mContext.getString(R.string.app_name),
                    "Please open internet.",
                    XNotificationConstants.SERVICE_CHANNEL_ID,
                    XNotificationConstants.LOCATION_REQUEST_NOTIFICATION_ID,
                    intent);
        }
    }
}
