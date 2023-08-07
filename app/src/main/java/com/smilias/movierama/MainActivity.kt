package com.smilias.movierama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.smilias.movierama.navigation.SetupNavGraph
import com.smilias.movierama.ui.theme.MovieRamaTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieRamaTheme {

                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { _ ->
                    SetupNavGraph(navController, onShowSnackbar = { message, action ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = SnackbarDuration.Short,
                        ) == SnackbarResult.ActionPerformed
                    })
                }
                // A surface container using the 'background' color from the theme

            }
        }
    }
}

