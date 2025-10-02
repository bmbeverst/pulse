package org.lenatin.pulse.model

data class Challenge(
    val id: String,
    val title: String,
    val progress: Float // 0f..1f
)
