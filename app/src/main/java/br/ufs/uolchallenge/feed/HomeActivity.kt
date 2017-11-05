package br.ufs.uolchallenge.feed

import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
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
import br.ufs.uolchallenge.presentation.feed.NewsFeedView
import br.ufs.uolchallenge.presentation.feed.NewsFeedViewModel
import br.ufs.uolchallenge.presentation.feed.NewsFeedViewModelFactory
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry
import br.ufs.uolchallenge.util.bindView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

class HomeActivity : AppCompatActivity(), NewsFeedView {

    private val uiScheduler = AndroidSchedulers.mainThread()
    private val screenRoot by bindView<View>(R.id.screenRoot)

    lateinit var composite: CompositeDisposable
    lateinit var viewModel: NewsFeedViewModel

    val feedview by bindView<RecyclerView>(R.id.feedView)
    val loading by bindView<ProgressBar>(R.id.loading)
    val feedbackContainer by bindView<View>(R.id.feedbackContainer)
    val errorImage by bindView<ImageView>(R.id.errorImage)
    val errorMessage by bindView<TextView>(R.id.errorMessage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setup()
    }

    override fun onResume() {
        super.onResume()
        fetchNews(false)
    }

    override fun onDestroy() {
        releaseSubscriptions()
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

    private fun fetchNews(forceUpdate: Boolean) {
        val items: MutableList<NewsFeedEntry> = mutableListOf()

        val disposable = viewModel.fetchLastestNews(forceUpdate)
                .observeOn(uiScheduler)
                .subscribe(
                        { items.add(it) },
                        { Log.d("Feed", "Error -> ${it.message}") },
                        { showNewsFeed(items) }
                )

        composite = CompositeDisposable()
        composite.add(disposable)
    }


    fun showNewsFeed(items: List<NewsFeedEntry>) {
        feedview.adapter = NewsFeedAdapter(items)
    }

    private fun setup() {
        retrieveViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val orientation = resources.configuration.orientation

        val displayMode = when (orientation) {
            ORIENTATION_PORTRAIT -> LinearLayoutManager.VERTICAL
            ORIENTATION_LANDSCAPE -> LinearLayoutManager.HORIZONTAL
            else -> LinearLayoutManager.VERTICAL
        }

        feedview.layoutManager = LinearLayoutManager(this, displayMode, false)
    }

    private fun retrieveViewModel() {
        val factory = NewsFeedViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(NewsFeedViewModel::class.java)
    }

    private fun showCallToAction(callToActionText: Int) {
        make(screenRoot, callToActionText, LENGTH_INDEFINITE)
                .setAction(R.string.snackaction_retry, {
                    releaseSubscriptions()
                    fetchNews(true)
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

    private fun releaseSubscriptions() {
        composite.clear()
    }

}
