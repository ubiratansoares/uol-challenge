package br.ufs.uolchallenge.data.fakes

import br.ufs.uolchallenge.data.fakes.NextState.*
import br.ufs.uolchallenge.data.models.NewsFeedPayload
import br.ufs.uolchallenge.data.rest.UOLWebService
import io.reactivex.Observable

/**
 * Created by bira on 11/7/17.
 */

object FakeWebService : UOLWebService {

    private var next: NextState = Success

    override fun latestNews(): Observable<NewsFeedPayload> {

        return when (next) {
            is ConnectionError -> FakeResponses.connectionIssue()
            is ConnectionTimeout -> FakeResponses.requestTimeout()
            is InternalServer -> FakeResponses.internalServerError()
            is ClientError -> FakeResponses.clientError()
            is NotFound -> FakeResponses.notFound()
            is Success -> FakeResponses.newsFeed()
        }
    }

}

sealed class NextState {
    object ConnectionError : NextState()
    object ConnectionTimeout : NextState()
    object InternalServer : NextState()
    object NotFound : NextState()
    object ClientError : NextState()
    object Success : NextState()
}
