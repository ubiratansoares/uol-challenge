package br.ufs.uolchallenge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import br.ufs.uolchallenge.data.NewsInfrastructure
import br.ufs.uolchallenge.data.rest.WebServiceFactory
import br.ufs.uolchallenge.domain.News
import br.ufs.uolchallenge.presentation.BehaviorsCoordinator
import br.ufs.uolchallenge.presentation.behaviors.emptystate.AssignEmptyState
import br.ufs.uolchallenge.presentation.behaviors.errorstate.AssignErrorState
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingCoordination
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorFeedback
import br.ufs.uolchallenge.presentation.feed.NewsFeedView
import br.ufs.uolchallenge.presentation.feed.NewsFeedViewModel
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class HomeActivity : AppCompatActivity(), NewsFeedView {

    val uiScheduler = AndroidSchedulers.mainThread()
    val ioScheduler = Schedulers.io()

    val emptyState = AssignEmptyState<News>(this, uiScheduler)
    val errorState = AssignErrorState<News>(this, uiScheduler)
    val loadingContent = LoadingCoordination<News>(this, uiScheduler)
    val networkingFeedback = NetworkingErrorFeedback<News>(this, uiScheduler)
    val behaviors = listOf(loadingContent, emptyState, errorState, networkingFeedback)

    val webservice = WebServiceFactory.create()
    val infrastructure = NewsInfrastructure(webservice, ioScheduler)
    val coordinator = BehaviorsCoordinator(behaviors, this)
    val presenter = NewsFeedViewModel(infrastructure, coordinator)

    val composite = CompositeDisposable()

    val items: MutableList<NewsFeedEntry> = mutableListOf()

    val feedview by bindView<RecyclerView>(R.id.feedView)
    val loading by bindView<ProgressBar>(R.id.loading)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setup()
        fetchNews()
    }

    private fun fetchNews() {
        val disposable = presenter.fetchLastestNews()
                .observeOn(uiScheduler)
                .subscribe(
                        { items.add(it) },
                        { Log.d("Feed", "Error -> ${it.message}") },
                        { showNewsFeed() }
                )

        composite.add(disposable)
    }

    private fun setup() {
        feedview.layoutManager = LinearLayoutManager(this)
    }

    private fun showNewsFeed() {
        feedview.adapter = NewsFeedAdapter(items)
    }

    override fun onDestroy() {
        composite.clear()
        super.onDestroy()
    }

    override fun showLoading(): Action {
        return Action {
            Log.d("Feed", "Show loading")
            loading.visibility = View.VISIBLE
        }
    }

    override fun hideLoading(): Action {
        return Action {
            Log.d("Feed", "Hide loading")
            loading.visibility = View.GONE
        }
    }

    override fun showEmptyState(): Action {
        return Action { Log.d("Feed", "Show empty State") }
    }

    override fun showErrorState(): Action {
        return Action { Log.d("Feed", "Show error State") }
    }

    override fun reportNetworkingError(): Action {
        return Action { Log.d("Feed", "Networking Error") }
    }

    override fun hideEmptyState(): Action {
        return Action { Log.d("Feed", "Hide empty state") }
    }

    override fun hideErrorState(): Action {
        return Action { Log.d("Feed", "Hide error state") }
    }

}
