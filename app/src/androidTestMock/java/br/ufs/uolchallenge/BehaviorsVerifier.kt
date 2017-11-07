package br.ufs.uolchallenge

import com.schibsted.spain.barista.BaristaAssertions.*

/**
 * Created by bira on 11/7/17.
 */

fun afterExecution(func: BehaviorsVerifier.() -> Unit) = BehaviorsVerifier().apply { func() }

class BehaviorsVerifier {

    fun fabButtonAvailable(): BehaviorsVerifier {
        assertDisplayed(R.id.fab)
        return this
    }

    fun fabButtionHidden(): BehaviorsVerifier {
        assertNotDisplayed(R.id.fab)
        return this
    }

    fun loadingIndicatorInvisible(): BehaviorsVerifier {
        assertNotDisplayed(R.id.loading)
        return this
    }

    fun serverErrorReported(): BehaviorsVerifier {
        assertDrawable(R.id.errorImage, R.drawable.img_server_off)
        assertDisplayed(R.string.error_server_down)
        return this
    }

    fun internetIssueReported(): BehaviorsVerifier {
        assertDisplayed(R.id.snackbar_text)
        assertDisplayed(R.string.error_internet_connection)
        return this
    }

    fun noErrors(): BehaviorsVerifier {
        assertNotDisplayed(R.id.feedbackContainer)
        return this
    }

    fun emptyStateReported(): BehaviorsVerifier {
        assertDrawable(R.id.errorImage, R.drawable.img_uol_logo_bw)
        assertDisplayed(R.string.error_not_found)
        return this
    }

    fun undesiredResponseReported() {
        assertDrawable(R.id.errorImage, R.drawable.img_server_off)
        assertDisplayed(R.string.error_server_down)
    }
}