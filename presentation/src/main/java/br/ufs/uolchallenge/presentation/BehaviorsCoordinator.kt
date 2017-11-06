package br.ufs.uolchallenge.presentation

import br.ufs.uolchallenge.presentation.behaviors.emptystate.AssignEmptyState
import br.ufs.uolchallenge.presentation.behaviors.errorstate.AssignErrorState
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingCoordination
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorFeedback
import br.ufs.uolchallenge.presentation.behaviors.fab.FabToogle
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * Created by bira on 11/3/17.
 */

class BehaviorsCoordinator<T> internal constructor(
        val showEmptyState: AssignEmptyState<T>,
        val showErrorState: AssignErrorState<T>,
        val networkingFeedback: NetworkingErrorFeedback<T>,
        val loadingCoordination: LoadingCoordination<T>,
        val toogleFab: FabToogle<T>) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
                .compose(showEmptyState)
                .compose(showErrorState)
                .compose(networkingFeedback)
                .compose(loadingCoordination)
                .compose(toogleFab)
    }

    companion object Factory {

        fun <T> createWith(view: Any, uiScheduler: Scheduler): BehaviorsCoordinator<T> {

            val emptyState = AssignEmptyState<T>(view, uiScheduler)
            val errorState = AssignErrorState<T>(view, uiScheduler)
            val loader = LoadingCoordination<T>(view, uiScheduler)
            val networkingFeedback = NetworkingErrorFeedback<T>(view, uiScheduler)
            val refresher = FabToogle<T>(view, uiScheduler)

            return BehaviorsCoordinator(
                    showEmptyState = emptyState,
                    showErrorState = errorState,
                    networkingFeedback = networkingFeedback,
                    loadingCoordination = loader,
                    toogleFab = refresher
            )
        }
    }
}