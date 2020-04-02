package software.techbase.novid.component.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.io.Closeable;

/**
 * Created by Wai Yan on 3/28/20.
 */
public class NetworkStatusBroadcastReceiver extends BroadcastReceiver implements Closeable {

    private Context mContext;
    private NetworkStatusBroadcastReceiver.NetworkStatusListener networkStatusListener;

    public NetworkStatusBroadcastReceiver(
            Context mContext,
            NetworkStatusBroadcastReceiver.NetworkStatusListener networkStatusListener) {

        this.mContext = mContext;
        this.networkStatusListener = networkStatusListener;
        this.registerToContext();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (isInternetAvailable(this.mContext)) {
            networkStatusListener.onInternetAvailable();
        } else {
            networkStatusListener.onInternetUnavailable();
        }
    }

    private void registerToContext() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.mContext.registerReceiver(this, intentFilter);
    }

    @Override
    public void close() {
        this.mContext.unregisterReceiver(this);
    }

    public interface NetworkStatusListener {

        void onInternetAvailable();

        void onInternetUnavailable();
    }

    public static boolean isInternetAvailable(Context mContext) {

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
