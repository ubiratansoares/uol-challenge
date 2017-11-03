package br.ufs.uolchallenge.presentation.behaviors.errorstate

import io.reactivex.functions.Action

/**
 * Created by bira on 11/3/17.
 */

interface ErrorStateView {

    fun showErrorState(): Action

    fun hideErrorState(): Action

}