package br.ufs.uolchallenge.feed

import android.os.Bundle
import android.support.design.widget.Snackbar.LENGTH_INDEFINITE
import android.support.design.widget.Snackbar.make
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import br.ufs.uolchallenge.R
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
import br.ufs.uolchallenge.util.bindView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class HomeActivity : AppCompatActivity(), NewsFeedView {

    val uiScheduler = AndroidSchedulers.mainThread()
    val ioScheduler = Schedulers.io()

    lateinit var presenter: NewsFeedViewModel
    val composite = CompositeDisposable()

    val screenRoot by bindView<View>(R.id.screenRoot)
    val feedview by bindView<RecyclerView>(R.id.feedView)
    val loading by bindView<ProgressBar>(R.id.loading)
    val feedbackContainer by bindView<View>(R.id.feedbackContainer)
    val errorImage by bindView<ImageView>(R.id.errorImage)
    val errorMessage by bindView<TextView>(R.id.errorMessage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val emptyState = AssignEmptyState<News>(this, uiScheduler)
        val errorState = AssignErrorState<News>(this, uiScheduler)
        val loadingContent = LoadingCoordination<News>(this, uiScheduler)
        val networkingFeedback = NetworkingErrorFeedback<News>(this, uiScheduler)
        val webservice = WebServiceFactory.create()
        val infrastructure = NewsInfrastructure(webservice, ioScheduler)
        val coordinator = BehaviorsCoordinator(
                showEmptyState = emptyState,
                showErrorState = errorState,
                networkingFeedback = networkingFeedback,
                loadingCoordination = loadingContent
        )

        presenter = NewsFeedViewModel(infrastructure, coordinator)

        setContentView(R.layout.activity_home)
        setup()
        fetchNews()
    }

    override fun onDestroy() {
        composite.clear()
        super.onDestroy()
    }

    override fun showLoading(): Action {
        return Action { loading.visibility = View.VISIBLE }
    }

    override fun hideLoading(): Action {
        return Action { loading.visibility = View.GONE }
    }

    override fun showEmptyState(): Action {
        return Action { feedback(R.drawable.img_uol_logo_bw, R.string.error_not_found) }
    }

    override fun showErrorState(): Action {
        return Action {
            feedback(R.drawable.img_server_off, R.string.error_server_down)
            showCallToAction(R.string.snacktext_server_down)
        }
    }

    override fun reportNetworkingError(): Action {
        return Action {
            feedback(R.drawable.img_network_off, R.string.error_internet_connection)
            showCallToAction(R.string.snacktext_internet_connection)
        }
    }

    override fun hideEmptyState(): Action {
        return resetErrorContainer()
    }

    override fun hideErrorState(): Action {
        return resetErrorContainer()
    }

    private fun feedback(errorImageResource: Int, errorMessageResource: Int) {
        feedbackContainer.visibility = View.VISIBLE
        errorImage.setImageResource(errorImageResource)
        errorMessage.text = getString(errorMessageResource)
    }

    private fun fetchNews() {
        val items: MutableList<NewsFeedEntry> = mutableListOf()

        val disposable = presenter.fetchLastestNews()
                .observeOn(uiScheduler)
                .subscribe(
                        { items.add(it) },
                        { Log.d("Feed", "Error -> ${it.message}") },
                        { showNewsFeed(items) }
                )

        composite.add(disposable)
    }

    private fun setup() {
        feedview.layoutManager = LinearLayoutManager(this)
    }

    private fun showNewsFeed(items: MutableList<NewsFeedEntry>) {
        feedview.adapter = NewsFeedAdapter(items)
    }

    private fun showCallToAction(callToActionText: Int) {
        make(screenRoot, callToActionText, LENGTH_INDEFINITE)
                .setAction(R.string.snackaction_retry, {
                    fetchNews()
                    resetErrorContainer()
                })
                .show()
    }

    private fun resetErrorContainer(): Action {
        return Action {
            errorMessage.text = ""
            errorImage.setImageResource(0)
            feedbackContainer.visibility = View.GONE
        }
    }
}
