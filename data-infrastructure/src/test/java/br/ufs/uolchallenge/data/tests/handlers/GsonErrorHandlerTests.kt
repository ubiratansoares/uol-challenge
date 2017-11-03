package br.ufs.uolchallenge.data.tests.handlers

import br.ufs.uolchallenge.data.handlers.GsonErrorHandler
import br.ufs.uolchallenge.domain.DataAccessError
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test


/**
 * Created by bira on 11/3/17.
 */
class GsonErrorHandlerTests {

    lateinit var gsonErrorHandler: GsonErrorHandler<Any>

    @Before fun `before each test`() {
        gsonErrorHandler = GsonErrorHandler()
    }

    @Test fun `should handle GSON IO error`() {
        val broken = Observable.error<Any>(JsonIOException("Cannot read file"))

        broken.compose(gsonErrorHandler)
                .test()
                .assertError({ error -> error is DataAccessError.UndesiredResponse })
    }

    @Test fun `should handle json sintax error`() {
        val broken = Observable.error<Any>(JsonSyntaxException("Json should not have comments"))

        broken.compose(gsonErrorHandler)
                .test()
                .assertError({ error -> error is DataAccessError.UndesiredResponse })

    }

    @Test fun `sould handle json parsing error`() {
        val broken = Observable.error<Any>(JsonParseException("Failed to parse object"))

        broken.compose(gsonErrorHandler)
                .test()
                .assertError({ error -> error is DataAccessError.UndesiredResponse })
    }

    @Test fun `should not handle other errors`() {
        val fuck = IllegalAccessError("FUCK")
        val broken = Observable.error<String>(fuck)

        broken.compose(gsonErrorHandler)
                .test()
                .assertError { throwable -> throwable == fuck }
    }
}