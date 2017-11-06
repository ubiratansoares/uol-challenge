package br.ufs.uolchallenge.presentation.behaviors.emptystate

import br.ufs.uolchallenge.domain.DataAccessError
import io.reactivex.*
import io.reactivex.functions.Action

/**
 * Created by bira on 11/3/17.
 */

class AssignEmptyState<T>(val view: Any,
                          val uiScheduler: Scheduler) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {

        if (view is EmptyStateView) {
            return upstream
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe { _ -> subscribeAndFire(view.hideEmptyState()) }
                    .doOnError(this::evalute)
        }

        return upstream
    }

    private fun evalute(error: Throwable) {
        if (error is DataAccessError.ContentNotFound) {
            subscribeAndFire((view as EmptyStateView).showEmptyState())
        }
    }

    private fun subscribeAndFire(toPerform: Action) {
        Completable.fromAction(toPerform)
                .subscribeOn(uiScheduler)
                .subscribe()
    }

}