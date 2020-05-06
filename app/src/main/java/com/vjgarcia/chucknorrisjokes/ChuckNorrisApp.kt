package com.vjgarcia.chucknorrisjokes

import android.app.Application
import com.vjgarcia.chucknorrisjokes.data.dataModule
import com.vjgarcia.chucknorrisjokes.domain.domainModule
import com.vjgarcia.chucknorrisjokes.presentation.presentationModule
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.io.IOException
import java.lang.IllegalStateException
import java.lang.NullPointerException
import java.net.SocketException

class ChuckNorrisApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChuckNorrisApp)
            modules(dataModule, domainModule, presentationModule)
        }
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                when (val causeE = e.cause) {
                    is IOException, is SocketException, is InterruptedException -> Unit
                    is NullPointerException, is IllegalAccessException, is IllegalStateException -> {
                        Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), causeE)
                    }
                }
            }
        }
    }
}