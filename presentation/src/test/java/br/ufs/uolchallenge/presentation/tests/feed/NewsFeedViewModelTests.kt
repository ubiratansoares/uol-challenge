package br.ufs.uolchallenge.presentation.tests.feed

import br.ufs.uolchallenge.domain.FetchNewsFeed
import br.ufs.uolchallenge.domain.News
import br.ufs.uolchallenge.presentation.RowModelMapper
import br.ufs.uolchallenge.presentation.feed.NewsFeedViewModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Created by bira on 11/3/17.
 */

class NewsFeedViewModelTests {

    val usecase by lazy { mock<FetchNewsFeed>() }

    lateinit var viewModel: NewsFeedViewModel

    @Before fun `before each test`() {
        viewModel = NewsFeedViewModel(usecase)
    }

    @Test fun `should translate and emmit proper data from usecase`() {

        val data = listOf(fakeNews(), fakeNews(), fakeNews())
        val mapped = data.map { RowModelMapper().toRowModel(it) }

        whenever(usecase.latestNews()).then { Observable.fromIterable(data) }

        viewModel.fetchLastestNews()
                .test()
                .assertValueSequence(mapped)
    }

    @Test fun `should replay previous state`() {

        val originallyFetched = listOf(fakeNews(), fakeNews(), fakeNews())
        val mapped = originallyFetched.map { RowModelMapper().toRowModel(it) }

        whenever(usecase.latestNews())
                .then { Observable.fromIterable(originallyFetched) }

        // First trigger
        viewModel.fetchLastestNews()
                .test()
                .assertNoErrors()
                .assertValueSequence(mapped)
                .assertComplete()

        // Second trigger
        viewModel.fetchLastestNews()
                .test()
                .assertValueSequence(mapped)

    }

    @Test fun `should invalidate previous state`() {

        val originallyFetched = listOf(fakeNews(), fakeNews(), fakeNews())

        whenever(usecase.latestNews())
                .then { Observable.fromIterable(originallyFetched) }

        viewModel.fetchLastestNews().subscribe()

        val updatedData = listOf(fakeNews(), fakeNews())

        whenever(usecase.latestNews())
                .then { Observable.fromIterable(updatedData) }

        viewModel.fetchLastestNews(shouldRefresh = true)
                .test()
                .assertValueCount(updatedData.size)
    }

    fun fakeNews(): News {

        val random = Random().nextInt(500)

        return News(
                title = "Chuck Norris Fact $random",
                sharableURL = "https://chuck.facts/$random?format=mobile",
                visualizationContentURL = "https://chuck.facts/$random",
                updateDateTime = Calendar.getInstance().time
        )
    }
}