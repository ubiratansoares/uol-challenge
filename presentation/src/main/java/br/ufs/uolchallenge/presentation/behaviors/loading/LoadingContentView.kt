package br.ufs.uolchallenge.presentation.behaviors.loading

import io.reactivex.functions.Action

/**
 * Created by bira on 11/3/17.
 */

interface LoadingContentView {

    fun showLoading(): Action

    fun hideLoading(): Action

}