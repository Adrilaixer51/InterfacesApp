package com.example.primetea

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chargemap.compose.numberpicker.NumberPicker
import java.time.*
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarioScreen(navController: NavController) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    val citas = remember { mutableStateListOf<LocalDate>() }
    var selectedDay by remember { mutableStateOf<LocalDate?>(null) }
    var hour by remember { mutableStateOf(12) }
    var minute by remember { mutableStateOf(0) }
    var isAm by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        // Logo
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )

        Spacer(Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Calendario",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32)
        )
        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton({ currentMonth = currentMonth.minusMonths(1) }) {
                Icon(Icons.Default.ArrowBack, "")
            }
            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.year,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
                color = Color.Black,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            IconButton({ currentMonth = currentMonth.plusMonths(1) }) {
                Icon(Icons.Default.ArrowForward, "")
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("L", "M", "X", "J", "V", "S", "D").forEach {
                Text(it, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
            }
        }

        Spacer(Modifier.height(4.dp))

        val firstDayOfMonth = currentMonth.atDay(1)
        val startDow = firstDayOfMonth.dayOfWeek.value % 7
        val totalDays = currentMonth.lengthOfMonth()
        val weeks = ((startDow + totalDays + 6) / 7)

        Column {
            var dayCounter = 1 - startDow
            repeat(weeks) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    repeat(7) {
                        if (dayCounter in 1..totalDays) {
                            val thisDate = currentMonth.atDay(dayCounter)
                            val isCita = citas.contains(thisDate)
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = when {
                                            selectedDay == thisDate -> Color(0xFF90CAF9)
                                            isCita -> Color(0xFF64B5F6)
                                            else -> Color.White
                                        },
                                        shape = CircleShape
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (selectedDay == thisDate) Color(0xFF42A5F5) else Color.LightGray,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        selectedDay = thisDate
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayCounter.toString(),
                                    color = if (isCita || selectedDay == thisDate) Color.White else Color.Black
                                )
                            }
                        } else {
                            Spacer(Modifier.size(40.dp))
                        }
                        dayCounter++
                    }
                }
                Spacer(Modifier.height(4.dp))
            }
        }

        selectedDay?.let { fecha ->
            Spacer(Modifier.height(24.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD1C4E9), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Selecciona hora para el ${fecha.dayOfMonth}/${fecha.monthValue}/${fecha.year}",
                    color = Color(0xFF512DA8),
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Selector de hora
                    NumberPicker(
                        value = hour,
                        range = 1..24,
                        onValueChange = { hour = it },
                        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 24.sp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(":", fontSize = 24.sp)
                    Spacer(Modifier.width(8.dp))
                    // Selector de minuto
                    NumberPicker(
                        value = minute,
                        range = 0..59,
                        onValueChange = { minute = it },
                        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 24.sp)
                    )
                    Spacer(Modifier.width(16.dp))
                    // AM/PM toggle
                    SegmentedButtonGroup(options = listOf("AM", "PM"), selected = if (isAm) 0 else 1) {
                        isAm = (it == 0)
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row {
                    Button(
                        onClick = { selectedDay = null },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Cancel", color = Color.White)
                    }
                    Spacer(Modifier.width(24.dp))
                    Button(
                        onClick = {
                            citas.add(fecha)
                            selectedDay = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                    ) {
                        Text("OK", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun <Painter> Image(painter: Painter, contentDescription: String, modifier: Modifier) {}

@Composable
fun SegmentedButtonGroup(
    options: List<String>,
    selected: Int,
    onSelect: (index: Int) -> Unit
) {
    Row {
        options.forEachIndexed { idx, text ->
            Box(
                modifier = Modifier
                    .background(
                        if (idx == selected) Color(0xFF64B5F6) else Color.White,
                        RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .clickable { onSelect(idx) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(text, color = if (idx == selected) Color.White else Color.Black)
            }
            Spacer(Modifier.width(8.dp))
        }
    }
}
