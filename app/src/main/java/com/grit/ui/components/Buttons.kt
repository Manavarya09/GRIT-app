package com.grit.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.grit.ui.theme.GritBlack
import com.grit.ui.theme.GritRed
import com.grit.ui.theme.GritWhite
import com.grit.ui.theme.GritYellow

@Composable
fun GritButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDestructive: Boolean = false,
    isSecondary: Boolean = false
) {
    val haptic = LocalHapticFeedback.current
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "scale"
    )

    val backgroundColor = when {
        isDestructive -> GritRed
        isSecondary -> GritYellow
        else -> GritBlack
    }

    val contentColor = when {
        isDestructive -> GritWhite
        isSecondary -> GritBlack
        else -> GritWhite
    }

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            isPressed = true
            onClick()
        },
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth()
            .scale(scale),
        enabled = enabled,
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = Color(0xFFCCCCCC),
            disabledContentColor = Color(0xFF888888)
        ),
        border = BorderStroke(4.dp, GritBlack),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun GritOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val haptic = LocalHapticFeedback.current
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "scale"
    )

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            isPressed = true
            onClick()
        },
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth()
            .scale(scale),
        enabled = enabled,
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GritWhite,
            contentColor = GritBlack
        ),
        border = BorderStroke(4.dp, GritBlack),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
