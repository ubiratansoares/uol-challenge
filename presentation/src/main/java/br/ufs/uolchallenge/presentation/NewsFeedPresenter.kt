package br.ufs.uolchallenge.presentation

import io.reactivex.Observable

/**
 * Created by bira on 11/3/17.
 */

class NewsFeedPresenter {

    fun fetchLastestNews(): Observable<NewsFeedRowModel> {
        return Observable.empty()
    }

}