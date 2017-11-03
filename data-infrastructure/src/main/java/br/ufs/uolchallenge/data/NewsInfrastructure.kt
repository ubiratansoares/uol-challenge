package br.ufs.uolchallenge.data

import br.ufs.uolchallenge.domain.FetchNewsFeed
import br.ufs.uolchallenge.domain.News
import io.reactivex.Observable

/**
 * Created by bira on 11/3/17.
 */
class NewsInfrastructure : FetchNewsFeed {

    override fun latestNews(): Observable<News> {
        return Observable.empty()
    }

}