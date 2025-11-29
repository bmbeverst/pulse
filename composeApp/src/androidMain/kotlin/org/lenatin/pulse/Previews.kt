package org.lenatin.pulse

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.flow.Flow
import org.lenatin.pulse.model.Challenge
import org.lenatin.pulse.model.Workout
import org.lenatin.pulse.ui.components.*
import org.lenatin.pulse.ui.screen.PulseHomeScreen
import org.lenatin.pulse.ui.screen.SettingsScreen
import org.lenatin.pulse.ui.screen.StatsScreen
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.lenatin.pulse.data.DatabaseDriverFactory
import org.lenatin.pulse.data.ShareDatabaseDao
import org.lenatin.pulse.data.SharedDatabase
import org.lenatin.pulse.ui.screen.ActvityScreen

/**
 * Android-specific preview functions with @Preview annotation.
 * These show up in Android Studio's preview pane.
 */

@Preview(name = "Workout Card", showBackground = true)
@Composable
fun PreviewWorkoutCard() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            SwipeableWorkoutRow(
                workout = Workout("w1", "Back Squat", targetReps = 25, doneReps = 10),
                onSwipe = {}
            )
        }
    }
}

@Preview(name = "Challenge Card", showBackground = true)
@Composable
fun PreviewChallengeCard() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ChallengeCard(
                challenge = Challenge("c1", "100 Push-ups Week", 0.6f)
            )
        }
    }
}

@Preview(name = "Date Selector", showBackground = true)
@Composable
fun PreviewDateSelector() {
    MaterialTheme {
        Surface {
            DateSelectorBar(
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                isToday = true,
                onPrev = {},
                onNext = {},
                onToday = {}
            )
        }
    }
}

@Preview(name = "Share Row", showBackground = true)
@Composable
fun PreviewShareRow() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ShareRow(onShare = {})
        }
    }
}

@Preview(name = "Home Screen", showBackground = true, showSystemUi = true)
@Composable
fun PreviewPulseHomeScreen() {
    val context = LocalContext.current
    val sharedDatabase = remember {
        SharedDatabase(DatabaseDriverFactory(context))
    }

    MaterialTheme {
        PulseHomeScreen(sharedDatabase = sharedDatabase)
    }
}

@Preview(name = "Full App", showBackground = true, showSystemUi = true)
@Composable
fun PreviewApp() {
    val context = LocalContext.current
    App(DatabaseDriverFactory(context))
}


@Preview(name = "Activity Screen", showBackground = true, showSystemUi = true)
@Composable
fun PreviewActivityScreen() {
    val context = LocalContext.current
    val sharedDatabase = remember {
        SharedDatabase(DatabaseDriverFactory(context))
    }

    MaterialTheme {
        ActvityScreen(sharedDatabase = sharedDatabase)
    }
}


@Preview(name = "Settings Screen", showBackground = true, showSystemUi = true)
@Composable
fun PreviewSettingsScreen() {
    val context = LocalContext.current
    val sharedDatabase = remember {
        SharedDatabase(DatabaseDriverFactory(context))
    }

    MaterialTheme {
        SettingsScreen(sharedDatabase = sharedDatabase)
    }
}

@Preview(name = "Stats Screen", showBackground = true, showSystemUi = true)
@Composable
fun PreviewStatsScreen() {
    val context = LocalContext.current
    val sharedDatabase = remember {
        SharedDatabase(DatabaseDriverFactory(context))
    }

    MaterialTheme {
        StatsScreen(sharedDatabase = sharedDatabase)
    }
}
