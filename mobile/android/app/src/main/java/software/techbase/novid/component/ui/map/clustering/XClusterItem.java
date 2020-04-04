package software.techbase.novid.component.ui.map.clustering;

import androidx.annotation.Nullable;

/**
 * Created by Wai Yan on 4/4/20.
 */
public interface XClusterItem extends QuadTreePoint {
    /**
     * The latitude of the item.
     *
     * @return the latitude of the item
     */
    @Override
    double getLatitude();

    /**
     * The longitude of the item.
     *
     * @return the longitude of the item
     */
    @Override
    double getLongitude();

    /**
     * The title of the item.
     *
     * @return the title of the item
     */
    @Nullable
    String getTitle();

    /**
     * The snippet of the item.
     *
     * @return the snippet of the item
     */
    @Nullable
    String getSnippet();
}