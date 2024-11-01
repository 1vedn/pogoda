import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.WeatherService
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    var city by remember { mutableStateOf("Moscow") }
    var temperature by remember { mutableStateOf<Float?>(null) }
    var humidity by remember { mutableStateOf<Int?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter City") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                try {
                    val response = WeatherService.getWeather(city)
                    temperature = response?.main?.temp
                    humidity = response?.main?.humidity
                    error = null
                } catch (e: Exception) {
                    error = "City not found"
                }
            }
        }) {
            Text("Get Weather")
        }
        Spacer(modifier = Modifier.height(16.dp))
        temperature?.let {
            Text("Temperature: $it°C")
        }
        humidity?.let {
            Text("Humidity: $it%")
        }
        error?.let {
            Text("Error: $it", color = MaterialTheme.colors.error)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}
