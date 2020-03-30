package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import java.io.Closeable;

import software.techbase.novid.component.android.xlogger.XLogger;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class BluetoothController implements Closeable {

    private final BluetoothAdapter bluetoothAdapter;
    private final BroadcastReceiverDelegator broadcastReceiverDelegator;
    private static int counter;

    public BluetoothController(Context context, BluetoothAdapter adapter, BroadcastReceiverDelegator.BluetoothDiscoveryDeviceListener listener) {
        this.bluetoothAdapter = adapter;
        this.broadcastReceiverDelegator = new BroadcastReceiverDelegator(context, listener);
    }

    /**
     * Starts the discovery of new Bluetooth devices nearby.
     */
    public void startDiscovery() {

        counter++;
        XLogger.debug(this.getClass(), "SCAN Outer" + counter);

        // If another discovery is in progress, cancels it before starting the new one.
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        } else {
            bluetoothAdapter.startDiscovery();
            XLogger.debug(this.getClass(), "SCAN Inner" + counter);
        }
    }

    @Override
    public void close() {
        this.broadcastReceiverDelegator.close();
    }
}
