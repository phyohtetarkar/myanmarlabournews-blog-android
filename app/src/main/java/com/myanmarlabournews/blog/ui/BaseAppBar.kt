package com.myanmarlabournews.blog.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun BaseAppBar() {

    val logo = if (MaterialTheme.colors.isLight) {
        painterResource(id = R.drawable.logo_action_bar)
    } else {
        painterResource(id = R.drawable.logo192)
    }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = logo,
                    contentDescription = "Logo",
                    modifier = Modifier.width(36.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(
                        Font(R.font.merriweather_bold)
                    )
                )
            }
        },
    )
}

@Preview
@Composable
fun BaseAppBarPreview() {
    MyanmarLabourNewsTheme {
        BaseAppBar()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BaseAppBarPreviewNight() {
    MyanmarLabourNewsTheme {
        BaseAppBar()
    }
}