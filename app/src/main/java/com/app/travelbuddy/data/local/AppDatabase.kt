package com.app.travelbuddy.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.travelbuddy.data.local.dao.CountryDao
import com.app.travelbuddy.data.local.entity.*
import com.app.travelbuddy.data.local.entity.converter.Converters

@Database(
    entities = [
        Country::class,
        City::class,
        Wiki::class,
        WikiCurrency::class,
        WikiDemonym::class,
        WikiGeolocation::class,
        WikiImage::class,
        WikiImageDetail::class,
        WikiLanguage::class,
        WikiPopulation::class,
        WikiWebPage::class,
        WikiWebPageDetail::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "travelbuddy_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}