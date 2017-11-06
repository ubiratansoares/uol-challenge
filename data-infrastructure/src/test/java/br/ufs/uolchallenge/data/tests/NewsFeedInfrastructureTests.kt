package br.ufs.uolchallenge.data.tests

import br.ufs.uolchallenge.data.NewsFeedInfrastructure
import br.ufs.uolchallenge.data.models.NewsFeedPayload
import br.ufs.uolchallenge.data.rest.UOLWebService
import br.ufs.uolchallenge.data.rest.WebServiceFactory
import br.ufs.uolchallenge.domain.CommunicationError
import br.ufs.uolchallenge.domain.DataAccessError
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


/**
 * Created by bira on 11/3/17.
 */

class NewsFeedInfrastructureTests {

    lateinit var infrastructure: NewsFeedInfrastructure
    lateinit var server: MockWebServer

    @Before fun `before each test`() {
        server = MockWebServer()
        val serverURL = server.url("/").toString()
        val webservice = WebServiceFactory.create(apiURL = serverURL)
        infrastructure = NewsFeedInfrastructure(webservice, Schedulers.trampoline())
    }

    @After fun `after each test`() {
        server.shutdown()
    }

    @Test fun `should integrate successfully with api, exposing returned data`() {

        val json = FileReader.readFile("response_success_200OK.json")

        server.enqueue(
                MockResponse()
                        .setResponseCode(200)
                        .setBody(json)
        )

        infrastructure.latestNews()
                .test()
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(100) // Check at payload file
    }

    @Test fun `should integrate successfully with api, handling broken payload`() {

        val json = FileReader.readFile("response_broken_200OK.json")

        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(json)

        server.enqueue(mockResponse)

        infrastructure.latestNews()
                .test()
                .assertError({ error -> error is DataAccessError.UndesiredResponse })
    }

    @Test fun `should integrate successfully with api, handling not found`() {

        val mockResponse = MockResponse().setResponseCode(404)
        server.enqueue(mockResponse)

        infrastructure.latestNews()
                .test()
                .assertError({ error -> error is DataAccessError.ContentNotFound })
    }

    @Test fun `should integrate successfully with api, handling server error`() {

        val mockResponse = MockResponse().setResponseCode(503)
        server.enqueue(mockResponse)

        infrastructure.latestNews()
                .test()
                .assertError({ error -> error is DataAccessError.RemoteSystemDown })
    }

    @Test fun `should integrate successfully with api, other rest error`() {

        val mockResponse = MockResponse().setResponseCode(400)
        server.enqueue(mockResponse)

        infrastructure.latestNews()
                .test()
                .assertError({ error -> error is DataAccessError.UndesiredResponse })
    }

    @Test fun `should integrate successfully with networking, handling timeout`() {

        val timeToExceed = WebServiceFactory.DEFAULT_TIMEOUT_SECONDS + 5
        val json = FileReader.readFile("response_broken_200OK.json")

        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(json)
                .setBodyDelay(timeToExceed, TimeUnit.SECONDS)

        server.enqueue(mockResponse)

        infrastructure.latestNews()
                .test()
                .assertError({ error -> error is CommunicationError.Slow })
    }

    @Test fun `should integrate successfully with networking, handling no internet`() {

        val signaler = UnknownHostException("No Internet")
        val noInternet: Observable<NewsFeedPayload> = Observable.error(signaler)

        val mockedWebService = mock<UOLWebService>{
            on(it.latestNews()) doReturn noInternet
        }

        infrastructure = NewsFeedInfrastructure(mockedWebService, Schedulers.trampoline())

        infrastructure.latestNews()
                .test()
                .assertError({ error -> error is CommunicationError.InternetUnavailable })
    }

    @Test fun `should integrate successfully with networking, handling connection issue`() {

        val signaler = IOException("Canceled")
        val connectionDied: Observable<NewsFeedPayload> = Observable.error(signaler)

        val mockedWebService = mock<UOLWebService>{
            on(it.latestNews()) doReturn connectionDied
        }

        infrastructure = NewsFeedInfrastructure(mockedWebService, Schedulers.trampoline())

        infrastructure.latestNews()
                .test()
                .assertError({ error -> error is CommunicationError.NetworkingHippcup })
    }
}