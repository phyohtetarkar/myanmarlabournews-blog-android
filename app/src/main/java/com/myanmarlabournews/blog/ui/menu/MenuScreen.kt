package com.myanmarlabournews.blog.ui.menu

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import com.myanmarlabournews.blog.ui.theme.MyanmarLabourNewsTheme

@Composable
fun MenuScreen(
    toggleDarkMode: (checked: Boolean) -> Unit,
    navigateToAbout: () -> Unit,
) {
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .wrapContentWidth()
                .widthIn(max = 600.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            LabelText(stringResource(id = R.string.setting))
            Card(
                shape = RoundedCornerShape(2.dp),
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
                            checked = !MaterialTheme.colors.isLight,
                            onCheckedChange = { checked ->
                                //context.findActivity()?.recreate()
                                toggleDarkMode(checked)
                            },
                            modifier = Modifier.padding(0.dp),
                        )
                    }
                    Divider(thickness = 0.5.dp)
                    Row(
                        modifier = Modifier
                            .clickable {
                                val intent = Intent()
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                } else {
                                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                                    intent.putExtra("app_package", context.packageName)
                                    intent.putExtra("app_uid", context.applicationInfo.uid)
                                }

                                context.startActivity(intent)
                            }
                            .padding(horizontal = 16.dp)
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.notifications),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LabelText(stringResource(id = R.string.more))
            Card(
                shape = RoundedCornerShape(2.dp),
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
                    Divider(thickness = 0.5.dp)
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

                                context.startActivity(
                                    Intent.createChooser(
                                        intent,
                                        "Send Email"
                                    )
                                )
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
                    Divider(thickness = 0.5.dp)
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
                    Divider(thickness = 0.5.dp)
                    Row(
                        modifier = Modifier
                            .clickable(
                                onClick = navigateToAbout
                            )
                            .height(56.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(id = R.string.about),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
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
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MenuScreenPreview() {
    MyanmarLabourNewsTheme {
        Surface {
            MenuScreen(
                toggleDarkMode = {},
                navigateToAbout = {}
            )
        }
    }
}