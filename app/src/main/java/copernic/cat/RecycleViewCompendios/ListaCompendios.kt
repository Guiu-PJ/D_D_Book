package copernic.cat.RecycleViewCompendios

import copernic.cat.R

class ListaCompendios {
    companion object{
        val ListaCompendioslist = listOf<ClassCompendios>(
            ClassCompendios(
                "Compendio del jugador",
                R.drawable.player_handbook_5e
            ),
            ClassCompendios(
                "Dungeon master",
                R.drawable.dungeon_masters_guide
            ),
            ClassCompendios(
                "El resurgir del dragon",
                R.drawable.el_resurgir_del_dragon
            ),
            ClassCompendios(
                "Compendio de monstruos",
                R.drawable.monster_manual_5
            )
        )
    }
}