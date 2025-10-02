package org.lenatin.pulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.lenatin.pulse.model.Workout
import kotlin.math.abs

@Composable
internal fun SwipeableWorkoutRow(
    workout: Workout,
    onSwipe: (deltaDirection: Int) -> Unit
) {
    var dragOffset by remember { mutableStateOf(0f) }
    val thresholdPx = 40f

    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(workout.id) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset += dragAmount
                    },
                    onDragEnd = {
                        if (abs(dragOffset) > thresholdPx) {
                            onSwipe(dragOffset.toInt())
                        }
                        dragOffset = 0f
                    },
                    onDragCancel = { dragOffset = 0f }
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFECECEC)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Image, contentDescription = null, tint = Color(0xFF9E9E9E))
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(workout.name, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.width(8.dp))
                    AssistChip(amount = "+/- 1 rep", showBorder = dragOffset != 0f)
                }
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (workout.doneReps.toFloat() / workout.targetReps.coerceAtLeast(1)) },
                    modifier = Modifier.fillMaxWidth().height(8.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${workout.doneReps} / ${workout.targetReps} reps",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF666666)
                )
            }

            Spacer(Modifier.width(12.dp))

            // Quick + button as alternative input
            FilledTonalButton(onClick = {
                workout.doneReps = (workout.doneReps + 1).coerceAtMost(workout.targetReps)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add rep")
            }
        }
    }
}

@Composable
private fun AssistChip(amount: String, showBorder: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(Color(0xFFF7F7F7))
            .then(if (showBorder) Modifier.border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(999.dp)) else Modifier)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) { Text(amount, style = MaterialTheme.typography.labelSmall, color = Color(0xFF666666)) }
}
