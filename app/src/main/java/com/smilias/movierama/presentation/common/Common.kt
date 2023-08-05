package com.smilias.movierama.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

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