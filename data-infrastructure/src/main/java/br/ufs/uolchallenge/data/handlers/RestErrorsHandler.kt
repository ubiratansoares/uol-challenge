package br.ufs.uolchallenge.data.handlers

import br.ufs.uolchallenge.domain.DataAccessError
import br.ufs.uolchallenge.domain.DataAccessError.*
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.HttpException

/**
 * Created by bira on 11/3/17.
 */
class RestErrorsHandler<T> : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.onErrorResumeNext(this::handleIfRestError)
    }

    private fun handleIfRestError(throwable: Throwable): Observable<T> {
        if (isRestError(throwable)) return asDataAccessError(throwable as HttpException)
        return Observable.error(throwable)
    }

    private fun asDataAccessError(restError: HttpException): Observable<T> {
        val dataAccessError = mapErrorWith(restError.code())
        return Observable.error(dataAccessError)
    }

    private fun mapErrorWith(code: Int): DataAccessError {
        return when (code) {
            404 -> ContentNotFound()
            in 500..511 -> RemoteSystemDown()
            else -> UndesiredResponse()
        }
    }

    private fun isRestError(throwable: Throwable): Boolean {
        return throwable is HttpException
    }
}