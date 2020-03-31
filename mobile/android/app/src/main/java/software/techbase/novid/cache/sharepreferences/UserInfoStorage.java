package software.techbase.novid.cache.sharepreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Wai Yan on 10/17/18.
 */
public class UserInfoStorage {

    private final static String PREFERENCES_NAME = "USER_INFO_STORAGE";
    private final static String KEY_ACCESS_TOKEN = PREFERENCES_NAME + ".KEY_ACCESS_TOKEN";
    private final static String KEY_FIREBASE_TOKEN = PREFERENCES_NAME + ".KEY_FIREBASE_TOKEN";

    private static UserInfoStorage userInfoStorage;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    private UserInfoStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
    }

    public synchronized static void initialize(Context context) {
        if (userInfoStorage == null) {
            userInfoStorage = new UserInfoStorage(context);
        }
    }

    public synchronized static UserInfoStorage getInstance() {
        return UserInfoStorage.userInfoStorage;
    }

    public String getAccessToken() {
        return this.sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public void setAccessToken(String token) {
        this.editor.putString(KEY_ACCESS_TOKEN, token);
        this.editor.commit();
    }

    public String getFirebaseToken() {
        return this.sharedPreferences.getString(KEY_FIREBASE_TOKEN, null);
    }

    public void setFirebaseToken(String token) {
        this.editor.putString(KEY_FIREBASE_TOKEN, token);
        this.editor.commit();
    }

    public boolean isCredentialsAvailable() {

        return this.getAccessToken() != null && this.getAccessToken().trim().length() > 0;
    }

    public static void cleanUpAll() {
        UserInfoStorage.getInstance().editor.remove(KEY_ACCESS_TOKEN);
        UserInfoStorage.getInstance().editor.commit();
    }
}
