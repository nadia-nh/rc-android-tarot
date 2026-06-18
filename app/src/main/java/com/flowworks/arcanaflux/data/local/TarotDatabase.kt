package com.flowworks.arcanaflux.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [ReadingEntity::class, DrawnCardEntity::class, TarotCardEntity::class, DailyCardEntity::class],
    version = 5,
    exportSchema = false
)
abstract class TarotDatabase : RoomDatabase() {
    abstract fun tarotDao(): TarotDao

    companion object {
        @Volatile
        private var INSTANCE: TarotDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS tarot_cards (
                        name TEXT NOT NULL PRIMARY KEY,
                        uprightMeaning TEXT NOT NULL,
                        reversedMeaning TEXT NOT NULL,
                        suit TEXT NOT NULL,
                        rank TEXT
                    )
                """.trimIndent())
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE drawn_cards DROP COLUMN suit")
                db.execSQL("ALTER TABLE drawn_cards DROP COLUMN rank")
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create the new daily_readings table matching the DailyCardEntity with embedded DrawnCardEntity
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS daily_readings (
                        dailyId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        timestamp INTEGER NOT NULL, 
                        date TEXT NOT NULL, 
                        cardId INTEGER NOT NULL,
                        readingOwnerId INTEGER NOT NULL,
                        name TEXT NOT NULL, 
                        isReversed INTEGER NOT NULL
                    )
                """.trimIndent())

                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_daily_readings_date` ON `daily_readings` (`date`)")
            }
        }

        fun getDatabase(context: Context): TarotDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarotDatabase::class.java,
                    "arcana_flux_database"
                )
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3,
                        MIGRATION_4_5)
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}