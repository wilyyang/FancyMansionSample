package com.cheesejuice.fancymansionsample.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheesejuice.fancymansionsample.ui.theme.DividerDark
import com.cheesejuice.fancymansionsample.ui.theme.PurpleGreyFancy

@Composable
fun DividerRow(
    color: Color = DividerDark
) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = color
    )
}

@Composable
fun TypeText(
    color: Color = PurpleGreyFancy,
    text:String = ""
) {
    Text(
        modifier = Modifier.border(
            width = 1.dp,
            color = PurpleGreyFancy,
            shape = RoundedCornerShape(30)
        ).padding(2.dp),
        color = color,
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium
    )

}

@Preview
@Composable
fun PreviewCustomComponent() {
}