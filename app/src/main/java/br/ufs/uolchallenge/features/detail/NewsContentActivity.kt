package br.ufs.uolchallenge.features.detail

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import br.ufs.uolchallenge.R
import br.ufs.uolchallenge.factories.BehaviorsFactory
import br.ufs.uolchallenge.factories.NewsContentFactory
import br.ufs.uolchallenge.presentation.detail.NewsContentView
import br.ufs.uolchallenge.util.Navigator
import br.ufs.uolchallenge.util.bindView
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action

class NewsContentActivity : AppCompatActivity(), NewsContentView {

    private val shareURL by lazy { intent.extras.getString(SHARE_URL) }
    private val viewURL by lazy { intent.extras.getString(VISUALIZATION_URL) }

    val screenRoot by bindView<View>(R.id.screenRoot)
    val webView by bindView<WebView>(R.id.webview)
    val loading by bindView<ProgressBar>(R.id.loading)
    val feedbackContainer by bindView<View>(R.id.feedbackContainer)
    val errorImage by bindView<ImageView>(R.id.errorImage)
    val errorMessage by bindView<TextView>(R.id.errorMessage)
    val fab by bindView<FloatingActionButton>(R.id.shareButton)

    val coordinator by lazy {BehaviorsFactory(this)}
    val presenter by lazy { NewsContentFactory() }

    lateinit var subscription: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_news)
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        loadNews()
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
        return Action { feedback(R.drawable.img_uol_logo_bw, R.string.error_not_found_detail) }
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

    override fun disableFab(): Action {
        return Action { fab.visibility = View.GONE }
    }

    override fun enableFab(): Action {
        return Action { fab.visibility = View.VISIBLE }
    }

    private fun feedback(errorImageResource: Int, errorMessageResource: Int) {
        feedbackContainer.visibility = View.VISIBLE
        errorImage.setImageResource(errorImageResource)
        errorMessage.text = getString(errorMessageResource)
    }

    private fun showCallToAction(callToActionText: Int) {
        Snackbar.make(screenRoot, callToActionText, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackaction_retry, { retry() })
                .show()
    }

    private fun retry() {
        releaseSubscriptions()
        loadNews()
    }

    private fun resetErrorContainer(): Action {
        return Action {
            errorMessage.text = ""
            errorImage.setImageResource(0)
            feedbackContainer.visibility = View.GONE
        }
    }

    private fun setupViews() {
        fab.setOnClickListener { share() }
        webView.settings.javaScriptEnabled = false
        webView.webViewClient = presenter.webViewClient()
    }

    private fun loadNews() {
        Log.v(TAG, viewURL)

        subscription =
                presenter.loadNews()
                        .compose(coordinator)
                        .subscribe(
                                { Log.v(TAG, it.toString()) },
                                {
                                    Log.e(TAG, it.toString())
                                    webView.visibility = View.INVISIBLE
                                },
                                {
                                    webView.visibility = View.VISIBLE
                                    Log.v(TAG, "DONE")
                                }
                        )

        webView.loadUrl(viewURL)

    }

    private fun releaseSubscriptions() {
        subscription.dispose()
    }

    private fun share() {
        Navigator.shareURL(this, shareURL, "Veja no UOL")
    }

    companion object {
        val VISUALIZATION_URL = "visualization.url"
        val SHARE_URL = "share.url"
        val TAG = "NewsContent"
    }
}
