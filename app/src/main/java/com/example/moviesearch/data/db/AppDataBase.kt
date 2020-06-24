package com.example.moviesearch.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesearch.data.db.entities.User
import com.example.moviesearch.data.model.Movie
import com.example.moviesearch.data.model.MoviewDetails

@Database(entities = [Movie::class, MoviewDetails::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getMovieDao() : MovieDao

    companion object {

        @Volatile
        private var instance : AppDataBase ? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

                instance?:buildDatabase(context).also {
                    instance = it
                }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "MyCoroutineDb.db"
        ).build()
    }

}