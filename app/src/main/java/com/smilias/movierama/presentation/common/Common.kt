package com.smilias.movierama.presentation.common

import android.widget.ImageView
import android.widget.LinearLayout
import com.smilias.movierama.R

fun LinearLayout.setRating(rating: Float){
    for (i in 0 until 5) {
        val starImageView = this.getChildAt(i) as ImageView

        when {
            i + 1 <= rating -> {
                starImageView.setImageResource(R.drawable.star_full) // Full star
            }
            i + 1 - rating <= 0.5 -> {
                starImageView.setImageResource(R.drawable.star_half) // Half star
            }
            else -> {
                starImageView.setImageResource(R.drawable.star_empty) // Border star
            }
        }
    }
}