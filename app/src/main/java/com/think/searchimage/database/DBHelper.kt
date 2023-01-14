
package com.think.searchimage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.think.searchimage.converter.TimeStampConverter
import com.think.searchimage.database.dao.JokeDao
import com.think.searchimage.database.entity.JokeEntity

@TypeConverters(TimeStampConverter::class)
@Database(entities = [JokeEntity::class ], version = 1, exportSchema = false,
)
abstract class DBHelper : RoomDatabase() {

    abstract fun jokeDao(): JokeDao

}