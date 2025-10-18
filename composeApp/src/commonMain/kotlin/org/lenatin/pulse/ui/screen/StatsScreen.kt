package org.lenatin.pulse.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lenatin.pulse.model.BottomTab
import org.lenatin.pulse.state.PulseState
import org.lenatin.pulse.state.rememberPulseState
import org.lenatin.pulse.ui.components.PulseBottomBar

enum class TimePeriod { Week, Month, Year }

data class WorkoutStats(
    val exerciseName: String,
    val lifetimeTotal: Int,
    val personalBest: Int
)

data class ChartDataPoint(
    val label: String,
    val value: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    state: PulseState = rememberPulseState(),
    onNavigate: (BottomTab) -> Unit = {}
) {
    var selectedPeriod by remember { mutableStateOf(TimePeriod.Week) }

    val workoutStats = remember {
        listOf(
            WorkoutStats("Back Squat", 1250, 50),
            WorkoutStats("Push-ups", 3420, 75),
            WorkoutStats("Plank", 2100, 180),
            WorkoutStats("Deadlift", 980, 45),
            WorkoutStats("Pull-ups", 1560, 30)
        )
    }
    var selectedTab by remember { mutableStateOf(state.selectedTab) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stats", fontWeight = FontWeight.SemiBold) }
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Time period selector
            item {
                TimePeriodSelector(
                    selectedPeriod = selectedPeriod,
                    onPeriodSelected = { selectedPeriod = it }
                )
            }

            // Chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Workouts Completed",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(16.dp))
                        WorkoutChart(
                            data = getChartData(selectedPeriod),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }
            }

            // Lifetime Stats Section
            item {
                Text(
                    "Lifetime Totals",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            items(workoutStats) { stat ->
                WorkoutStatCard(stat)
            }
        }
    }
}

@Composable
private fun TimePeriodSelector(
    selectedPeriod: TimePeriod,
    onPeriodSelected: (TimePeriod) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TimePeriod.entries.forEach { period ->
            FilterChip(
                selected = selectedPeriod == period,
                onClick = { onPeriodSelected(period) },
                label = { Text(period.name) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun WorkoutChart(
    data: List<ChartDataPoint>,
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { mutableStateOf(0f) }
    val progress by animateFloatAsState(
        targetValue = animatedProgress.value,
        animationSpec = tween(1000)
    )

    LaunchedEffect(data) {
        animatedProgress.value = 1f
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val padding = 40f
        val chartWidth = width - padding * 2
        val chartHeight = height - padding * 2

        if (data.isEmpty()) return@Canvas

        val maxValue = data.maxOf { it.value }
        val minValue = data.minOf { it.value }
        val valueRange = maxValue - minValue

        val stepX = chartWidth / (data.size - 1).coerceAtLeast(1)

        // Draw grid lines
        for (i in 0..4) {
            val y = padding + (chartHeight * i / 4)
            drawLine(
                color = Color.Gray.copy(alpha = 0.2f),
                start = Offset(padding, y),
                end = Offset(width - padding, y),
                strokeWidth = 1f
            )
        }

        // Draw gradient area under the line
        val path = Path()
        path.moveTo(padding, height - padding)

        data.forEachIndexed { index, point ->
            val x = padding + index * stepX
            val normalizedValue = if (valueRange > 0) {
                (point.value - minValue) / valueRange
            } else 0.5f
            val y = height - padding - (chartHeight * normalizedValue * progress)

            if (index == 0) {
                path.lineTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        path.lineTo(width - padding, height - padding)
        path.close()

        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(
                    primaryColor.copy(alpha = 0.3f),
                    primaryColor.copy(alpha = 0.05f)
                )
            )
        )

        // Draw the line
        val linePath = Path()
        data.forEachIndexed { index, point ->
            val x = padding + index * stepX
            val normalizedValue = if (valueRange > 0) {
                (point.value - minValue) / valueRange
            } else 0.5f
            val y = height - padding - (chartHeight * normalizedValue * progress)

            if (index == 0) {
                linePath.moveTo(x, y)
            } else {
                linePath.lineTo(x, y)
            }
        }

        drawPath(
            path = linePath,
            color = primaryColor,
            style = Stroke(width = 3f)
        )

        // Draw data points
        data.forEachIndexed { index, point ->
            val x = padding + index * stepX
            val normalizedValue = if (valueRange > 0) {
                (point.value - minValue) / valueRange
            } else 0.5f
            val y = height - padding - (chartHeight * normalizedValue * progress)

            drawCircle(
                color = surfaceColor,
                radius = 6f,
                center = Offset(x, y)
            )
            drawCircle(
                color = primaryColor,
                radius = 4f,
                center = Offset(x, y)
            )
        }
    }
}

@Composable
private fun WorkoutStatCard(stat: WorkoutStats) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stat.exerciseName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Total: ${stat.lifetimeTotal} reps",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Personal Best badge
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "PB",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        "${stat.personalBest}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

private fun getChartData(period: TimePeriod): List<ChartDataPoint> {
    return when (period) {
        TimePeriod.Week -> listOf(
            ChartDataPoint("Mon", 5f),
            ChartDataPoint("Tue", 8f),
            ChartDataPoint("Wed", 6f),
            ChartDataPoint("Thu", 10f),
            ChartDataPoint("Fri", 7f),
            ChartDataPoint("Sat", 12f),
            ChartDataPoint("Sun", 4f)
        )
        TimePeriod.Month -> listOf(
            ChartDataPoint("W1", 32f),
            ChartDataPoint("W2", 45f),
            ChartDataPoint("W3", 38f),
            ChartDataPoint("W4", 52f)
        )
        TimePeriod.Year -> listOf(
            ChartDataPoint("Jan", 120f),
            ChartDataPoint("Feb", 135f),
            ChartDataPoint("Mar", 145f),
            ChartDataPoint("Apr", 128f),
            ChartDataPoint("May", 160f),
            ChartDataPoint("Jun", 155f),
            ChartDataPoint("Jul", 142f),
            ChartDataPoint("Aug", 168f),
            ChartDataPoint("Sep", 175f),
            ChartDataPoint("Oct", 180f),
            ChartDataPoint("Nov", 165f),
            ChartDataPoint("Dec", 190f)
        )
    }
}