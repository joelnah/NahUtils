package nah.prayer.library.serializer

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer
import androidx.datastore.preferences.core.emptyPreferences
import nah.prayer.library.log.Nlog
import okio.buffer
import okio.sink
import okio.source
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

// 암호화된 Preferences Serializer 구현 (이전과 동일)
object EncryptedPreferencesSerializer : Serializer<Preferences> {
    private val KEY_ALIAS = "nah_datastore_encryption_key"
    private val TRANSFORMATION = "AES/GCM/NoPadding"
    private val IV_LENGTH = 12
    private val TAG_LENGTH = 128

    // 키스토어에서 암호화 키 획득
    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            val keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore")
            keyGenerator.init(
                KeyGenParameterSpec.Builder(KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
            return keyGenerator.generateKey()
        }

        return (keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
    }

    // 암호화 함수
    private fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey())
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data)

        return iv + encryptedData
    }

    // 복호화 함수
    private fun decrypt(encryptedData: ByteArray): ByteArray {
        if (encryptedData.size <= IV_LENGTH) return ByteArray(0)

        val iv = encryptedData.sliceArray(0 until IV_LENGTH)
        val ciphertext = encryptedData.sliceArray(IV_LENGTH until encryptedData.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateSecretKey(), GCMParameterSpec(TAG_LENGTH, iv))

        return cipher.doFinal(ciphertext)
    }

    override val defaultValue: Preferences = emptyPreferences()

    override suspend fun readFrom(input: InputStream): Preferences {
        return try {
            val encryptedData = input.readBytes()
            if (encryptedData.isEmpty()) return defaultValue

            val decryptedData = decrypt(encryptedData)
            val bufferedSource = decryptedData.inputStream().source().buffer()
            PreferencesSerializer.readFrom(bufferedSource)
        } catch (e: Exception) {
            Nlog.e("암호화된 Preferences 읽기 오류: ${e.message}")
            defaultValue
        }
    }

    override suspend fun writeTo(t: Preferences, output: OutputStream) {
        try {
            val byteArrayOutputStream = java.io.ByteArrayOutputStream()
            val bufferedSink = byteArrayOutputStream.sink().buffer()

            PreferencesSerializer.writeTo(t, bufferedSink)
            bufferedSink.flush()

            val rawData = byteArrayOutputStream.toByteArray()
            val encryptedData = encrypt(rawData)
            output.write(encryptedData)
        } catch (e: Exception) {
            Nlog.e("암호화된 Preferences 쓰기 오류: ${e.message}")
        }
    }
}