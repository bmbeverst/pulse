package org.lenatin.pulse.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.lenatin.pulse.model.BottomTab
import org.lenatin.pulse.model.ShareTarget
import org.lenatin.pulse.model.Workout
import org.lenatin.pulse.state.PulseState
import org.lenatin.pulse.state.rememberPulseState
import org.lenatin.pulse.ui.components.DateSelectorBar
import org.lenatin.pulse.ui.components.PulseBottomBar
import org.lenatin.pulse.util.minus
import org.lenatin.pulse.util.plus
import org.lenatin.pulse.util.today

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PulseHomeScreen(
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
        // Display screen based on selected item
        Box(
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                BottomTab.Activity -> ActvityScreen()
                BottomTab.Stats -> StatsScreen()
                BottomTab.Settings -> SettingsScreen()
            }
        }

    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
}
