package org.lenatin.pulse.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lenatin.pulse.model.BottomTab
import org.lenatin.pulse.model.ShareTarget
import org.lenatin.pulse.model.Workout
import org.lenatin.pulse.state.PulseState
import org.lenatin.pulse.state.rememberPulseState
import org.lenatin.pulse.ui.components.ChallengeCard
import org.lenatin.pulse.ui.components.DateSelectorBar
import org.lenatin.pulse.ui.components.PulseBottomBar
import org.lenatin.pulse.ui.components.ShareRow
import org.lenatin.pulse.ui.components.SwipeableWorkoutRow
import org.lenatin.pulse.util.minus
import org.lenatin.pulse.util.plus
import org.lenatin.pulse.util.today

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActvityScreen(
    modifier: Modifier = Modifier,
    state: PulseState = rememberPulseState(),
    onEditCurrentWorkout: () -> Unit = {},
    onAddWorkout: () -> Unit = {
        state.workouts.add(
            Workout(
                id = "w" + (state.workouts.size + 1),
                name = "New Exercise",
                targetReps = 20,
                doneReps = 0
            )
        )
    },
    onShare: (ShareTarget) -> Unit = { target ->
        // TODO: wire up to platform share
        println("Share weekly progress via $target")
    },
    onNavigate: (BottomTab) -> Unit = {},
) {
    var selectedTab by remember { mutableStateOf(state.selectedTab) }
    var currentDate by remember { mutableStateOf(state.date) }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Pulse", fontWeight = FontWeight.SemiBold)
                        }
                    },
                    actions = {
                        TextButton(onClick = onEditCurrentWorkout) { Text("Edit") }
                    }
                )
                DateSelectorBar(
                    date = currentDate,
                    isToday = currentDate == today(),
                    onPrev = { currentDate = currentDate.minus(1) },
                    onNext = { currentDate = currentDate.plus(1) },
                    onToday = { currentDate = today() }
                )
                HorizontalDivider()
            }
        },
        bottomBar = {
            PulseBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    state.selectedTab = tab
                    onNavigate(tab)
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Challenges section (if any)
            if (state.challenges.isNotEmpty()) {
                item { SectionHeader(title = "Challenges") }
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(state.challenges) { challenge ->
                            ChallengeCard(challenge)
                        }
                    }
                }
            }

            // Workouts list
            item { SectionHeader(title = "Workouts") }
            items(state.workouts, key = { it.id }) { w ->
                SwipeableWorkoutRow(
                    workout = w,
                    onSwipe = { delta ->
                        // Right swipe (+), left swipe (-)
                        val change = if (delta > 0) 1 else -1
                        w.doneReps = (w.doneReps + change).coerceIn(0, w.targetReps)
                    }
                )
            }

            // Add more button
            item {
                OutlinedButton(
                    onClick = onAddWorkout,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Add more")
                }
            }

            // Share weekly progress
            item { SectionHeader(title = "Share progress (this week)") }
            item { ShareRow(onShare) }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
}
