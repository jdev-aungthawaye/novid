package software.techbase.novid.component.ui.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import software.techbase.novid.R;

/**
 * Created by Wai Yan on 4/5/20.
 */
public class MarkerClusterRenderer extends DefaultClusterRenderer<XContactClusterItem> implements ClusterManager.OnClusterClickListener<XContactClusterItem> {

    private GoogleMap googleMap;
    private LayoutInflater layoutInflater;
    private final IconGenerator clusterIconGenerator;
    private final View clusterItemView;

    public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<XContactClusterItem> clusterManager) {
        super(context, map, clusterManager);

        this.googleMap = map;

        layoutInflater = LayoutInflater.from(context);
        clusterItemView = layoutInflater.inflate(R.layout.marker_single_cluster_view, null);

        clusterIconGenerator = new IconGenerator(context);
        clusterIconGenerator.setBackground(ContextCompat.getDrawable(context, android.R.color.transparent));
        clusterIconGenerator.setContentView(clusterItemView);

        clusterManager.setOnClusterClickListener(this);
        clusterManager.getMarkerCollection().setInfoWindowAdapter(new XCustomClusterItemInfoView());
        clusterManager.setAlgorithm(new NonHierarchicalDistanceBasedAlgorithm<>());

        googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager());
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnInfoWindowClickListener(clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(XContactClusterItem item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOptions.title(item.getTitle());
        markerOptions.snippet(item.getSnippet());
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<XContactClusterItem> cluster, MarkerOptions markerOptions) {
        TextView singleClusterMarkerSizeTextView = clusterItemView.findViewById(R.id.singleClusterMarkerSizeTextView);
        singleClusterMarkerSizeTextView.setText(String.valueOf(cluster.getSize()));
        Bitmap icon = clusterIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected void onClusterItemRendered(XContactClusterItem clusterItem, Marker marker) {
        marker.setTag(clusterItem);
    }

    @Override
    public boolean onClusterClick(Cluster<XContactClusterItem> cluster) {

        if (cluster == null) return false;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (XContactClusterItem clusterItem : cluster.getItems())
            builder.include(clusterItem.getPosition());
        LatLngBounds bounds = builder.build();
        try {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private class XCustomClusterItemInfoView implements GoogleMap.InfoWindowAdapter {

        private final View clusterItemView;

        XCustomClusterItemInfoView() {
            clusterItemView = layoutInflater.inflate(R.layout.marker_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            XContactClusterItem XContactClusterItem = (XContactClusterItem) marker.getTag();
            if (XContactClusterItem == null) return clusterItemView;
            TextView itemLblTownship = clusterItemView.findViewById(R.id.itemLblTownship);
            TextView itemLblDepartment = clusterItemView.findViewById(R.id.itemLblDepartment);
            itemLblTownship.setText(marker.getTitle());
            itemLblDepartment.setText(XContactClusterItem.getSnippet());
            return clusterItemView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}
