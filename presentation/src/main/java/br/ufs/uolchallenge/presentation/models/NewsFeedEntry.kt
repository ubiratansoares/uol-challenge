package br.ufs.uolchallenge.presentation.models

/**
 * Created by bira on 11/3/17.
 */

sealed class NewsFeedEntry {

    class Plain(
            val title: String,
            val shareableURL: String,
            val visualizationURL: String,
            val formmatedDate: String) : NewsFeedEntry()

    class WithImage(
            val title: String,
            val shareableURL: String,
            val visualizationURL: String,
            val formmatedDate: String,
            val dimensionedImageURL: String) : NewsFeedEntry()

}