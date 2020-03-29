package software.techbase.novid.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class LocationUtils {

    public static String getAddressName(Context mContext, double lat, double lng) {

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address address = addresses.get(0);
            return address.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
