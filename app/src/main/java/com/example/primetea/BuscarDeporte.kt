package com.example.primetea

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material.icons.rounded.DirectionsBike
import androidx.compose.material.icons.rounded.Pool
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun BuscarDeporteScreen(
    navController: NavController,
    viewModel: FavoritosViewModel = viewModel()
) {
    val ctx = LocalContext.current
    var textoBusqueda by remember { mutableStateOf("") }

    // Lista completa de deportes
    val deportes = listOf("Fútbol", "Baloncesto", "Natación", "Tenis", "Karate", "Ciclismo")
    val favoritos = viewModel.favoritos

    // Scroll state para toda la columna
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
    ) {
        // Contenido principal con scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ── BOX SUPERIOR: flecha atrás, logo centrado ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver a Home",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(32.dp)
                        .clickable { navController.navigate("home") }
                )
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── TÍTULO ──
            Text(
                "Buscar Deporte",
                fontSize = 28.sp,
                color = Color(0xFF2E7D32),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // ── CAMPO DE BÚSQUEDA ──
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

            Spacer(modifier = Modifier.height(32.dp))

            // ── LISTA FILTRADA ──
            val filtrados = deportes.filter {
                it.contains(textoBusqueda, ignoreCase = true)
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                filtrados.forEach { deporte ->
                    val esFav = favoritos.contains(deporte)
                    BotonDeporte(
                        nombre = deporte,
                        esFavorito = esFav
                    ) {
                        if (esFav) {
                            viewModel.eliminarFavorito(deporte)
                            Toast
                                .makeText(ctx, "$deporte eliminado de favoritos", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            viewModel.agregarFavorito(deporte)
                            Toast
                                .makeText(ctx, "$deporte guardado en favoritos", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // espacio para que no quede tapado por el botón
        }

        // ── Botón de Ayuda fijo abajo a la derecha ──
        Icon(
            imageVector = Icons.Filled.Help,
            contentDescription = "Ayuda",
            tint = Color(0xFF2E7D32),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .size(48.dp)
                .clickable { navController.navigate("duda1") }
        )
    }
}

@Composable
fun BotonDeporte(
    nombre: String,
    esFavorito: Boolean,
    onFavoritoClick: () -> Unit
) {
    val iconoDeporte = when (nombre) {
        "Fútbol" -> Icons.Filled.SportsSoccer
        "Baloncesto" -> Icons.Filled.SportsBasketball
        "Natación" -> Icons.Rounded.Pool
        "Tenis" -> Icons.Filled.SportsTennis
        "Karate" -> Icons.Filled.SportsMartialArts
        "Ciclismo" -> Icons.Rounded.DirectionsBike
        else -> Icons.Filled.Sports
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = iconoDeporte,
            contentDescription = "$nombre icono",
            tint = Color(0xFF2E7D32),
            modifier = Modifier.size(36.dp)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            nombre,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = if (esFavorito) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = if (esFavorito) "Eliminar favorito" else "Agregar favorito",
            tint = if (esFavorito) Color(0xFFFFD700) else Color.Gray,
            modifier = Modifier
                .size(28.dp)
                .clickable(onClick = onFavoritoClick)
        )
    }
}
