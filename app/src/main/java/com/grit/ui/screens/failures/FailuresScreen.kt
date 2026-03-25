package com.grit.ui.screens.failures

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grit.ui.components.FailureCard
import com.grit.ui.components.GritOutlinedButton
import com.grit.ui.theme.GritBlack
import com.grit.ui.theme.GritRed
import com.grit.ui.theme.GritWhite
import com.grit.ui.theme.GritYellow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FailuresScreen(
    viewModel: FailuresViewModel,
    onBack: () -> Unit
) {
    val distractions by viewModel.distractions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GritWhite)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(6.dp, GritRed, RoundedCornerShape(0.dp))
                .background(GritRed)
                .padding(16.dp)
        ) {
            Text(
                text = "FAILURES",
                style = MaterialTheme.typography.displaySmall,
                color = GritWhite
            )
            Text(
                text = "RECORD OF YOUR WEAKNESS",
                style = MaterialTheme.typography.titleMedium,
                color = GritBlack
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (distractions.isEmpty()) {
            EmptyFailuresState()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(distractions) { distraction ->
                    FailureCard(
                        reason = distraction.reason,
                        duration = "${distraction.durationBeforeDistraction} mins",
                        timestamp = formatTimestamp(distraction.timestamp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        GritOutlinedButton(
            text = "BACK",
            onClick = onBack
        )
    }
}

@Composable
private fun EmptyFailuresState() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(4.dp, GritBlack, RoundedCornerShape(0.dp))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NO FAILURES YET",
                style = MaterialTheme.typography.headlineSmall,
                color = GritBlack
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "OR YOU HAVEN'T FAILED",
                style = MaterialTheme.typography.titleMedium,
                color = GritBlack.copy(alpha = 0.6f)
            )
            Text(
                text = "YET...",
                style = MaterialTheme.typography.titleMedium,
                color = GritRed
            )
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
