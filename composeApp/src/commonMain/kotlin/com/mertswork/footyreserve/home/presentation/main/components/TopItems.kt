package com.mertswork.footyreserve.home.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.mertswork.footyreserve.ui.theme.Dimens
import footyreserve.composeapp.generated.resources.Res
import footyreserve.composeapp.generated.resources.empty_pfp
import footyreserve.composeapp.generated.resources.greeting
import footyreserve.composeapp.generated.resources.pfp
import footyreserve.composeapp.generated.resources.search_icon
import footyreserve.composeapp.generated.resources.search_png
import footyreserve.composeapp.generated.resources.user_placeholder
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TopItem(
    name: String?,
    onSearchClick: () -> Unit,
    imageUrl: String?,
    modifier: Modifier = Modifier

){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.TopPadding),
        verticalAlignment = Alignment.CenterVertically
    ){



        //Load image into pfp
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }
        val painter = rememberAsyncImagePainter(
            model = imageUrl,

            onSuccess = {
                val width = it.painter.intrinsicSize.width
                val height = it.painter.intrinsicSize.height
                imageLoadResult = if(width > 1 && height > 1 ){
                    Result.success(it.painter)
                }else{
                    Result.failure(Exception("Invalid image size"))
                }
            },
            onError = {
                it.result.throwable.printStackTrace()
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )
        when(val result = imageLoadResult){
            null -> CircularProgressIndicator()
            else ->{
                Image(
                    painter = if (result.isSuccess) painter else{
                        painterResource(Res.drawable.empty_pfp)
                    },
                    contentDescription = stringResource(Res.string.pfp),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)

                )
            }
        }
//        Image loaded from image url provided in arg



//        text column for name and greeting
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name?: stringResource(Res.string.user_placeholder),
                fontWeight = FontWeight.Bold,
                fontSize = Dimens.Title,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = stringResource(Res.string.greeting),
                fontWeight = FontWeight.Medium,
                fontSize = Dimens.SubTitle,
                color = Color.White.copy(alpha = .7f)
            )
        }
//        end of user name and greeting


        Image(
            painter = painterResource(Res.drawable.search_png),
            contentDescription = stringResource(Res.string.search_icon),
            modifier = Modifier
                .size(60.dp)
                .clickable {
                    onSearchClick()
                },
            contentScale = ContentScale.Fit,

        )
    }
}