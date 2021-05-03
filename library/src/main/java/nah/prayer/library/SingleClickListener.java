package nah.prayer.library;

import android.view.View;

import static nah.prayer.library.CommonData.INTERVAL;
import static nah.prayer.library.TimeCheckUtil.isWaiting;

public abstract class SingleClickListener implements View.OnClickListener {
    public abstract void onSingleClick(View v);

    private int interval = INTERVAL;

    public SingleClickListener(){}
    public SingleClickListener(int sec){
        interval = sec;
    }

    @Override
    public void onClick(View v) {
        if (isWaiting(interval)) {
            onSingleClick(v);
        }
    }
}
