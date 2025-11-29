package org.lenatin.pulse.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
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
import org.lenatin.pulse.data.SharedDatabase
import org.lenatin.pulse.data.SharedDatabaseDaoImpl
import org.lenatin.pulse.model.BottomTab
import org.lenatin.pulse.state.PulseState
import org.lenatin.pulse.state.rememberPulseState
import org.lenatin.pulse.ui.components.PulseBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun PulseHomeScreen(
    modifier: Modifier = Modifier,
    sharedDatabase: SharedDatabase,
    onEditCurrentWorkout: () -> Unit = {},
    onNavigate: (BottomTab) -> Unit = {},
) {
    val dao = remember { SharedDatabaseDaoImpl(sharedDatabase) }
    val state = rememberPulseState(dao) // Create state here, not in parameters

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
                BottomTab.Activity -> ActvityScreen(sharedDatabase=sharedDatabase)
                BottomTab.Stats -> StatsScreen(sharedDatabase=sharedDatabase)
                BottomTab.Settings -> SettingsScreen(sharedDatabase=sharedDatabase)
            }
        }

    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
}
