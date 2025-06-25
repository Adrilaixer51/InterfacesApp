// ContactoFalseScreen.kt
package com.example.primetea

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun ContactoFalseScreen(navController: NavController) {
    // Reutilizamos el mismo mapa de detalles que en MisDeportesScreen:
    val detalles = mapOf(
        "Natación" to Pair(
            """
            • Movimiento en agua, sin ruidos fuertes.  
            • Mejora respiración y coordinación.  

            🔥 Calentamiento:
            1. Estiramientos de brazos y hombros  
            2. Patadas en seco (kickboard)  
            3. Rotaciones de tobillos  
            4. Respiración profunda  
            5. Movilidad de cuello y espalda  

            🎯 Consejos TEA:
            • Piscina tranquila y conocida  
            • Temporizador visual para tiempos  
            • Pausas frecuentes fuera del agua  
            • Refuerzos positivos tras cada largo
            """.trimIndent(),
            R.drawable.arena_crawl_blog_nologo
        ),
        "Ciclismo" to Pair(
            """
            • Pedaleo al aire libre, resistencia.  
            • Mejora cardiovascular y equilibrio.  

            🔥 Calentamiento:
            1. Pedaleo suave 5 min sin resistencia  
            2. Balanceo de piernas parado  
            3. Círculos de cadera  
            4. Estiramiento de cuádriceps  
            5. Breves aceleraciones  

            🎯 Consejos TEA:
            • Ruta fija y conocida  
            • Paradas breves programadas  
            • Señales de giro claras  
            • Hidratación frecuente  
            • Recompensas al final del trayecto
            """.trimIndent(),
            R.drawable.fb040d1a863de9a3d9260e169f5dafaf
        ),
        "Tenis" to Pair(
            """
            • Uno contra uno o dobles, golpeo de pelota.  
            • Coordinación, concentración y turnos.  

            🔥 Calentamiento:
            1. Swings de raqueta sin pelota  
            2. Movilidad de muñecas y codos  
            3. Side-steps y mini-sprints  
            4. Estiramientos de hombros  
            5. Pases contra la pared  

            🎯 Consejos TEA:
            • Secuencia fija de ejercicios  
            • Señales claras de “tu turno”  
            • Practica en pista tranquila  
            • Snacks e hidratación programada
            """.trimIndent(),
            R.drawable.fs
        )
    )

    val scroll = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .background(Color(0xFFE8F5E9))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(160.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            "Deportes sin Contacto",
            fontSize = 28.sp,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        detalles.forEach { (deporte, detalle) ->
            var expanded by remember { mutableStateOf(false) }
            Card(
                Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(deporte, fontSize = 20.sp, color = Color.Black)
                    if (expanded) {
                        Spacer(Modifier.height(8.dp))
                        Text(detalle.first, fontSize = 16.sp, color = Color.DarkGray)
                        detalle.second?.let { res ->
                            Spacer(Modifier.height(12.dp))
                            AsyncImage(
                                model = res,
                                contentDescription = "$deporte animado",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("home") },
            Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continuar", fontSize = 18.sp, color = Color.White)
        }
    }
}
