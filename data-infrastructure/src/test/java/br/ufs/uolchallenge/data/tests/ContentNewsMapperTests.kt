package br.ufs.uolchallenge.data.tests

import br.ufs.uolchallenge.data.ContentNewsMapper
import br.ufs.uolchallenge.data.models.NewsContentPayload
import br.ufs.uolchallenge.domain.News
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Created by bira on 11/3/17.
 */
class ContentNewsMapperTests {

    lateinit var mapper: ContentNewsMapper
    lateinit var stubbedPayload: NewsContentPayload

    @Before fun `before each test`() {
        mapper = ContentNewsMapper()
        stubbedPayload = NewsContentPayload()
        stubbedPayload.title = "Some title"
        stubbedPayload.webviewURL = "http://some.url.to.content/content.html"
        stubbedPayload.shareURL = "http://some.url.to.content/content.html"
        stubbedPayload.updatedAt = 20171101172633
    }

    @Test fun `should map payload without image url`() {
        val news = mapper.toNews(payload = stubbedPayload)
        assertThat(news.relatedImageURL).isEqualTo(News.NO_IMAGE_AVAILABLE)
    }

    @Test fun `should map payload with image url when available`() {
        val imageURL = "https://path.to/image.jpg"
        stubbedPayload.thumbURL = imageURL
        val news = mapper.toNews(payload = stubbedPayload)
        assertThat(news.relatedImageURL).isEqualTo(imageURL)
    }


    @Test fun `should map date properly`() {
        val news = mapper.toNews(payload = stubbedPayload)
        val updateDateTime = news.updateDateTime

        assertThat(updateDateTime).hasYear(2017)
        assertThat(updateDateTime).hasMonth(11)
        assertThat(updateDateTime).hasDayOfMonth(1)
        assertThat(updateDateTime).hasHourOfDay(17)
        assertThat(updateDateTime).hasMinute(26)
        assertThat(updateDateTime).hasSecond(33)
    }

    @Test fun `should map other fields`() {
        val news = mapper.toNews(payload = stubbedPayload)
        assertThat(news.title).isEqualTo(stubbedPayload.title)
        assertThat(news.sharableURL).isEqualTo(stubbedPayload.shareURL)
        assertThat(news.visualizationContentURL).isEqualTo(stubbedPayload.webviewURL)

    }
}