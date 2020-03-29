package software.techbase.novid;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.analytics.FirebaseAnalytics;

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
    }
}
