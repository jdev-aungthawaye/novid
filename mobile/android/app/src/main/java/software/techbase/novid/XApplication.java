package software.techbase.novid;

import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.analytics.FirebaseAnalytics;

import software.techbase.novid.cache.sharepreferences.UserInfoStorage;
import software.techbase.novid.component.android.notifications.XNotificationManager;

/**
 * Created by Wai Yan on 2019-09-05.
 */
public class XApplication extends MultiDexApplication {

    private static XApplication instance;

    public XApplication() {
        instance = this;
    }

    public static XApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        FirebaseAnalytics.getInstance(this);
        UserInfoStorage.initialize(this);
        //Initialize notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            XNotificationManager.createNotificationChannel(
                    this,
                    XNotificationManager.CHAT_CHANNEL_ID,
                    "Notify alert",
                    "Application will notify whenever there is incoming status");
        }
    }
}
