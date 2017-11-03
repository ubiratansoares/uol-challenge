package br.ufs.uolchallenge.presentation.behaviors.networking

import io.reactivex.functions.Action

/**
 * Created by bira on 11/3/17.
 */

interface NetworkingErrorView {

    fun reportNetworkingError(): Action

}