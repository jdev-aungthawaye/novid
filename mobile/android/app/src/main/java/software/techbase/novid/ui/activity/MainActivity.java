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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.clustering.ClusterManager;

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
import software.techbase.novid.component.service.ServiceUtils;
import software.techbase.novid.component.ui.base.BaseActivity;
import software.techbase.novid.component.ui.map.GoogleMapHelper;
import software.techbase.novid.component.ui.map.MarkerClusterRenderer;
import software.techbase.novid.component.ui.map.XContactClusterItem;
import software.techbase.novid.component.ui.reusable.XAlertDialog;
import software.techbase.novid.domain.bluetooth.BluetoothUtils;
import software.techbase.novid.domain.location.CurrentLocation;
import software.techbase.novid.domain.location.LocationUtils;
import software.techbase.novid.domain.remote.api.GetContacts;
import software.techbase.novid.ui.contract.MainActivityContract;
import software.techbase.novid.ui.fragment.ContactDetailFragment;
import software.techbase.novid.ui.fragment.ContactListFragment;
import software.techbase.novid.ui.fragment.DashboardFragment;
import software.techbase.novid.ui.fragment.EntryFragment;
import software.techbase.novid.ui.fragment.QuestionsFragment;
import software.techbase.novid.ui.presenter.MainActivityPresenter;

/**
 * Created by Wai Yan on 3/28/20.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends BaseActivity implements MainActivityContract.View, OnMapReadyCallback {

    @BindView(R.id.fabVerify)
    FloatingActionButton fabVerify;

    private GoogleMap mMap;
    private final MainActivityPresenter presenter = new MainActivityPresenter(this);
    private ArrayList<GetContacts.Response> contacts = new ArrayList<>();

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        this.checkLoggedIn();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.checkPermissionsAndLaunch();
    }

    private void checkPermissionsAndLaunch() {
        RuntimePermissions.with(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .onAccepted(permissionResult -> {
                    BluetoothUtils.setDeviceName(this, this.getPackageName() + UserInfoStorage.getInstance().getUserId());
                    this.requestRequiredAccess();
                    this.showMapOnUI();
                })
                .onDenied(permissionResult -> {
                    this.checkPermissionsAndLaunch();
                })
                .ask();
    }

    private void showMapOnUI() {
        if (mMap != null) {
            //OnMapReady
            GoogleMapHelper.defaultMapSettings(mMap);
            this.presenter.loadContacts(this);

            //Move to myanmar
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(21.9162, 95.9560), 8F));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(21.9162, 95.9560), 8F));

            //Move to current location
            CurrentLocation.getCurrentLocation(this, mLocation -> {

                double lat = mLocation.getLatitude();
                double lng = mLocation.getLongitude();

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 8F));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 8F));
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(LocationUtils.getAddressName(this, lat, lng)));
                marker.showInfoWindow();
            });
        }
    }

    private void checkLoggedIn() {

        if (UserInfoStorage.getInstance().isUserLoggedIn()) {
            fabVerify.hide();
            this.startRequiredCredentialServices();
        } else {
            fabVerify.show();
            new EntryFragment().show(getSupportFragmentManager(), "entry");
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

    @Override
    public void showContacts(List<GetContacts.Response> contacts) {

        this.contacts = (ArrayList<GetContacts.Response>) contacts;

        ClusterManager<XContactClusterItem> clusterManager = new ClusterManager<>(MainActivity.this, mMap);
        clusterManager.setRenderer(new MarkerClusterRenderer(this, mMap, clusterManager));

        List<XContactClusterItem> clusterItems = new ArrayList<>();
        for (GetContacts.Response contact : this.contacts) {
            clusterItems.add(new XContactClusterItem(new LatLng(contact.lat, contact.lng), contact.region + "၊ " + contact.township, contact.department, contact));
        }
        clusterManager.clearItems();
        clusterManager.addItems(clusterItems);
        clusterManager.cluster();
        clusterManager.setOnClusterItemInfoWindowClickListener(item -> this.showContactDetail(item.getContact()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_questions) {
            new QuestionsFragment().show(getSupportFragmentManager(), "questions");
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fabVerify)
    void onClickVerify() {
        new EntryFragment().show(getSupportFragmentManager(), "entry");
    }

    @OnClick(R.id.fabShowDashboard)
    public void onClickShowDashboard() {
        new DashboardFragment().show(getSupportFragmentManager(), "dashboard");
    }

    private void showContactDetail(GetContacts.Response contact) {

        ContactDetailFragment contactDetailFragment = new ContactDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", contact);
        contactDetailFragment.setArguments(bundle);
        contactDetailFragment.show(getSupportFragmentManager(), "contact-detail");
    }

    @OnClick(R.id.fabContactList)
    void onClickFabContactList() {

        ContactListFragment contactListFragment = new ContactListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("contacts", this.contacts);
        contactListFragment.setArguments(bundle);
        contactListFragment.show(getSupportFragmentManager(), "contact-detail");
    }
}

