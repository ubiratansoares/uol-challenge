package br.ufs.uolchallenge.presentation.behaviors.refresh

import io.reactivex.*
import io.reactivex.functions.Action


/**
 * Created by bira on 11/5/17.
 */
class RefreshToogle<T>(
        private val view: RefreshableView,
        private val targetScheduler: Scheduler) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
                .doOnSubscribe { _ -> fireAction(view.disableRefresh()) }
                .doOnComplete { fireAction(view.enableRefresh()) }
    }

    private fun fireAction(toPerform: Action) {
        Completable.fromAction(toPerform)
                .subscribeOn(targetScheduler)
                .subscribe()
    }

}