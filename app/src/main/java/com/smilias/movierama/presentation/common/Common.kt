package com.smilias.movierama.presentation.common

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.smilias.movierama.R

@Composable
fun RatingBar(
    rating: Float,
    maxRating: Int
) {
    Row {
        for (i in 1..maxRating) {
            if (i <= rating) {
                Icon(imageVector = Icons.Rounded.Star, contentDescription = "Icon star")
            }else if (i - rating <= 0.5){
                Icon(imageVector = Icons.Rounded.StarHalf, contentDescription = "Icon star")
            }else {
                Icon(imageVector = Icons.Rounded.StarBorder, contentDescription = "Icon starBorder")
            }
        }
    }

}

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