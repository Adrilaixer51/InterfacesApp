package com.example.primetea

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun MisDeportesScreen(
    favoritosViewModel: FavoritosViewModel,
    navController: NavController
) {
    val favoritos = favoritosViewModel.favoritos

    // Detalles de cada deporte: descripción, calentamiento y consejos TEA, + GIF opcional
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
            • Señales claras de “tú turno”  
            • Practica en pista tranquila  
            • Snacks e hidratación programada
            """.trimIndent(),
            R.drawable.fs
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
        )
    )

    // Estado de scroll
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .verticalScroll(scroll)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // ── Top bar ──
        Box(
            Modifier
                .fillMaxWidth()
                .height(64.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.ArrowBack, "Volver",
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

        Text(
            "Mis Deportes",
            fontSize = 28.sp,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favoritos.isEmpty()) {
            Text(
                "No tienes deportes favoritos.",
                color = Color.DarkGray,
                fontSize = 18.sp
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                favoritos.forEach { deporte ->
                    var expanded by remember { mutableStateOf(false) }
                    val (texto, gifRes) = detalles[deporte] ?: Pair("", null)

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                            .clickable { expanded = !expanded },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            // Título
                            Text(deporte, fontSize = 20.sp, color = Color.Black)

                            if (expanded) {
                                Spacer(modifier = Modifier.height(8.dp))

                                // Texto
                                Text(texto, fontSize = 16.sp, color = Color.DarkGray)

                                // GIF si existe
                                gifRes?.let { resId ->
                                    Spacer(modifier = Modifier.height(12.dp))
                                    AsyncImage(
                                        model = resId,
                                        contentDescription = "$deporte animado",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
