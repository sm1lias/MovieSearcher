package com.smilias.movierama.presentation.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    init {
        savedStateHandle.get<Int>("id")?.let { Log.d("my id", it.toString()) }
    }
}