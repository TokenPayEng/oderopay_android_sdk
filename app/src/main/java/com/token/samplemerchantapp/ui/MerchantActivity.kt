package com.token.samplemerchantapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.token.oderopay.odero.OderoLibrary
import com.token.oderopay.odero.OderoPayResultListener
import com.token.oderopay.odero.OderoResult
import com.token.samplemerchantapp.R
import com.token.samplemerchantapp.data.remote.MerchantRepository
import com.token.samplemerchantapp.data.remote.MerchantRetrofitInstance
import com.token.samplemerchantapp.data.remote.model.Item
import com.token.samplemerchantapp.data.remote.request.InitRequest
import com.token.samplemerchantapp.data.remote.response.InitResponse
import com.token.samplemerchantapp.databinding.ActivityMerchantBinding
import com.token.samplemerchantapp.domain.model.RowItem
import com.token.samplemerchantapp.util.EncryptionUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MerchantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMerchantBinding
    var token: String = ""
    private lateinit var adapter: MerchantAdapter
    private lateinit var myList: List<RowItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMerchantBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        myList = getMyList()
        adapter = MerchantAdapter(myList)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val merchantApi = MerchantRetrofitInstance.getOderoApi()
        val repository = MerchantRepository(merchantApi)

        binding.oderoPayButton.setOnClickListener {

            val call = repository.requestMerchantSettings(
                InitRequest(
                    paymentGroup = "PRODUCT",
                    callbackUrl = "NO_CALLBACK_URL",
                    paidPrice = 128,
                    price = 100,
                    conversationId = EncryptionUtil.generateRandomKey(),
                    currency = "TRY",
                    cardUserKey = "292bbb3d-ffb2-4cb2-bbb5-8675a7484ebb",
                    items = listOf(
                        Item("Elma", 100)
                    )
                )
            )

            call.enqueue(object : Callback<InitResponse> {
                override fun onResponse(call: Call<InitResponse>, response: Response<InitResponse>) {
                    if (response.isSuccessful) {
                        token = response.body()?.data?.token ?: ""

                        OderoLibrary.getInstance()?.startPayment(
                            token = token,
                            applicationContext = applicationContext,
                            listener = object : OderoPayResultListener {
                                override fun onOderoPaySuccess(result : OderoResult) {
                                    Log.e("MERCHANT", "onOderoPaySuccess")
                                }

                                override fun onOderoPayCancelled() {
                                    Log.e("MERCHANT", "onOderoPayCancelled")
                                }

                                override fun onOderoPayFailure(errorId: Int, errorMsg: String?) {
                                    Log.e("MERCHANT", "onOderoPayFailure")
                                }
                            })
                    }
                }

                override fun onFailure(call: Call<InitResponse>, t: Throwable) {
                    // handle failure
                }
            })
        }

    }

    private fun getMyList(): List<RowItem> {
        return listOf(
            RowItem(R.drawable.macbook, "MacBook Pro", 1000.00),
            RowItem(R.drawable.airpods, "Airpods", 520.50),
            RowItem(R.drawable.macbook, "MacBook Pro", 1000.00),
            RowItem(R.drawable.airpods, "Airpods", 520.50)
        )
    }
}