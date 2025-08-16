package com.mertswork.footyreserve.core.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mertswork.footyreserve.core.presentation.DarkBlue
import com.mertswork.footyreserve.ui.theme.Dimens
import com.mertswork.footyreserve.ui.theme.FootyReserveTheme
import footyreserve.composeapp.generated.resources.Res
import footyreserve.composeapp.generated.resources.get_started
import footyreserve.composeapp.generated.resources.login
import footyreserve.composeapp.generated.resources.test_splash
import footyreserve.composeapp.generated.resources.welcome_to_footy_reserve
import footyreserve.composeapp.generated.resources.when_everyone_pays
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun WelcomeScreen(
    loginClick : () -> Unit,
    registerClick : () -> Unit
){


    FootyReserveTheme {

    Box(
        modifier = Modifier
            .fillMaxSize()

    ){
        Image(
            painter = painterResource(   Res.drawable.test_splash),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = Dimens.HorizontalPadding,
                    end = Dimens.HorizontalPadding,
                    bottom = Dimens.HorizontalPadding)
        ){
            Text(
                text = stringResource(Res.string.welcome_to_footy_reserve) ,
                fontWeight = FontWeight.Bold,
                fontSize = Dimens.ExtraLargeText,
                color = Color.White
            )

            Spacer(
                modifier = Modifier
                    .height(height = 10.dp)
            )

            Text(
                text = stringResource(Res.string.when_everyone_pays) ,
                fontWeight = FontWeight.Normal,
                fontSize = Dimens.SubTitle,
                color = Color.White.copy(alpha = .7f),
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier
                    .height(height = 10.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        loginClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    shape = RoundedCornerShape(8.dp)

                ){
                    Text(
                        text = stringResource(Res.string.login) ,
                        fontWeight = FontWeight.Normal,
                        fontSize = Dimens.SubTitle,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(
                    modifier = Modifier
                        .width(width = 10.dp)
                )

                TextButton(
                    onClick = {
                        registerClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        ,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = DarkBlue.copy(alpha = 0.7f),
                        contentColor = Color.White
                    )

                ){
                    Text(
                        text = stringResource(Res.string.get_started) ,
                        fontWeight = FontWeight.Normal,
                        fontSize = Dimens.SubTitle,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }


            Spacer(
                modifier = Modifier
                    .height(height = 40.dp)
            )

        }
    }
    }

}