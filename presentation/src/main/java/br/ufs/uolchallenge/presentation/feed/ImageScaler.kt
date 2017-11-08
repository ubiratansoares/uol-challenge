package br.ufs.uolchallenge.presentation.feed

/**
 * Created by bira on 11/3/17.
 */

class ImageScaler {

    // "https://imguol.com/c/esporte/...../1495325671684_142x100.jpg"

    fun resize(uolImageURL: String): String {
        // We notice this pattern by looking at the related mobile pages with head images
        return uolImageURL.replace("142x100", DEFAULT_SIZE)
    }

    companion object {
        val DEFAULT_SIZE = "615x300"
    }
}