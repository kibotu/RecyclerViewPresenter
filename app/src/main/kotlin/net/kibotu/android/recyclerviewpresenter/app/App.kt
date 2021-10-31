package net.kibotu.android.recyclerviewpresenter.app

import android.app.Application
import android.os.Build
import android.os.StrictMode
import net.kibotu.logger.LogcatLogger
import net.kibotu.logger.Logger

/**
 * Created by [Jan Rabe](https://kibotu.net).
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initStrictMode()

        Logger.addLogger(LogcatLogger())

        Logger.v("onCreate")
    }

    private fun initStrictMode() {

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls()
                .detectNetwork()
                .penaltyLog()
                .build()
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .apply {
                    detectLeakedRegistrationObjects()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        detectCleartextNetwork()
                }
                .detectActivityLeaks()
                .detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .build()
        )
    }
}