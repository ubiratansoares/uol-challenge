package br.ufs.uolchallenge.presentation.behaviors.fab

import io.reactivex.functions.Action

/**
 * Created by bira on 11/5/17.
 */

interface FabActionableView {

    fun disableFab(): Action

    fun enableFab(): Action

}