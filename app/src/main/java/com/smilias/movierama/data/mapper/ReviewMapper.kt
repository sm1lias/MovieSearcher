package com.smilias.movierama.data.mapper

import com.smilias.movierama.data.remote.dto.ReviewDto
import com.smilias.movierama.domain.model.Review

fun ReviewDto.toReview(): Review {
    return Review(
        author = author,
        review = review
    )
}