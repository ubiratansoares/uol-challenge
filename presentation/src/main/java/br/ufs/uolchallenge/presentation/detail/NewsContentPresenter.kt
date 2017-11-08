package br.ufs.uolchallenge.presentation.detail

import android.webkit.WebViewClient
import br.ufs.uolchallenge.domain.FetchContentForNews
import io.reactivex.Observable

/**
 * Created by bira on 11/5/17.
 */

class NewsContentPresenter(private val usecase: FetchContentForNews) {

    fun loadNews(): Observable<Any> {
        return usecase.execute()
    }

    fun webViewClient() : WebViewClient {
        return usecase as WebViewClient
    }

}