package br.ufs.uolchallenge.presentation

import br.ufs.uolchallenge.presentation.behaviors.emptystate.AssignEmptyState
import br.ufs.uolchallenge.presentation.behaviors.errorstate.AssignErrorState
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingCoordination
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorFeedback
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Created by bira on 11/3/17.
 */

class BehaviorsCoordinator<T>(
        val showEmptyState: AssignEmptyState<T>,
        val showErrorState: AssignErrorState<T>,
        val networkingFeedback: NetworkingErrorFeedback<T>,
        val loadingCoordination: LoadingCoordination<T>) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
                .compose(showEmptyState)
                .compose(showErrorState)
                .compose(networkingFeedback)
                .compose(loadingCoordination)
    }
}