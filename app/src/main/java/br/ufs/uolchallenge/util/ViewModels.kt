package br.ufs.uolchallenge.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import kotlin.LazyThreadSafetyMode.NONE

/**
 * Created by bira on 11/7/17.
 */

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
        mode: LazyThreadSafetyMode = NONE,
        crossinline provider: () -> VM) = lazy(mode) {

    val factory = object : ViewModelProvider.Factory {
        override fun <Model : ViewModel> create(klass: Class<Model>) = provider() as Model
    }

    ViewModelProviders.of(this, factory).get(VM::class.java)
}

inline fun <reified VM : ViewModel> FragmentActivity.withFactory(
        mode: LazyThreadSafetyMode = NONE,
        crossinline factory: () -> ViewModelProvider.Factory) = lazy(mode) {

    ViewModelProviders.of(this, factory()).get(VM::class.java)

}