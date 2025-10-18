package org.lenatin.pulse.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.lenatin.pulse.model.BottomTab
import org.lenatin.pulse.model.Challenge
import org.lenatin.pulse.model.Workout


data class PulseState(
    val date: LocalDate,
    val challenges: List<Challenge>,
    val workouts: SnapshotStateList<Workout>,
    var selectedTab: BottomTab
)

@Composable
fun rememberPulseState(): PulseState {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val workouts = remember {
        mutableStateListOf(
            Workout("w1", "Back Squat", targetReps = 25, doneReps = 10),
            Workout("w2", "Bench Press", targetReps = 30, doneReps = 12),
            Workout("w3", "Pull-ups", targetReps = 40, doneReps = 20)
        )
    }
    val challenges = remember {
        mutableStateListOf(
            Challenge("c1", "100 Push-ups Week", 0.6f),
            Challenge("c2", "5 Runs in 7 Days", 0.4f)
        )
    }
    val selectedTab = remember {
        mutableStateOf(BottomTab.Activity)
    }
    return remember { PulseState(today, challenges, workouts, selectedTab.value) }
}
