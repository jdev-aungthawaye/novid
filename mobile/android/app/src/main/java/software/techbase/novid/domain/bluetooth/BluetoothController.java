package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import java.io.Closeable;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class BluetoothController implements Closeable {

    private final BluetoothAdapter bluetoothAdapter;
    private final BluetoothDiscoveredBroadcastReceiver bluetoothDiscoveredBroadcastReceiver;

    public BluetoothController(Context context, BluetoothAdapter adapter, BluetoothDiscoveredBroadcastReceiver.DiscoveryDeviceListener listener) {
        this.bluetoothAdapter = adapter;
        this.bluetoothDiscoveredBroadcastReceiver = new BluetoothDiscoveredBroadcastReceiver(context, listener);
    }

    public void startDiscovery() {

        // If another discovery is in progress, cancels it before starting the new one.
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
    }

    @Override
    public void close() {
        this.bluetoothDiscoveredBroadcastReceiver.close();
    }
}
