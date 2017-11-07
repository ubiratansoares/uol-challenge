package br.ufs.uolchallenge.presentation.tests.feed

import br.ufs.uolchallenge.domain.News
import br.ufs.uolchallenge.presentation.RowModelMapper
import br.ufs.uolchallenge.presentation.models.NewsFeedEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

/**
 * Created by bira on 11/4/17.
 */

class RowModelMapperTests {

    val mapper = RowModelMapper()

    val newsMissingImage = News(
            title = "Some title",
            sharableURL = "https://esporte.uol.com.br/content?format=mobile",
            visualizationContentURL = "https://esporte.uol.com.br/content",
            updateDateTime = Calendar.getInstance().time
    )

    @Test fun `should map as plain entry`() {
        val rowEntry = mapper.toRowModel(newsMissingImage)
        assertThat(rowEntry).isInstanceOf(NewsFeedEntry.Plain::class.java)
    }

    @Test fun `should map with image`() {
        val withImage = newsMissingImage.copy(
                relatedImageURL = "https://path.to/imageid_300x150.jpg"
        )

        val rowEntry = mapper.toRowModel(withImage)
        assertThat(rowEntry).isInstanceOf(NewsFeedEntry.WithImage::class.java)
    }


}