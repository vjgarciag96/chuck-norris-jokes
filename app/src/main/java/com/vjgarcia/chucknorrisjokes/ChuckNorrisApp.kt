package com.vjgarcia.chucknorrisjokes

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.vjgarcia.chucknorrisjokes.core.coreModule
import com.vjgarcia.chucknorrisjokes.data.dataModule
import com.vjgarcia.chucknorrisjokes.domain.domainModule
import com.vjgarcia.chucknorrisjokes.presentation.presentationModule
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.io.IOException
import java.net.SocketException

class ChuckNorrisApp : Application() {

    @VisibleForTesting
    val appModules = listOf(coreModule, dataModule, domainModule, presentationModule)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChuckNorrisApp)
            modules(appModules)
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