package com.ahrorovkspace.codebeyondearth.presentation.applicationScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovkspace.codebeyondearth.R
import com.ahrorovkspace.codebeyondearth.app.theme.GreySecond
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomButton
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomTextField

@Composable
fun ApplicationScreen(
    state: ApplicationState,
    onEvent: (ApplicationEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = R.drawable.nasalogolarge),
                contentDescription = "nasa"
            )
            Spacer(modifier = Modifier.padding(15.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .padding(horizontal = 30.dp)
                    .background(GreySecond),
                contentAlignment = Alignment.Center
            ) {
                Text(modifier=Modifier.padding(vertical = 40.dp),text = "Share your expertise and tell us \n how can you contribute our project")
            }

            Spacer(modifier = Modifier.padding(15.dp))

            CustomTextField(
                value = state.description,
                hint = "Description",
                onValueChange = { onEvent(ApplicationEvent.OnDescriptionChange(it)) }
            )
            Spacer(modifier = Modifier.padding(15.dp))
            CustomButton(
                text = "Apply",
                textSize = 14,
                isLoading = state.applicationRespState.isLoading
            ) {
                onEvent(ApplicationEvent.Apply)
                onEvent(ApplicationEvent.GoToMain)
            }
        }
    }
}