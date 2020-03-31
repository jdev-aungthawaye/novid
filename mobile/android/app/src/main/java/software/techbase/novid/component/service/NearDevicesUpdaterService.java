package software.techbase.novid.component.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import software.techbase.novid.R;
import software.techbase.novid.component.android.broadcast.NetworkStatusBroadcastReceiver;
import software.techbase.novid.component.android.notifications.XNotificationConstants;
import software.techbase.novid.component.android.notifications.XNotificationManager;
import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.domain.bluetooth.BluetoothDevicesScanner;

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

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {

                    BluetoothDevicesScanner.scan(NearDevicesUpdaterService.this, bluetoothDevice -> {

                        XLogger.debug(this.getClass(), "Found device : " + bluetoothDevice.getName());

                        if (bluetoothDevice.getAddress().startsWith(getPackageName())) {

                            String nearedUserId = StringUtils.substringAfter(bluetoothDevice.getAddress(), getPackageName());
                            XLogger.debug(this.getClass(), "Neared user id : " + nearedUserId);
                            sendData(getApplicationContext(), nearedUserId);
                        }
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

    private void sendData(Context mContext, String nearedUserId) {

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
