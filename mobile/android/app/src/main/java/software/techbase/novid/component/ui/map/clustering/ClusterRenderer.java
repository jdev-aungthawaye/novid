package software.techbase.novid.component.ui.map.clustering;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wai Yan on 4/4/20.
 */
class ClusterRenderer<T extends XClusterItem> implements GoogleMap.OnMarkerClickListener {

    private static final int BACKGROUND_MARKER_Z_INDEX = 0;

    private static final int FOREGROUND_MARKER_Z_INDEX = 1;

    private final GoogleMap mGoogleMap;

    private final List<XCluster<T>> mXClusters = new ArrayList<>();

    private final Map<XCluster<T>, Marker> mMarkers = new HashMap<>();

    private IconGenerator<T> mIconGenerator;

    private ClusterManager.Callbacks<T> mCallbacks;

    ClusterRenderer(@NonNull Context context, @NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMarkerClickListener(this);
        mIconGenerator = new DefaultIconGenerator<>(context);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Object markerTag = marker.getTag();
        if (markerTag instanceof XCluster) {
            //noinspection unchecked
            XCluster<T> XCluster = (XCluster<T>) marker.getTag();
            //noinspection ConstantConditions
            List<T> clusterItems = XCluster.getItems();

            if (mCallbacks != null) {
                if (clusterItems.size() > 1) {
                    return mCallbacks.onClusterClick(XCluster);
                } else {
                    return mCallbacks.onClusterItemClick(clusterItems.get(0));
                }
            }
        }

        return false;
    }

    void setCallbacks(@Nullable ClusterManager.Callbacks<T> listener) {
        mCallbacks = listener;
    }

    void setIconGenerator(@NonNull IconGenerator<T> iconGenerator) {
        mIconGenerator = iconGenerator;
    }

    void render(@NonNull List<XCluster<T>> XClusters) {
        List<XCluster<T>> clustersToAdd = new ArrayList<>();
        List<XCluster<T>> clustersToRemove = new ArrayList<>();

        for (XCluster<T> XCluster : XClusters) {
            if (!mMarkers.containsKey(XCluster)) {
                clustersToAdd.add(XCluster);
            }
        }

        for (XCluster<T> XCluster : mMarkers.keySet()) {
            if (!XClusters.contains(XCluster)) {
                clustersToRemove.add(XCluster);
            }
        }

        mXClusters.addAll(clustersToAdd);
        mXClusters.removeAll(clustersToRemove);

        // Remove the old XClusters.
        for (XCluster<T> XClusterToRemove : clustersToRemove) {
            Marker markerToRemove = mMarkers.get(XClusterToRemove);
            markerToRemove.setZIndex(BACKGROUND_MARKER_Z_INDEX);

            XCluster<T> parentXCluster = findParentCluster(mXClusters, XClusterToRemove.getLatitude(),
                    XClusterToRemove.getLongitude());
            if (parentXCluster != null) {
                animateMarkerToLocation(markerToRemove, new LatLng(parentXCluster.getLatitude(),
                        parentXCluster.getLongitude()), true);
            } else {
                markerToRemove.remove();
            }

            mMarkers.remove(XClusterToRemove);
        }

        // Add the new XClusters.
        for (XCluster<T> XClusterToAdd : clustersToAdd) {
            Marker markerToAdd;

            BitmapDescriptor markerIcon = getMarkerIcon(XClusterToAdd);
            String markerTitle = getMarkerTitle(XClusterToAdd);
            String markerSnippet = getMarkerSnippet(XClusterToAdd);

            XCluster parentXCluster = findParentCluster(clustersToRemove, XClusterToAdd.getLatitude(),
                    XClusterToAdd.getLongitude());
            if (parentXCluster != null) {
                markerToAdd = mGoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(parentXCluster.getLatitude(), parentXCluster.getLongitude()))
                        .icon(markerIcon)
                        .title(markerTitle)
                        .snippet(markerSnippet)
                        .zIndex(FOREGROUND_MARKER_Z_INDEX));
                animateMarkerToLocation(markerToAdd,
                        new LatLng(XClusterToAdd.getLatitude(), XClusterToAdd.getLongitude()), false);
            } else {
                markerToAdd = mGoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(XClusterToAdd.getLatitude(), XClusterToAdd.getLongitude()))
                        .icon(markerIcon)
                        .title(markerTitle)
                        .snippet(markerSnippet)
                        .alpha(0.0F)
                        .zIndex(FOREGROUND_MARKER_Z_INDEX));
                animateMarkerAppearance(markerToAdd);
            }
            markerToAdd.setTag(XClusterToAdd);

            mMarkers.put(XClusterToAdd, markerToAdd);
        }
    }

    @NonNull
    private BitmapDescriptor getMarkerIcon(@NonNull XCluster<T> XCluster) {
        BitmapDescriptor clusterIcon;

        List<T> clusterItems = XCluster.getItems();
        if (clusterItems.size() > 1) {
            clusterIcon = mIconGenerator.getClusterIcon(XCluster);
        } else {
            clusterIcon = mIconGenerator.getClusterItemIcon(clusterItems.get(0));
        }

        return Preconditions.checkNotNull(clusterIcon);
    }

    @Nullable
    private String getMarkerTitle(@NonNull XCluster<T> XCluster) {
        List<T> clusterItems = XCluster.getItems();
        if (clusterItems.size() > 1) {
            return null;
        } else {
            return clusterItems.get(0).getTitle();
        }
    }

    @Nullable
    private String getMarkerSnippet(@NonNull XCluster<T> XCluster) {
        List<T> clusterItems = XCluster.getItems();
        if (clusterItems.size() > 1) {
            return null;
        } else {
            return clusterItems.get(0).getSnippet();
        }
    }

    @Nullable
    private XCluster<T> findParentCluster(@NonNull List<XCluster<T>> XClusters,
                                          double latitude, double longitude) {
        for (XCluster<T> XCluster : XClusters) {
            if (XCluster.contains(latitude, longitude)) {
                return XCluster;
            }
        }

        return null;
    }

    private void animateMarkerToLocation(@NonNull final Marker marker, @NonNull LatLng targetLocation,
                                         final boolean removeAfter) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(marker, "position",
                new LatLngTypeEvaluator(), targetLocation);
        objectAnimator.setInterpolator(new FastOutSlowInInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (removeAfter) {
                    marker.remove();
                }
            }
        });
        objectAnimator.start();
    }

    private void animateMarkerAppearance(@NonNull Marker marker) {
        ObjectAnimator.ofFloat(marker, "alpha", 1.0F).start();
    }

    private static class LatLngTypeEvaluator implements TypeEvaluator<LatLng> {

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            double latitude = (endValue.latitude - startValue.latitude) * fraction + startValue.latitude;
            double longitude = (endValue.longitude - startValue.longitude) * fraction + startValue.longitude;
            return new LatLng(latitude, longitude);
        }
    }
}