package br.ufs.uolchallenge.presentation.tests.feed

import br.ufs.uolchallenge.presentation.ContentDateTimeFormatter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

/**
 * Created by bira on 11/3/17.
 */

class ContentDateTimeFormatterTests {

    @Test fun `should format date to content`() {
        val formatter = ContentDateTimeFormatter()
        val calendar = Calendar.getInstance()

        // Months start with zero, february = 01
        calendar.set(2017, 1, 25, 22, 15, 0)
        val dateTime = calendar.time

        val formatted = formatter.formatAsContent(dateTime)
        assertThat(formatted).isEqualTo("atualizado em 25/02 às 22h:15m")
    }
}