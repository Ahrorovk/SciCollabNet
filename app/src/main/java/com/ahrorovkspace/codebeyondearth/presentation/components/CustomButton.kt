package com.ahrorovkspace.codebeyondearth.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovkspace.codebeyondearth.app.theme.BLUE
import com.ahrorovkspace.codebeyondearth.app.theme.PrimaryText

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    textSize: Int,
    enabled: Boolean = true,
    color: Color = PrimaryText,
    isLoading: Boolean,
    height: Int = 50,
    width: Int = 140,
    onClick: () -> Unit,

    ) {
    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .height(height.dp)
            .width(width.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color, contentColor = Color.White),
        content = {
            if (!isLoading) {
                Text(
                    text = text,
                    fontSize = textSize.sp
                )
            } else {
                CircularProgressIndicator()
            }
        },
    )
}