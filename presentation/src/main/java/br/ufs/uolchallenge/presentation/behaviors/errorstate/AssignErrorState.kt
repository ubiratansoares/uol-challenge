package br.ufs.uolchallenge.presentation.behaviors.errorstate

import br.ufs.uolchallenge.domain.DataAccessError.RemoteSystemDown
import br.ufs.uolchallenge.domain.DataAccessError.UndesiredResponse
import io.reactivex.*
import io.reactivex.functions.Action

/**
 * Created by bira on 11/3/17.
 */
class AssignErrorState<T>(val view: Any,
                          val uiScheduler: Scheduler) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {

        if (view is ErrorStateView) {
            return upstream
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe { _ -> subscribeAndFireAction(view.hideErrorState()) }
                    .doOnError(this::evalute)

        }
        return upstream
    }

    private fun evalute(error: Throwable) {
        if (error is RemoteSystemDown || error is UndesiredResponse) {
            subscribeAndFireAction((view as ErrorStateView).showErrorState())
        }
    }

    private fun subscribeAndFireAction(toPerform: Action) {
        Completable.fromAction(toPerform)
                .subscribeOn(uiScheduler)
                .subscribe()
    }
}