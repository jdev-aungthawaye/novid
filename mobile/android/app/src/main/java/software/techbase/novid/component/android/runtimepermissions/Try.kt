package software.techbase.novid.component.android.runtimepermissions

import android.util.Log

/**
 * Created by Wai Yan on 3/30/19.
 */
object Try {

    private const val DELAY = 2000
    private val TAG: String = Try::class.java.simpleName

    fun withUiThread(runnable: Runnable?, tryCount: Int) {
        runnable?.let {
            runTryCycle(it, tryCount)
        }
    }

    fun withThreadIfFail(action: () -> Unit, tryCount: Int) {
        action.let {
            try {
                Runnable(it).run()
            } catch (e: Exception) {
                Log.d(TAG, "Attempt in UI thread fail!")
                Thread { runTryCycle(Runnable(it), tryCount) }.start()
            }
        }
    }

    fun withThread(action: () -> Unit, tryCount: Int) {
        Thread { runTryCycle(Runnable(action), tryCount) }.start()
    }

    private fun runTryCycle(runnable: Runnable?, tryCount: Int) {
        runnable?.let {
            var tryCountLocal = tryCount
            var attempt = 1
            while (tryCountLocal > 0) {
                try {
                    Log.d(TAG, "Attempt $attempt")
                    it.run()
                } catch (e: Exception) {
                    Log.w(TAG, "Attempt $attempt fail!")
                    attempt++
                    tryCountLocal--
                    try {
                        Thread.sleep(DELAY.toLong())
                    } catch (e1: InterruptedException) {
                        e1.printStackTrace()
                    }

                    continue
                }
                tryCountLocal = 0
            }
        }
    }
}