package com.example.primetea

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun BuscarDeporteScreen(
    navController: NavController,
    viewModel: FavoritosViewModel = viewModel()
) {
    var textoBusqueda by remember { mutableStateOf("") }
    val deportes = listOf("Fútbol", "Baloncesto", "Natación", "Tenis", "Karate", "Ciclismo")
    val favoritos = viewModel.favoritos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )

        // Título
        Text(
            "Buscar Deporte",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campo de búsqueda
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Search, contentDescription = null, tint = Color.Gray)
                Spacer(Modifier.width(8.dp))
                BasicTextField(
                    value = textoBusqueda,
                    onValueChange = { textoBusqueda = it },
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Lista filtrada
        val filtrados = deportes.filter {
            it.contains(textoBusqueda, ignoreCase = true)
        }
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            filtrados.forEach { deporte ->
                BotonDeporte(
                    nombre = deporte,
                    esFavorito = favoritos.contains(deporte)
                ) {
                    if (favoritos.contains(deporte)) viewModel.eliminarFavorito(deporte)
                    else viewModel.agregarFavorito(deporte)
                }
            }
        }
    }
}

@Composable
fun BotonDeporte(
    nombre: String,
    esFavorito: Boolean,
    onFavoritoClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            nombre,
            fontSize = 20.sp,
            color = Color.Black
        )
        Icon(
            imageVector = if (esFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = null,
            tint = if (esFavorito) Color(0xFFFFD700) else Color.Gray,
            modifier = Modifier
                .size(28.dp)
                .clickable { onFavoritoClick() }
        )
    }
}
