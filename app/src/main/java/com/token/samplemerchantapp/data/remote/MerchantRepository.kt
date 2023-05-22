package com.token.samplemerchantapp.data.remote

import com.token.samplemerchantapp.data.remote.MerchantApi
import com.token.samplemerchantapp.data.remote.request.InitRequest
import com.token.samplemerchantapp.data.remote.response.InitResponse
import retrofit2.Call

class MerchantRepository(private var merchantApi: MerchantApi) {

    fun requestMerchantSettings(request: InitRequest): Call<InitResponse> {
       return merchantApi.requestInitToken(request)
    }
}