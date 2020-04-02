package software.techbase.novid.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

/**
 * Created by Wai Yan on 12/26/18.
 */
object XTransitionUtil {

    @JvmStatic
    fun showNextActivity(
        activity: Activity,
        @NonNull activityClazz: Class<out AppCompatActivity>,
        @AnimRes incomingActivityAnim: Int,
        @AnimRes outgoingActivityAnim: Int,
        killMe: Boolean
    ) {
        val intent = Intent(activity, activityClazz)
        activity.startActivity(intent)
        if (killMe) {
            activity.finish()
        }
        if (incomingActivityAnim != 0 && outgoingActivityAnim != 0) {
            activity.overridePendingTransition(incomingActivityAnim, outgoingActivityAnim)
        }
    }

    @JvmStatic
    fun showNextActivityAsRoot(
        activity: Activity,
        @NonNull activityClazz: Class<out AppCompatActivity>,
        @AnimRes incomingActivityAnim: Int,
        @AnimRes outgoingActivityAnim: Int
    ) {
        val intent = Intent(activity, activityClazz)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        activity.startActivity(intent)
        activity.finish()
        if (incomingActivityAnim != 0 && outgoingActivityAnim != 0) {
            activity.overridePendingTransition(incomingActivityAnim, outgoingActivityAnim)
        }
    }

    @JvmStatic
    fun showNextActivityWithMap(
        activity: Activity,
        activityClazz: Class<out AppCompatActivity>,
        data: Map<String, Serializable>?,
        @AnimRes incomingActivityAnim: Int,
        @AnimRes outgoingActivityAnim: Int,
        killMe: Boolean
    ) {

        val intent = Intent(activity, activityClazz)
        if (data != null && data.isNotEmpty()) {
            for (key in data.keys) {
                intent.putExtra(key, data[key])
            }
        }
        activity.startActivity(intent)
        if (killMe) {
            activity.finish()
        }
        if (incomingActivityAnim != 0 && outgoingActivityAnim != 0) {
            activity.overridePendingTransition(incomingActivityAnim, outgoingActivityAnim)
        }
    }

    @JvmStatic
    fun showNextActivityForResult(
        activity: Activity,
        @NonNull activityClazz: Class<out AppCompatActivity>,
        @Nullable extras: Bundle?,
        @AnimRes incomingActivityAnim: Int,
        @AnimRes outgoingActivityAnim: Int,
        @NonNull requestCode: Int
    ) {
        val intent = Intent(activity, activityClazz)
        if (extras != null) {
            intent.putExtras(extras)
        }
        activity.startActivityForResult(intent, requestCode)
        activity.overridePendingTransition(incomingActivityAnim, outgoingActivityAnim)
    }
}