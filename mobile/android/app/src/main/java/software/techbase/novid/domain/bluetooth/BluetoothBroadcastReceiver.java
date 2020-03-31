package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothClass;
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
public class BluetoothBroadcastReceiver extends BroadcastReceiver implements Closeable {

    private final DiscoveryDeviceListener listener;
    private final Context context;

    public BluetoothBroadcastReceiver(Context context, DiscoveryDeviceListener listener) {
        this.listener = listener;
        this.context = context;

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(this, filter);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            assert device != null;
            //TODO
            if (device.getType() == BluetoothClass.Device.PHONE_SMART || device.getType() == BluetoothClass.Device.PHONE_UNCATEGORIZED) {
                listener.onDeviceDiscovered(device);
            }
        }
    }

    @Override
    public void close() {
        context.unregisterReceiver(this);
    }

    public interface DiscoveryDeviceListener {

        void onDeviceDiscovered(BluetoothDevice device);
    }
}
