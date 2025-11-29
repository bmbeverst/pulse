package org.lenatin.pulse.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.lenatin.pulse.data.ShareDatabaseDao
import org.lenatin.pulse.model.BottomTab
import org.lenatin.pulse.model.Challenge
import org.lenatin.pulse.model.Workout


data class PulseState(
    val date: LocalDate,
    val challenges: List<Challenge>,
    val workouts: SnapshotStateList<Workout>,
    var selectedTab: BottomTab,
    val dao: ShareDatabaseDao // ✅ Fixed: was 'doa', now 'dao'
)

@Composable
fun rememberPulseState(dao: ShareDatabaseDao): PulseState {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val workouts = remember { mutableStateListOf<Workout>() }

    LaunchedEffect(dao) {
        // Collect all items into a list first
        val allWorkouts = mutableListOf<org.lenatin.pulse.shared.database.Workout>()
        dao.selectAllWorkouts().collect { singleWorkout -> // Single item
            allWorkouts.add(singleWorkout)
        }
        // Then update UI
        workouts.clear()
        workouts.addAll(allWorkouts.map {
            Workout(it.c_workout_wt, "Test", 1, 1) })
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

    return remember(dao) { // ✅ Added dao as key to remember
        PulseState(
            date = today,
            challenges = challenges,
            workouts = workouts,
            selectedTab = selectedTab.value,
            dao = dao // ✅ Fixed: was 'doa', now 'dao'
        )
    }
}