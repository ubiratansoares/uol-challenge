package br.ufs.uolchallenge.presentation.feed

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.ufs.uolchallenge.data.NewsInfrastructure
import br.ufs.uolchallenge.data.rest.WebServiceFactory
import br.ufs.uolchallenge.domain.News
import br.ufs.uolchallenge.presentation.BehaviorsCoordinator
import br.ufs.uolchallenge.presentation.BuildConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by bira on 11/5/17.
 */

class NewsFeedViewModelFactory(
        private val passiveView: NewsFeedView) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {

        val uiScheduler = AndroidSchedulers.mainThread()
        val ioScheduler = Schedulers.io()
        val webservice = WebServiceFactory.create(debuggable = BuildConfig.DEBUG)
        val infrastructure = NewsInfrastructure(webservice, ioScheduler)
        val coordinator = BehaviorsCoordinator.createWith<News>(passiveView, uiScheduler)

        return NewsFeedViewModel(infrastructure, coordinator) as T
    }
}