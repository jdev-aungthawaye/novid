package software.techbase.novid.component.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

/**
 * Created by Wai Yan on 3/30/20.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BLEDevices {

    private static final long SCAN_PERIOD = 10000;

    public static void scanDevices(Context mContext, Listener listener) {

        final BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        assert bluetoothManager != null;
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            mContext.startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
        } else {

            new Handler().postDelayed(() -> bluetoothAdapter.stopLeScan((device, rssi, scanRecord) -> listener.onDeviceFound(device.getAddress())), SCAN_PERIOD);

            bluetoothAdapter.startLeScan((device, rssi, scanRecord) -> listener.onDeviceFound(device.getAddress()));
        }
    }

    public interface Listener {

        void onDeviceFound(String macAddress);
    }
}
