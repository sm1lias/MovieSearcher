package com.smilias.movierama.util

import com.smilias.movierama.util.Util.toLowercaseAndCapitalize
import java.time.LocalDate
import java.util.Locale

object Util {

    fun String.toLowercaseAndCapitalize(): String {
        return this.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }

    fun LocalDate.toMyFormattedString() = "${this.dayOfMonth} ${
        this.month.toString().toLowercaseAndCapitalize()
    } ${this.year}"
}