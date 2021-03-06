package br.ufs.uolchallenge.presentation.feed

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bira on 11/3/17.
 */

class ContentDateTimeFormatter {

    fun formatAsContent(updateDateTime: Date): String {
        val format = "'atualizado em' dd/MM à's' HH'h':mm'm'"
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(updateDateTime)
    }
}