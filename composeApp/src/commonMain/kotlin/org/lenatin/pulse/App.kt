package org.lenatin.pulse

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.lenatin.pulse.ui.screen.PulseHomeScreen

/**
 * Main entry point for the Pulse fitness tracking app.
 * This composable sets up the Material Theme and displays the home screen.
 */
@Composable
fun App() {
    MaterialTheme {
        PulseHomeScreen()
    }
}
