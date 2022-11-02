package com.cheesejuice.fancymansionsample.ui.common

import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cheesejuice.fancymansionsample.R

@Composable
fun CustomAlertDialog(
    title: String = stringResource(id = R.string.alert_default_title),
    text:String = stringResource(id = R.string.alert_default_message),
    confirmAction: () -> Unit = { },
    dismissAction: () -> Unit = { }
) {
    AlertDialog(onDismissRequest = {},
        title = {
            Text(text=title)
        },
        text = {
            Text(text=text)
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