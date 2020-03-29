package software.techbase.novid.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.Nullable;

import software.techbase.novid.R;
import software.techbase.novid.component.android.runtimepermissions.RuntimePermissions;
import software.techbase.novid.component.service.Constants;
import software.techbase.novid.component.service.DataSenderService;
import software.techbase.novid.component.service.ServiceUtils;
import software.techbase.novid.component.ui.base.BaseActivity;
import software.techbase.novid.component.ui.reusable.XAlertDialog;
import software.techbase.novid.util.CurrentLocation;
import software.techbase.novid.util.LocationUtils;

/**
 * Created by Wai Yan on 3/28/20.
 */
public class MainActivity extends BaseActivity {

    private GoogleMap mMap;

    @Override
    protected int getLayoutFileId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.requestRequiredPermissions();
    }

    private void requestRequiredPermissions() {

        RuntimePermissions.with(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .onAccepted(permissionResult -> {

                    this.startCurrentLocationSenderService();
                    this.showMapOnUI();

                })
                .onDenied(permissionResult -> {
                    XAlertDialog.show(this, XAlertDialog.Type.INFO, "Need to allow location permission.", null, v -> this.requestRequiredPermissions());
                })
                .ask();
    }

    private void showMapOnUI() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.setOnMapLoadedCallback(this::initMapStuff);
        });
    }

    private void initMapStuff() {

        this.setCurrentLocationOnMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    private void setCurrentLocationOnMap() {

        CurrentLocation.getCurrentLocation(this, mLocation -> {
            if (mMap != null) {
                //Current
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 10));
                this.addMaker(mLocation.getLatitude(), mLocation.getLongitude());

                mMap.setOnCameraMoveListener(() -> mMap.clear());

                mMap.setOnCameraIdleListener(() -> {

                    LatLng midLatLng = mMap.getCameraPosition().target;
                    this.addMaker(midLatLng.latitude, midLatLng.longitude);
                });
            }
        });
    }

    private void addMaker(double latitude, double longitude) {

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(LocationUtils.getAddressName(this, latitude, longitude)));
        marker.showInfoWindow();
    }

    private void startCurrentLocationSenderService() {

        if (ServiceUtils.isServiceRunning(DataSenderService.class, this)) return;

        Intent startIntent = new Intent(MainActivity.this, DataSenderService.class);
        startIntent.setAction(Constants.ACTION.START_ACTION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(startIntent);
        } else {
            startService(startIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            this.startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
