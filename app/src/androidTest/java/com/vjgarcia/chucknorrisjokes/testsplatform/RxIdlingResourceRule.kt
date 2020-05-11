package com.vjgarcia.chucknorrisjokes.testsplatform

import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxIdlingResourceRule : TestRule {

    override fun apply(base: Statement, description: Description?): Statement = object : Statement() {
        override fun evaluate() {
            RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("RxJava2 Computation Scheduler"))
            RxJavaPlugins.setInitIoSchedulerHandler(Rx2Idler.create("RxJava2 IO Scheduler"))

            try {
                base.evaluate()
            } finally {
                RxJavaPlugins.reset()
            }
        }
    }
}