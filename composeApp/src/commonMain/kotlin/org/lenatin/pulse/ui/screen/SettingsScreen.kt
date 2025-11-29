package org.lenatin.pulse.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lenatin.pulse.data.SharedDatabase
import org.lenatin.pulse.data.SharedDatabaseDaoImpl
import org.lenatin.pulse.model.BottomTab
import org.lenatin.pulse.state.PulseState
import org.lenatin.pulse.state.rememberPulseState
import org.lenatin.pulse.ui.components.PulseBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    sharedDatabase: SharedDatabase,
    onNavigate: (BottomTab) -> Unit = {}
) {

    val dao = remember { SharedDatabaseDaoImpl(sharedDatabase) }
    val state = rememberPulseState(dao) // Create state here, not in parameters

    var notificationsEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(state.selectedTab) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.SemiBold) }
            )
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Account Section
            item {
                Text(
                    "Account",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                SettingsItem(
                    icon = Icons.Default.Person,
                    title = "Profile",
                    subtitle = "Manage your profile information",
                    onClick = { /* TODO */ }
                )
            }
            item { HorizontalDivider() }

            // Preferences Section
            item {
                Text(
                    "Preferences",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                SettingsSwitchItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    subtitle = "Enable workout reminders",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
            }
            item {
                SettingsSwitchItem(
                    icon = Icons.AutoMirrored.Filled.VolumeUp,
                    title = "Sound",
                    subtitle = "Enable sound effects",
                    checked = soundEnabled,
                    onCheckedChange = { soundEnabled = it }
                )
            }
            item {
                SettingsSwitchItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Mode",
                    subtitle = "Enable dark theme",
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it }
                )
            }
            item { HorizontalDivider() }

            // Data & Privacy Section
            item {
                Text(
                    "Data & Privacy",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                SettingsItem(
                    icon = Icons.Default.CloudUpload,
                    title = "Backup & Sync",
                    subtitle = "Sync your data across devices",
                    onClick = { /* TODO */ }
                )
            }
            item {
                SettingsItem(
                    icon = Icons.Default.Security,
                    title = "Privacy",
                    subtitle = "Manage privacy settings",
                    onClick = { /* TODO */ }
                )
            }
            item { HorizontalDivider() }

            // About Section
            item {
                Text(
                    "About",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item {
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "About Pulse",
                    subtitle = "Version 1.0.0",
                    onClick = { /* TODO */ }
                )
            }
            item {
                SettingsItem(
                    icon =  Icons.AutoMirrored.Filled.Article,
                    title = "Terms & Conditions",
                    subtitle = "View terms of service",
                    onClick = { /* TODO */ }
                )
            }
            item {
                SettingsItem(
                    icon = Icons.Default.Policy,
                    title = "Privacy Policy",
                    subtitle = "View privacy policy",
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium)
            Text(
                subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )
    }
}

@Composable
private fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium)
            Text(
                subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}