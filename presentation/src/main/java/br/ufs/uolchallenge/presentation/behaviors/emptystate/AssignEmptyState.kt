package br.ufs.uolchallenge.presentation.behaviors.emptystate

import br.ufs.uolchallenge.domain.DataAccessError
import io.reactivex.*
import io.reactivex.functions.Action

/**
 * Created by bira on 11/3/17.
 */

class AssignEmptyState<T>(val view: EmptyStateView,
                          val uiScheduler: Scheduler) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {

        return upstream
                .subscribeOn(uiScheduler)
                .doOnSubscribe { _ -> subscribeAndFireAction(view.hideEmptyState()) }
                .doOnError(this::evalute)
    }

    private fun evalute(error: Throwable) {
        if (error is DataAccessError.ContentNotFound) {
            subscribeAndFireAction(view.showEmptyState())
        }
    }

    private fun subscribeAndFireAction(toPerform: Action) {
        Completable.fromAction(toPerform)
                .subscribeOn(uiScheduler)
                .subscribe()
    }

}