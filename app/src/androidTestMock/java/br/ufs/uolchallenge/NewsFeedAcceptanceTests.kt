package br.ufs.uolchallenge

import android.support.test.runner.AndroidJUnit4
import br.ufs.uolchallenge.data.fakes.WebResponseScenario
import br.ufs.uolchallenge.features.feed.HomeActivity
import br.ufs.uolchallenge.util.ScriptableWebServerRule
import com.schibsted.spain.barista.BaristaAssertions.assertRecyclerViewItemCount
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by bira on 11/7/17.
 */

@RunWith(AndroidJUnit4::class)
class NewsFeedAcceptanceTests {

    @Rule @JvmField val launcher = ScriptableWebServerRule(HomeActivity::class.java)

    private val robot = BehaviorsVerifier

    @Test fun serverDownScenario() {
        launcher.startWithCenario(WebResponseScenario.InternalServerError)

        robot.fabShouldNotBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .serverErrorReported()
    }

    @Test fun notFoundScenario() {
        launcher.startWithCenario(WebResponseScenario.NotFound)

        robot.fabShouldNotBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .emptyStateReported()
    }

    @Test fun otherRestErrorScenario() {
        launcher.startWithCenario(WebResponseScenario.ClientError)

        robot.fabShouldNotBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .undesiredResponseReported()
    }

    @Test fun requestTimeoutScenario() {
        launcher.startWithCenario(WebResponseScenario.ConnectionTimeout)

        robot.fabShouldNotBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .intenetIssueReported()
    }

    @Test fun connectionIssueScenario() {
        launcher.startWithCenario(WebResponseScenario.ConnectionError)

        robot.fabShouldNotBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .intenetIssueReported()
    }

    @Test fun internetUnavailableScenario() {
        launcher.startWithCenario(WebResponseScenario.NoInternet)

        robot.fabShouldNotBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .intenetIssueReported()
    }

    @Test fun resultsAvailable() {
        launcher.startWithCenario(WebResponseScenario.Success)

        robot.fabButtonShouldBeAvailable()
                .loadingIndicatorShouldBeInvisible()
                .noErrors()

        assertRecyclerViewItemCount(R.id.feedView, 18) // From fakes

    }
}