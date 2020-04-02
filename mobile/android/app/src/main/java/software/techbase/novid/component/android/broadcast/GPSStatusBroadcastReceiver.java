package software.techbase.novid.component.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;

import java.io.Closeable;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class GPSStatusBroadcastReceiver extends BroadcastReceiver implements Closeable {

    private Context mContext;
    private LocationStatusListener LocationStatusListener;

    public GPSStatusBroadcastReceiver(Context mContext, LocationStatusListener LocationStatusListener) {
        this.mContext = mContext;
        this.LocationStatusListener = LocationStatusListener;
        this.registerToContext();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (isLocationEnabled(this.mContext)) {
            LocationStatusListener.isLocationEnable();
        } else {
            LocationStatusListener.isLocationDisable();
        }
    }

    private void registerToContext() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        this.mContext.registerReceiver(this, intentFilter);
    }

    @Override
    public void close() {
        this.mContext.unregisterReceiver(this);
    }

    public interface LocationStatusListener {

        void isLocationEnable();

        void isLocationDisable();
    }

    public static boolean isLocationEnabled(Context mContext) {

        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
