package com.smilias.movierama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.smilias.movierama.databinding.ActivityMainBinding
import com.smilias.movierama.navigation.SetupNavGraph
import com.smilias.movierama.ui.theme.MovieRamaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        setContent {
//            MovieRamaTheme {
//
//                val snackbarHostState = remember { SnackbarHostState() }
//                val navController = rememberNavController()
//                Scaffold(
//                    snackbarHost = { SnackbarHost(snackbarHostState) }
//                ) { _ ->
//                    SetupNavGraph(navController, onShowSnackbar = { message, action ->
//                        snackbarHostState.showSnackbar(
//                            message = message,
//                            actionLabel = action,
//                            duration = SnackbarDuration.Short,
//                        ) == SnackbarResult.ActionPerformed
//                    })
//                }
//                // A surface container using the 'background' color from the theme
//
//            }
//        }
    }
}

