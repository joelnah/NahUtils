package nah.prayer.library

import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object NetworkUtil {

    /**
     * 실제 통신이 되는지 체크
     */
    suspend fun getWhatKindOfNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        cm?.let {
            it.getNetworkCapabilities(it.activeNetwork)?.let { capabilities ->
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> isOnline()
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> isOnline()
                    else -> false
                }
            }
        }
        return false
    }

    private suspend fun isOnline(): Boolean = withContext(Dispatchers.IO) {
        var success = false

        try {
            val conn = URL("http://clients3.google.com/generate_204").openConnection() as HttpURLConnection
            conn.apply {
                setRequestProperty("User-Agent", "Android")
                connectTimeout = 5000
                connect()
                success = responseCode == 204
                disconnect()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        success
    }
}