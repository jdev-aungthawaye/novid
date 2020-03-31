package software.techbase.novid.domain.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Created by Wai Yan on 3/31/20.
 */
public class BluetoothUtils {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void setDeviceName(Context mContext, String name) {

        final BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        assert bluetoothManager != null;
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                bluetoothAdapter.setName(name);
            }
        }
    }
}
