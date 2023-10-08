package com.ahrorovkspace.codebeyondearth.presentation.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovkspace.codebeyondearth.app.theme.Grey
import com.ahrorovkspace.codebeyondearth.app.theme.GreySecond

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    isAvailable: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        maxLines = 10,
        readOnly = !isAvailable,
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .width(250.dp),
        value = value,
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight(500),
            color = MaterialTheme.colors.onBackground,

            ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = onValueChange,
        placeholder = {
            Text(text = hint, fontSize = 18.sp)
        },
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            backgroundColor = GreySecond,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}