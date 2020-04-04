package software.techbase.novid.component.ui.map;

import com.google.android.gms.maps.model.LatLng;

import software.techbase.novid.component.ui.map.clustering.XClusterItem;
import software.techbase.novid.domain.remote.api.GetContacts;

/**
 * Created by Wai Yan on 4/4/20.
 */
public class ContactClusterItem implements XClusterItem {

    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private GetContacts.Response contact;

    public ContactClusterItem(LatLng mPosition,
                              String mTitle,
                              String mSnippet,
                              GetContacts.Response contact) {
        this.mPosition = mPosition;
        this.mTitle = mTitle;
        this.mSnippet = mSnippet;
        this.contact = contact;
    }

    @Override
    public double getLatitude() {
        return this.mPosition.latitude;
    }

    @Override
    public double getLongitude() {
        return this.mPosition.longitude;
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
