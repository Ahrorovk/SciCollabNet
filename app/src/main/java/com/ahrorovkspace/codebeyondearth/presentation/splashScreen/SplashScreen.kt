package com.ahrorovkspace.codebeyondearth.presentation.splashScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ahrorovkspace.codebeyondearth.presentation.components.ProgressIndicator

@Composable
fun SplashScreen(
    state: SplashState,
    onEvent: (SplashEvent) -> Unit
) {
    LaunchedEffect(key1 = state.stateRegistered) {
        if (state.stateRegistered)
            onEvent(SplashEvent.GoToAuthScreen)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "SplashScreen")
            ProgressIndicator(isProgress = !state.stateRegistered)
        }
    }
}