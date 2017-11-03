package br.ufs.uolchallenge.domain

import io.reactivex.Observable

/**
 * Created by bira on 11/3/17.
 */

interface FetchNewsFeed {

    fun latestNews(): Observable<News>

}