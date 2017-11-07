package br.ufs.uolchallenge.presentation.feed

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.ufs.uolchallenge.data.NewsFeedInfrastructure
import br.ufs.uolchallenge.data.rest.WebServiceFactory
import br.ufs.uolchallenge.presentation.BuildConfig
import io.reactivex.schedulers.Schedulers

/**
 * Created by bira on 11/5/17.
 */

class NewsFeedViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        val ioScheduler = Schedulers.io()
        val webservice = WebServiceFactory.create(debuggable = BuildConfig.DEBUG)
        val infrastructure = NewsFeedInfrastructure(webservice, ioScheduler)
        return NewsFeedViewModel(infrastructure) as T
    }
}