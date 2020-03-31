package software.techbase.novid.component.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.domain.bluetooth.BluetoothDevices;

/**
 * Created by Wai Yan on 3/29/20.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NearDevicesUpdaterService extends Service {

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

            Set<BluetoothDevice> devices = new HashSet<>();
            BluetoothDevices.scanDevices(this, devices::add);

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {

                    XLogger.debug(this.getClass(), "Near devices to server : " + devices);
                    //TODO Send data to server

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
