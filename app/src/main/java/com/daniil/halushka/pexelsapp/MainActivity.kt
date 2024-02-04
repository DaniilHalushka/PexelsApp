package com.daniil.halushka.pexelsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.daniil.halushka.pexelsapp.presentation.navigation.NavigationGraph
import com.daniil.halushka.pexelsapp.presentation.navigation.ScreenRoutes
import com.daniil.halushka.pexelsapp.ui.theme.PexelsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PexelsAppTheme {
                PexelsApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PexelsApp() {
    val navigationController = rememberNavController()

    Scaffold(

    ) {
        NavigationGraph(
            navController = navigationController,
            startDestination = ScreenRoutes.SplashScreen.screenType.name
        )
    }

}