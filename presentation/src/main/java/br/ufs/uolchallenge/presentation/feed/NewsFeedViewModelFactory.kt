package br.ufs.uolchallenge.presentation.feed

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.ufs.uolchallenge.data.NewsInfrastructure
import br.ufs.uolchallenge.data.rest.WebServiceFactory
import br.ufs.uolchallenge.domain.News
import br.ufs.uolchallenge.presentation.BehaviorsCoordinator
import br.ufs.uolchallenge.presentation.behaviors.emptystate.AssignEmptyState
import br.ufs.uolchallenge.presentation.behaviors.errorstate.AssignErrorState
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingCoordination
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorFeedback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by bira on 11/5/17.
 */
class NewsFeedViewModelFactory(val passiveView: NewsFeedView) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {

        val uiScheduler = AndroidSchedulers.mainThread()
        val ioScheduler = Schedulers.io()

        val emptyState = AssignEmptyState<News>(passiveView, uiScheduler)
        val errorState = AssignErrorState<News>(passiveView, uiScheduler)
        val loadingContent = LoadingCoordination<News>(passiveView, uiScheduler)
        val networkingFeedback = NetworkingErrorFeedback<News>(passiveView, uiScheduler)
        val webservice = WebServiceFactory.create(debuggable = true)
        val infrastructure = NewsInfrastructure(webservice, ioScheduler)

        val coordinator = BehaviorsCoordinator(
                showEmptyState = emptyState,
                showErrorState = errorState,
                networkingFeedback = networkingFeedback,
                loadingCoordination = loadingContent
        )

        return NewsFeedViewModel(infrastructure, coordinator) as T
    }
}