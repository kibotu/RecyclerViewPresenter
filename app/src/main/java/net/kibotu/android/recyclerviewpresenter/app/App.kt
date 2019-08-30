package net.kibotu.android.recyclerviewpresenter.app

import android.app.Application
import android.os.Build
import android.os.StrictMode
import net.kibotu.logger.LogcatLogger
import net.kibotu.logger.Logger
import net.kibotu.logger.Logger.logv
import net.kibotu.logger.logv

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initStrictMode()

        Logger.addLogger(LogcatLogger())

        logv("onCreate")
    }

    override fun onTerminate() {
        super.onTerminate()
        Logger.onTerminate()
    }

    fun initStrictMode() {

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls()
                .detectNetwork()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        detectLeakedRegistrationObjects()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        detectCleartextNetwork()
                }
                .detectActivityLeaks()
                .detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }
}