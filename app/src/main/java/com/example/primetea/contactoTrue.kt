// ContactoTrueScreen.kt
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
fun ContactoTrueScreen(navController: NavController) {
    // Reutilizamos el mismo mapa de detalles que en MisDeportesScreen:
    val detalles = mapOf(
        "Fútbol" to Pair(
            """
            • Deporte en equipo para correr y chutar con los pies.
            • Comunicación por gestos y turnos.
            • Campo amplio, sin ruidos fuertes.

            🔥 Calentamiento:
            1. Trote suave 5–10 min  
            2. Estiramientos dinámicos de piernas  
            3. Movilidad articular (tobillos, rodillas, caderas)  
            4. Sprints cortos + recuperación  
            5. Pases suaves en pareja  

            🎯 Consejos TEA:
            • Rutinas claras y repetidas  
            • Gestos/visual para “tu turno”  
            • Entorno tranquilo  
            • Pausas sensoriales si es necesario  
            • Refuerzos inmediatos (“¡Bien!”)
            """.trimIndent(),
            R.drawable.futbol
        ),
        "Baloncesto" to Pair(
            """
            • Juego de lanzar balón a canasta.  
            • Mejora coordinación mano-ojo.  
            • Espacio cerrado con rutinas claras.  

            🔥 Calentamiento:
            1. Bote suave de balón  
            2. Pases en pareja  
            3. Lanzamientos cortos  
            4. Estiramientos de brazos y hombros  
            5. Mini-sprints cruzando la cancha  

            🎯 Consejos TEA:
            • Divide ejercicios en pasos  
            • Usa señales visuales para turnos  
            • Practica en un entorno con pocos estímulos  
            • Refuerza cada acierto con un gesto positivo
            """.trimIndent(),
            R.drawable.ter2
        ),
        "Karate" to Pair(
            """
            • Disciplina de katas y respeto.  
            • Rutinas repetitivas y concentración.  

            🔥 Calentamiento:
            1. Rotaciones de muñecas y tobillos  
            2. Holds de postura básica (zuki dachi)  
            3. Estiramientos de piernas  
            4. Kicks suaves sin contacto  
            5. Respiración controlada  

            🎯 Consejos TEA:
            • Dojo tranquilo y ordenado  
            • Visualización de secuencias  
            • Señales por colores o gestos  
            • Refuerzos tras cada kata
            """.trimIndent(),
            R.drawable.karate
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
            "Deportes de Contacto",
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
