package com.ahrorovkspace.codebeyondearth.presentation.profileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ahrorovkspace.codebeyondearth.R
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomButton
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomTextField

@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.nasalogolarge),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextField(
                    value = state.firstName,
                    hint = "First name",
                    onValueChange = {
                        onEvent(
                            ProfileEvent.OnFirstNameChange(
                                it
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextField(
                    value = state.secondName,
                    hint = "Last name",
                    onValueChange = {
                        onEvent(
                            ProfileEvent.OnSecondNameChange(
                                it
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextField(
                    value = state.username,
                    hint = "Username",
                    onValueChange = {
                    }, isAvailable = false
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextField(
                    value = state.email,
                    hint = "email",
                    onValueChange = {
                    },
                    isAvailable = false
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextField(
                    value = state.phone,
                    hint = "Phone",
                    onValueChange = {
                    },
                    keyboardType = KeyboardType.Number,
                    isAvailable = false
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomTextField(
                    value = state.description,
                    hint = "Description",
                    onValueChange = {
                        onEvent(
                            ProfileEvent.OnDescriptionChange(
                                it
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.padding(12.dp))

                CustomButton(
                    text = "Save",
                    textSize = 16,
                    isLoading = false
                ) {
                    onEvent(ProfileEvent.SaveChanges)
                }
            }
        }
    }
}