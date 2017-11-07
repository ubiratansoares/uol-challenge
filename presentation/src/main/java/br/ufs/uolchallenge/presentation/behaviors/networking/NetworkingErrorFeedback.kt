package br.ufs.uolchallenge.presentation.behaviors.networking

import br.ufs.uolchallenge.domain.CommunicationError
import io.reactivex.*


/**
 * Created by bira on 11/3/17.
 */

class NetworkingErrorFeedback(
        val passiveView: Any,
        val uiScheduler: Scheduler) : ObservableTransformer<Any, Any> {

    override fun apply(upstream: Observable<Any>): ObservableSource<Any> {
        return upstream.doOnError { handleIfNetworkingError(it) }
    }

    private fun handleIfNetworkingError(throwable: Throwable): Observable<Any> {

        if (passiveView is NetworkingErrorView && throwable is CommunicationError) {
            Completable.fromAction(passiveView.reportNetworkingError())
                    .subscribeOn(uiScheduler)
                    .subscribe()
        }

        return Observable.error(throwable)
    }
}