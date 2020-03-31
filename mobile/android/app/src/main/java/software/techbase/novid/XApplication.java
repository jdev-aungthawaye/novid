package software.techbase.novid;

import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

import software.techbase.novid.cache.sharepreferences.UserInfoStorage;
import software.techbase.novid.component.android.notifications.XNotificationConstants;
import software.techbase.novid.component.android.notifications.XNotificationManager;
import software.techbase.novid.component.android.xlogger.XLogger;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            XNotificationManager.createNotificationChannel(
                    this,
                    XNotificationConstants.SERVICE_CHANNEL_ID,
                    "Service Channel",
                    "Application will notify whenever there is incoming status");

            XNotificationManager.createNotificationChannel(
                    this,
                    XNotificationConstants.REQUEST_CHANNEL_ID,
                    "Request Channel",
                    "Application will notify whenever there is incoming status");
        }

        FirebaseInstanceId
                .getInstance()
                .getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) return;
                    String token = Objects.requireNonNull(task.getResult()).getToken();
                    XLogger.debug(this.getClass(), "Firebase token : " + token);
                    UserInfoStorage.getInstance().setFirebaseToken(token);
                });
    }
}
