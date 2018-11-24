package nah.prayer.nahutils;

import android.app.Application;

import nah.prayer.library.Nlog;
import nah.prayer.library.Nsharedpreferences.Npref;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Nlog 생성
        //new Nlog(this);
        //Nlog 생성, tag 변경
        new Nlog(this, "nah");

        Npref.init(this).build();

    }
}
