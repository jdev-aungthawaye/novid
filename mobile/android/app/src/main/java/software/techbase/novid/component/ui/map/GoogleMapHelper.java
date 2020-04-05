package software.techbase.novid.component.ui.map;

import com.google.android.gms.maps.GoogleMap;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Wai Yan on 4/5/20.
 */
public class GoogleMapHelper {

    public static void defaultMapSettings(@NotNull GoogleMap googleMap) {

        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.setMyLocationEnabled(true);
    }
}
