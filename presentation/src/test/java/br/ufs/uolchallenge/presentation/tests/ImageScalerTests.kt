package br.ufs.uolchallenge.presentation.tests

import br.ufs.uolchallenge.presentation.ImageScaler
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Created by bira on 11/3/17.
 */

class ImageScalerTests {

    lateinit var scaler: ImageScaler

    @Before fun `before each test`() {
        scaler = ImageScaler()
    }

    @Test fun `should scale with default size`() {
        val image = "https://imguol.com/c/esporte/" +
                "2b/2017/09/05/yaya-banhoro-burkina-faso-1504648624490_142x100.jpg"

        val scaled = scaler.resize(image)
        assertThat(scaled).contains(ImageScaler.DEFAULT_SIZE)
        assertThat(scaled).doesNotContain("142x100")
    }

}