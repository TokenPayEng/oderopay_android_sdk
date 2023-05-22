package com.token.samplemerchantapp.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.token.samplemerchantapp.data.remote.model.Item

@JsonClass(generateAdapter = true)
 data class InitRequest(
    @Json(name = "paymentGroup")
    var paymentGroup : String,
    @Json(name = "callbackUrl")
    var callbackUrl : String,
    @Json(name = "paidPrice")
    var paidPrice : Int,
    @Json(name = "price")
    var price: Int,
    @Json(name = "conversationId")
    var conversationId: String,
    @Json(name = "currency")
    var currency: String,
    @Json(name = "cardUserKey")
    var cardUserKey: String?,
    @Json(name = "items")
    var items: List<Item>
)
