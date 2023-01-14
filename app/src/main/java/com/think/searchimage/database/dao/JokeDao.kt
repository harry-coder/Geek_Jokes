package com.think.searchimage.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.think.searchimage.model.Joke
import java.util.*

@Dao
interface JokeDao {

    @Query("INSERT OR REPLACE INTO t_jokes (jokeName,dateTime) VALUES (:jokeName,:dateTime)")
   suspend fun insert(jokeName: String,dateTime: Date)

    @Query("select * from (select * from t_jokes ORDER BY dateTime desc limit 10)var1 order by dateTime asc ")
    fun getJokesList():LiveData<List<Joke>>




}