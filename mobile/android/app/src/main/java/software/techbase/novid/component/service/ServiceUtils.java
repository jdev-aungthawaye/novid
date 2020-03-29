package software.techbase.novid.component.service;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by Wai Yan on 3/29/20.
 */
public class ServiceUtils {

    public static boolean isServiceRunning(Class<?> serviceClass, Context context) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
