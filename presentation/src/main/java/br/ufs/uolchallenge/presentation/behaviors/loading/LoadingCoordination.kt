package br.ufs.uolchallenge.presentation.behaviors.loading

import io.reactivex.*
import io.reactivex.functions.Action


/**
 * Created by bira on 11/3/17.
 */

class LoadingCoordination<T>(val view: Any,
                             val uiScheduler: Scheduler) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {

        if (view is LoadingContentView) {
            return upstream
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe { _ -> subscribeAndFireAction(view.showLoading()) }
                    .doOnTerminate { subscribeAndFireAction(view.hideLoading()) }
        }

        return upstream
    }

    private fun subscribeAndFireAction(toPerform: Action) {
        Completable.fromAction(toPerform)
                .subscribeOn(uiScheduler)
                .subscribe()
    }

}