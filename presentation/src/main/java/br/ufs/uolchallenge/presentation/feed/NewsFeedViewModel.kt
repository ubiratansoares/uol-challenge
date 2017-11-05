package br.ufs.uolchallenge.presentation.feed

import br.ufs.uolchallenge.domain.FetchNewsFeed
import br.ufs.uolchallenge.domain.News
import br.ufs.uolchallenge.presentation.BehaviorsCoordinator
import br.ufs.uolchallenge.presentation.RowModelMapper
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry
import io.reactivex.Observable

/**
 * Created by bira on 11/3/17.
 */

class NewsFeedViewModel(
        val usecase: FetchNewsFeed,
        val coordinator: BehaviorsCoordinator<News>) {

    val mapper = RowModelMapper()

    fun fetchLastestNews(): Observable<NewsFeedEntry> {
        return usecase
                .latestNews()
                .compose(coordinator)
                .map { mapper.toRowModel(it) }
    }

}