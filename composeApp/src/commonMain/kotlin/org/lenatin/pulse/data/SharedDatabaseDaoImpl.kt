package org.lenatin.pulse.data

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.lenatin.pulse.shared.database.Workout

class SharedDatabaseDaoImpl(private val sharedDatabase: SharedDatabase): ShareDatabaseDao {

    override suspend fun insertWorkout(workout: Workout) {
        sharedDatabase { database ->
            database.workoutsQueries.insertWorkout(
                c_workout_wt = workout.c_workout_wt,
                d_date_wt = workout.d_date_wt,
                i_duration_minutes_wt = workout.i_duration_minutes_wt,
                t_notes_wt = workout.t_notes_wt,
                b_achieve_wt = workout.b_achieve_wt
            )
        }
    }
//
//    override suspend fun selectAllWorkouts(): List<Workout> {
//        sharedDatabase { database ->
//            database.workoutsQueries.selectAllWorkouts().executeAsList()
//        }
//    }

    override suspend fun selectAllWorkouts(): Flow<Workout> {
        return sharedDatabase { database ->
            database.workoutsQueries.selectAllWorkouts().asFlow().mapToOneNotNull(Dispatchers.Main.immediate)
        }
    }


    override suspend fun deleteWorkout(id: String) {
        sharedDatabase { database ->
            database.workoutsQueries.deleteWorkout(id)
        }
    }
}