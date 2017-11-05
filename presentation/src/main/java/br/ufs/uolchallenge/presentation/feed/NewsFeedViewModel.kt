package br.ufs.uolchallenge.presentation.feed

import android.arch.lifecycle.ViewModel
import br.ufs.uolchallenge.domain.FetchNewsFeed
import br.ufs.uolchallenge.domain.News
import br.ufs.uolchallenge.presentation.BehaviorsCoordinator
import br.ufs.uolchallenge.presentation.RowModelMapper
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry
import io.reactivex.Observable

/**
 * Created by bira on 11/3/17.
 */

open class NewsFeedViewModel internal constructor(
        private val usecase: FetchNewsFeed,
        private val coordinator: BehaviorsCoordinator<News>) : ViewModel() {

    private val mapper = RowModelMapper()
    var replayer: Observable<NewsFeedEntry>? = null

    fun fetchLastestNews(): Observable<NewsFeedEntry> {

        if (replayer == null) {
            replayer = usecase.latestNews()
                    .compose(coordinator)
                    .map { mapper.toRowModel(it) }
                    .replay(BUFFER_COUNT)
                    .autoConnect()
        }

        return replayer as Observable<NewsFeedEntry>
    }

    companion object {
        val BUFFER_COUNT = 100
    }

}