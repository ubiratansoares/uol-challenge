package br.ufs.uolchallenge.presentation.feed

import br.ufs.uolchallenge.presentation.behaviors.emptystate.EmptyStateView
import br.ufs.uolchallenge.presentation.behaviors.errorstate.ErrorStateView
import br.ufs.uolchallenge.presentation.behaviors.loading.LoadingContentView
import br.ufs.uolchallenge.presentation.behaviors.networking.NetworkingErrorView
import br.ufs.uolchallenge.presentation.behaviors.fab.FabActionableView

/**
 * Created by bira on 11/4/17.
 */
interface NewsFeedView :
        LoadingContentView,
        ErrorStateView,
        EmptyStateView,
        NetworkingErrorView,
        FabActionableView
