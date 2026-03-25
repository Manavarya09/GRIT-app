package com.grit.ui.screens.focus

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.grit.ui.components.GritButton
import com.grit.ui.theme.GritBlack
import com.grit.ui.theme.GritRed
import com.grit.ui.theme.GritWhite
import com.grit.ui.theme.GritYellow

@Composable
fun FocusScreen(
    taskName: String,
    viewModel: FocusViewModel,
    onExit: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(taskName) {
        if (!uiState.isActive && uiState.sessionId == null) {
            viewModel.startSession(taskName)
        }
    }

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            onExit()
        }
    }

    BackHandler(enabled = uiState.isActive) {
        viewModel.requestExit()
    }

    if (uiState.showExitConfirmation) {
        ExitConfirmationDialog(
            onConfirm = {
                viewModel.confirmExit()
                onExit()
            },
            onDismiss = viewModel::dismissExitConfirmation
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GritBlack),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isActive) {
            ActiveSessionContent(
                taskName = uiState.taskName,
                elapsedSeconds = uiState.elapsedSeconds,
                onRequestExit = viewModel::requestExit,
                onComplete = viewModel::completeSession
            )
        } else {
            LoadingContent()
        }
    }
}

@Composable
private fun ActiveSessionContent(
    taskName: String,
    elapsedSeconds: Int,
    onRequestExit: () -> Unit,
    onComplete: () -> Unit
) {
    val pulseScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = androidx.compose.animation.core.tween(500),
        label = "pulse"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "FOCUS MODE",
                style = MaterialTheme.typography.titleLarge,
                color = GritRed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .border(6.dp, GritRed, RoundedCornerShape(0.dp))
                    .background(GritRed.copy(alpha = 0.1f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "DO NOT QUIT",
                    style = MaterialTheme.typography.labelLarge,
                    color = GritRed
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = taskName.uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                color = GritWhite,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .border(8.dp, GritWhite, RoundedCornerShape(0.dp))
                    .background(GritBlack)
                    .padding(32.dp)
            ) {
                Text(
                    text = formatTime(elapsedSeconds),
                    style = MaterialTheme.typography.displayLarge,
                    color = GritWhite,
                    modifier = Modifier.scale(pulseScale)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TAP ONLY WHEN DONE",
                style = MaterialTheme.typography.labelMedium,
                color = GritYellow
            )
            Spacer(modifier = Modifier.height(16.dp))
            GritButton(
                text = "COMPLETE SESSION",
                onClick = onComplete,
                isSecondary = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = onRequestExit
            ) {
                Text(
                    text = "I'M WEAK (EXIT)",
                    style = MaterialTheme.typography.labelMedium,
                    color = GritRed.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "INITIALIZING...",
            style = MaterialTheme.typography.headlineMedium,
            color = GritWhite
        )
    }
}

@Composable
private fun ExitConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "ARE YOU WEAK?",
                style = MaterialTheme.typography.headlineSmall,
                color = GritRed
            )
        },
        text = {
            Text(
                text = "You're about to give up. This will be recorded as a FAILURE. Are you sure?",
                style = MaterialTheme.typography.bodyLarge,
                color = GritBlack
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = "YES, I'M WEAK",
                    color = GritRed
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "NO, I GRIND",
                    color = GritBlack
                )
            }
        },
        containerColor = GritWhite
    )
}

private fun formatTime(totalSeconds: Int): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return if (hours > 0) {
        String.format("%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
