package com.rasel.introtokmm.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rasel.introtokmm.data.RocketLaunch
import com.rasel.introtokmm.android.ui.navigation.NavHostKMM
import com.rasel.introtokmm.android.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHostKMM(navController = navController)
}

@Composable
fun LaunchList(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    onItemClick: (RocketLaunch) -> Unit = {}
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getLaunches()
    }
    val uiState = viewModel.uiState
    if (uiState.isLoading) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(15.dp),
                text = uiState.error
            )
        }
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing),
            onRefresh = { viewModel.getLaunches(true) },
            modifier = modifier
        ) {
            LazyColumn {
                items(uiState.rocketList, key = { it.flightNumber }) { launch ->
                    RocketLaunchItem(rocketLaunch = launch) {
                        onItemClick(launch)
                    }
                }
            }
        }
    }
}

@Composable
fun RocketLaunchItem(
    rocketLaunch: RocketLaunch,
    onItemClick: () -> Unit = {}
) {
    val isSuccess = rocketLaunch.success == true
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .clickable { onItemClick() }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = "Mission Name: ${rocketLaunch.name}")
            val successString = buildAnnotatedString {
                append("Launch Status: ")
                withStyle(style = SpanStyle(color = if (isSuccess) Color.Green else Color.Red))  {
                    append(if (isSuccess) "Successful" else "Unsuccessful")
                }
            }
            Text(text = successString)
            Text(text = "Launch Date: ${rocketLaunch.dateUTC}")
            Text(text = "Launch Details: ${rocketLaunch.details}")
        }
    }
}
