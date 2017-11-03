package br.ufs.uolchallenge.data.handlers

import br.ufs.uolchallenge.domain.CommunicationError.*
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * Created by bira on 11/3/17.
 */

class NetworkingErrorHandler<T> : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.onErrorResumeNext(this::handleIfNetworkingError)
    }

    private fun handleIfNetworkingError(throwable: Throwable): Observable<T> {
        if (isNetworkingError(throwable)) return asNetworkingError(throwable)
        return Observable.error(throwable)
    }

    private fun asNetworkingError(throwable: Throwable): Observable<T> {
        return Observable.error(mapToDomainError(throwable))
    }

    private fun mapToDomainError(throwable: Throwable): Throwable {
        if (isConnectionTimeout(throwable)) return SlowConnection()
        if (noInternetAvailable(throwable)) return InternetUnavailable()
        return NetworkingHippcup()
    }

    private fun isNetworkingError(throwable: Throwable): Boolean {
        return isConnectionTimeout(throwable) ||
                noInternetAvailable(throwable) ||
                isRequestCanceled(throwable)
    }

    private fun isRequestCanceled(throwable: Throwable): Boolean {
        return throwable is IOException
                && throwable.message?.contentEquals("Canceled") ?: false
    }

    private fun noInternetAvailable(throwable: Throwable): Boolean {
        return throwable is UnknownHostException
    }

    private fun isConnectionTimeout(throwable: Throwable): Boolean {
        return throwable is SocketTimeoutException
    }

}