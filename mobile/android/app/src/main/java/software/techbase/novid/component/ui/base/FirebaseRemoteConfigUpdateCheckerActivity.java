package software.techbase.novid.component.ui.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;

import software.techbase.novid.component.android.apkinstaller.ApkInstaller;
import software.techbase.novid.component.android.runtimepermissions.RuntimePermissions;
import software.techbase.novid.component.ui.reusable.XAlertDialog;

/**
 * Created by Wai Yan on 3/18/20.
 */
public abstract class FirebaseRemoteConfigUpdateCheckerActivity extends AppCompatActivity {

    private static final String VERSION_CODE_KEY = "latest_app_version";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Language
        String languageToLoad = "my";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        this.setContentView(this.getLayoutFileId());
        this.checkUpdate();
    }

    protected abstract int getLayoutFileId();

    private void checkUpdate() {

        this.getVersionCodeFromFirebase(versionCode -> {

            if (versionCode > getCurrentVersionCode()) {

                new XAlertDialog(this,
                        XAlertDialog.Type.INFO,
                        "Update version available.",
                        "Download",
                        v -> this.directDownloadAndInstall()).show();
            }
        });
    }

    private void getVersionCodeFromFirebase(VersionCodeResponseListener listener) {

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        HashMap<String, Object> firebaseDefaultMap = new HashMap<>();
        firebaseDefaultMap.put(VERSION_CODE_KEY, getCurrentVersionCode());

        mFirebaseRemoteConfig.setDefaultsAsync(firebaseDefaultMap);

        mFirebaseRemoteConfig.fetch(0L)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        mFirebaseRemoteConfig.activate();
                        listener.onResponse(Integer.parseInt(mFirebaseRemoteConfig.getString(VERSION_CODE_KEY)));
                    }
                });
    }

    private int getCurrentVersionCode() {

        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @FunctionalInterface
    interface VersionCodeResponseListener {

        void onResponse(int versionCode);
    }

    private void directDownloadAndInstall() {

        final String APP_DIRECT_DOWNLOAD_URL = "https://github.com/wyphyoe/android_apk/raw/master/Novid.apk";

        RuntimePermissions.with(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .onAccepted(permissionResult -> {
                    ApkInstaller.install(this, APP_DIRECT_DOWNLOAD_URL, "Novid.apk");
                })
                .onDenied(permissionResult -> {
                    new XAlertDialog(this,
                            XAlertDialog.Type.WARNING,
                            "Need to allow storage permission.",
                            null,
                            v -> this.directDownloadAndInstall()).show();
                })
                .ask();
    }
}
