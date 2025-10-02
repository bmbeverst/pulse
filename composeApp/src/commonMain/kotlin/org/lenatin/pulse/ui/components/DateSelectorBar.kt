package org.lenatin.pulse.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.lenatin.pulse.util.formatDate
import kotlinx.datetime.LocalDate

@Composable
internal fun DateSelectorBar(
    date: LocalDate,
    isToday: Boolean,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onToday: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPrev) { Icon(Icons.Default.ChevronLeft, contentDescription = "Previous day") }
        TextButton(onClick = onToday) {
            Text(if (isToday) "Today" else formatDate(date), fontWeight = FontWeight.Medium)
        }
        IconButton(onClick = onNext) { Icon(Icons.Default.ChevronRight, contentDescription = "Next day") }
    }
}
