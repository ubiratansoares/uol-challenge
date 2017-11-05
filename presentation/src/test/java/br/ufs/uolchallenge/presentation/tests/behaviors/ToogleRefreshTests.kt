package br.ufs.uolchallenge.presentation.tests.behaviors

import br.ufs.uolchallenge.domain.DataAccessError
import br.ufs.uolchallenge.presentation.behaviors.refresh.RefreshToogle
import br.ufs.uolchallenge.presentation.behaviors.refresh.RefreshableView
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by bira on 11/5/17.
 */

class ToogleRefreshTests {

    val uiScheduler = Schedulers.trampoline()
    lateinit var refreshToogler: RefreshToogle<Any>
    lateinit var enable: Action
    lateinit var disable: Action

    @Before fun `before each test`() {
        enable = mock()
        disable = mock()

        val view = object : RefreshableView {
            override fun disableRefresh(): Action {
                return disable
            }
            override fun enableRefresh(): Action {
                return enable
            }

        }

        refreshToogler = RefreshToogle(view, uiScheduler)
    }

    @Test fun `should coordinate loading when flow emmits`() {
        Observable.just("A", "B", "C")
                .compose(refreshToogler)
                .subscribe()

        `check refresh coordinated`()
    }

    @Test fun `should coordinate loading when flow is empty`() {
        Observable.empty<Any>()
                .compose(refreshToogler)
                .subscribe()

        `check refresh coordinated`()
    }

    @Test fun `should not offer refresh when flow signal error`() {
        Observable.error<Any>(DataAccessError.RemoteSystemDown())
                .compose(refreshToogler)
                .subscribe({}, {}, {})

        verify(disable).run()
        verify(enable, never()).run()
    }

    private fun `check refresh coordinated`() {
        val inOrder = Mockito.inOrder(disable, enable)
        inOrder.verify(disable).run()
        inOrder.verify(enable).run()
    }

}