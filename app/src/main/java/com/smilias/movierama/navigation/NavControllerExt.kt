package com.smilias.movierama.navigation

import androidx.navigation.NavController

fun NavController.navigateToDetailsScreen(id: Int){
    this.navigate(Screen.DetailsScreen.route+"/$id")
}