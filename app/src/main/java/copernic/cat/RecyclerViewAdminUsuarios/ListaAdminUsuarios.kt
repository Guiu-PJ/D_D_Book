package copernic.cat.RecyclerViewAdminUsuarios

import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import copernic.cat.R
import copernic.cat.RecycleViewCompendios.ClassCompendios
import kotlinx.coroutines.tasks.await


class ListaAdminUsuarios {
    companion object{
        val ListaAdminUsuarioslista = listOf<ClassAdminUsuarios>(
            ClassAdminUsuarios(
                "Usuario 1",
                R.drawable.player_handbook_5e
            ),
            ClassAdminUsuarios(
                "Usuario 2",
                R.drawable.dungeon_masters_guide
            ),
            ClassAdminUsuarios(
                "Usuario 3",
                R.drawable.el_resurgir_del_dragon
            ),
            ClassAdminUsuarios(
                "Usuario 4",
                R.drawable.monster_manual_5
            )
        )
    }


}
