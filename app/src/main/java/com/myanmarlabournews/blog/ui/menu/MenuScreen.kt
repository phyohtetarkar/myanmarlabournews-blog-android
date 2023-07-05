package com.myanmarlabournews.blog.ui.menu

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myanmarlabournews.blog.R
import com.myanmarlabournews.blog.ui.BaseAppBar
import com.myanmarlabournews.blog.ui.BottomNavigationBar
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun MenuScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            BaseAppBar()
        },
        bottomBar = { BottomNavigationBar(selectedIndex = 2) },

        ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            LabelText(stringResource(id = R.string.setting))
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(id = R.string.dark_mode),
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = false,
                            onCheckedChange = {
                                //context.findActivity()?.recreate()
                            },
                            modifier = Modifier.padding(0.dp),
                        )
                    }
                    Divider()
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(id = R.string.receive_notification),
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = true,
                            onCheckedChange = {},
                            modifier = Modifier.padding(0.dp),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LabelText(stringResource(id = R.string.more))
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse("https://m.me/myanmarlabournews")
                                context.startActivity(intent)
                            }
                            .height(56.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(id = R.string.send_message_to_us),
                            modifier = Modifier.weight(1f)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_moon_messenger_24dp),
                            contentDescription = "Messenger"
                        )
                    }
                    Divider()
                    Row(
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    putExtra(Intent.EXTRA_SUBJECT, "")
                                    putExtra(
                                        Intent.EXTRA_EMAIL,
                                        arrayOf(context.getString(R.string.contact_mail))
                                    )
                                    putExtra(Intent.EXTRA_TEXT, "")
                                    type = "text/plain"
                                }

                                context.startActivity(Intent.createChooser(intent, "Send Email"))
                            }
                            .height(56.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(id = R.string.contact_us))
                        Text(
                            stringResource(
                                id = R.string.contact_mail
                            ),
                            modifier = Modifier.weight(1f),
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.End
                        )
                    }
                    Divider()
                    Row(
                        modifier = Modifier
                            .clickable {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "#MyanmarLabourNews https://play.google.com/store/apps/details?id=com.mmlabour.news"
                                    )
                                    type = "text/plain"
                                }

                                val shareIntent = Intent.createChooser(sendIntent, null)
                                context.startActivity(shareIntent)
                            }
                            .height(56.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(id = R.string.share_app),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Divider()
                    Row(
                        modifier = Modifier
                            .clickable { }
                            .height(56.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(id = R.string.about), modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun LabelText(text: String) {
    Text(
        text,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Preview
@Composable
fun MenuScreenPreview() {
    MyanmarLabourNewsTheme {
        MenuScreen()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MenuScreenPreviewDark() {
    MyanmarLabourNewsTheme {
        MenuScreen()
    }
}