package br.ufs.uolchallenge

import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import br.ufs.uolchallenge.feed.HomeActivity
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

/**
 * Created by bira on 11/5/17.
 */

@RunWith(RobolectricTestRunner::class)
class NewsFeedActivityTests {

    lateinit var activity: HomeActivity
    lateinit var loading: ProgressBar
    lateinit var feedView: RecyclerView
    lateinit var feedbackContainer: View
    lateinit var errorImage: ImageView
    lateinit var errorLabel: TextView
    lateinit var fab: FloatingActionButton

    @Before fun `before each test`() {
        activity = Robolectric.buildActivity(HomeActivity::class.java).create().get()
        loading = activity.loading
        feedView = activity.feedview
        feedbackContainer = activity.feedbackContainer
        errorImage = activity.errorImage
        errorLabel = activity.errorMessage
        fab = activity.fab
    }

    @Test fun `should setup views properly at screen creation`() {
        `loading should not be visible`()
        `recycler view should have layout manager attached`()
        `error related views should not be visible`()
    }

    @Test fun `should dispatch actions for progress bar control`() {
        activity.showLoading().run()
        assertThat(loading.visibility).isEqualTo(View.VISIBLE)

        activity.hideLoading().run()
        assertThat(loading.visibility).isEqualTo(View.GONE)
    }

    @Test fun `should dispatch actions for error state control`() {
        activity.showErrorState().run()
        `error related view should be visible`()
        `error label should have text with`(R.string.error_server_down)
        `error image should have drawable with`(R.drawable.img_server_off)
        `call to action should be visible with message`(R.string.snacktext_server_down)

        activity.hideErrorState().run()
        `error views should be reseted`()
    }

    @Test fun `should dispatch actions for empty state control`() {
        activity.showEmptyState().run()
        `error related view should be visible`()
        `error label should have text with`(R.string.error_not_found)
        `error image should have drawable with`(R.drawable.img_uol_logo_bw)

        activity.hideErrorState().run()
        `error views should be reseted`()
    }

    @Test fun `should dispatch actions for internet error report`() {
        activity.reportNetworkingError().run()
        `error related view should be visible`()
        `error label should have text with`(R.string.error_internet_connection)
        `error image should have drawable with`(R.drawable.img_network_off)
        `call to action should be visible with message`(R.string.snacktext_internet_connection)

        activity.hideErrorState().run()
        `error views should be reseted`()
    }

    @Test fun `should dispatch actions for refresh option`() {
        activity.disableRefresh().run()
        `refresh option should not be visible`()

        activity.enableRefresh().run()
        `refresh option should be visible`()
    }

    @Test fun `should fill adapter when data arrives`() {

        val newsItems = listOf(
                NewsFeedEntry.Plain(
                        title = "Notícia 01",
                        shareableURL = "http://esporte.uol.com/content/news1",
                        visualizationURL = "http://esporte.uol.com/content/news1?target=android",
                        formmatedDate = "atualizado em 31/02/2000"
                ),
                NewsFeedEntry.WithImage(
                        title = "Notícia 02",
                        shareableURL = "http://esporte.uol.com/content/news2",
                        visualizationURL = "http://esporte.uol.com/content/news2?target=android",
                        formmatedDate = "atualizado em 31/04/2000",
                        dimensionedImageURL = "http://path.to.some/image.jpg"
                )
        )

        activity.showNewsFeed(newsItems)
        assertThat(feedView.adapter).isNotNull()
        assertThat(feedView.adapter.itemCount).isEqualTo(newsItems.size)
    }

    private fun `call to action should be visible with message`(messageResource: Int) {
        val snackText = activity.findViewById<TextView>(R.id.snackbar_text)
        assertThat(snackText).isNotNull()

        val feedback = activity.getString(messageResource)
        assertThat(snackText.text).isEqualTo(feedback)
    }

    private fun `error label should have text with`(errorString: Int) {
        val feedback = activity.getString(errorString)
        assertThat(errorLabel.text).isEqualTo(feedback)
    }

    private fun `error image should have drawable with`(imageResource: Int) {
        val target = ContextCompat.getDrawable(activity, imageResource)
        assertThat(errorImage.drawable).isEqualTo(target)
    }

    private fun `error views should be reseted`() {
        assertThat(feedbackContainer.visibility).isEqualTo(View.GONE)
        assertThat(errorLabel.text).isEqualTo("")
        assertThat(errorImage.drawable).isNull()
    }

    private fun `error related view should be visible`() {
        assertThat(feedbackContainer.visibility).isEqualTo(View.VISIBLE)
        assertThat(errorImage.visibility).isEqualTo(View.VISIBLE)
        assertThat(errorLabel.visibility).isEqualTo(View.VISIBLE)
    }

    private fun `error related views should not be visible`() {
        assertThat(feedbackContainer.visibility).isEqualTo(View.GONE)
    }

    private fun `refresh option should be visible`() {
        assertThat(fab.visibility).isEqualTo(View.VISIBLE)
    }

    private fun `refresh option should not be visible`() {
        assertThat(fab.visibility).isEqualTo(View.GONE)
    }

    private fun `recycler view should have layout manager attached`() {
        assertThat(feedView.layoutManager).isNotNull()
    }

    private fun `loading should not be visible`() {
        assertThat(loading.visibility).isEqualTo(View.GONE)
    }

}