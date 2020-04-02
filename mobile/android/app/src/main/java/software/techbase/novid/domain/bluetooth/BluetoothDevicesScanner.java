package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import software.techbase.novid.component.android.broadcast.BluetoothStatusBroadcastReceiver;
import software.techbase.novid.component.android.xlogger.XLogger;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class BluetoothDevicesScanner {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void scan(Context mContext, Listener listener) {

        if (BluetoothStatusBroadcastReceiver.isBluetoothEnabled(mContext)) {

            new BluetoothController(mContext, BluetoothAdapter.getDefaultAdapter(), listener::onDeviceFound).startDiscovery();
        }
    }

    public interface Listener {

        void onDeviceFound(BluetoothDevice bluetoothDevice);
    }
}
