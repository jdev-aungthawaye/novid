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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.cache.sharepreferences.UserInfoStorage;
import software.techbase.novid.component.android.broadcast.BluetoothStatusBroadcastReceiver;
import software.techbase.novid.component.android.broadcast.GPSStatusBroadcastReceiver;
import software.techbase.novid.component.android.runtimepermissions.RuntimePermissions;
import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.component.service.ServiceUtils;
import software.techbase.novid.component.ui.base.BaseActivity;
import software.techbase.novid.component.ui.map.ContactClusterItem;
import software.techbase.novid.component.ui.map.clustering.ClusterManager;
import software.techbase.novid.component.ui.map.clustering.XCluster;
import software.techbase.novid.component.ui.reusable.XAlertDialog;
import software.techbase.novid.domain.bluetooth.BluetoothUtils;
import software.techbase.novid.domain.location.CurrentLocation;
import software.techbase.novid.domain.location.LocationUtils;
import software.techbase.novid.domain.remote.api.GetContacts;
import software.techbase.novid.ui.contract.MainActivityContract;
import software.techbase.novid.ui.fragment.ContactFragment;
import software.techbase.novid.ui.fragment.DashboardFragment;
import software.techbase.novid.ui.fragment.EntryFragment;
import software.techbase.novid.ui.presenter.MainActivityPresenter;

/**
 * Created by Wai Yan on 3/28/20.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends BaseActivity implements MainActivityContract.View {

    @BindView(R.id.fabVerify)
    FloatingActionButton fabVerify;

    private GoogleMap mMap;
    private final MainActivityPresenter presenter = new MainActivityPresenter(this);

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
        this.checkLoggedIn();
    }

    private void launch() {

        RuntimePermissions.with(this)
                .permissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .onAccepted(permissionResult -> {

                    BluetoothUtils.setDeviceName(this, this.getPackageName() + UserInfoStorage.getInstance().getUserId());
                    this.showMapOnUI();
                })
                .onDenied(permissionResult -> {
                })
                .ask();
    }

    private void showMapOnUI() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.setOnMapLoadedCallback(() -> {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled(true);
                //After get current location, when map is ready
                this.presenter.loadContacts(this);
                //InitMapStuff
                CurrentLocation.getCurrentLocation(this, mLocation -> {

                    double lat = mLocation.getLatitude();
                    double lng = mLocation.getLongitude();

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 8F));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 8F));
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(LocationUtils.getAddressName(this, lat, lng)));
                    marker.showInfoWindow();
                });
            });
        });
    }

    private void checkLoggedIn() {

        if (UserInfoStorage.getInstance().isUserLoggedIn()) {
            fabVerify.hide();
            this.startRequiredCredentialServices();
        } else {
            fabVerify.show();
            EntryFragment.getInstance().show(getSupportFragmentManager(), "entry");
        }
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

    private void startRequiredCredentialServices() {

        ServiceUtils.startRequiredAccessObserverService(this);
        ServiceUtils.startLocationUpdaterService(this);
        ServiceUtils.startNearDevicesUpdaterService(this);
    }

    @OnClick(R.id.fabShowDashboard)
    public void onClickShowDashboard() {
        DashboardFragment.getInstance().show(getSupportFragmentManager(), "dashboard");
    }

    @Override
    public void showContacts(List<GetContacts.Response> contacts) {

        ClusterManager<ContactClusterItem> mClusterManager = new ClusterManager<>(this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);

        mClusterManager.setCallbacks(new ClusterManager.Callbacks<ContactClusterItem>() {
            @Override
            public boolean onClusterClick(@NonNull XCluster<ContactClusterItem> XCluster) {
                return false;
            }

            @Override
            public boolean onClusterItemClick(@NonNull ContactClusterItem clusterItem) {
                XLogger.debug(this.getClass(), "onClusterItemClick : " + clusterItem.getContact());

                ContactFragment contactFragment = ContactFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", clusterItem.getContact());
                contactFragment.setArguments(bundle);
                contactFragment.show(getSupportFragmentManager(), "contact");

                return false;
            }
        });

        List<ContactClusterItem> clusterItems = new ArrayList<>();
        for (GetContacts.Response contact : contacts) {
            clusterItems.add(new ContactClusterItem(
                    new LatLng(contact.lat, contact.lng),
                    contact.region + "၊" + contact.township,
                    contact.department,
                    contact));
        }
        mClusterManager.setItems(clusterItems);
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

    @OnClick(R.id.fabVerify)
    void onClickVerify() {
        EntryFragment.getInstance().show(getSupportFragmentManager(), "entry");
    }
}
