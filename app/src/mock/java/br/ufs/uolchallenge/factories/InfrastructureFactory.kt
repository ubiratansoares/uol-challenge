package br.ufs.uolchallenge.factories

import br.ufs.uolchallenge.data.NewsContentInfrastructure
import br.ufs.uolchallenge.data.NewsFeedInfrastructure
import br.ufs.uolchallenge.domain.FetchContentForNews
import br.ufs.uolchallenge.domain.FetchNewsFeed
import br.ufs.uolchallenge.data.fakes.FakeWebService
import io.reactivex.schedulers.Schedulers

/**
 * Created by bira on 11/7/17.
 */

object InfrastructureFactory {

    private val ioScheduler = Schedulers.trampoline()

    fun newsFeed(): FetchNewsFeed {
        return NewsFeedInfrastructure(FakeWebService, ioScheduler)
    }

    fun newsContent(): FetchContentForNews {
        return NewsContentInfrastructure()
    }

}