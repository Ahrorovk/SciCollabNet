package com.ahrorovkspace.codebeyondearth.presentation.authorizationScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomButton
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomTextField

@Composable
fun AuthorizationScreen(
    state: AuthorizationState,
    onEvent: (AuthorizationEvent) -> Unit
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
                hint = "Login",
                onValueChange = {
                    onEvent(
                        AuthorizationEvent.OnLoginChange(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = state.password,
                hint = "Password",
                onValueChange = {
                    onEvent(
                        AuthorizationEvent.OnPasswordChange(
                            it
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomButton(
                text = "Enter",
                textSize = 16,
                isLoading = state.authorizationRespState.isLoading
            ) {
                onEvent(AuthorizationEvent.Authorization)
                if (state.authorizationRespState.response != null) {
                    onEvent(AuthorizationEvent.GoToMainScreen)
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
            Text(text = "Sign up", modifier = Modifier.clickable {
                onEvent(AuthorizationEvent.GoToSignUp)
            })
        }

    }
}