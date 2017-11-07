package br.ufs.uolchallenge.presentation

import br.ufs.uolchallenge.presentation.behaviors.emptystate.AssignEmptyState
import br.ufs.uolchallenge.presentation.behaviors.errorstate.AssignErrorState
import br.ufs.uolchallenge.presentation.behaviors.fab.FabToogle
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingCoordination
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorFeedback
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * Created by bira on 11/3/17.
 */

class BehaviorsCoordinator internal constructor(
        val showEmptyState: AssignEmptyState,
        val showErrorState: AssignErrorState,
        val networkingFeedback: NetworkingErrorFeedback,
        val loadingCoordination: LoadingCoordination,
        val toogleFab: FabToogle
) : ObservableTransformer<Any, Any> {

    override fun apply(upstream: Observable<Any>): ObservableSource<Any> {
        return upstream
                .compose(showEmptyState)
                .compose(showErrorState)
                .compose(networkingFeedback)
                .compose(loadingCoordination)
                .compose(toogleFab)
    }

    companion object Factory {
        operator fun invoke(view: Any, uiScheduler: Scheduler): BehaviorsCoordinator {
            val emptyState = AssignEmptyState(view, uiScheduler)
            val errorState = AssignErrorState(view, uiScheduler)
            val networkingFeedback = NetworkingErrorFeedback(view, uiScheduler)
            val loader = LoadingCoordination(view, uiScheduler)
            val refresher = FabToogle(view, uiScheduler)

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