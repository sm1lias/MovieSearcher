package com.smilias.movierama.util

import java.util.Locale

object Util {

    fun String.toLowercaseAndCapitalize(): String {
        return this.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }
}