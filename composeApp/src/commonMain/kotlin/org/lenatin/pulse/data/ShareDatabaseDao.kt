package org.lenatin.pulse.data

import kotlinx.coroutines.flow.Flow
import org.lenatin.pulse.shared.database.Workout

interface ShareDatabaseDao {

    suspend fun insertWorkout(workout: Workout)
    suspend fun selectAllWorkouts(): Flow<Workout>
    suspend fun deleteWorkout(id: String)
}