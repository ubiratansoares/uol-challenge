package br.ufs.uolchallenge.presentation

import br.ufs.uolchallenge.presentation.behaviors.emptystate.AssignEmptyState
import br.ufs.uolchallenge.presentation.behaviors.emptystate.EmptyStateView
import br.ufs.uolchallenge.presentation.behaviors.errorstate.AssignErrorState
import br.ufs.uolchallenge.presentation.behaviors.errorstate.ErrorStateView
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingContentView
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingCoordination
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorFeedback
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Created by bira on 11/3/17.
 */


class BehaviorsCoordinator<T>(
        val behaviors: Set<ObservableTransformer<T, T>>,
        val passiveView: Any) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        behaviors.forEach { if (canByApplied(it, passiveView)) upstream.compose(it) }
        return upstream
    }

    private fun canByApplied(behavior: ObservableTransformer<T, T>, view: Any): Boolean {
        return (behavior is AssignEmptyState && view is EmptyStateView) ||
                (behavior is AssignErrorState && view is ErrorStateView) ||
                (behavior is NetworkingErrorFeedback && view is NetworkingErrorView) ||
                (behavior is LoadingCoordination && view is LoadingContentView)
    }
}