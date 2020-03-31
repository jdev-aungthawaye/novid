package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import java.io.Closeable;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class BluetoothController implements Closeable {

    private final BluetoothAdapter bluetoothAdapter;
    private final BroadcastReceiverDelegator broadcastReceiverDelegator;

    public BluetoothController(Context context, BluetoothAdapter adapter, BroadcastReceiverDelegator.BluetoothDiscoveryDeviceListener listener) {
        this.bluetoothAdapter = adapter;
        this.broadcastReceiverDelegator = new BroadcastReceiverDelegator(context, listener);
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
        this.broadcastReceiverDelegator.close();
    }
}
