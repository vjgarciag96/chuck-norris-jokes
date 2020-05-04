package com.vjgarcia.chucknorrisjokes

import android.app.Application
import com.facebook.soloader.SoLoader
import com.vjgarcia.chucknorrisjokes.data.dataModule
import com.vjgarcia.chucknorrisjokes.domain.domainModule
import com.vjgarcia.chucknorrisjokes.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChuckNorrisApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
        startKoin {
            androidContext(this@ChuckNorrisApp)
            modules(dataModule, domainModule, presentationModule)
        }
    }
}