package com.cheesejuice.fancymansionsample.ui.common

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.cheesejuice.fancymansionsample.R
import java.io.File

@Composable
fun SelectiveImage(
    modifier: Modifier = Modifier,
    imageFile: File?
) {
    Image(
        painter = imageFile?.let {
            val context = LocalContext.current
            val imageLoader = ImageLoader.Builder(context)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()

            rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = it).apply(block = {
                    size(Size.ORIGINAL)
                }).build(), imageLoader = imageLoader
            )
        } ?: run {
            painterResource(id = R.drawable.image_slide_no_image)
        },
        contentDescription = null,
        modifier = modifier
    )
}


@Preview
@Composable
fun PreviewSelectiveImage() {
    SelectiveImage(imageFile = null)
}