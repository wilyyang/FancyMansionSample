package com.cheesejuice.fancymansionsample.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.cheesejuice.fancymansionsample.ui.theme.DividerDark

@Composable
fun DividerRow(
    color: Color = DividerDark
) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = color
    )
}


@Preview
@Composable
fun PreviewCustomComponent() {
}