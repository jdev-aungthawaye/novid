package software.techbase.novid.ui.activity;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import software.techbase.novid.R;
import software.techbase.novid.component.android.runtimepermissions.RuntimePermissions;
import software.techbase.novid.component.ui.base.BaseActivity;
import software.techbase.novid.component.ui.reusable.XAlertDialog;

/**
 * Created by Wai Yan on 3/28/20.
 */
public class MainActivity extends BaseActivity {

    private GoogleMap mMap;

    private String addressName;
    private double latitude;
    private double longitude;

    @Override
    protected int getLayoutFileId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.setOnMapLoadedCallback(this::initMapStuff);
        });
    }

    private void initMapStuff() {

        RuntimePermissions.with(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .onAccepted(permissionResult -> {
                    this.getCurrentLocation();
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                })
                .onDenied(permissionResult -> {
                    new XAlertDialog(this,
                            XAlertDialog.Type.WARNING,
                            "Need to allow location permission.",
                            null,
                            v -> this.initMapStuff()).show();
                })
                .ask();
    }

    private void getCurrentLocation() {

        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            locationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            if (mMap != null) {
                                //Current
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(latitude, longitude))
                                        // .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
                                        .title(this.getAddressName(latitude, longitude)));

                                //OnChange
                                mMap.setOnCameraMoveListener(() -> mMap.clear());

                                mMap.setOnCameraIdleListener(() -> {

                                    LatLng midLatLng = mMap.getCameraPosition().target;
                                    mMap.addMarker(new MarkerOptions().position(midLatLng));

                                    this.addressName = this.getAddressName(midLatLng.latitude, midLatLng.longitude);
                                    this.latitude = midLatLng.latitude;
                                    this.longitude = midLatLng.longitude;
                                });
                            }
                        }
                    })
                    .addOnFailureListener(Throwable::printStackTrace);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private String getAddressName(double lat, double lng) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address address = addresses.get(0);
            return address.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
