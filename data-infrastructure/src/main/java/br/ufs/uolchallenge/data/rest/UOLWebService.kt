package br.ufs.uolchallenge.data.rest

import br.ufs.uolchallenge.data.models.NewsFeedPayload
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by bira on 11/3/17.
 */

interface UOLWebService {

    @GET("c/api/v1/list/news/?app=uol-placar-futebol&version=2")
    fun latestNews(): Observable<NewsFeedPayload>

    companion object {
        val BASE_URL = "http://app.servicos.uol.com.br/"
    }
}