package br.ufs.uolchallenge.domain

import java.util.*

/**
 * Created by bira on 11/3/17.
 */

data class News(
        val title: String,
        val publicationDateTime: Date,
        val sharableURL: String,
        val visualizationContentURL: String,
        val relatedImageURL: String
) {
    companion object {
        val NO_IMAGE_AVAILABLE = "http://no.image.available.jpg"
    }
}

