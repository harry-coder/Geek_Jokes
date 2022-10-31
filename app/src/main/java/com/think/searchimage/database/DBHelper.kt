/*
 * Copyright 2020 Appinventiv. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.think.searchimage.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.think.searchimage.database.dao.LocationDao
import com.think.searchimage.database.entity.LocationEntity
import javax.inject.Inject


@Database(
    entities = [LocationEntity::class ], version = 1, exportSchema = false
)   abstract class DBHelper : RoomDatabase() {

    abstract fun locationDao(): LocationDao

}