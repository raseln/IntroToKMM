package com.rasel.introtokmm.android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rasel.introtokmm.android.ui.LaunchDetailScreen
import com.rasel.introtokmm.android.ui.LaunchList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHostKMM(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("home") {
            Scaffold(
                topBar = {
                    SmallTopAppBar(
                        title = {
                            Text(text = "Rocket Launch Info")
                        },
                        modifier = Modifier.shadow(elevation = 3.dp)
                    )
                }
            ) {
                LaunchList(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    onItemClick = { launch ->
                        navController.navigate(
                            "detail/${launch.id}"
                        )
                    }
                )
            }
        }
        composable("detail/{id}") {
            LaunchDetailScreen(
                onBackPress = {
                    navController.popBackStack()
                }
            )
        }
    }
}