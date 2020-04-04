package software.techbase.novid.component.ui.map.clustering;

/**
 * Created by Wai Yan on 4/4/20.
 */

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.BitmapDescriptor;


/**
 * Generates icons for clusters and cluster items. Note that its implementations
 * should cache generated icons for subsequent use. For the example implementation see
 * {@link DefaultIconGenerator}.
 */
public interface IconGenerator<T extends XClusterItem> {
    /**
     * Returns an icon for the given XCluster.
     *
     * @param XCluster the XCluster to return an icon for
     * @return the icon for the given XCluster
     */
    @NonNull
    BitmapDescriptor getClusterIcon(@NonNull XCluster<T> XCluster);

    /**
     * Returns an icon for the given cluster item.
     *
     * @param clusterItem the cluster item to return an icon for
     * @return the icon for the given cluster item
     */
    @NonNull
    BitmapDescriptor getClusterItemIcon(@NonNull T clusterItem);
}
