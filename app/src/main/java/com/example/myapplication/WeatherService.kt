package com.example.myapplication
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

const val API_KEY = "f8e870107c61f67c1967732e72f93fe8"
const val BASE_URL = "https://api.openweathermap.org/data/2.5"

@Serializable
data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val name: String,
    val cod: Int
)

@Serializable
data class Coord(val lon: Double, val lat: Double)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Main(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class Wind(val speed: Float, val deg: Int)

@Serializable
data class Clouds(val all: Int)

@Serializable
data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)



object WeatherService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getWeather(city: String): WeatherResponse? {
        val encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString())
        val url = "$BASE_URL/weather?q=$encodedCity&appid=$API_KEY&units=metric"
        println("Request URL: $url") // Логируем URL запроса

        return try {
            val response: HttpResponse = client.get(url)
            val status = response.status
            val body = response.bodyAsText()

            println("Response Status: $status")
            println("Response Body: $body")

            if (status.value == 200) {
               Json { ignoreUnknownKeys = true }.decodeFromString<WeatherResponse>(body)
            } else {
                println("Error: ${status.value}, Message: ${response.status.description}")
                null
            }
        } catch (e: Exception) {
           println("Error fetching weather: ${e.message}")
            null
        }
    }
}
