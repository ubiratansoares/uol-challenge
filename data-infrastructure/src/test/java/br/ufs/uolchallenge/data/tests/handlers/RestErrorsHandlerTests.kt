package br.ufs.uolchallenge.data.tests.handlers

import br.ufs.uolchallenge.data.handlers.RestErrorsHandler
import br.ufs.uolchallenge.domain.DataAccessError.*
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


/**
 * Created by bira on 11/3/17.
 */
class RestErrorsHandlerTests {

    lateinit var handler: RestErrorsHandler<String>

    val jsonMediaType = MediaType.parse("application/json")
    val badRequest = "{\"message\":\"You failed\"}"
    val notFoundMessage = "{\"message\":\"Not found\"}"
    val serverDownMessage = "{\"message\":\"Internal server error\"}"

    @Before fun `before each test`() {
        handler = RestErrorsHandler()
    }

    @Test fun `should handle not found error`() {
        val body = ResponseBody.create(jsonMediaType, notFoundMessage)
        val response: Response<String> = Response.error(404, body)
        val error = HttpException(response)
        val notFound: Observable<String> = Observable.error(error)

        notFound.compose(handler)
                .test()
                .assertError(ContentNotFound::class.java)
    }

    @Test fun `should handle internal server error`() {
        val body = ResponseBody.create(jsonMediaType, serverDownMessage)
        val response: Response<String> = Response.error(503, body)
        val error = HttpException(response)
        val internalServer: Observable<String> = Observable.error(error)

        internalServer.compose(handler)
                .test()
                .assertError(RemoteSystemDown::class.java)
    }

    @Test fun `should handle undesired returns`() {
        val body = ResponseBody.create(jsonMediaType, badRequest)
        val response: Response<String> = Response.error(400, body)
        val error = HttpException(response)
        val badRequest: Observable<String> = Observable.error(error)

        badRequest.compose(handler)
                .test()
                .assertError(UndesiredResponse::class.java)
    }

    @Test fun `should not handle any other errros`() {

        val npe = NullPointerException()
        val nonRestIssue = Observable.error<String>(npe)

        nonRestIssue.compose(handler)
                .test()
                .assertError({ throwable -> throwable == npe })
    }
}