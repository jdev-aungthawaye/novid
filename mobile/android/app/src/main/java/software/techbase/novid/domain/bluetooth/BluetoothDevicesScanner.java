package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import software.techbase.novid.R;
import software.techbase.novid.component.android.notifications.XNotificationConstants;
import software.techbase.novid.component.android.notifications.XNotificationManager;
import software.techbase.novid.component.android.xlogger.XLogger;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class BluetoothDevicesScanner {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void scan(Context mContext, Listener listener) {

        final BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        assert bluetoothManager != null;
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {

            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            XNotificationManager.notify(mContext,
                    mContext.getString(R.string.app_name),
                    "Please open bluetooth.",
                    XNotificationConstants.REQUEST_CHANNEL_ID,
                    XNotificationConstants.BLUETOOTH_REQUEST_NOTIFICATION_ID,
                    intent);

        } else {

            new BluetoothController(mContext, BluetoothAdapter.getDefaultAdapter(), device -> {
                XLogger.debug(mContext.getClass(), "Found device : " + device);
                listener.onDeviceFound(device);
            }).startDiscovery();
        }
    }

    public interface Listener {

        void onDeviceFound(BluetoothDevice bluetoothDevice);
    }
}
