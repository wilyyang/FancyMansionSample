package com.cheesejuice.fancymansionsample.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.cheesejuice.fancymansionsample.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomAlertDialog(
    title: String = stringResource(id = R.string.alert_default_title),
    text:String = stringResource(id = R.string.alert_default_message),
    confirmAction: () -> Unit = { },
    dismissAction: () -> Unit = { }
) {
    AlertDialog(onDismissRequest = {},
        containerColor = Color.White,
        shape = RoundedCornerShape(3),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxWidth().padding(40.dp),
        title = {
            Text(text=title, fontSize = 20.sp)
        },
        text = {
            Text(text=text, fontSize = 15.sp)
        },
        confirmButton = {
            TextButton(onClick = confirmAction) {
                Text(text = stringResource(id = R.string.alert_confirm_button))
            }
        },
        dismissButton = {
            TextButton(onClick = dismissAction) {
                Text(text = stringResource(id = R.string.alert_dismiss_button))
            }
        }
    )
}


@Preview
@Composable
fun PreviewCustomAlertDialog() {
    CustomAlertDialog()
}