package br.ufs.uolchallenge.factories

import br.ufs.uolchallenge.presentation.behaviors.BehaviorsCoordinator
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by bira on 11/7/17.
 */

object BehaviorsFactory {

    val uiScheduler by lazy { AndroidSchedulers.mainThread() }

    operator fun invoke(passiveView: Any): BehaviorsCoordinator {
        return BehaviorsCoordinator(passiveView, uiScheduler)
    }
}