package br.ufs.uolchallenge.presentation

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bira on 11/3/17.
 */

class ContentDateTimeFormatter {

    fun formatAsContent(updateDateTime: Date): String {
        val formatter = SimpleDateFormat("dd/MM à's' HH'h':mm'm'", Locale.getDefault())
        return formatter.format(updateDateTime)
    }
}