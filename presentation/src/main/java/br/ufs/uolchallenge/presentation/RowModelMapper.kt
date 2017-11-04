package br.ufs.uolchallenge.presentation

import br.ufs.uolchallenge.domain.News
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry

/**
 * Created by bira on 11/3/17.
 */

class RowModelMapper {

    val imageScaler = ImageScaler()
    val dateTimeFormatter = ContentDateTimeFormatter()

    fun toRowModel(item: News): NewsFeedEntry {

        val contentFormatted = dateTimeFormatter.formatAsContent(item.updateDateTime)

        if (item.relatedImageURL != News.NO_IMAGE_AVAILABLE) {
            val resized = imageScaler.resize(item.relatedImageURL)

            return NewsFeedEntry.WithImage(
                    title = item.title,
                    shareableURL = item.sharableURL,
                    visualizationURL = item.visualizationContentURL,
                    formmatedDate = contentFormatted,
                    dimensionedImageURL = resized
            )
        }

        return NewsFeedEntry.Plain(
                title = item.title,
                shareableURL = item.sharableURL,
                visualizationURL = item.visualizationContentURL,
                formmatedDate = contentFormatted
        )
    }

}