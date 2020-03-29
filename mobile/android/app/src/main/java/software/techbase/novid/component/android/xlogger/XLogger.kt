package software.techbase.novid.component.android.xlogger

import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import software.techbase.novid.BuildConfig

/**
 * Created by Wai Yan on 3/28/20.
 */

/**
 * Debug log call.
 *
 * Should be used in the most of the cases to print a debug message
 * The color for this call is Green [#66BB6A]
 * */
fun debug(@NonNull vararg items: Any?) {
    if (BuildConfig.DEBUG) {
        for (item in items) {
            Log.d("$LOGGING_TAG $DEBUG_TAG", item.toString())
        }
    }
}

/**
 * Direct debug code logger
 */
fun debug(@NonNull code: () -> Any?) = Log.d("$LOGGING_TAG $DEBUG_TAG", code().toString())

/**
 * Error log call.
 *
 * Should be used to log errors or exceptions
 * The color for this call is Red [#EF5350]
 * */
fun error(@Nullable vararg items: Any?) {
    if (BuildConfig.DEBUG) {
        for (item in items) {
            Log.e("$LOGGING_TAG $ERROR_TAG", item.toString())
        }
    }
}

/**
 * Direct error code logger
 */
fun error(@NonNull code: () -> Any?) = Log.e("$LOGGING_TAG $ERROR_TAG", code().toString())

/**
 * Warning log call.
 *
 * Should be used to print warnings and non-critical errors
 * The color for this call is Yellow [#FFA726]
 * */
fun warnings(@Nullable vararg items: Any?) {
    if (BuildConfig.DEBUG) {
        for (item in items) {
            Log.w("$LOGGING_TAG $WARNING_TAG", item.toString())
        }
    }
}

/**
 * Direct warning code logger
 */
fun warnings(@NonNull code: () -> Any?) = Log.w("$LOGGING_TAG $WARNING_TAG", code().toString())

/**
 * Info log call.
 *
 * Should be used to print information or data about
 * the current program execution algorithm state
 * The color for this call is Blue [#00B0FF]
 * */
fun information(@Nullable vararg items: Any?) {
    if (BuildConfig.DEBUG) {
        for (item in items) {
            Log.i("$LOGGING_TAG $INFO_TAG", item.toString())
        }
    }
}

/**
 * Direct info code logger
 */
fun information(@NonNull code: () -> Any?) = Log.i("$LOGGING_TAG $INFO_TAG", code().toString())

/**
 * Assert log call.
 *
 * Should be used to print assert details
 * The color for this call is Purple [#BA68C8]
 * */
fun assert(@Nullable vararg items: Any?) {
    if (BuildConfig.DEBUG) {
        for (item in items) {
            Log.wtf("$LOGGING_TAG $ASSERT_TAG", item.toString())
        }
    }
}

/**
 * Direct assert code logger
 */
fun assert(@NonNull code: () -> Any?) = Log.wtf("$LOGGING_TAG $ASSERT_TAG", code().toString())