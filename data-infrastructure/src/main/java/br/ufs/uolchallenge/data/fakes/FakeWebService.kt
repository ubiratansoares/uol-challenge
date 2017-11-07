package br.ufs.uolchallenge.data.fakes

import br.ufs.uolchallenge.data.fakes.WebResponseScenario.*
import br.ufs.uolchallenge.data.models.NewsFeedPayload
import br.ufs.uolchallenge.data.rest.UOLWebService
import io.reactivex.Observable

/**
 * Created by bira on 11/7/17.
 */

object FakeWebService : UOLWebService {

    private var next: WebResponseScenario = Success

    fun nextState(state : WebResponseScenario) {
        next = state
    }

    override fun latestNews(): Observable<NewsFeedPayload> {

        return when (next) {
            is ConnectionError -> FakeResponses.connectionIssue()
            is ConnectionTimeout -> FakeResponses.requestTimeout()
            is NoInternet -> FakeResponses.noInternet()
            is InternalServerError -> FakeResponses.internalServerError()
            is ClientError -> FakeResponses.clientError()
            is NotFound -> FakeResponses.notFound()
            is Success -> FakeResponses.newsFeed()
        }
    }

}

sealed class WebResponseScenario {
    object ConnectionError : WebResponseScenario()
    object ConnectionTimeout : WebResponseScenario()
    object NoInternet : WebResponseScenario()
    object InternalServerError : WebResponseScenario()
    object NotFound : WebResponseScenario()
    object ClientError : WebResponseScenario()
    object Success : WebResponseScenario()
}
