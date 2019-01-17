package com.app.cellstudio.pokemobile.data.mapper

import com.app.cellstudio.pokemobile.domain.entity.PokemonTCGCard
import com.app.cellstudio.pokemobile.data.entity.PokemonTCGCardDataResponseModel

class PokemonTCGCardDataEntityMapper {

    companion object {
        fun create (pokemonTCGCardDataModel: PokemonTCGCardDataResponseModel.PokemonTCGCardDataModel): PokemonTCGCard {
            val ability = createAbility(pokemonTCGCardDataModel.ability)
            val attacks = ArrayList<PokemonTCGCard.Attack>()
            val weaknesses = ArrayList<PokemonTCGCard.Weakness>()

            if (pokemonTCGCardDataModel.attacks != null) {
                pokemonTCGCardDataModel.attacks.mapTo(attacks) { createAttack(it) }
            }

            if (pokemonTCGCardDataModel.weaknesses != null) {
                pokemonTCGCardDataModel.weaknesses.mapTo(weaknesses) { createWeakness(it) }
            }

            return PokemonTCGCard(pokemonTCGCardDataModel.id ?: "",
                    pokemonTCGCardDataModel.name ?: "",
                    pokemonTCGCardDataModel.nationalPokedexNumber?: 0,
                    pokemonTCGCardDataModel.imageUrl ?: "",
                    pokemonTCGCardDataModel.imageUrlHiRes ?: "",
                    pokemonTCGCardDataModel.types ?: ArrayList(),
                    pokemonTCGCardDataModel.supertype ?: "",
                    pokemonTCGCardDataModel.subtype?: "",
                    ability,
                    pokemonTCGCardDataModel.hp?: "",
                    pokemonTCGCardDataModel.retreatCost?: ArrayList(),
                    pokemonTCGCardDataModel.convertedRetreatCost?: 0,
                    pokemonTCGCardDataModel.number?: "",
                    pokemonTCGCardDataModel.artist?: "",
                    pokemonTCGCardDataModel.rarity?: "",
                    pokemonTCGCardDataModel.series?: "",
                    pokemonTCGCardDataModel.set?: "",
                    pokemonTCGCardDataModel.setCode?: "",
                    attacks,
                    weaknesses
            )
        }

        private fun createAbility (abilityDataModel: PokemonTCGCardDataResponseModel.PokemonTCGCardDataModel.AbilityDataModel?): PokemonTCGCard.Ability {
            if (abilityDataModel != null)
                return PokemonTCGCard.Ability(abilityDataModel.name?: "",
                        abilityDataModel.text?: "",
                        abilityDataModel.type?: ""
                )

            return PokemonTCGCard.Ability("", "", "")
        }

        private fun createAttack (attackDataModel: PokemonTCGCardDataResponseModel.PokemonTCGCardDataModel.AttackDataModel?): PokemonTCGCard.Attack {
            if (attackDataModel != null)
                return PokemonTCGCard.Attack(attackDataModel.cost?: ArrayList(),
                        attackDataModel.name?: "",
                        attackDataModel.text?: "",
                        attackDataModel.damage?: "",
                        attackDataModel.convertedEnergyCost?: 0
                )
            return PokemonTCGCard.Attack(ArrayList(), "", "", "", 0)
        }

        private fun createWeakness (weaknessDataModel: PokemonTCGCardDataResponseModel.PokemonTCGCardDataModel.WeaknessDataModel?): PokemonTCGCard.Weakness {
            if (weaknessDataModel != null)
                return PokemonTCGCard.Weakness(weaknessDataModel.type?: "",
                        weaknessDataModel.value?: ""
                )
            return PokemonTCGCard.Weakness("", "")
        }
    }
}