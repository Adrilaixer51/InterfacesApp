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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaHome(navController: NavController, nombreUsuario: String = "Marco") {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Logo más grande
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(160.dp)
                .padding(bottom = 16.dp)
        )

        // Texto de bienvenida
        Text(
            text = "Hola, $nombreUsuario!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Fila superior
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BotonOpcion(
                texto = "Buscar deporte",
                icono = Icons.Default.Search
            ) { navController.navigate("buscarDeporte") }

            BotonOpcion(
                texto = "Mis deportes",
                icono = Icons.Default.Favorite
            ) { navController.navigate("misDeportes") }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Fila inferior
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BotonOpcion(
                texto = "Tips para mí",
                icono = Icons.Default.Info
            ) { navController.navigate("tips") }

            BotonOpcion(
                texto = "Calendario",
                icono = Icons.Default.DateRange
            ) { navController.navigate("calendario") }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de configuración centrado abajo
        BotonOpcion(
            texto = "Configuración",
            icono = Icons.Default.Settings
        ) {
            navController.navigate("configuracion3")
        }
    }
}

@Composable
fun BotonOpcion(texto: String, icono: ImageVector, onClick: () -> Unit) {
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
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = texto,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}
