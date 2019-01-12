package nah.prayer.library;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.util.Log;


/**
 * Created by S on 2017-04-13.
 */



public class Nlog {

    private static String TAG = "nah";
    private static boolean DEBUG= false;


    Nlog(Context con){
        DEBUG = isDebuggable(con);
    }
    Nlog(Context con, String tag){
        DEBUG = isDebuggable(con);
        TAG = tag;
    }


    /**
     * Log Level Error
     **/
    public static void e(String message) {
        if (DEBUG) Log.e(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Warning
     **/
    public static void w(String message) {
        if (DEBUG) Log.w(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Information
     **/
    public static void i(String message) {
        if (DEBUG) Log.i(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Debug
     **/
    public static void d(String message) {
        if (DEBUG) Log.d(TAG, buildLogMsg(message));
    }
    public static void d(int message) {
        if (DEBUG) Log.d(TAG, buildLogMsg("int : "+message));
    }
    public static void d(double message) {
        if (DEBUG) Log.d(TAG, buildLogMsg("double : "+message));
    }
    public static void d(float message) {
        if (DEBUG) Log.d(TAG, buildLogMsg("float : "+message));
    }
    public static void d(long message) {
        if (DEBUG) Log.d(TAG, buildLogMsg("long : "+message));
    }
    public static void d(boolean message) {
        if (DEBUG) Log.d(TAG, buildLogMsg("boolean : "+message));
    }

    /**
     * Log Level Verbose
     **/
    public static void v(String message) {
        if (DEBUG) Log.v(TAG, buildLogMsg(message));
    }
    public static void mem() {
        if (DEBUG){
            Log.e(TAG,buildLogMsg("Native heap size: "+(Debug.getNativeHeapSize() / (1024)) + "KB"));
            Log.e(TAG,buildLogMsg("Heap Free size : "+(Debug.getNativeHeapFreeSize() / (1024)) + "KB"));
            Log.e(TAG,buildLogMsg("Heap Allocated size : "+(Debug.getNativeHeapAllocatedSize() / (1024)) + "KB"));

            Log.e(TAG,buildLogMsg("TOTAL MEMORY : "+(Runtime.getRuntime().totalMemory() / (1024 * 1024)) + "MB"));
            Log.e(TAG,buildLogMsg("MAX MEMORY : "+(Runtime.getRuntime().maxMemory() / (1024 * 1024)) + "MB"));
            Log.e(TAG,buildLogMsg("FREE MEMORY : "+(Runtime.getRuntime().freeMemory() / (1024 * 1024)) + "MB"));
            Log.e(TAG,buildLogMsg("ALLOCATION MEMORY : "+((Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory()) / (1024 * 1024)) + "MB"));
        }
    }


    public static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName());
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);

        return sb.toString();
    }


    private static boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /* debuggable variable will remain false */
        }
        return debuggable;
    }

}
