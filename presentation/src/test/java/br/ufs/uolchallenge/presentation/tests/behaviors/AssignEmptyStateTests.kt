package br.ufs.uolchallenge.presentation.tests.behaviors

import br.ufs.uolchallenge.domain.DataAccessError
import br.ufs.uolchallenge.presentation.behaviors.emptystate.AssignEmptyState
import br.ufs.uolchallenge.presentation.behaviors.emptystate.EmptyStateView
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

/**
 * Created by bira on 11/3/17.
 */

class AssignEmptyStateTests {

    val uiScheduler = Schedulers.trampoline()
    lateinit var emptyStateAssigner: AssignEmptyState<Any>

    lateinit var showAction: Action
    lateinit var hideAction: Action

    @Before fun `before each test`() {
        showAction = mock()
        hideAction = mock()

        val view = object : EmptyStateView {
            override fun showEmptyState(): Action {
                return showAction
            }

            override fun hideEmptyState(): Action {
                return hideAction
            }

        }

        emptyStateAssigner = AssignEmptyState(view, uiScheduler)
    }

    @Test fun `should not show empty state when flow emmits`() {
        Observable.just("A", "B", "C")
                .compose(emptyStateAssigner)
                .subscribe()

        `verify hidden at start and no more displayed`()
    }

    @Test fun `should not show empty state when flow is empty`() {
        Observable.empty<Any>()
                .compose(emptyStateAssigner)
                .subscribe()

        `verify hidden at start and no more displayed`()
    }

    @Test fun `should not show empty state when other errors signaled`() {
        Observable.error<Any>(DataAccessError.RemoteSystemDown())
                .compose(emptyStateAssigner)
                .subscribe({}, {}, {})

        `verify hidden at start and no more displayed`()
    }

    @Test fun `should show empty state when content not found`() {
        Observable.error<Any>(DataAccessError.ContentNotFound())
                .compose(emptyStateAssigner)
                .subscribe({}, {}, {})

        `verify hidden at start and displayed after`()
    }

    private fun `verify hidden at start and no more displayed`() {
        verify(hideAction, atLeastOnce()).run()
        verify(showAction, never()).run()
    }

    private fun `verify hidden at start and displayed after`() {
        verify(hideAction, atLeastOnce()).run()
        verify(showAction, atLeastOnce()).run()
    }
}