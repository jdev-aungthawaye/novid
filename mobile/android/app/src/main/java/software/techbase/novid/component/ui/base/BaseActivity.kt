package software.techbase.novid.component.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import software.techbase.novid.R
import software.techbase.novid.component.android.NetworkStatusBroadcastReceiver
import software.techbase.novid.component.android.xlogger.debug
import software.techbase.novid.component.ui.reusable.XSnackBar


/**
 * Created by Wai Yan on 3/28/20.
 */

abstract class BaseActivity : FirebaseRemoteConfigUpdateCheckerActivity(),
    NetworkStatusBroadcastReceiver.NetworkStatusListener {

    private lateinit var networkStatusBroadcastReceiver: NetworkStatusBroadcastReceiver

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        this.networkStatusBroadcastReceiver = NetworkStatusBroadcastReceiver(this, this)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onInternetAvailable() {

        debug { "onInternetAvailable" }

        XSnackBar.hide()
    }

    override fun onInternetUnavailable() {

        debug { "onInternetUnavailable" }

        XSnackBar.show(
            this.findViewById(android.R.id.content),
            R.string.MESSAGE_LOCAL__INTERNET_CONNECTION_ERROR,
            Snackbar.LENGTH_INDEFINITE,
            R.drawable.ic_no_internet_white,
            R.color.x_error,
            R.string.DISMISS
        )
    }

    override fun onResume() {
        super.onResume()
        this.networkStatusBroadcastReceiver.registerToContext()
    }

    override fun onPause() {
        super.onPause()
        this.networkStatusBroadcastReceiver.unregisterFromContext()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val view = this.currentFocus
        if (view != null) {
            val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
