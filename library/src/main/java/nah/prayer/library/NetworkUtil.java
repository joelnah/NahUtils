package nah.prayer.library;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by S on 2017-11-02.
 */

public class NetworkUtil {


    /**
     * SHA-256으로 해싱
     *
     */
    public static String getSha256En(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());

        StringBuilder builder = new StringBuilder();
        for (byte b:md.digest()){
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }

    /**
     * 실제 통신이 되는지 체크
     *
     */
    public boolean getWhatKindOfNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                return isOnline();
            }
        }
        return false;
    }

    private boolean isOnline() {
        CheckConnect cc = new CheckConnect("http://clients3.google.com/generate_204");
        cc.start();
        try {
            cc.join();
            return cc.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static class CheckConnect extends Thread {
        private boolean success;
        private final String host;

        private CheckConnect(String host) {
            this.host = host;
        }

        @Override
        public void run() {

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(host).openConnection();
                conn.setRequestProperty("User-Agent", "Android");
                conn.setConnectTimeout(5000);
                conn.connect();
                int responseCode = conn.getResponseCode();
                success = responseCode == 204;
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        private boolean isSuccess() {
            return success;
        }

    }

}
