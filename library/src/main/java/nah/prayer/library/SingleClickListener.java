package nah.prayer.library;

import android.view.View;

import static nah.prayer.library.TimeCheckUtil.isWaiting;

public abstract class SingleClickListener implements View.OnClickListener {
    private int sec=1000;
    public abstract void onSingleClick(View v);

    public SingleClickListener(){}
    public SingleClickListener(int sec){
        this.sec = sec;
    }

    @Override
    public void onClick(View v) {
        if (isWaiting(sec)) {
            onSingleClick(v);
        }
    }
}
