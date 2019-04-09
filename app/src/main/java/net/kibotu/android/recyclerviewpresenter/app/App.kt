package net.kibotu.android.recyclerviewpresenter.app

import android.app.Application
import net.kibotu.logger.LogcatLogger
import net.kibotu.logger.Logger
import net.kibotu.logger.Logger.logv

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.addLogger(LogcatLogger())

        logv("onCreate")
    }

    override fun onTerminate() {
        super.onTerminate()
        Logger.onTerminate()
    }
}