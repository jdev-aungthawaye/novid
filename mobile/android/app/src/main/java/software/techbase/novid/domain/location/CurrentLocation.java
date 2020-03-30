package software.techbase.novid.domain.location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.provider.Settings;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import software.techbase.novid.component.android.broadcast.GPSStatusBroadcastReceiver;
import software.techbase.novid.component.android.xlogger.XLogger;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class CurrentLocation {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public static void getCurrentLocation(Context mContext, Listener listener) {

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);

        try {
            mFusedLocationClient
                    .getLastLocation()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location mLocation = task.getResult();
                            if (mLocation != null) {
                                listener.onLocationAvailable(mLocation);
                            }
                            mFusedLocationClient.removeLocationUpdates(new LocationCallback() {
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                }
                            });
                        } else {
                            XLogger.debug(CurrentLocation.class, "Failed to get location.");
                            if (!GPSStatusBroadcastReceiver.isLocationEnabled(mContext)) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                            }
                        }
                    });
        } catch (SecurityException ex) {
            XLogger.debug(CurrentLocation.class, "Lost location permission." + ex);
        }

        try {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, null);
        } catch (SecurityException ex) {
            XLogger.debug(CurrentLocation.class, "Lost location permission." + ex);
        }

    }

    public interface Listener {

        void onLocationAvailable(Location location);
    }
}
