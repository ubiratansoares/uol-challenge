package br.ufs.uolchallenge.presentation.tests.behaviors

import br.ufs.uolchallenge.domain.CommunicationError
import br.ufs.uolchallenge.domain.DataAccessError
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorFeedback
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorView
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.never


/**
 * Created by bira on 11/3/17.
 */

class NetworkingErrorFeedbackTests {

    val uiScheduler = Schedulers.trampoline()

    lateinit var networkingFeedback: NetworkingErrorFeedback<Any>
    lateinit var report: Action

    @Before fun `before each test`() {
        report = mock()

        val view = object : NetworkingErrorView {
            override fun reportNetworkingError(): Action {
                return report
            }
        }

        networkingFeedback = NetworkingErrorFeedback(view, uiScheduler)
    }

    @Test fun `should not report networking error when flow emmits`() {
        Observable.just("A", "B")
                .compose(networkingFeedback)
                .subscribe()

        verify(report, never()).run()
    }

    @Test fun `should not report networking error when flow is empty`() {
        Observable.empty<Any>()
                .compose(networkingFeedback)
                .subscribe()

        verify(report, never()).run()
    }

    @Test fun `should not report networking error when upstream reports other errors`() {
        val otherError = DataAccessError.RemoteSystemDown()

        Observable.error<Any>(otherError)
                .compose(networkingFeedback)
                .subscribe()

        verify(report, never()).run()
    }

    @Test fun `should report networking error when upstream reports`() {
        val networkingError = CommunicationError.NetworkingHippcup()

        Observable.error<Any>(networkingError)
                .compose(networkingFeedback)
                .subscribe()

        verify(report, Mockito.atMost(1)).run()
    }
}