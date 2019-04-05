package com.app.cellstudio.pokemobile.data.db.dao


import androidx.room.*
import com.app.cellstudio.pokemobile.data.db.entity.PokemonTCGCardDatabaseEntity
import io.reactivex.Single


@Dao
abstract class PokemonTCGCardDao {

    @Transaction @Query("SELECT * FROM cards WHERE setCode=:setCode")
    abstract fun getCardsFromSet(setCode: String): Single<List<PokemonTCGCardDatabaseEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertCards(cards: List<PokemonTCGCardDatabaseEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertCard(card: PokemonTCGCardDatabaseEntity)

    @Query("DELETE FROM cards")
    abstract fun clear()
}