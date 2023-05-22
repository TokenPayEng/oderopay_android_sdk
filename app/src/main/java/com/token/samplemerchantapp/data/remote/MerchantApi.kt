package com.token.samplemerchantapp.data.remote

import com.token.samplemerchantapp.data.remote.request.InitRequest
import com.token.samplemerchantapp.data.remote.response.InitResponse
import retrofit2.Call
import retrofit2.http.*

interface MerchantApi {

    @POST("payment/v1/checkout-payments/init")
    fun requestInitToken(@Body initRequest: InitRequest): Call<InitResponse>
}