package com.token.samplemerchantapp.data.remote

import com.token.samplemerchantapp.util.Constant
import com.token.samplemerchantapp.util.EncryptionUtil.generateRandomKey
import com.token.samplemerchantapp.util.EncryptionUtil.generateSignature
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MerchantRetrofitInstance {

    private var merchantApi: MerchantApi? = null
    private val interceptor = HttpLoggingInterceptor()

    private val customHeaderInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()

        val request = chain.request()

        val requestUrl = request.url.toString()
        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val requestBodyString = buffer.readString(Charsets.UTF_8)
        val randomKey = generateRandomKey()

        requestBuilder
            .addHeader("x-signature", generateSignature(requestUrl, randomKey, requestBodyString))
            .addHeader("x-auth-version", "V1")
            .addHeader("x-api-key", Constant.apiKey)
            .addHeader("x-rnd-key", randomKey)

        chain.proceed(requestBuilder.build())
    }

    fun getOderoApi(): MerchantApi {
        if (merchantApi == null) {

            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(customHeaderInterceptor)
                .build()

            merchantApi = Retrofit.Builder()
                .baseUrl("https://sandbox-api-gateway.oderopay.com.tr")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(MerchantApi::class.java)

        }
        return merchantApi!!
    }

}