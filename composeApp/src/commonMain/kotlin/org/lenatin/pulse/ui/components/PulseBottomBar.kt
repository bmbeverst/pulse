package org.lenatin.pulse.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import org.lenatin.pulse.model.BottomTab
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.filled.*

@Composable
fun PulseBottomBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == BottomTab.Activity,
            onClick = { onTabSelected(BottomTab.Activity) },
            icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Activity") },
            label = { Text("Activity") }
        )
        NavigationBarItem(
            selected = selectedTab == BottomTab.Stats,
            onClick = { onTabSelected(BottomTab.Stats) },
            icon = { Icon(Icons.Default.Insights, contentDescription = "Stats") },
            label = { Text("Stats") }
        )
        NavigationBarItem(
            selected = selectedTab == BottomTab.Settings,
            onClick = { onTabSelected(BottomTab.Settings) },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}
