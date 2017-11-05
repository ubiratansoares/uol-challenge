package br.ufs.uolchallenge.presentation.behaviors.refresh

import io.reactivex.functions.Action

/**
 * Created by bira on 11/5/17.
 */

interface RefreshableView {

    fun disableRefresh(): Action

    fun enableRefresh(): Action

}