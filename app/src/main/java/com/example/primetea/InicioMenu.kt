package com.example.primetea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.primetea.ui.theme.PrimeTeaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimeTeaTheme {
                val navController = rememberNavController()
                val favVM: FavoritosViewModel = viewModel()
                NavHost(navController = navController, startDestination = "bienvenida") {
                    composable("bienvenida") { PantallaBienvenida(navController) }
                    composable("inicioMenu2") { InicioMenu2(navController) }
                    composable("inicioSesion") { InicioSesion(navController) }
                    composable("registro") { Registro(navController) }
                    composable("home") { PantallaHome(navController) }
                    composable("buscarDeporte") { BuscarDeporteScreen(navController, favVM) }
                    composable("tips") { TipsScreen(navController) }
                    composable("calendario") { CalendarioScreen(navController) }
                    composable("configuracion") { ConfiguracionScreen(navController) }
                    composable("configuracion2") { Configuracion2Screen(navController) }
                    composable("misDeportes") { MisDeportesScreen(favVM, navController) }
                    composable("configuracion3") { Configuracion3Screen(navController) }
                    composable("Duda1") { Duda1Screen(navController) }
                    composable("contactoTrue") { ContactoTrueScreen(navController) }
                    composable("contactoFalse") { ContactoFalseScreen(navController) }
                }
            }
        }
    }
}
@Composable
fun PantallaBienvenida(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)) // fondo verdoso suave
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo centrado
            Image(
                painter = painterResource(id = R.drawable.logo), // Asegúrate de tener el logo como "logo.png" en res/drawable
                contentDescription = "Logo PrimeTea",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(320.dp)
                    .padding(bottom = 32.dp)
            )

            Text(
                text = "Bienvenido a PrimeTea",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32) // verde oscuro
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tu app para sentirte bien.",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Aquí irá la navegación a la siguiente pantalla
                    navController.navigate("inicioMenu2")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A))
            ) {
                Text(text = "Empezar", fontSize = 40.sp, color = Color.White)
            }
        }
    }
}
