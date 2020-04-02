package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Closeable;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class BluetoothDiscoveredBroadcastReceiver extends BroadcastReceiver implements Closeable {

    private final DiscoveryDeviceListener listener;
    private final Context context;

    public BluetoothDiscoveredBroadcastReceiver(Context context, DiscoveryDeviceListener listener) {
        this.listener = listener;
        this.context = context;
        this.registerToContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            assert device != null;
            listener.onDeviceDiscovered(device);
        }
    }

    private void registerToContext() {
        // Register for broadcasts when a device is discovered.
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        this.context.registerReceiver(this, intentFilter);
    }

    @Override
    public void close() {
        this.context.unregisterReceiver(this);
    }

    public interface DiscoveryDeviceListener {

        void onDeviceDiscovered(BluetoothDevice device);
    }
}
