package com.example.primetea

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.*
import com.chargemap.compose.numberpicker.NumberPicker
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Entity(tableName = "eventos")
data class EventEntity(
    @PrimaryKey val fecha: LocalDate,
    val hora24: Int,
    val minuto: Int,
    val texto: String
)

@Dao
interface EventDao {
    @Query("SELECT * FROM eventos") fun flowAll(): Flow<List<EventEntity>>
    @Query("SELECT * FROM eventos WHERE fecha = :dia") suspend fun get(dia: LocalDate): EventEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsert(ev: EventEntity)
    @Query("DELETE FROM eventos WHERE fecha = :dia") suspend fun delete(dia: LocalDate)
}

class Converters {
    @TypeConverter fun dateToString(d: LocalDate) = d.toString()
    @TypeConverter fun stringToDate(s: String) = LocalDate.parse(s)
}

@Database(entities = [EventEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() { abstract fun dao(): EventDao }

class CalendarVm(app: Application) : AndroidViewModel(app) {
    private val dao = Room.databaseBuilder(app, AppDb::class.java, "db").build().dao()
    val eventos = dao.flowAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    suspend fun save(ev: EventEntity) {
        dao.upsert(ev)
        NotiUtils.programar(getApplication(), ev)
    }
    suspend fun delete(dia: LocalDate) { dao.delete(dia) }
    suspend fun get(dia: LocalDate) = dao.get(dia)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarioScreen(navController: NavController, vm: CalendarVm = viewModel()) {
    val ctx = LocalContext.current
    LaunchedEffect(Unit) { NotiUtils.crearCanal(ctx) }

    val permisoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted)
            Toast.makeText(ctx, "Sin permiso, no habrá notificaciones", Toast.LENGTH_SHORT).show()
    }

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDay by remember { mutableStateOf<LocalDate?>(null) }
    var hour by remember { mutableStateOf(12) }
    var minute by remember { mutableStateOf(0) }
    var texto by remember { mutableStateOf("") }

    val eventos by vm.eventos.collectAsState()
    val scope = rememberCoroutineScope()
    val tieneEvento: (LocalDate) -> Boolean = { d -> eventos.any { it.fecha == d } }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo más grande
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(60.dp)
        )
        // Top bar
        Row(
            Modifier.fillMaxWidth().height(64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.ArrowBack, "back",
                Modifier
                    .size(32.dp)
                    .clickable { navController.navigate("home") })
            Spacer(Modifier.weight(1f))
            Text("Añadir Eventos",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32))
            Spacer(Modifier.weight(1f))
        }

        // Mes
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton({ currentMonth = currentMonth.minusMonths(1) }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
            Text(
                "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
                Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            IconButton({ currentMonth = currentMonth.plusMonths(1) }) {
                Icon(Icons.Filled.ArrowForward, null)
            }
        }

        Spacer(Modifier.height(4.dp))

        // Días de la semana
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("L","M","X","J","V","S","D").forEach {
                Text(it, fontWeight = FontWeight.Medium, color = Color.Gray)
            }
        }
        Spacer(Modifier.height(4.dp))

        // Cuadrícula
        val firstDay = currentMonth.atDay(1)
        val start = firstDay.dayOfWeek.value % 7
        val days = currentMonth.lengthOfMonth()
        val weeks = ((start + days + 6)/7)
        var day = 1 - start
        repeat(weeks) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                repeat(7) {
                    if (day in 1..days) {
                        val date = currentMonth.atDay(day)
                        val marcado = tieneEvento(date)
                        val seleccionado = selectedDay == date
                        Box(
                            Modifier.size(35.dp)
                                .background(
                                    when {
                                        seleccionado -> Color(0xFF90CAF9)
                                        marcado      -> Color(0xFF64B5F6)
                                        else         -> Color.White
                                    }, CircleShape
                                )
                                .border(
                                    1.dp,
                                    if (seleccionado) Color(0xFF42A5F5) else Color.LightGray,
                                    CircleShape
                                )
                                .clickable { selectedDay = date },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("$day",
                                color = if (marcado || seleccionado) Color.White else Color.Black)
                        }
                    } else Spacer(Modifier.size(40.dp))
                    day++
                }
            }
            Spacer(Modifier.height(4.dp))
        }

        // Editor
        selectedDay?.let { fecha ->
            LaunchedEffect(fecha) {
                vm.get(fecha)?.let {
                    hour = it.hora24; minute = it.minuto; texto = it.texto
                } ?: run {
                    hour = 12; minute = 0; texto = ""
                }
            }

            Spacer(Modifier.height(24.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD1C4E9), RoundedCornerShape(16.dp))
                    .animateContentSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Filled.Delete,"borrar",
                    Modifier
                        .align(Alignment.End)
                        .clickable {
                            scope.launch { vm.delete(fecha) }
                            Toast.makeText(ctx,"Evento eliminado", Toast.LENGTH_SHORT).show()
                            selectedDay = null
                        })

                Text("Hora para ${fecha.dayOfMonth}/${fecha.monthValue}/${fecha.year}",
                    fontWeight = FontWeight.Medium, color = Color(0xFF512DA8))

                OutlinedTextField(
                    value = texto,
                    onValueChange = { texto = it },
                    label = { Text("Descripción (ej. Tenis)", color= Color.White)},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Black,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Black
                    )


                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    NumberPicker(
                        value = hour,
                        range = 0..23,
                        onValueChange = { hour = it },
                        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 24.sp)
                    )
                    Text(":", fontSize = 24.sp)
                    NumberPicker(
                        value = minute,
                        range = 0..59,
                        onValueChange = { minute = it },
                        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 24.sp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Button(onClick={ selectedDay=null },
                        colors=ButtonDefaults.buttonColors(containerColor=Color.Red)) {
                        Text("Cancel", color=Color.White)
                    }
                    Button(onClick={
                        if (Build.VERSION.SDK_INT>=33 &&
                            ContextCompat.checkSelfPermission(
                                ctx, Manifest.permission.POST_NOTIFICATIONS
                            )!= PackageManager.PERMISSION_GRANTED) {
                            permisoLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        scope.launch {
                            vm.save(EventEntity(fecha,hour,minute,texto.ifBlank{"Entrenamiento"}))
                        }
                        Toast.makeText(ctx,"Evento guardado", Toast.LENGTH_SHORT).show()
                        selectedDay = null
                    }, colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF2E7D32))) {
                        Text("OK", color=Color.White)
                    }
                }
            }
        }
    }
}
