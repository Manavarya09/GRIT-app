package com.grit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.grit.ui.theme.GritBlack
import com.grit.ui.theme.GritWhite

@Composable
fun GritTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = GritBlack
        ),
        cursorBrush = SolidColor(GritBlack),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder.uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = GritBlack.copy(alpha = 0.4f)
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun GritOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = GritBlack
        ),
        cursorBrush = SolidColor(GritBlack),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                    )
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder.uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = GritBlack.copy(alpha = 0.4f)
                    )
                }
                innerTextField()
            }
        }
    )
}
