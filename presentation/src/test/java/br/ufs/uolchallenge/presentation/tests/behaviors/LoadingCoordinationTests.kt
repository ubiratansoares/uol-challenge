package br.ufs.uolchallenge.presentation.tests.behaviors

import br.ufs.uolchallenge.domain.DataAccessError
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingContentView
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingCoordination
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito




/**
 * Created by bira on 11/3/17.
 */

class LoadingCoordinationTests {

    val uiScheduler = Schedulers.trampoline()
    lateinit var loadingCoordination: LoadingCoordination<Any>
    lateinit var showAction: Action
    lateinit var hideAction: Action

    @Before fun `before each test`() {
        showAction = mock()
        hideAction = mock()

        val view = object : LoadingContentView {
            override fun showLoading(): Action {
                return showAction
            }

            override fun hideLoading(): Action {
                return hideAction
            }
        }

        loadingCoordination = LoadingCoordination(view, uiScheduler)
    }

    @Test fun `should coordinate loading when flow emmits`() {
        Observable.just("A", "B", "C")
                .compose(loadingCoordination)
                .subscribe()

        checkLoadingCoordinated()
    }

    @Test fun `should coordinate loading when flow is empty`() {
        Observable.empty<Any>()
                .compose(loadingCoordination)
                .subscribe()

        checkLoadingCoordinated()
    }

    @Test fun `should coordinate loading when flow signal error`() {
        Observable.error<Any>(DataAccessError.RemoteSystemDown())
                .compose(loadingCoordination)
                .subscribe({}, {}, {})

        checkLoadingCoordinated()
    }

    private fun checkLoadingCoordinated() {
        val inOrder = Mockito.inOrder(showAction, hideAction)
        inOrder.verify(showAction).run()
        inOrder.verify(hideAction).run()
    }

}