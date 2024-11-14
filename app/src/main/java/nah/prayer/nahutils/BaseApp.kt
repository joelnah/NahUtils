package nah.prayer.nahutils

import android.app.Application
import nah.prayer.library.NahUtils

class BaseApp : Application(){
    override fun onCreate() {
        super.onCreate()
        NahUtils.init(this, "nah")
    }
}