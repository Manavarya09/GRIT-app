package com.grit.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grit.ui.theme.GritBlack
import com.grit.ui.theme.GritRed
import com.grit.ui.theme.GritWhite
import com.grit.ui.theme.GritYellow

@Composable
fun GritCard(
    modifier: Modifier = Modifier,
    isWarning: Boolean = false,
    isFailure: Boolean = false,
    content: @Composable () -> Unit
) {
    val backgroundColor = when {
        isWarning -> GritYellow
        isFailure -> GritRed
        else -> GritWhite
    }

    val contentColor = when {
        isWarning -> GritBlack
        isFailure -> GritWhite
        else -> GritBlack
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        border = BorderStroke(6.dp, GritBlack)
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun StatBlock(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    isHighlighted: Boolean = false
) {
    GritCard(
        modifier = modifier,
        isWarning = isHighlighted
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.displaySmall,
                color = if (isHighlighted) GritBlack else GritBlack
            )
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = if (isHighlighted) GritBlack else GritBlack.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun FailureCard(
    reason: String,
    duration: String,
    timestamp: String,
    modifier: Modifier = Modifier
) {
    GritCard(
        modifier = modifier,
        isFailure = true
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "FAILURE RECORDED",
                style = MaterialTheme.typography.labelSmall,
                color = GritWhite.copy(alpha = 0.7f)
            )
            Text(
                text = reason,
                style = MaterialTheme.typography.titleMedium,
                color = GritWhite
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "DURATION: $duration",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GritWhite.copy(alpha = 0.8f)
                )
                Text(
                    text = timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = GritWhite.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun SocialActivityCard(
    userName: String,
    action: String,
    duration: String,
    timestamp: String,
    isFailure: Boolean,
    modifier: Modifier = Modifier
) {
    GritCard(
        modifier = modifier,
        isFailure = isFailure
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = userName.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = GritBlack.copy(alpha = 0.7f)
                )
                Text(
                    text = action,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isFailure) GritWhite else GritBlack
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = duration,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isFailure) GritWhite else GritBlack
                )
                Text(
                    text = timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = GritBlack.copy(alpha = 0.5f)
                )
            }
        }
    }
}
