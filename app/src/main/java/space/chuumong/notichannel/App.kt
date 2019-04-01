package space.chuumong.notichannel

import android.app.Application
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

/**
 * Created by chuumong on 2019-04-01.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        NotificationManager.createChannel(this)
    }
}