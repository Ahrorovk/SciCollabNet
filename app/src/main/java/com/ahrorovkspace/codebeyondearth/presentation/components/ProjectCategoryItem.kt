package com.ahrorovkspace.codebeyondearth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahrorovkspace.codebeyondearth.app.theme.Grey

@Composable
fun ProjectCategoryItem(
    name: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Grey)
    ) {
        Text(
            modifier = Modifier
                .padding(3.dp),
            text = name, color = Color(0x75000000)
        )
    }
}