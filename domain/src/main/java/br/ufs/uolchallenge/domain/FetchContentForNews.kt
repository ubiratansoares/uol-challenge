package br.ufs.uolchallenge.domain

import io.reactivex.Observable

/**
 * Created by bira on 11/5/17.
 */

interface FetchContentForNews {

    fun execute(): Observable<Any>

}