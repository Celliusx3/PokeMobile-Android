package com.app.cellstudio.pokemobile.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.cellstudio.pokemobile.BuildConfig
import com.app.cellstudio.pokemobile.data.db.converter.UriConverter
import com.app.cellstudio.pokemobile.data.db.dao.PokemonTCGCardDao
import com.app.cellstudio.pokemobile.data.db.dao.PokemonTCGSetDao
import com.app.cellstudio.pokemobile.data.db.entity.PokemonTCGCardDatabaseEntity
import com.app.cellstudio.pokemobile.data.db.entity.PokemonTCGSetDatabaseEntity

@Database(entities = [ PokemonTCGCardDatabaseEntity::class, PokemonTCGSetDatabaseEntity::class ], version = 1)
@TypeConverters(UriConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonTCGCardDao(): PokemonTCGCardDao
    abstract fun pokemonTCGSetDao(): PokemonTCGSetDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            if (INSTANCE == null) {
                val appDatabaseBuilder = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "pokemobile-db")

                if (BuildConfig.DEBUG)
                    appDatabaseBuilder.fallbackToDestructiveMigration()

                INSTANCE = appDatabaseBuilder
                        .build()
            }

            return INSTANCE!!
        }
    }

}
