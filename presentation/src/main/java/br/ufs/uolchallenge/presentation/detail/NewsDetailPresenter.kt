package br.ufs.uolchallenge.presentation.detail

import br.ufs.uolchallenge.domain.FetchContentForNews
import br.ufs.uolchallenge.presentation.BehaviorsCoordinator
import io.reactivex.Observable

/**
 * Created by bira on 11/5/17.
 */

class NewsDetailPresenter(
        private val usecase: FetchContentForNews,
        private val coordinator: BehaviorsCoordinator) {

    fun loadNews(): Observable<Any> {
        return usecase.execute().compose(coordinator)
    }

}