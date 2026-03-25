package com.grit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grit.data.local.dao.DistractionEventDao
import com.grit.data.local.dao.FocusSessionDao
import com.grit.data.local.dao.SocialActivityDao
import com.grit.data.local.entity.DistractionEventEntity
import com.grit.data.local.entity.FocusSessionEntity
import com.grit.data.local.entity.SocialActivityEntity

@Database(
    entities = [
        FocusSessionEntity::class,
        DistractionEventEntity::class,
        SocialActivityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GritDatabase : RoomDatabase() {
    abstract fun focusSessionDao(): FocusSessionDao
    abstract fun distractionEventDao(): DistractionEventDao
    abstract fun socialActivityDao(): SocialActivityDao

    companion object {
        @Volatile
        private var INSTANCE: GritDatabase? = null

        fun getDatabase(context: Context): GritDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GritDatabase::class.java,
                    "grit_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
