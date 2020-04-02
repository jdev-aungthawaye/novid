package software.techbase.novid.domain.remote.client;

import android.annotation.SuppressLint;

import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.domain.remote.api.UpdateLocation;
import software.techbase.novid.domain.remote.api.UpdateNearbyUser;
import software.techbase.novid.util.TimestampUtil;

/**
 * Created by Wai Yan on 4/2/20.
 */
@SuppressLint("CheckResult")
public class XApplicationAPIClient {

    public static void updateLocation(double lat, double lng) {

        UpdateLocation.Request request = new UpdateLocation.Request();
        request.lat = lat;
        request.lng = lng;
        request.collectedAt = TimestampUtil.now();

        new UpdateLocation()
                .invoke(request)
                .doOnSubscribe(disposable -> {
                })
                .doOnTerminate(() -> {
                })
                .doOnError(throwable -> {
                })
                .subscribe(httpResponse -> {
                    if (httpResponse.isSuccessful()) {
                        XLogger.debug(XApplicationAPIClient.class, "Successfully send current location.");
                    }
                }, throwable -> XLogger.debug(XApplicationAPIClient.class, "Failed send current location : " + throwable.getMessage()));
    }

    public static void updateNearbyUser(String userId) {

        UpdateNearbyUser.Request request = new UpdateNearbyUser.Request();
        request.nearBy = userId;
        request.collectedAt = TimestampUtil.now();

        new UpdateNearbyUser()
                .invoke(request)
                .doOnSubscribe(disposable -> {
                })
                .doOnTerminate(() -> {
                })
                .doOnError(throwable -> {
                })
                .subscribe(httpResponse -> {
                    if (httpResponse.isSuccessful()) {
                        XLogger.debug(XApplicationAPIClient.class, "Successfully send nearby user.");
                    }
                }, throwable -> XLogger.debug(XApplicationAPIClient.class, "Failed send current nearby user : " + throwable.getMessage()));
    }
}
