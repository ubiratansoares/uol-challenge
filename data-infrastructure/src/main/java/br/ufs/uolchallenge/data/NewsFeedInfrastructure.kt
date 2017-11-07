package br.ufs.uolchallenge.data

import br.ufs.uolchallenge.data.handlers.GsonErrorHandler
import br.ufs.uolchallenge.data.handlers.NetworkingErrorHandler
import br.ufs.uolchallenge.data.handlers.RestErrorsHandler
import br.ufs.uolchallenge.data.models.NewsFeedPayload
import br.ufs.uolchallenge.data.rest.UOLWebService
import br.ufs.uolchallenge.domain.FetchNewsFeed
import br.ufs.uolchallenge.domain.News
import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Created by bira on 11/3/17.
 */

class NewsFeedInfrastructure(private val webservice: UOLWebService,
                             private val ioBoundedScheduler: Scheduler) : FetchNewsFeed {

    private val restErrorHandler = RestErrorsHandler<NewsFeedPayload>()
    private val networkingErrorHandler = NetworkingErrorHandler<NewsFeedPayload>()
    private val gsonErrorHandler = GsonErrorHandler<NewsFeedPayload>()

    private val mapper = ContentNewsMapper()

    override fun latestNews(): Observable<News> {
        return webservice.latestNews()
                .subscribeOn(ioBoundedScheduler)
                .compose(restErrorHandler)
                .compose(networkingErrorHandler)
                .compose(gsonErrorHandler)
                .flatMap { payload -> Observable.fromIterable(payload.feed) }
                .map { content -> mapper.toNews(content) }
    }

}