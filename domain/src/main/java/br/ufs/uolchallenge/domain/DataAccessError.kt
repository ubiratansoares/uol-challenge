package br.ufs.uolchallenge.domain

/**
 * Created by bira on 11/3/17.
 */
sealed class DataAccessError : Throwable() {
    class ContentNotFound : DataAccessError()
    class RemoteSystemDown : DataAccessError()
    class UndesiredResponse : DataAccessError()
}