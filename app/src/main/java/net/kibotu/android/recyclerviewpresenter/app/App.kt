package net.kibotu.android.recyclerviewpresenter.app

import android.app.Application
import net.kibotu.logger.Logger

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.with(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        Logger.onTerminate()
    }
}