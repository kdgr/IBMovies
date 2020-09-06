package com.kaandogruer.ibmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaandogruer.ibmovies.data.local.dao.MovieDao
import com.kaandogruer.ibmovies.data.local.entity.Movie

@Database(entities = arrayOf(Movie::class),
        version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
