package br.ufs.uolchallenge.factories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.ufs.uolchallenge.presentation.feed.NewsFeedViewModel

/**
 * Created by bira on 11/7/17.
 */

object NewsFeedFactory {

    operator fun invoke(): ViewModelProvider.Factory {

        return object : ViewModelProvider.Factory {

            override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
                val infrastructure = InfrastructureFactory.newsFeed()
                return NewsFeedViewModel(usecase = infrastructure) as T
            }

        }
    }

}