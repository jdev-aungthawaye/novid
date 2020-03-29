package software.techbase.novid.component.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class GPSStatusBroadcastReceiver extends BroadcastReceiver {

    private Context mContext;
    private LocationListener LocationListener;

    public GPSStatusBroadcastReceiver(Context mContext, LocationListener LocationListener) {
        this.mContext = mContext;
        this.LocationListener = LocationListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (isLocationEnabled(this.mContext)) {
            LocationListener.isLocationEnable();
        } else {
            LocationListener.isLocationDisable();
        }
    }

    public void registerToContext() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        this.mContext.registerReceiver(this, intentFilter);
    }

    public void unregisterFromContext() {

        this.mContext.unregisterReceiver(this);
    }

    public interface LocationListener {

        void isLocationEnable();

        void isLocationDisable();
    }

    public static boolean isLocationEnabled(Context mContext) {

        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
