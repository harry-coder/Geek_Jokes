package com.think.searchimage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "T_JOKES")
class JokeEntity {

      @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int = 0

    @ColumnInfo(name="jokeName")
    var jokeName: String = ""



    @ColumnInfo(name="dateTime", )
    var dateTime: Date?=Date()



}