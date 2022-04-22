package com.luistorm.melitest.presentation.testrules


import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxSchedulersRules : TestRule {

    override fun apply(base: Statement, description: Description) =
        object : Statement() {
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { SCHEDULER_INSTANCE }

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler { SCHEDULER_INSTANCE }
                RxJavaPlugins.setNewThreadSchedulerHandler { SCHEDULER_INSTANCE }
                RxJavaPlugins.setComputationSchedulerHandler { SCHEDULER_INSTANCE }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }

    companion object {
        private val SCHEDULER_INSTANCE = Schedulers.trampoline()
    }
}