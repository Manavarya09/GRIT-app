package com.grit.ui.screens.home

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grit.ui.components.GritButton
import com.grit.ui.components.GritCard
import com.grit.ui.components.GritOutlinedTextField
import com.grit.ui.components.StatBlock
import com.grit.ui.theme.GritBlack
import com.grit.ui.theme.GritRed
import com.grit.ui.theme.GritWhite
import com.grit.ui.theme.GritYellow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onStartFocus: (String) -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToFailures: () -> Unit,
    onNavigateToSocial: () -> Unit
) {
    val stats by viewModel.stats.collectAsState()
    val taskName by viewModel.taskName.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.seedSocialData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GritWhite)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Header()

        StatsSection(
            todayFocus = stats.todayFocusTime,
            todayDistractions = stats.todayDistractions,
            totalFocus = stats.totalFocusTimeMinutes
        )

        TaskInputSection(
            taskName = taskName,
            onTaskNameChange = viewModel::updateTaskName,
            onStartFocus = { onStartFocus(taskName) }
        )

        QuickActions(
            onStatsClick = onNavigateToStats,
            onFailuresClick = onNavigateToFailures,
            onSocialClick = onNavigateToSocial
        )

        MotivationalMessage()
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(6.dp, GritBlack, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(GritYellow)
            .padding(16.dp)
    ) {
        Text(
            text = "GRIT",
            style = MaterialTheme.typography.displayMedium,
            color = GritBlack
        )
        Text(
            text = "FOCUS OR FAIL",
            style = MaterialTheme.typography.titleMedium,
            color = GritBlack.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun StatsSection(
    todayFocus: Int,
    todayDistractions: Int,
    totalFocus: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatBlock(
            label = "TODAY",
            value = formatMinutes(todayFocus),
            modifier = Modifier.weight(1f),
            isHighlighted = true
        )
        StatBlock(
            label = "TOTAL",
            value = formatMinutes(totalFocus),
            modifier = Modifier.weight(1f)
        )
    }

    GritCard(isFailure = todayDistractions > 0) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DISTRACTIONS TODAY",
                style = MaterialTheme.typography.titleMedium,
                color = GritBlack
            )
            Text(
                text = todayDistractions.toString(),
                style = MaterialTheme.typography.displaySmall,
                color = if (todayDistractions > 0) GritRed else GritBlack
            )
        }
    }
}

@Composable
private fun TaskInputSection(
    taskName: String,
    onTaskNameChange: (String) -> Unit,
    onStartFocus: () -> Unit
) {
    GritCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "WHAT DO YOU NEED TO FOCUS ON?",
                style = MaterialTheme.typography.labelMedium,
                color = GritBlack.copy(alpha = 0.6f)
            )
            GritOutlinedTextField(
                value = taskName,
                onValueChange = onTaskNameChange,
                placeholder = "Enter task name..."
            )
            GritButton(
                text = "START FOCUS SESSION",
                onClick = onStartFocus,
                enabled = taskName.isNotBlank()
            )
        }
    }
}

@Composable
private fun QuickActions(
    onStatsClick: () -> Unit,
    onFailuresClick: () -> Unit,
    onSocialClick: () -> Unit
) {
    Text(
        text = "QUICK ACCESS",
        style = MaterialTheme.typography.labelMedium,
        color = GritBlack.copy(alpha = 0.6f)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GritButton(
            text = "STATS",
            onClick = onStatsClick,
            modifier = Modifier.weight(1f),
            isSecondary = true
        )
        GritButton(
            text = "FAILURES",
            onClick = onFailuresClick,
            modifier = Modifier.weight(1f),
            isDestructive = true
        )
    }
    GritButton(
        text = "COMMUNITY ACTIVITY",
        onClick = onSocialClick,
        modifier = Modifier.fillMaxWidth(),
        isSecondary = true
    )
}

@Composable
private fun MotivationalMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(4.dp, GritBlack, androidx.compose.foundation.shape.RoundedCornerShape(0.dp))
            .background(GritBlack)
            .padding(16.dp)
    ) {
        Text(
            text = "NO EXCUSES. NO ESCAPES. JUST GRIT.",
            style = MaterialTheme.typography.titleMedium,
            color = GritWhite,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private fun formatMinutes(minutes: Int): String {
    return when {
        minutes < 60 -> "${minutes}m"
        minutes % 60 == 0 -> "${minutes / 60}h"
        else -> "${minutes / 60}h ${minutes % 60}m"
    }
}
