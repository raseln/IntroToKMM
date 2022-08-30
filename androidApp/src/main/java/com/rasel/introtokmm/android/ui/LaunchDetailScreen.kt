package com.rasel.introtokmm.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.rasel.introtokmm.android.R
import com.rasel.introtokmm.shared.SpaceXSDK
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailScreen(
    viewModel: LaunchDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    sdk: SpaceXSDK = get(),
    onBackPress: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.getDetail(sdk)
    }
    val uiState = viewModel.uiState
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Launch Details")
                },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                modifier = Modifier.shadow(elevation = 3.dp)
            )
        }
    ) {
        val modifier = Modifier
            .padding(it)
            .fillMaxSize()

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
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${uiState.error}")
            }
        } else {
            val details = uiState.detail
            val isSuccess = details?.success == true
            val links = details?.links
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if (links != null) {
                    links.patch.small?.let { imageUrl ->
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = imageUrl,
                                placeholder = painterResource(id = R.drawable.placeholder)
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                    }
                }
                Text(
                    text = "${details?.name}",
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                val successString = buildAnnotatedString {
                    append("Launch Status: ")
                    withStyle(style = SpanStyle(color = if (isSuccess) Color.Green else Color.Red))  {
                        append(if (isSuccess) "Successful" else "Unsuccessful")
                    }
                }
                Text(text = successString, fontSize = 20.sp)
                Text(text = "Launch Date: ${details?.dateUTC}", fontSize = 20.sp)
                Text(text = "Launch Details: ${details?.details}", fontSize = 20.sp)
                details?.failures?.firstOrNull()?.reason?.let { failureReason ->
                    Text(text = "Failure Reason: $failureReason", fontSize = 20.sp)
                }
                if (links != null) {
                    val uriHandler = LocalUriHandler.current
                    ClickableText(
                        text = buildAnnotatedString {
                            append("Read article about it")
                            addStringAnnotation(
                                tag = "article",
                                annotation = links.article,
                                start = 0,
                                end = 20
                            )
                        },
                        onClick = {
                            uriHandler.openUri(links.article)
                        },
                        style = TextStyle.Default.copy(color = Color.Blue, textDecoration = TextDecoration.Underline, fontSize = 20.sp)
                    )
                    links.webcast?.let { url ->
                        ClickableText(
                            text = buildAnnotatedString {
                                append("Watch video about it")
                                addStringAnnotation(
                                    tag = "webcast",
                                    annotation = url,
                                    start = 0,
                                    end = 20
                                )
                            },
                            onClick = {
                                uriHandler.openUri(url)
                            },
                            style = TextStyle.Default.copy(color = Color.Blue, textDecoration = TextDecoration.Underline, fontSize = 20.sp)
                        )
                    }
                    links.wikipedia?.let { url ->
                        ClickableText(
                            text = buildAnnotatedString {
                                append("Learn more...")
                                addStringAnnotation(
                                    tag = "wikipedia",
                                    annotation = url,
                                    start = 0,
                                    end = 13
                                )
                            },
                            onClick = {
                                uriHandler.openUri(url)
                            },
                            style = TextStyle.Default.copy(color = Color.Blue, textDecoration = TextDecoration.Underline, fontSize = 20.sp),
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
        }
    }
}