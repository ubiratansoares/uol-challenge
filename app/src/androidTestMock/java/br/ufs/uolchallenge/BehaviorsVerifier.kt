package br.ufs.uolchallenge

import com.schibsted.spain.barista.BaristaAssertions

/**
 * Created by bira on 11/7/17.
 */

object BehaviorsVerifier {

    fun fabButtonShouldBeAvailable(): BehaviorsVerifier {
        BaristaAssertions.assertDisplayed(R.id.fab)
        return this
    }

    fun fabShouldNotBeAvailable(): BehaviorsVerifier {
        BaristaAssertions.assertNotDisplayed(R.id.fab)
        return this
    }

    fun loadingIndicatorShouldBeInvisible(): BehaviorsVerifier {
        BaristaAssertions.assertNotDisplayed(R.id.loading)
        return this
    }

    fun serverErrorReported(): BehaviorsVerifier {
        BaristaAssertions.assertDrawable(R.id.errorImage, R.drawable.img_server_off)
        BaristaAssertions.assertDisplayed(R.string.error_server_down)
        return this
    }

    fun intenetIssueReported(): BehaviorsVerifier {
        BaristaAssertions.assertDisplayed(R.id.snackbar_text)
        BaristaAssertions.assertDisplayed(R.string.error_internet_connection)
        return this
    }

    fun noErrors(): BehaviorsVerifier {
        BaristaAssertions.assertNotDisplayed(R.id.feedbackContainer)
        return this
    }

    fun emptyStateReported(): BehaviorsVerifier {
        BaristaAssertions.assertDrawable(R.id.errorImage, R.drawable.img_uol_logo_bw)
        BaristaAssertions.assertDisplayed(R.string.error_not_found)
        return this
    }

    fun undesiredResponseReported() {
        BaristaAssertions.assertDrawable(R.id.errorImage, R.drawable.img_server_off)
        BaristaAssertions.assertDisplayed(R.string.error_server_down)
    }
}