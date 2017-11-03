package br.ufs.uolchallenge.data.handlers

import br.ufs.uolchallenge.domain.DataAccessError.UndesiredResponse
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Created by bira on 11/3/17.
 */
class GsonErrorHandler<T> : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.onErrorResumeNext(this::handleErrorFromDeserializer)
    }

    private fun handleErrorFromDeserializer(throwable: Throwable): Observable<T> {
        if (isErrorFromGson(throwable)) return Observable.error(UndesiredResponse())
        return Observable.error(throwable)
    }

    private fun isErrorFromGson(throwable: Throwable): Boolean {
        return when (throwable) {
            is JsonIOException -> true
            is JsonSyntaxException -> true
            is JsonParseException -> true
            else -> false
        }
    }
}