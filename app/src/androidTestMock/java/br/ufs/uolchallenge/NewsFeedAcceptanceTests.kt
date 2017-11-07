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

    @Test fun serverDownScenario() {
        launcher.startWithCenario(WebResponseScenario.InternalServerError)

        afterExecution {
            fabButtionHidden()
            loadingIndicatorInvisible()
            serverErrorReported()
        }
    }

    @Test fun notFoundScenario() {
        launcher.startWithCenario(WebResponseScenario.NotFound)

        afterExecution {
            fabButtionHidden()
            loadingIndicatorInvisible()
            emptyStateReported()
        }
    }

    @Test fun otherRestErrorScenario() {
        launcher.startWithCenario(WebResponseScenario.ClientError)

        afterExecution {
            fabButtionHidden()
            loadingIndicatorInvisible()
            undesiredResponseReported()
        }

    }

    @Test fun requestTimeoutScenario() {
        launcher.startWithCenario(WebResponseScenario.ConnectionTimeout)

        afterExecution {
            fabButtionHidden()
            loadingIndicatorInvisible()
            internetIssueReported()
        }
    }

    @Test fun connectionIssueScenario() {
        launcher.startWithCenario(WebResponseScenario.ConnectionError)

        afterExecution {
            fabButtionHidden()
            loadingIndicatorInvisible()
            internetIssueReported()
        }
    }

    @Test fun internetUnavailableScenario() {
        launcher.startWithCenario(WebResponseScenario.NoInternet)

        afterExecution {
            fabButtionHidden()
            loadingIndicatorInvisible()
            internetIssueReported()
        }
    }

    @Test fun resultsAvailable() {
        launcher.startWithCenario(WebResponseScenario.Success)

        afterExecution {
            fabButtonAvailable()
            loadingIndicatorInvisible()
            noErrors()
        }

        assertRecyclerViewItemCount(R.id.feedView, 18) // From fakes

    }
}