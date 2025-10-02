package org.lenatin.pulse.model

data class Workout(
    val id: String,
    val name: String,
    val targetReps: Int,
    var doneReps: Int,
    val imageUrl: String? = null // placeholder only; not loaded in this file
)
