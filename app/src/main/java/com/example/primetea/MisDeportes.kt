package com.example.primetea

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun MisDeportesScreen(favoritosViewModel: FavoritosViewModel) {
    val favoritos = favoritosViewModel.favoritos

    // Descripciones adaptadas
    val descripciones = mapOf(
        "Fútbol" to "El fútbol es un deporte en equipo donde puedes correr y patear una pelota. Puedes comunicarte con gestos y aprender a turnarte.",
        "Baloncesto" to "El baloncesto es un juego de equipo donde lanzas la pelota a una canasta. Se puede practicar en espacios cerrados y con rutinas claras.",
        "Natación" to "La natación te permite moverte en el agua con tranquilidad. Es un deporte sin ruidos fuertes y puedes hacerlo solo o con alguien.",
        "Tenis" to "El tenis se juega uno contra uno o por parejas. Permite mejorar la coordinación y seguir turnos fácilmente.",
        "Karate" to "El karate es una disciplina que enseña respeto y concentración. Las rutinas son repetitivas y ayudan a crear hábitos.",
        "Ciclismo" to "El ciclismo te permite pedalear en línea recta o dar paseos tranquilos. Se puede practicar al aire libre sin contacto físico."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Logo más grande
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(160.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            "Mis Deportes",
            fontSize = 28.sp,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (favoritos.isEmpty()) {
            Text(
                "No has marcado ningún deporte como favorito.",
                color = Color.DarkGray,
                fontSize = 18.sp
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                favoritos.forEach { deporte ->
                    var expanded by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                            .clickable { expanded = !expanded },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = deporte,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                            if (expanded) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = descripciones[deporte] ?: "",
                                    fontSize = 16.sp,
                                    color = Color.DarkGray,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
