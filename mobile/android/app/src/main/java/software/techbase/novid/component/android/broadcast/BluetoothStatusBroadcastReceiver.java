package software.techbase.novid.component.android.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Closeable;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class BluetoothStatusBroadcastReceiver extends BroadcastReceiver implements Closeable {

    private Context mContext;
    private BluetoothStatusListener bluetoothStatusListener;

    public BluetoothStatusBroadcastReceiver(Context mContext, BluetoothStatusListener bluetoothStatusListener) {
        this.mContext = mContext;
        this.bluetoothStatusListener = bluetoothStatusListener;
        this.registerToContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        assert action != null;
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                case BluetoothAdapter.STATE_TURNING_OFF:
                    bluetoothStatusListener.isBluetoothDisable();
                    break;
                case BluetoothAdapter.STATE_ON:
                case BluetoothAdapter.STATE_TURNING_ON:
                    bluetoothStatusListener.isBluetoothEnable();
                    break;
            }
        }
    }

    private void registerToContext() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.mContext.registerReceiver(this, intentFilter);
    }

    @Override
    public void close() {
        this.mContext.unregisterReceiver(this);
    }

    public interface BluetoothStatusListener {

        void isBluetoothEnable();

        void isBluetoothDisable();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isBluetoothEnabled(Context mContext) {

        final BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        assert bluetoothManager != null;
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON;
    }
}
