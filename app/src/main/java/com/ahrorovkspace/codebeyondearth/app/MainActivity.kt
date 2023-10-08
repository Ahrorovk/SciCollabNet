package com.ahrorovkspace.codebeyondearth.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrorovkspace.codebeyondearth.app.navigation.Navigation
import com.ahrorovkspace.codebeyondearth.app.navigation.NavigationViewModel
import com.ahrorovkspace.codebeyondearth.app.theme.CodeBeyondEarthTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeBeyondEarthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<NavigationViewModel>()
                    val state = viewModel.state.collectAsState()
                    Navigation(
                        navState = state.value,
                        onEvent = { event ->
                            viewModel.onEvent(event)
                        }
                    )
                }
            }
        }
    }
}