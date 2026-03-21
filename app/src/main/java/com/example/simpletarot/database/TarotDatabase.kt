package com.example.simpletarot.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simpletarot.data.local.DrawnCardEntity
import com.example.simpletarot.data.local.ReadingEntity
import com.example.simpletarot.data.local.TarotDao

@Database(
    entities = [ReadingEntity::class, DrawnCardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TarotDatabase : RoomDatabase() {
    abstract fun tarotDao(): TarotDao

    companion object {
        @Volatile
        private var INSTANCE: TarotDatabase? = null

        fun getDatabase(context: Context): TarotDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarotDatabase::class.java,
                    "arcana_flux_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
