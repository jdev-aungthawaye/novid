package software.techbase.novid.component.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;

import software.techbase.novid.component.android.broadcast.NetworkStatusBroadcastReceiver;
import software.techbase.novid.domain.location.CurrentLocation;
import software.techbase.novid.domain.remote.client.XApplicationAPIClient;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class LocationUpdaterService extends Service {

    private Handler handler;
    private Runnable runnable;
    private final long DATA_SEND_PERIOD = 5 * 60 * 1000; //Every 5 minutes

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (action.contains(Constants.ACTION.START_ACTION)) {

                    super.startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, ServiceNotification.setNotification(getApplicationContext()));

                    handler = new Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            CurrentLocation.getCurrentLocation(getApplicationContext(), location -> sendData(getApplicationContext(), location));
                            handler.postDelayed(this, DATA_SEND_PERIOD);
                        }
                    };
                    handler.post(runnable);
                } else {
                    handler.removeCallbacks(runnable);
                    super.stopForeground(true);
                    super.stopSelf();
                }
            }
        }
        return Service.START_STICKY;
    }

    private void sendData(Context mContext, Location location) {

        if (NetworkStatusBroadcastReceiver.isInternetAvailable(mContext)) {
            XApplicationAPIClient.updateLocation(location.getLatitude(), location.getLongitude());
        }
    }
}
