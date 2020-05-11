package com.vjgarcia.chucknorrisjokes.testsplatform

import android.app.Activity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.vjgarcia.chucknorrisjokes.ChuckNorrisApp
import com.vjgarcia.chucknorrisjokes.screens.JokesScreen
import com.vjgarcia.chucknorrisjokes.core.KoinConfiguration
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class UITest<T : Activity>(activityClass: Class<T>) : KoinTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(activityClass, false, false)

    @get:Rule
    val rxIdlingResourceRule = RxIdlingResourceRule()

    private val jokesScreen by inject<JokesScreen>()
    private val mockedApiServer by inject<MockedApiServer>()

    private val instrumentation by lazy { InstrumentationRegistry.getInstrumentation() }
    private val appOverrideModules by lazy {
        val application = instrumentation.targetContext.applicationContext as ChuckNorrisApp
        // We reload appModules in every test because otherwise singletons will be kept from one test execution to another
        application.appModules + mockedApiModule(mockedApiServer.url)
    }

    @Before
    fun setUp() {
        loadKoinModules(appOverrideModules)
    }

    @After
    fun tearDown() {
        unloadKoinModules(appOverrideModules)
    }

    fun givenThatCurrentScreenIsJokes(): JokesScreen {
        launchTarget()
        return jokesScreen
    }

    private fun launchTarget() {
        activityTestRule.launchActivity(null)
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun staticSetUp() {
            loadKoinModules(testModule)
            KoinConfiguration.overridable = true
        }

        @AfterClass
        @JvmStatic
        fun staticTearDown() {
            KoinConfiguration.overridable = false
        }
    }
}