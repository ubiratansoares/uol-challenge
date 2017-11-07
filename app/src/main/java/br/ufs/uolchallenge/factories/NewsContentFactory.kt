package br.ufs.uolchallenge.factories

import br.ufs.uolchallenge.presentation.detail.NewsContentPresenter

/**
 * Created by bira on 11/7/17.
 */
object NewsContentFactory {

    operator fun invoke(passiveView: Any): NewsContentPresenter {
        val coordinator = BehaviorsFactory(passiveView)
        val infrastructure = InfrastructureFactory.newsContent()
        return NewsContentPresenter(infrastructure, coordinator)
    }

}