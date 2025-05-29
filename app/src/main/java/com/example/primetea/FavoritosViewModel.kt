// FavoritosViewModel.kt
package com.example.primetea

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class FavoritosViewModel : ViewModel() {
    // Lista observable de favoritos
    val favoritos = mutableStateListOf<String>()

    fun agregarFavorito(deporte: String) {
        if (!favoritos.contains(deporte)) {
            favoritos.add(deporte)
        }
    }

    fun eliminarFavorito(deporte: String) {
        favoritos.remove(deporte)
    }
}
