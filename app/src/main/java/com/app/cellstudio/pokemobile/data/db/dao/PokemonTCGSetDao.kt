package com.app.cellstudio.pokemobile.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.cellstudio.pokemobile.data.db.entity.PokemonTCGSetDatabaseEntity
import io.reactivex.Single

@Dao
abstract class PokemonTCGSetDao {

    @Query("SELECT * FROM sets")
    abstract fun getSets(): Single<List<PokemonTCGSetDatabaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSets(sets: List<PokemonTCGSetDatabaseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSet(set: PokemonTCGSetDatabaseEntity)

    @Query("DELETE FROM sets")
    abstract fun clear()
}