package com.vjgarcia.chucknorrisjokes

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class RxUnitTest {

    private val fakeSchedulerProvider = FakeSchedulerProvider()

    @BeforeEach
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.from(fakeSchedulerProvider.getUiExecutor()) }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.from(fakeSchedulerProvider.getUiExecutor()) }
        RxJavaPlugins.setInitComputationSchedulerHandler { Schedulers.from(fakeSchedulerProvider.getUiExecutor()) }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.from(fakeSchedulerProvider.getUiExecutor()) }
        RxJavaPlugins.setInitIoSchedulerHandler { Schedulers.from(fakeSchedulerProvider.getUiExecutor()) }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.from(fakeSchedulerProvider.getUiExecutor()) }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.from(fakeSchedulerProvider.getUiExecutor()) }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.from(fakeSchedulerProvider.getUiExecutor()) }
    }

    @AfterEach
    fun tearDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
}