@file:OptIn(ExperimentalMaterial3Api::class)
package com.mertswork.footyreserve.core.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mertswork.footyreserve.core.domain.model.UserRegistration
import com.mertswork.footyreserve.core.presentation.components.ProfileImagePicker
import com.mertswork.footyreserve.core.presentation.viewmodel.RegistrationViewModel
import com.mertswork.footyreserve.ui.theme.Dimens
import footyreserve.composeapp.generated.resources.Res
import footyreserve.composeapp.generated.resources.back
import footyreserve.composeapp.generated.resources.create_account
import footyreserve.composeapp.generated.resources.enter_your_firstname
import footyreserve.composeapp.generated.resources.go_back
import footyreserve.composeapp.generated.resources.just_a_few_quick
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.mertswork.footyreserve.core.presentation.components.CountryDropdown
import footyreserve.composeapp.generated.resources.enter_password
import footyreserve.composeapp.generated.resources.enter_your_email
import footyreserve.composeapp.generated.resources.enter_your_lastname
import footyreserve.composeapp.generated.resources.ic_eye
import footyreserve.composeapp.generated.resources.ic_eye_off
import footyreserve.composeapp.generated.resources.password_must_match
import footyreserve.composeapp.generated.resources.repeat_password
import footyreserve.composeapp.generated.resources.select_your_country


@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel,
    onNavigateBack: () -> Unit,
    onRegistrationSuccess: () -> Unit = {}
) {
    val uiState by registrationViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    var userInfo by remember { mutableStateOf(UserRegistration()) }

    // State for repeat password
    var repeatPassword by remember { mutableStateOf("") }
    val passwordsMatch = userInfo.password == repeatPassword

    //state for password visibility
    var passwordVisible by remember { mutableStateOf(false) }
    var repeatPasswordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }


    // Handle success navigation
    LaunchedEffect(uiState.registeredUser) {
        if (uiState.registeredUser != null) {
            onRegistrationSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                ,

        ) {
//            back
            Image(
                painter = painterResource(Res.drawable.back),
                contentDescription = stringResource(Res.string.go_back),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clickable { onNavigateBack() }
                    .size(Dimens.backButton)

            )

        }
        Spacer(
            modifier = Modifier
                .height(35.dp)
        )

//        create account text
        Text(
            text = stringResource(Res.string.create_account),
            fontSize = Dimens.ExtraLargeText,
            fontWeight = FontWeight.Bold,
            color = Color.White

            )
        Spacer(
            modifier = Modifier
                .height(13.dp)
        )
//        subtitle text
        Text(
            text = stringResource(Res.string.just_a_few_quick),
            fontSize = Dimens.SubTitle,
            fontWeight = FontWeight.Light,
            color = Color.White,
            textAlign = TextAlign.Start,
            lineHeight = 20.sp
            )

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Profile Image Picker
            ProfileImagePicker(
                imageUri = userInfo.imageUri,
                onImageSelected = { uri ->
                    userInfo = userInfo.copy(imageUri = uri)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
            // Email
            OutlinedTextField(
                value = userInfo.email,
                onValueChange = { userInfo = userInfo.copy(email = it) },
                label = {
                    Text(
                        text = stringResource(Res.string.enter_your_email),
                        fontSize = Dimens.textInput,
                        color = Color.White
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = Dimens.textInput,
                    color = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            // First Name
            OutlinedTextField(
                value = userInfo.firstName,
                onValueChange = { userInfo = userInfo.copy(firstName = it) },
                label = {
                    Text(
                        text = stringResource(Res.string.enter_your_firstname),
                        fontSize = Dimens.textInput,
                        color = Color.White.copy(alpha = .7f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = Dimens.textInput,
                    color = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )

            )

            Spacer(modifier = Modifier.height(16.dp))

            // Last Name
            OutlinedTextField(
                value = userInfo.lastName,
                onValueChange = { userInfo = userInfo.copy(lastName = it) },
                label = {
                    Text(
                        text = stringResource(Res.string.enter_your_lastname),
                        fontSize = Dimens.textInput,
                        color = Color.White.copy(alpha = .7f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = Dimens.textInput,
                    color = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Country
            CountryDropdown(
                selectedCountry = userInfo.country,
                onCountrySelected = { userInfo = userInfo.copy(country = it) }
            )


            Spacer(modifier = Modifier.height(16.dp))


            // Password
            OutlinedTextField(
                value = userInfo.password,
                onValueChange = { userInfo = userInfo.copy(password = it) },
                label = {
                    Text(
                        text = stringResource(Res.string.enter_password),
                        fontSize = Dimens.textInput,
                        color = Color.White.copy(alpha = .7f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = Dimens.textInput,
                    color = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                trailingIcon = {
                    val image = if (passwordVisible)
                        painterResource(Res.drawable.ic_eye_off)
                    else
                        painterResource(Res.drawable.ic_eye)

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = image, contentDescription = null, tint = Color.White, modifier = Modifier.size(Dimens.eyeSize))
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Repeat Password
            OutlinedTextField(
                value = repeatPassword,
                onValueChange = {
                    repeatPassword = it
                    passwordError = it.isNotEmpty() && it != userInfo.password
                },
                label = {
                    Text(
                        text = stringResource(Res.string.repeat_password),
                        fontSize = Dimens.textInput,
                        color = Color.White.copy(alpha = .7f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (repeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = Dimens.textInput,
                    color = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                trailingIcon = {
                    val image = if (repeatPasswordVisible)
                        painterResource(Res.drawable.ic_eye_off)
                    else
                        painterResource(Res.drawable.ic_eye)

                    IconButton(onClick = { repeatPasswordVisible = !repeatPasswordVisible }) {
                        Icon(painter = image, contentDescription = null, tint = Color.White, modifier = Modifier.size(Dimens.eyeSize))
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = if (passwordError) Color.Red else Color.White,
                    unfocusedIndicatorColor = if (passwordError) Color.Red.copy(alpha = 0.7f) else Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

// Error text
            if (passwordError) {
                Text(
                    text = "Passwords do not match",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
//            // Password
//            OutlinedTextField(
//                value = userInfo.password,
//                onValueChange = { userInfo = userInfo.copy(password = it) },
//                label = {
//                    Text(
//                        text = stringResource(Res.string.enter_password),
//                        fontSize = Dimens.textInput,
//                        color = Color.White.copy(alpha = .7f)
//                    )
//                },
//                modifier = Modifier.fillMaxWidth(),
//                visualTransformation = PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                singleLine = true,
//                textStyle = TextStyle(
//                    fontSize = Dimens.textInput,
//                    color = Color.White
//                ),
//                shape = RoundedCornerShape(8.dp),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.White,
//                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
//                    cursorColor = Color.White,
//                    focusedLabelColor = Color.White,
//                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
//                    focusedTextColor = Color.White,
//                    unfocusedTextColor = Color.White
//                )
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//
//            // Repeat Password
//            OutlinedTextField(
//                value = repeatPassword,
//                onValueChange = { repeatPassword = it },
//                label = {
//                    Text(
//                        text = stringResource(Res.string.repeat_password),
//                        fontSize = Dimens.textInput,
//                        color = Color.White.copy(alpha = .7f)
//                    )
//                },
//                modifier = Modifier.fillMaxWidth(),
//                visualTransformation = PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                singleLine = true,
//                textStyle = TextStyle(
//                    fontSize = Dimens.textInput,
//                    color = Color.White
//                ),
//                shape = RoundedCornerShape(8.dp),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    focusedIndicatorColor = if (!passwordsMatch && repeatPassword.isNotEmpty()) Color.Red else Color.White,
//                    unfocusedIndicatorColor = if (!passwordsMatch && repeatPassword.isNotEmpty()) Color.Red else Color.White.copy(alpha = 0.5f),
//                    cursorColor = Color.White,
//                    focusedLabelColor = Color.White,
//                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
//                    focusedTextColor = Color.White,
//                    unfocusedTextColor = Color.White
//                )
//            )
//
//            // Error message if mismatch
//            if (!passwordsMatch && repeatPassword.isNotEmpty()) {
//                Text(
//                    text = stringResource(Res.string.password_must_match),
//                    color = Color.Red,
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
//                )
//            }


            Spacer(modifier = Modifier.height(32.dp))





            // Register Button
            Button(
                onClick = {
                    scope.launch {
                        registrationViewModel.registerUser(userInfo)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading,
                shape = RoundedCornerShape(12.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = "Create Account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Error Message
            uiState.error?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            // Success Message
            uiState.successMessage?.let { message ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    color = Color.Green,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

