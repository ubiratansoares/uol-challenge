package br.ufs.uolchallenge.factories

import br.ufs.uolchallenge.data.BuildConfig
import br.ufs.uolchallenge.data.NewsContentInfrastructure
import br.ufs.uolchallenge.data.NewsFeedInfrastructure
import br.ufs.uolchallenge.data.rest.WebServiceFactory
import br.ufs.uolchallenge.domain.FetchContentForNews
import br.ufs.uolchallenge.domain.FetchNewsFeed
import io.reactivex.schedulers.Schedulers

/**
 * Created by bira on 11/7/17.
 */

object InfrastructureFactory {

    private val ioScheduler = Schedulers.io()
    private val webservice = WebServiceFactory.create(debuggable = BuildConfig.DEBUG)

    fun newsFeed(): FetchNewsFeed {
        return NewsFeedInfrastructure(webservice, ioScheduler)
    }

    fun newsContent(): FetchContentForNews {
        return NewsContentInfrastructure()
    }

}