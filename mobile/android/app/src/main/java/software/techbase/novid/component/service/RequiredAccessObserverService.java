package software.techbase.novid.component.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import java.util.Objects;

import software.techbase.novid.component.android.broadcast.BluetoothStatusBroadcastReceiver;
import software.techbase.novid.component.android.broadcast.GPSStatusBroadcastReceiver;
import software.techbase.novid.component.android.broadcast.NetworkStatusBroadcastReceiver;
import software.techbase.novid.component.android.notifications.XNotificationConstants;
import software.techbase.novid.component.android.notifications.XNotificationManager;

/**
 * Created by Wai Yan on 4/2/20.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class RequiredAccessObserverService extends Service {

    private Handler handler;
    private Runnable runnable;
    private final long STATE_CHECKER_PERIOD = 30 * 1000;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Objects.requireNonNull(intent.getAction()).contains(Constants.ACTION.START_ACTION)) {

            super.startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, ServiceNotification.setNotification(getApplicationContext()));

            new BluetoothStatusBroadcastReceiver(getApplicationContext(), new BluetoothStatusBroadcastReceiver.BluetoothStatusListener() {
                @Override
                public void isBluetoothEnable() {

                }

                @Override
                public void isBluetoothDisable() {
                    enableBluetoothNotification();
                }
            });

            new GPSStatusBroadcastReceiver(getApplicationContext(), new GPSStatusBroadcastReceiver.LocationStatusListener() {

                @Override
                public void isLocationEnable() {

                }

                @Override
                public void isLocationDisable() {

                    enableLocationNotification();
                }
            });

            new NetworkStatusBroadcastReceiver(getApplicationContext(), new NetworkStatusBroadcastReceiver.NetworkStatusListener() {
                @Override
                public void onInternetAvailable() {

                }

                @Override
                public void onInternetUnavailable() {

                    enableNetworkNotification();
                }
            });

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {

                    if (!NetworkStatusBroadcastReceiver.isInternetAvailable(getApplicationContext())) {
                        enableNetworkNotification();
                    }

                    if (!GPSStatusBroadcastReceiver.isLocationEnabled(getApplicationContext())) {
                        enableLocationNotification();
                    }

                    if (!BluetoothStatusBroadcastReceiver.isBluetoothEnabled(getApplicationContext())) {
                        enableBluetoothNotification();
                    }

                    handler.postDelayed(this, STATE_CHECKER_PERIOD);
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

    private void enableBluetoothNotification() {

        Intent bluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        bluetoothEnable.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        bluetoothEnable.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        XNotificationManager.notify(getApplicationContext(),
                "Novid requires bluetooth access",
                "Touch to open bluetooth",
                XNotificationConstants.REQUEST_CHANNEL_ID,
                XNotificationConstants.BLUETOOTH_REQUEST_NOTIFICATION_ID,
                bluetoothEnable);
    }

    private void enableLocationNotification() {

        Intent locationEnableIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        locationEnableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        locationEnableIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        XNotificationManager.notify(getApplicationContext(),
                "Novid requires location access",
                "Touch to open location",
                XNotificationConstants.SERVICE_CHANNEL_ID,
                XNotificationConstants.LOCATION_REQUEST_NOTIFICATION_ID,
                locationEnableIntent);
    }

    private void enableNetworkNotification() {

        Intent networkEnableIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        networkEnableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        networkEnableIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        XNotificationManager.notify(getApplicationContext(),
                "Novid requires network connection",
                "Touch to open internet",
                XNotificationConstants.SERVICE_CHANNEL_ID,
                XNotificationConstants.LOCATION_REQUEST_NOTIFICATION_ID,
                networkEnableIntent);
    }
}
