package nah.prayer.library;

import android.app.Activity;
import android.view.ViewConfiguration;
import android.widget.Toast;

/**
 * Created by inkg on 2016. 12. 2..
 */

public class TimeCheckUtil {

    private static long lastClick = 0;

    public static boolean isWaiting() {
        return isWaiting(0);
    }
    public static boolean isWaiting(int sec) {

        int timeout;

        if(sec==0)
            timeout = ViewConfiguration.getJumpTapTimeout();
        else
            timeout = sec;

        if (getLastClickTimeout() > timeout) {
            lastClick = System.currentTimeMillis();
            return true;
        }else{
            return false;
        }
    }

    private static Long getLastClickTimeout() {
        return System.currentTimeMillis() - lastClick;
    }
}