package com.vjgarcia.chucknorrisjokes

import java.util.concurrent.Executor

class FakeSchedulerProvider {

    private val fakeExecutor = Executor { runnable -> runnable.run() }

    fun getUiExecutor(): Executor = fakeExecutor
}