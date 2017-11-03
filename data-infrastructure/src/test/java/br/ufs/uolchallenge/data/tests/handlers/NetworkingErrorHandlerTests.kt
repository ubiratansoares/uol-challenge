package br.ufs.uolchallenge.data.tests.handlers

import br.ufs.uolchallenge.data.handlers.NetworkingErrorHandler
import br.ufs.uolchallenge.domain.CommunicationError
import br.ufs.uolchallenge.domain.CommunicationError.*
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * Created by bira on 11/3/17.
 */

typealias DataFlow = Observable<String>

class NetworkingErrorHandlerTests {

    lateinit var handler: NetworkingErrorHandler<String>

    @Before fun `before each test`() {
        handler = NetworkingErrorHandler()
    }

    @Test fun `should handle internet unavailable`() {
        val noInternet: DataFlow = Observable.error(UnknownHostException("No Internet"))
        noInternet.compose(handler)
                .test()
                .assertError(InternetUnavailable::class.java)
    }

    @Test fun `should handle connection errors`() {
        val brokenConnection: DataFlow = Observable.error(IOException("Canceled"))
        brokenConnection.compose(handler)
                .test()
                .assertError(NetworkingHippcup::class.java)
    }

    @Test fun `should handle timeouts`() {
        val timeout: DataFlow = Observable.error(SocketTimeoutException())
        timeout.compose(handler)
                .test()
                .assertError(SlowConnection::class.java)
    }

    @Test fun `should not handle other errors`() {
        val otherError: DataFlow = Observable.error(JsonSyntaxException("Broken json"))
        otherError.compose(handler)
                .test()
                .assertError({ throwable -> throwable !is CommunicationError })
    }
}