package software.techbase.novid.component.ui.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import software.techbase.novid.domain.remote.api.GetContacts;

/**
 * Created by Wai Yan on 4/4/20.
 */
public class XContactClusterItem implements ClusterItem {

    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private GetContacts.Response contact;

    public XContactClusterItem(LatLng mPosition,
                               String mTitle,
                               String mSnippet,
                               GetContacts.Response contact) {
        this.mPosition = mPosition;
        this.mTitle = mTitle;
        this.mSnippet = mSnippet;
        this.contact = contact;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public GetContacts.Response getContact() {
        return contact;
    }
}
