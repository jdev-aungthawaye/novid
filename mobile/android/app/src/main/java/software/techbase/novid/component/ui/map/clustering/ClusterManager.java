package software.techbase.novid.component.ui.map.clustering;

/**
 * Created by Wai Yan on 4/4/20.
 */

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Groups multiple items on a map into clusters based on the current zoom level.
 * Clustering occurs when the map becomes idle, so an instance of this class
 * must be set as a camera idle listener using {@link GoogleMap#setOnCameraIdleListener}.
 *
 * @param <T> the type of an item to be clustered
 */
public class ClusterManager<T extends XClusterItem> implements GoogleMap.OnCameraIdleListener {

    private static final int QUAD_TREE_BUCKET_CAPACITY = 4;
    private static final int DEFAULT_MIN_CLUSTER_SIZE = 1;

    private final GoogleMap mGoogleMap;

    private final QuadTree<T> mQuadTree;

    private final ClusterRenderer<T> mRenderer;

    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    private AsyncTask mQuadTreeTask;

    private AsyncTask mClusterTask;

    private int mMinClusterSize = DEFAULT_MIN_CLUSTER_SIZE;

    /**
     * Defines signatures for methods that are called when a cluster or a cluster item is clicked.
     *
     * @param <T> the type of an item managed by {@link ClusterManager}.
     */
    public interface Callbacks<T extends XClusterItem> {
        /**
         * Called when a marker representing a XCluster has been clicked.
         *
         * @param XCluster the XCluster that has been clicked
         * @return <code>true</code> if the listener has consumed the event (i.e., the default behavior should not occur);
         * <code>false</code> otherwise (i.e., the default behavior should occur). The default behavior is for the camera
         * to move to the marker and an info window to appear.
         */
        boolean onClusterClick(@NonNull XCluster<T> XCluster);

        /**
         * Called when a marker representing a cluster item has been clicked.
         *
         * @param clusterItem the cluster item that has been clicked
         * @return <code>true</code> if the listener has consumed the event (i.e., the default behavior should not occur);
         * <code>false</code> otherwise (i.e., the default behavior should occur). The default behavior is for the camera
         * to move to the marker and an info window to appear.
         */
        boolean onClusterItemClick(@NonNull T clusterItem);
    }

    /**
     * Creates a new cluster manager using the default icon generator.
     * To customize marker icons, set a custom icon generator using
     * {@link ClusterManager#setIconGenerator(IconGenerator)}.
     *
     * @param googleMap the map instance where markers will be rendered
     */
    public ClusterManager(@NonNull Context context, @NonNull GoogleMap googleMap) {
        Preconditions.checkNotNull(context);
        mGoogleMap = Preconditions.checkNotNull(googleMap);
        mRenderer = new ClusterRenderer<>(context, googleMap);
        mQuadTree = new QuadTree<>(QUAD_TREE_BUCKET_CAPACITY);
    }

    /**
     * Sets a custom icon generator thus replacing the default one.
     *
     * @param iconGenerator the custom icon generator that's used for generating marker icons
     */
    public void setIconGenerator(@NonNull IconGenerator<T> iconGenerator) {
        Preconditions.checkNotNull(iconGenerator);
        mRenderer.setIconGenerator(iconGenerator);
    }

    /**
     * Sets a callback that's invoked when a cluster or a cluster item is clicked.
     *
     * @param callbacks the callback that's invoked when a cluster or an individual item is clicked.
     *                  To unset the callback, use <code>null</code>.
     */
    public void setCallbacks(@Nullable Callbacks<T> callbacks) {
        mRenderer.setCallbacks(callbacks);
    }

    /**
     * Sets items to be clustered thus replacing the old ones.
     *
     * @param clusterItems the items to be clustered
     */
    public void setItems(@NonNull List<T> clusterItems) {
        Preconditions.checkNotNull(clusterItems);
        buildQuadTree(clusterItems);
    }

    /**
     * Sets the minimum size of a cluster. If the cluster size
     * is less than this value, display individual markers.
     */
    public void setMinClusterSize(int minClusterSize) {
        Preconditions.checkArgument(minClusterSize > 0);
        mMinClusterSize = minClusterSize;
    }

    @Override
    public void onCameraIdle() {
        cluster();
    }

    private void buildQuadTree(@NonNull List<T> clusterItems) {
        if (mQuadTreeTask != null) {
            mQuadTreeTask.cancel(true);
        }

        mQuadTreeTask = new QuadTreeTask(clusterItems).executeOnExecutor(mExecutor);
    }

    private void cluster() {
        if (mClusterTask != null) {
            mClusterTask.cancel(true);
        }

        mClusterTask = new ClusterTask(mGoogleMap.getProjection().getVisibleRegion().latLngBounds,
                mGoogleMap.getCameraPosition().zoom).executeOnExecutor(mExecutor);
    }

    @NonNull
    private List<XCluster<T>> getClusters(@NonNull LatLngBounds latLngBounds, float zoomLevel) {
        List<XCluster<T>> XClusters = new ArrayList<>();

        long tileCount = (long) (Math.pow(2, zoomLevel) * 2);

        double startLatitude = latLngBounds.northeast.latitude;
        double endLatitude = latLngBounds.southwest.latitude;

        double startLongitude = latLngBounds.southwest.longitude;
        double endLongitude = latLngBounds.northeast.longitude;

        double stepLatitude = 180.0 / tileCount;
        double stepLongitude = 360.0 / tileCount;

        if (startLongitude > endLongitude) { // Longitude +180°/-180° overlap.
            // [start longitude; 180]
            getClustersInsideBounds(XClusters, startLatitude, endLatitude,
                    startLongitude, 180.0, stepLatitude, stepLongitude);
            // [-180; end longitude]
            getClustersInsideBounds(XClusters, startLatitude, endLatitude,
                    -180.0, endLongitude, stepLatitude, stepLongitude);
        } else {
            getClustersInsideBounds(XClusters, startLatitude, endLatitude,
                    startLongitude, endLongitude, stepLatitude, stepLongitude);
        }

        return XClusters;
    }

    private void getClustersInsideBounds(@NonNull List<XCluster<T>> XClusters,
                                         double startLatitude, double endLatitude,
                                         double startLongitude, double endLongitude,
                                         double stepLatitude, double stepLongitude) {
        long startX = (long) ((startLongitude + 180.0) / stepLongitude);
        long startY = (long) ((90.0 - startLatitude) / stepLatitude);

        long endX = (long) ((endLongitude + 180.0) / stepLongitude) + 1;
        long endY = (long) ((90.0 - endLatitude) / stepLatitude) + 1;

        for (long tileX = startX; tileX <= endX; tileX++) {
            for (long tileY = startY; tileY <= endY; tileY++) {
                double north = 90.0 - tileY * stepLatitude;
                double west = tileX * stepLongitude - 180.0;
                double south = north - stepLatitude;
                double east = west + stepLongitude;

                List<T> points = mQuadTree.queryRange(north, west, south, east);

                if (points.isEmpty()) {
                    continue;
                }

                if (points.size() >= mMinClusterSize) {
                    double totalLatitude = 0;
                    double totalLongitude = 0;

                    for (T point : points) {
                        totalLatitude += point.getLatitude();
                        totalLongitude += point.getLongitude();
                    }

                    double latitude = totalLatitude / points.size();
                    double longitude = totalLongitude / points.size();

                    XClusters.add(new XCluster<>(latitude, longitude,
                            points, north, west, south, east));
                } else {
                    for (T point : points) {
                        XClusters.add(new XCluster<>(point.getLatitude(), point.getLongitude(),
                                Collections.singletonList(point), north, west, south, east));
                    }
                }
            }
        }
    }

    private class QuadTreeTask extends AsyncTask<Void, Void, Void> {

        private final List<T> mClusterItems;

        private QuadTreeTask(@NonNull List<T> clusterItems) {
            mClusterItems = clusterItems;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mQuadTree.clear();
            for (T clusterItem : mClusterItems) {
                mQuadTree.insert(clusterItem);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            cluster();
            mQuadTreeTask = null;
        }
    }

    private class ClusterTask extends AsyncTask<Void, Void, List<XCluster<T>>> {

        private final LatLngBounds mLatLngBounds;
        private final float mZoomLevel;

        private ClusterTask(@NonNull LatLngBounds latLngBounds, float zoomLevel) {
            mLatLngBounds = latLngBounds;
            mZoomLevel = zoomLevel;
        }

        @Override
        protected List<XCluster<T>> doInBackground(Void... params) {
            return getClusters(mLatLngBounds, mZoomLevel);
        }

        @Override
        protected void onPostExecute(@NonNull List<XCluster<T>> XClusters) {
            mRenderer.render(XClusters);
            mClusterTask = null;
        }
    }
}