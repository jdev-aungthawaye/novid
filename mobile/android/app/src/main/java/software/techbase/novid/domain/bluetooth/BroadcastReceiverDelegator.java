package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.io.Closeable;
import java.util.Objects;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class BroadcastReceiverDelegator extends BroadcastReceiver implements Closeable {

    private final BluetoothDiscoveryDeviceListener listener;
    private final Context context;

    public BroadcastReceiverDelegator(Context context, BluetoothDiscoveryDeviceListener listener) {
        this.listener = listener;
        this.context = context;

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(Objects.requireNonNull(action))) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            listener.onDeviceDiscovered(device);
        }
    }

    @Override
    public void close() {
        context.unregisterReceiver(this);
    }

    public interface BluetoothDiscoveryDeviceListener {

        void onDeviceDiscovered(BluetoothDevice device);
    }
}
