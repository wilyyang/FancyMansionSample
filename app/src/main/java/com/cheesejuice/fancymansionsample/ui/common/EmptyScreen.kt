package com.cheesejuice.fancymansionsample.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheesejuice.fancymansionsample.R

@Composable
fun EmptyScreen(
    emptyText: String = stringResource(id = R.string.alert_not_found_file)
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.image_not_found_file),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(

            modifier = Modifier.padding(top = 40.dp),
            text = emptyText,
            fontSize = 14.sp)
    }

}


@Preview
@Composable
fun PreviewEmptyScreen() {
    EmptyScreen(stringResource(id = R.string.alert_not_found_file))
}