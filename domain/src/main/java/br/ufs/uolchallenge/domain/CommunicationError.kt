package br.ufs.uolchallenge.domain

/**
 * Created by bira on 11/3/17.
 */

sealed class CommunicationError : Throwable() {

    class InternetUnavailable : CommunicationError()
    class NetworkingHippcup : CommunicationError()
    class SlowConnection : CommunicationError()

}