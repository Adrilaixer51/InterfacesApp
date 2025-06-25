package com.example.primetea

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun PantallaHome(
    navController: NavController,
    nombreUsuario: String = "Marco",
    favoritosViewModel: FavoritosViewModel = viewModel()
) {
    // true si hay al menos un favorito
    val hayFavoritos by remember { derivedStateOf { favoritosViewModel.favoritos.isNotEmpty() } }
    // controla visibilidad del badge; al entrar se inicializa según hayFavoritos
    var mostrarBadge by remember { mutableStateOf(hayFavoritos) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        // Logo y saludo
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(160.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = "Hola, $nombreUsuario!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Primera fila: Buscar deporte + Mis deportes (con badge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Buscar deporte
            BotonOpcion(
                texto = "Buscar deporte",
                icono = Icons.Default.Search
            ) {
                navController.navigate("buscarDeporte")
            }

            // Mis deportes con badge
            Box(
                modifier = Modifier
                    .width(155.dp)
                    .height(140.dp)
            ) {
                BotonOpcion(
                    texto = "Mis deportes",
                    icono = Icons.Default.Favorite
                ) {
                    // al pulsar, ocultamos badge y navegamos
                    mostrarBadge = false
                    navController.navigate("misDeportes")
                }

                if (mostrarBadge && hayFavoritos) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Hay deportes nuevos",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        // Segunda fila: Tips + Calendario
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BotonOpcion(
                texto = "Comunícate",
                icono = Icons.Default.Campaign
            ) { navController.navigate("pictos") }

            BotonOpcion(
                texto = "Aviso Evento",
                icono = Icons.Default.AddAlert
            ) { navController.navigate("calendario") }
        }

        Spacer(Modifier.height(32.dp))

        // Configuración abajo
        BotonOpcion(
            texto = "Configuración",
            icono = Icons.Default.Settings
        ) {
            navController.navigate("configuracion3")
        }
    }
}

@Composable
fun BotonOpcion(
    texto: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(155.dp)
            .height(140.dp)
            .clickable(onClick = onClick)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                icono,
                contentDescription = texto,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = texto,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}
