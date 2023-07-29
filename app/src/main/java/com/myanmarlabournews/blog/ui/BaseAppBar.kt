package com.myanmarlabournews.blog.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.myanmarlabournews.blog.model.Post
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun BaseAppBar(
    title: String = stringResource(id = R.string.app_name),
    locale: Post.Lang = Post.Lang.MM,
    showLogo: Boolean = true,
    showToggleLang: Boolean = false,
    toggleLang: (lang: Post.Lang) -> Unit = {}
) {

    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (showLogo) {
                    Image(
                        painter = if (MaterialTheme.colors.isLight) {
                            painterResource(id = R.drawable.logo_action_bar)
                        } else {
                            painterResource(id = R.drawable.logo192)
                        },
                        contentDescription = "Logo",
                        modifier = Modifier.width(36.dp),
                    )
//                    Box(
//                        modifier = Modifier
//                            .size(24.dp)
//                            .clip(RoundedCornerShape(4.dp))
//                            .background(Color.LightGray),
//                    )

                    Spacer(modifier = Modifier.padding(6.dp))
                }
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(
                        Font(R.font.merriweather_bold)
                    )
                )

            }
        },
        actions = {
            if (showToggleLang) {
                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.BottomEnd)
                ) {
                    TextButton(onClick = { expanded = true }) {
                        Text(
                            text = locale.name,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Icon(
                            Icons.Rounded.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                toggleLang(Post.Lang.MM)
                            },
                        ) {
                            Text("Myanmar")
                        }
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                toggleLang(Post.Lang.EN)
                            },
                        ) {
                            Text("English")
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BaseAppBarPreview() {
    MyanmarLabourNewsTheme {
        BaseAppBar()
    }
}