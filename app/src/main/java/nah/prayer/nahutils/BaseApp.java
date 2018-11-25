package nah.prayer.nahutils;

import android.app.Application;

import nah.prayer.library.NahUtil;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        //NahUtil.set(this);
        NahUtil.set(this, "nah");

    }
}
