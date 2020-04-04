package software.techbase.novid.component.service;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class ServiceUtils {

    public static boolean isServiceRunning(Class<?> serviceClass, Context context) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return false;
            }
        }
        return true;
    }

    public static void startLocationUpdaterService(Context mContext) {

        if (ServiceUtils.isServiceRunning(LocationUpdaterService.class, mContext)) {

            Intent startIntent = new Intent(mContext, LocationUpdaterService.class);
            startIntent.setAction(Constants.ACTION.START_ACTION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.startForegroundService(startIntent);
            } else {
                mContext.startService(startIntent);
            }
        }
    }

    public static void startNearDevicesUpdaterService(Context mContext) {

        if (ServiceUtils.isServiceRunning(NearbyUserUpdaterService.class, mContext)) {

            Intent startIntent = new Intent(mContext, NearbyUserUpdaterService.class);
            startIntent.setAction(Constants.ACTION.START_ACTION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.startForegroundService(startIntent);
            } else {
                mContext.startService(startIntent);
            }
        }
    }

    public static void startRequiredAccessObserverService(Context mContext) {

        if (ServiceUtils.isServiceRunning(RequiredAccessObserverService.class, mContext)) {

            Intent startIntent = new Intent(mContext, RequiredAccessObserverService.class);
            startIntent.setAction(Constants.ACTION.START_ACTION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.startForegroundService(startIntent);
            } else {
                mContext.startService(startIntent);
            }
        }
    }

}
