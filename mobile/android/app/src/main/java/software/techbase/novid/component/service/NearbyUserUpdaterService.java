package software.techbase.novid.component.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import software.techbase.novid.component.android.broadcast.NetworkStatusBroadcastReceiver;
import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.domain.bluetooth.BluetoothDevicesScanner;

/**
 * Created by Wai Yan on 3/29/20.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NearbyUserUpdaterService extends Service {

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

                    BluetoothDevicesScanner.scan(NearbyUserUpdaterService.this, bluetoothDevice -> {

                        XLogger.debug(this.getClass(), "Found device : " + bluetoothDevice.getName());

                        if (bluetoothDevice.getAddress().startsWith(getPackageName())) {

                            String nearedUserId = StringUtils.substringAfter(bluetoothDevice.getAddress(), getPackageName());
                            XLogger.debug(this.getClass(), "Neared user id : " + nearedUserId);
                            sendData(getApplicationContext(), nearedUserId);
                        }
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

    private void sendData(Context mContext, String nearedUserId) {

        if (NetworkStatusBroadcastReceiver.isInternetAvailable(mContext)) {

        }
    }
}
