package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import software.techbase.novid.component.android.xlogger.XLogger;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class BluetoothDevices {

    private static final long SCAN_PERIOD = 1000;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void scanDevices(Context mContext, Listener listener) {

        final BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        assert bluetoothManager != null;
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } else {

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(() ->
                            new BluetoothController(mContext, BluetoothAdapter.getDefaultAdapter(), new BroadcastReceiverDelegator.BluetoothDiscoveryDeviceListener() {
                                @Override
                                public void onDeviceDiscovered(BluetoothDevice device) {
                                    listener.onDeviceFound(device);
                                    XLogger.debug(this.getClass(), "Near by device : MAC " + device.getAddress() + ", Name " + device.getName());
                                }
                            }).startDiscovery(), SCAN_PERIOD);
                }
            };
            handler.post(runnable);
        }
    }

    public interface Listener {

        void onDeviceFound(BluetoothDevice bluetoothDevice);
    }
}
