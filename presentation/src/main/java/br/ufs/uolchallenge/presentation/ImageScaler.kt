package br.ufs.uolchallenge.presentation

/**
 * Created by bira on 11/3/17.
 */

class ImageScaler {

    // "https://imguol.com/c/esporte/...../1495325671684_142x100.jpg"

    fun resize(uolImageURL: String): String {
        val parts = uolImageURL.split("_")
        val base = parts[0]
        val values = parts[1]
        val format = values.split("x", ".")
        val extension = format.last()
        return "{$base}_$DEFAULT_SIZE.$extension"
    }

    companion object {
        val DEFAULT_SIZE = "720x405"
    }
}