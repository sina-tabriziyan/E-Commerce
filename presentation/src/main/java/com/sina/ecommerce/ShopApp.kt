package com.sina.ecommerce

import android.app.Application
import com.sina.data.di.dataModule
import com.sina.domain.di.domainModule
import com.sina.ecommerce.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShopApp)
            modules(
                listOf(
                    presentationModule,
                    dataModule,
                    domainModule
                )
            )
        }
    }
}