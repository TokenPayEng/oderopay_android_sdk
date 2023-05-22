package com.token.samplemerchantapp

import android.app.Application
import com.token.oderopay.odero.Environment
import com.token.oderopay.odero.OderoLibrary

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        OderoLibrary.initSDK(Environment.SANDBOX_TR)
    }

}