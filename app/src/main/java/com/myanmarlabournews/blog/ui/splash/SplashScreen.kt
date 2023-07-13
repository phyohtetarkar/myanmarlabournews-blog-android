package com.myanmarlabournews.blog.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.myanmarlabournews.blog.BuildConfig
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme
import com.myanmarlabournews.blog.ui.theme.RedCustom
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {

    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(true) {
        delay(3000)
        currentOnTimeout()
    }

    ConstraintLayout(
        modifier = Modifier
            .background(RedCustom)
            .fillMaxSize()
    ) {

        val (image, version) = createRefs()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 64.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_inner_full),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(140.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.logot_text),
                contentDescription = "Logo Text",
                modifier = Modifier
                    .width(180.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .constrainAs(version) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        ) {
            Text(
                text = stringResource(id = R.string.version_text),
                color = Color.White
            )
            Text(
                text = BuildConfig.VERSION_NAME,
                color = Color.White
            )
        }

    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    MyanmarLabourNewsTheme {
        SplashScreen(
            onTimeout = {}
        )
    }
}