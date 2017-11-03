package br.ufs.uolchallenge.data

import br.ufs.uolchallenge.data.models.NewsContentPayload
import br.ufs.uolchallenge.domain.News
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bira on 11/3/17.
 */
class ContentNewsMapper {

    fun toNews(payload: NewsContentPayload): News {
        val imageURLOrDefault = payload.thumbURL?.let { it } ?: News.NO_IMAGE_AVAILABLE
        val dateTime = toDate(payload.updatedAt)

        return News(
                title = payload.title,
                updateDateTime = dateTime,
                visualizationContentURL = payload.webviewURL,
                sharableURL = payload.shareURL,
                relatedImageURL = imageURLOrDefault
        )
    }

    private fun toDate(updatedAt: Long): Date {
        val formatter = SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault())
        return formatter.parse(updatedAt.toString())
    }
}