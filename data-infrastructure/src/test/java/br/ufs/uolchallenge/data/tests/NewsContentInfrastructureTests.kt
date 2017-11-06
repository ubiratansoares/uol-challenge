package br.ufs.uolchallenge.data.tests

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import br.ufs.uolchallenge.data.NewsContentInfrastructure
import br.ufs.uolchallenge.domain.CommunicationError
import br.ufs.uolchallenge.domain.DataAccessError
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.io.ByteArrayInputStream
import java.nio.charset.Charset

/**
 * Created by bira on 11/6/17.
 */

@RunWith(RobolectricTestRunner::class)
class NewsContentInfrastructureTests {

    lateinit var infrastructure: NewsContentInfrastructure
    lateinit var webView: WebView
    lateinit var originalRequest: WebResourceRequest

    @Before fun `before each test`() {
        infrastructure = NewsContentInfrastructure()
        webView = WebView(RuntimeEnvironment.application)
        originalRequest = mock()
    }

    @Test fun `should intercept and translate not found error`() {

        val notFound = mockResponse(404)

        infrastructure.execute()
                .doOnSubscribe { `perform webview http event`(notFound) }
                .test()
                .assertError { error -> error is DataAccessError.ContentNotFound }
    }

    @Test fun `should intercept and translate internal server error`() {

        val internalServer = mockResponse(503)

        infrastructure.execute()
                .doOnSubscribe { `perform webview http event`(internalServer) }
                .test()
                .assertError { error -> error is DataAccessError.RemoteSystemDown }
    }

    @Test fun `should intercept and translate undesired response error`() {

        val badRequest = mockResponse(400)

        infrastructure.execute()
                .doOnSubscribe { `perform webview http event`(badRequest) }
                .test()
                .assertError { error -> error is DataAccessError.UndesiredResponse }
    }

    @Test fun `should intercept and translate networking hippcup`() {

        val hippcup = WebViewClient.ERROR_IO

        infrastructure.execute()
                .doOnSubscribe { `perform webview error event`(hippcup) }
                .test()
                .assertError { error -> error is CommunicationError.NetworkingHippcup }
    }

    @Test fun `should intercept and translate networking timeout`() {

        val timeout = WebViewClient.ERROR_TIMEOUT

        infrastructure.execute()
                .doOnSubscribe { `perform webview error event`(timeout) }
                .test()
                .assertError { error -> error is CommunicationError.Slow }
    }

    @Test fun `should intercept and translate host lookup failed`() {

        val userProbablyOffline = WebViewClient.ERROR_HOST_LOOKUP

        infrastructure.execute()
                .doOnSubscribe { `perform webview error event`(userProbablyOffline) }
                .test()
                .assertError { error -> error is CommunicationError.InternetUnavailable }
    }
    
    @Test fun `should intercept and translate other networking error`() {

        val otherNetworkError = WebViewClient.ERROR_FAILED_SSL_HANDSHAKE

        infrastructure.execute()
                .doOnSubscribe { `perform webview error event`(otherNetworkError) }
                .test()
                .assertError { error -> error is DataAccessError.UndesiredResponse }
    }

    private fun `perform webview http event`(response: WebResourceResponse) {
        infrastructure.onReceivedHttpError(webView, originalRequest, response)
    }

    private fun `perform webview error event`(errorCode: Int) {
        infrastructure.onReceivedError(
                webView,
                errorCode,
                "Some reason",
                "http://broken.com"
        )
    }

    private fun mockResponse(statusCode: Int): WebResourceResponse {

        val reason = when (statusCode) {
            404 -> "Not found"
            in 500..511 -> "Internal Server Error"
            else -> "Bad Request"
        }

        return WebResourceResponse(
                "application/text",
                "UTF-8",
                statusCode,
                reason,
                emptyMap<String, String>(),
                ByteArrayInputStream(reason.toByteArray(Charset.forName("UTF-8")))
        )
    }

}