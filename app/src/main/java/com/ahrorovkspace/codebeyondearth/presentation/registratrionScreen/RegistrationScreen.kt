package com.ahrorovkspace.codebeyondearth.presentation.registratrionScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomButton
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomTextField

@Composable
fun RegistrationScreen(
    state: RegistrationState,
    onEvent: (RegistrationEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Authorization",
                fontSize = 23.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.username,
                hint = "username",
                onValueChange = {
                    onEvent(
                        RegistrationEvent.OnUsernameChange(
                            it
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.email,
                hint = "email",
                onValueChange = {
                    onEvent(
                        RegistrationEvent.OnEmailChange(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.password,
                hint = "password",
                onValueChange = {
                    onEvent(
                        RegistrationEvent.OnPasswordChange(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.passwordConfirm,
                hint = "please confirm password",
                onValueChange = {
                    onEvent(
                        RegistrationEvent.OnPasswordConfirmChange(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomButton(
                text = "Enter",
                textSize = 16,
                isLoading = state.registrationRespState.isLoading
            ) {
                onEvent(RegistrationEvent.Registration)
                if (state.registrationRespState.response != null) {
                    onEvent(RegistrationEvent.GoToMainScreen)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 15.dp), contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Sign in",
                modifier = Modifier.clickable { onEvent(RegistrationEvent.GoToSignUp) })
        }
    }
}