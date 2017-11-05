package br.ufs.uolchallenge.util

import android.content.Context
import android.content.Intent
import br.ufs.uolchallenge.detail.DisplayNewsActivity
import br.ufs.uolchallenge.detail.DisplayNewsActivity.Companion.SHARE_URL
import br.ufs.uolchallenge.detail.DisplayNewsActivity.Companion.VISUALIZATION_URL

/**
 * Created by bira on 11/5/17.
 */

object Navigator {

    fun toDisplayNews(origin: Context, shareURL: String, visualizationURL: String) {
        val intent = Intent(origin, DisplayNewsActivity::class.java).apply {
            putExtra(SHARE_URL, shareURL)
            putExtra(VISUALIZATION_URL, visualizationURL)
        }

        origin.startActivity(intent)
    }

}