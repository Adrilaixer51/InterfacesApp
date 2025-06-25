package com.example.primetea

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Locale

private data class Pictogram(val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)

@Composable
fun PictogramBuilderScreen(navController: NavController) {
    val context = LocalContext.current

    // 1) Inicializa TTS una sola vez y configura el idioma en el callback
    val tts = remember {
        TextToSpeech(context, /* listener = */ null).apply {
            // setOnInitListener no se reconoce como SAM en algunos entornos,
            // así que lo hacemos con un objeto explícito:
            setOnInitListener(object : TextToSpeech.OnInitListener {
                override fun onInit(status: Int) {
                    if (status == TextToSpeech.SUCCESS) {
                        language = Locale.getDefault()
                    }
                }
            })
        }
    }

    // Lista de pictogramas
    val allPictos = listOf(
        Pictogram(Icons.Filled.Person,        "Yo"),
        Pictogram(Icons.Filled.Favorite,      "Quiero"),
        Pictogram(Icons.Filled.WarningAmber,  "Necesito"),
        Pictogram(Icons.Filled.AddLocation, "Voy a"),
        Pictogram(Icons.Filled.CheckCircle,   "Puedo"),

        Pictogram(Icons.Filled.FitnessCenter,      "Mancuerna"),
        Pictogram(Icons.Filled.LocalDrink,         "Agua"),
        Pictogram(Icons.Filled.Restaurant,         "Comida"),
        Pictogram(Icons.Filled.DirectionsRun,      "Correr"),
        Pictogram(Icons.Filled.DirectionsBike,     "Bicicleta"),
        Pictogram(Icons.Filled.Pool,               "Natación"),
        Pictogram(Icons.Filled.SelfImprovement,    "Yoga"),
        Pictogram(Icons.Filled.Hotel,              "Descanso"),
        Pictogram(Icons.Filled.AccessTime,         "Tiempo"),
        Pictogram(Icons.Filled.Timer,              "Temporizador"),
        Pictogram(Icons.Filled.MonitorHeart,       "Cardio"),
        Pictogram(Icons.Filled.EmojiEvents,        "Meta"),
        Pictogram(Icons.Filled.Group,              "Equipo"),
        Pictogram(Icons.Filled.MusicNote,          "Música"),
        Pictogram(Icons.Filled.ThumbUp,            "Me gusta"),
        Pictogram(Icons.Filled.Star,               "Logro"),
        Pictogram(Icons.Filled.Whatshot,           "Intenso"),
        Pictogram(Icons.Filled.EmojiObjects,       "Idea"),
        Pictogram(Icons.Filled.Eco,                "Naturaleza"),
        Pictogram(Icons.Filled.Bedtime,            "Sueño"),
        Pictogram(Icons.Filled.AccessibilityNew,   "Estirar"),
        Pictogram(Icons.Filled.ShoppingCart,       "Comprar"),
        Pictogram(Icons.Filled.VolunteerActivism,  "Apoyo")
    )

    // Frase en construcción
    val selected = remember { mutableStateListOf<Pictogram>() }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(18.dp)
    ) {
        // ── Barra superior ──
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { navController.navigate("home") }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                "Construye tu frase",
                fontSize = 20.sp,
                color = Color(0xFF2E7D32)
            )
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.Filled.VolumeUp,
                contentDescription = "Reproducir frase",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        val phrase = selected.joinToString(" ") { it.label }
                        if (phrase.isNotBlank()) {
                            tts.speak(
                                phrase,
                                TextToSpeech.QUEUE_FLUSH,
                                /* params = */ null,
                                /* utteranceId = */ "UTTERANCE_ID"
                            )
                        }
                    },
                tint = Color(0xFF2E7D32)
            )
        }

        Spacer(Modifier.height(16.dp))

        // ── Frase seleccionada ──
        val scroll = rememberScrollState()
        Row(
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .horizontalScroll(scroll)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selected.isEmpty()) {
                Text("Toca abajo para añadir pictogramas", color = Color.Gray)
            }
            selected.forEach { p ->
                Column(
                    Modifier
                        .padding(end = 12.dp)
                        .clickable { selected.remove(p) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        p.icon,
                        contentDescription = p.label,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(36.dp)
                    )
                    Text(p.label, fontSize = 12.sp)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // ── Grid de todos los pictogramas ──
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(allPictos) { p ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { selected.add(p) }
                        .padding(4.dp)
                ) {
                    Box(
                        Modifier
                            .size(56.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            p.icon,
                            contentDescription = p.label,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(p.label, fontSize = 12.sp, color = Color.Black)
                }
            }
        }
    }
}

fun setOnInitListener(onInitListener: TextToSpeech.OnInitListener) {

}
