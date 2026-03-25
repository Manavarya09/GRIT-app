package com.grit.ui.screens.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grit.ui.components.GritCard
import com.grit.ui.components.GritOutlinedButton
import com.grit.ui.components.StatBlock
import com.grit.ui.theme.GritBlack
import com.grit.ui.theme.GritRed
import com.grit.ui.theme.GritWhite
import com.grit.ui.theme.GritYellow

@Composable
fun StatsScreen(
    viewModel: StatsViewModel,
    onBack: () -> Unit
) {
    val stats by viewModel.stats.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GritWhite)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(6.dp, GritBlack, RoundedCornerShape(0.dp))
                .background(GritBlack)
                .padding(16.dp)
        ) {
            Text(
                text = "STATS",
                style = MaterialTheme.typography.displaySmall,
                color = GritWhite
            )
            Text(
                text = "YOUR PERFORMANCE",
                style = MaterialTheme.typography.titleMedium,
                color = GritYellow
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatBlock(
                label = "TOTAL FOCUS",
                value = formatMinutes(stats.totalFocusTimeMinutes),
                modifier = Modifier.weight(1f),
                isHighlighted = true
            )
            StatBlock(
                label = "SESSIONS",
                value = stats.completedSessions.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatBlock(
                label = "TODAY FOCUS",
                value = formatMinutes(stats.todayFocusTime),
                modifier = Modifier.weight(1f)
            )
            StatBlock(
                label = "FAILURES",
                value = stats.distractionCount.toString(),
                modifier = Modifier.weight(1f),
                isHighlighted = stats.distractionCount > 0
            )
        }

        GritCard(isWarning = stats.todayFocusTime > 0) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "DAILY PERFORMANCE",
                    style = MaterialTheme.typography.labelMedium,
                    color = GritBlack.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                val percentage = if (stats.todayFocusTime > 0) {
                    when {
                        stats.todayFocusTime >= 120 -> "EXCELLENT"
                        stats.todayFocusTime >= 60 -> "GOOD"
                        stats.todayFocusTime >= 30 -> "OKAY"
                        else -> "NEEDS WORK"
                    }
                } else "NOT STARTED"
                Text(
                    text = percentage,
                    style = MaterialTheme.typography.headlineMedium,
                    color = when (percentage) {
                        "EXCELLENT" -> GritBlack
                        "GOOD" -> GritBlack
                        "OKAY" -> GritYellow
                        else -> GritRed
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                PerformanceBar(
                    focusMinutes = stats.todayFocusTime,
                    distractions = stats.todayDistractions
                )
            }
        }

        if (stats.distractionCount > 0) {
            GritCard(isFailure = true) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "FAILURE RATE",
                        style = MaterialTheme.typography.labelMedium,
                        color = GritWhite.copy(alpha = 0.7f)
                    )
                    val rate = (stats.distractionCount.toFloat() / 
                        (stats.completedSessions + stats.distractionCount) * 100).toInt()
                    Text(
                        text = "$rate%",
                        style = MaterialTheme.typography.displaySmall,
                        color = GritWhite
                    )
                    Text(
                        text = "OF SESSIONS ENDED IN FAILURE",
                        style = MaterialTheme.typography.labelSmall,
                        color = GritWhite.copy(alpha = 0.6f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GritOutlinedButton(
            text = "BACK",
            onClick = onBack
        )
    }
}

@Composable
private fun PerformanceBar(
    focusMinutes: Int,
    distractions: Int
) {
    val total = focusMinutes + (distractions * 15)
    val focusPercentage = if (total > 0) (focusMinutes.toFloat() / total * 100).toInt() else 0

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "FOCUS",
                style = MaterialTheme.typography.labelSmall,
                color = GritBlack
            )
            Text(
                text = "$focusPercentage%",
                style = MaterialTheme.typography.labelSmall,
                color = GritBlack
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .border(4.dp, GritBlack, RoundedCornerShape(0.dp))
        ) {
            if (focusPercentage > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(focusPercentage / 100f)
                        .background(GritBlack)
                )
            }
            if (focusPercentage < 100) {
                Box(
                    modifier = Modifier
                        .fillMaxSize((100 - focusPercentage) / 100f)
                        .background(GritRed)
                )
            }
        }
    }
}

private fun formatMinutes(minutes: Int): String {
    return when {
        minutes < 60 -> "${minutes}m"
        minutes % 60 == 0 -> "${minutes / 60}h"
        else -> "${minutes / 60}h ${minutes % 60}m"
    }
}
