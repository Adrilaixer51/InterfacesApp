package com.example.primetea

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InicioMenu2(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), // opcional si hay logo también en esta pantalla
                contentDescription = "Logo PrimeTea",
                modifier = Modifier
                    .height(190.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Para la siguiente fase se necesita la ayuda de un adulto/tutor para completar.",
                style = TextStyle(
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(20.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Aquí puedes navegar a la siguiente pantalla, por ejemplo:
                    navController.navigate("inicioSesion")

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A))
            ) {
                Text("Continuar", fontSize = 40.sp, color = Color.White)
            }
        }
    }
}
