package nah.prayer.library.DoubleBack;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by inkg on 2016. 12. 2..
 */

public class BackPressDestroy {

    private long backKeyPressedTime = 0;


    public BackPressDestroy() {
    }

    public void onBackPressed(Activity act, String msg) {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            act.finish();
        }
    }
}