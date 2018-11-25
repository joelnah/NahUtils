package nah.prayer.library;

import android.content.Context;

import nah.prayer.library.Nsharedpreferences.Npref;


public class NahUtil {
    public static void set(Context con){
        new Nlog(con);
        Npref.init(con).build();
    }
    public static void set(Context con, String tag){
        new Nlog(con, tag);
        Npref.init(con).build();
    }
}
