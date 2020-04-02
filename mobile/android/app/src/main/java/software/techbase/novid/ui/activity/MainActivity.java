package software.techbase.novid.ui.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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

import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.cache.sharepreferences.UserInfoStorage;
import software.techbase.novid.component.android.broadcast.BluetoothStatusBroadcastReceiver;
import software.techbase.novid.component.android.broadcast.GPSStatusBroadcastReceiver;
import software.techbase.novid.component.android.runtimepermissions.RuntimePermissions;
import software.techbase.novid.component.service.Constants;
import software.techbase.novid.component.service.LocationUpdaterService;
import software.techbase.novid.component.service.NearbyUserUpdaterService;
import software.techbase.novid.component.service.RequiredAccessObserverService;
import software.techbase.novid.component.service.ServiceUtils;
import software.techbase.novid.component.ui.base.BaseActivity;
import software.techbase.novid.component.ui.reusable.XAlertDialog;
import software.techbase.novid.domain.bluetooth.BluetoothUtils;
import software.techbase.novid.domain.location.CurrentLocation;
import software.techbase.novid.domain.location.LocationUtils;
import software.techbase.novid.ui.fragment.DashboardFragment;

/**
 * Created by Wai Yan on 3/28/20.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
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

        this.launch();
        this.requestRequiredAccess();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.launch();
    }

    private void launch() {

        RuntimePermissions.with(this)
                .permissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .onAccepted(permissionResult -> {

                    BluetoothUtils.setDeviceName(this, this.getPackageName() + UserInfoStorage.getInstance().getUserId());

                    this.startRequiredAccessObserverService();
                    this.startLocationUpdaterService();
                    this.startNearDevicesUpdaterService();

                    this.showMapOnUI();
                })
                .onDenied(permissionResult -> {
                })
                .ask();
    }

    private void requestRequiredAccess() {

        if (!GPSStatusBroadcastReceiver.isLocationEnabled(this)) {

            new XAlertDialog(this,
                    XAlertDialog.Type.INFO,
                    "Please open location.",
                    null,
                    v -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))).show();
        }

        if (!BluetoothStatusBroadcastReceiver.isBluetoothEnabled(this)) {

            new XAlertDialog(this,
                    XAlertDialog.Type.INFO,
                    "Please to open bluetooth.",
                    null,
                    v -> startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))).show();
        }
    }

    //MAP
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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 10F));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 10F));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15F));
                addMaker(mLocation.getLatitude(), mLocation.getLongitude());

                mMap.setOnCameraMoveListener(() -> mMap.clear());

                mMap.setOnCameraIdleListener(() -> {
                    LatLng midLatLng = mMap.getCameraPosition().target;
                    addMaker(midLatLng.latitude, midLatLng.longitude);
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
    //MAP

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

    private void startLocationUpdaterService() {

        if (ServiceUtils.isServiceRunning(LocationUpdaterService.class, this)) {

            Intent startIntent = new Intent(this, LocationUpdaterService.class);
            startIntent.setAction(Constants.ACTION.START_ACTION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(startIntent);
            } else {
                startService(startIntent);
            }
        }
    }

    private void startNearDevicesUpdaterService() {

        if (ServiceUtils.isServiceRunning(NearbyUserUpdaterService.class, this)) {

            Intent startIntent = new Intent(this, NearbyUserUpdaterService.class);
            startIntent.setAction(Constants.ACTION.START_ACTION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(startIntent);
            } else {
                startService(startIntent);
            }
        }
    }

    private void startRequiredAccessObserverService() {

        if (ServiceUtils.isServiceRunning(RequiredAccessObserverService.class, this)) {

            Intent startIntent = new Intent(this, RequiredAccessObserverService.class);
            startIntent.setAction(Constants.ACTION.START_ACTION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(startIntent);
            } else {
                startService(startIntent);
            }
        }
    }

    @OnClick(R.id.iBtnShowDashboard)
    public void onClickShowDashboard() {
        new DashboardFragment().show(getSupportFragmentManager(), "Dashboard");
    }
}
