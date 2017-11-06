package br.ufs.uolchallenge.data

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import br.ufs.uolchallenge.domain.CommunicationError
import br.ufs.uolchallenge.domain.DataAccessError
import br.ufs.uolchallenge.domain.FetchContentForNews
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by bira on 11/5/17.
 */

class NewsContentInfrastructure : WebViewClient(), FetchContentForNews {

    private lateinit var source: PublishSubject<Any>

    override fun execute(): Observable<Any> {
        source = PublishSubject.create<Any>()
        return source
    }

    // For API 23 and above ...
    override fun onReceivedHttpError(
            view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {
        super.onReceivedHttpError(view, request, errorResponse)

        val error: Throwable = when (errorResponse.statusCode) {
            404 -> DataAccessError.ContentNotFound()
            in 500..511 -> DataAccessError.RemoteSystemDown()
            else -> DataAccessError.UndesiredResponse()
        }

        source.onError(error)
    }

    // For API 22 and below ...
    override fun onReceivedError(
            view: WebView?,
            webViewErrorCode: Int,
            description: String?,
            failingUrl: String?) {
        super.onReceivedError(view, webViewErrorCode, description, failingUrl)

        val error: Throwable = when (webViewErrorCode) {
            ERROR_IO -> CommunicationError.NetworkingHippcup()
            ERROR_HOST_LOOKUP -> CommunicationError.InternetUnavailable()
            ERROR_TIMEOUT -> CommunicationError.Slow()
            ERROR_FILE_NOT_FOUND -> DataAccessError.ContentNotFound()
            else -> DataAccessError.UndesiredResponse()
        }

        source.onError(error)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        source.onNext("Loaded -> $url")
        source.onComplete()
    }
}