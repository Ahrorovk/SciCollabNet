package com.ahrorovkspace.codebeyondearth.presentation.myApplicationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahrorovkspace.codebeyondearth.app.theme.PrimaryText
import com.ahrorovkspace.codebeyondearth.domain.myApplication.model.Result
import com.ahrorovkspace.codebeyondearth.presentation.components.CustomButton

@Composable
fun MyApplicationScreen(
    state: MyApplicationState,
    onEvent: (MyApplicationEvent) -> Unit
) {
    val selectedItems = remember { mutableStateListOf<Result>() }
    LazyColumn() {
        item {
            Spacer(modifier = Modifier.padding(10.dp))
            state.myApplicationRespState.response?.let {
                it.results.forEach { item ->
                    val isSelected = remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .width(140.dp)
                                .height(50.dp)
                                .background(PrimaryText)
                        ) {
                            Text(text = item.user.username)
                            Checkbox(
                                checked = isSelected.value,
                                onCheckedChange = {
                                    isSelected.value = it
                                    if (isSelected.value) {
                                        selectedItems.add(item)
                                    } else {
                                        selectedItems.remove(item)
                                    }
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomButton(text = "Approve", textSize = 16, isLoading = false) {
                        onEvent(MyApplicationEvent.OnSelectedResultsChange(selectedItems))
                    }
                }
            }
        }
    }
}