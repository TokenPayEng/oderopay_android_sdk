package com.token.samplemerchantapp.util

import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {

    private const val HMAC_SHA512 = "HmacSHA216"

    fun getHexString(data: ByteArray): String {
        val hexString = StringBuilder()
        for (aData in data) {
            val hex = Integer.toHexString(0xff and aData.toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }

    @Throws(InvalidKeyException::class)
    fun encrypt(key: String, message: String): String {
        val secretKey = SecretKeySpec(key.toByteArray(), HMAC_SHA512)
        val mac = Mac.getInstance(HMAC_SHA512)
        mac.init(secretKey)
        val encodedMessage = mac.doFinal(message.toByteArray())
        return getHexString(encodedMessage)
    }


    fun sha256(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    fun generateSignature(url: String, randomKey: String, body: String): String {
        val concatenatedString = url + Constant.apiKey + Constant.secretKey + randomKey + body
        val digest = MessageDigest.getInstance("SHA256")
        val encodedHash = digest.digest(concatenatedString.toByteArray());
        return encodeToString(encodedHash,DEFAULT).trim().uppercase(Locale.ENGLISH);
    }

    fun generateRandomKey(): String {
        return UUID.randomUUID().toString()
    }
}