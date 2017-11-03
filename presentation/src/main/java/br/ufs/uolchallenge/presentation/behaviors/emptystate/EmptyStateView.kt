package br.ufs.uolchallenge.presentation.behaviors.emptystate

import io.reactivex.functions.Action

/**
 * Created by bira on 11/3/17.
 */
interface EmptyStateView {

    fun showEmptyState(): Action

    fun hideEmptyState(): Action

}