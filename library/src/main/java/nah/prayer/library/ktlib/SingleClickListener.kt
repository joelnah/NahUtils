package nah.prayer.library.ktlib


import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import nah.prayer.library.CommonData.INTERVAL
import nah.prayer.library.TimeCheckUtil

class SingleClickListener(
        private val clickListener: View.OnClickListener,
        private val interval: Int = INTERVAL,
): View.OnClickListener {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var tmpView:View?=null
    }

    override fun onClick(v: View) {
        if(tmpView != v){
            tmpView=v
            TimeCheckUtil.isWaiting(interval)
            clickListener.onClick(v)
        }else {
            if (TimeCheckUtil.isWaiting(interval)) {
                clickListener.onClick(v)
            } else {
                Log.d("nah", "waiting for a while")
            }
        }
    }
}

fun View.setOnSingleClickListener(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(SingleClickListener(listener))
}

fun View.setOnSingleClickListener(action: (v: View) -> Unit, interval: Int) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(SingleClickListener(listener, interval))
}