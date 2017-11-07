package br.ufs.uolchallenge.presentation.behaviors.fab

import io.reactivex.*
import io.reactivex.functions.Action


/**
 * Created by bira on 11/5/17.
 */
class FabToogle(
        private val view: Any,
        private val targetScheduler: Scheduler) : ObservableTransformer<Any, Any> {

    override fun apply(upstream: Observable<Any>): ObservableSource<Any> {

        if (view is FabActionableView) {
            return upstream
                    .doOnSubscribe { _ -> fireAction(view.disableFab()) }
                    .doOnComplete { fireAction(view.enableFab()) }
        }

        return upstream
    }

    private fun fireAction(toPerform: Action) {
        Completable.fromAction(toPerform)
                .subscribeOn(targetScheduler)
                .subscribe()
    }

}