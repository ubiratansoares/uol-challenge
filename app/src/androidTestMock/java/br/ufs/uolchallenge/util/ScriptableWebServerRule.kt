package br.ufs.uolchallenge.util

import android.app.Activity
import android.content.Intent
import android.support.test.rule.ActivityTestRule
import br.ufs.uolchallenge.data.fakes.FakeWebService
import br.ufs.uolchallenge.data.fakes.WebResponseScenario
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by bira on 11/7/17.
 */

class ScriptableWebServerRule<T : Activity>(klass: Class<T>?)
    : ActivityTestRule<T>(klass, false, false) {

    private val mockWebService by lazy { FakeWebService }
    private var next: WebResponseScenario = WebResponseScenario.Success

    init {
        SystemAnimationsDisabler().disableAll()
    }

    override fun beforeActivityLaunched() {
        RxJavaPlugins.setIoSchedulerHandler { AndroidSchedulers.mainThread() }
        mockWebService.nextState(next)
        super.beforeActivityLaunched()
    }

    fun startWithCenario(scenario: WebResponseScenario) {
        this.next = scenario
        launchActivity(Intent())
    }

}