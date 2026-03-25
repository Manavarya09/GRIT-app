package com.grit.ui.screens.social

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.grit.ui.components.GritOutlinedButton
import com.grit.ui.components.SocialActivityCard
import com.grit.ui.theme.GritBlack
import com.grit.ui.theme.GritWhite
import com.grit.ui.theme.GritYellow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SocialScreen(
    viewModel: SocialViewModel,
    onBack: () -> Unit
) {
    val activities by viewModel.activities.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GritWhite)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(6.dp, GritBlack, RoundedCornerShape(0.dp))
                .background(GritYellow)
                .padding(16.dp)
        ) {
            Text(
                text = "COMMUNITY",
                style = MaterialTheme.typography.displaySmall,
                color = GritBlack
            )
            Text(
                text = "SEE WHAT OTHERS ARE DOING",
                style = MaterialTheme.typography.titleMedium,
                color = GritBlack.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (activities.isEmpty()) {
            EmptySocialState()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(activities) { activity ->
                    SocialActivityCard(
                        userName = activity.userName,
                        action = activity.action,
                        duration = "${activity.duration}m",
                        timestamp = formatTimestamp(activity.timestamp),
                        isFailure = activity.isFailure
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
private fun EmptySocialState() {
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
                text = "NO ACTIVITY YET",
                style = MaterialTheme.typography.headlineSmall,
                color = GritBlack
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "BE THE FIRST TO GRIND",
                style = MaterialTheme.typography.titleMedium,
                color = GritBlack.copy(alpha = 0.6f)
            )
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    return when {
        diff < 3600000 -> "${diff / 60000}m ago"
        diff < 86400000 -> "${diff / 3600000}h ago"
        else -> {
            val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}
