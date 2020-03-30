package software.techbase.novid.component.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.util.Objects;

import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.domain.location.CurrentLocation;
import software.techbase.novid.domain.location.LocationUtils;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class LocationUpdaterService extends Service {

    private Handler handler;
    private Runnable runnable;

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
}
