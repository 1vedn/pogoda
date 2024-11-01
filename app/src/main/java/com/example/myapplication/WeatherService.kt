package com.example.myapplication

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

const val API_KEY = "f8e870107c61f67c1967732e72f93fe8"
const val BASE_URL = "https://api.openweathermap.org/data/2.5"

@Serializable
data class WeatherResponse(val main: Main)

@Serializable
data class Main(val temp: Float, val humidity: Int)

object WeatherService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getWeather(city: String): WeatherResponse? {
        val url = "$BASE_URL/weather?q=$city&appid=$API_KEY&units=metric"
        println("Request URL: $url") // Выводим URL для отладки
        return try {
            val response: HttpResponse = client.get(url)
            response.body()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }
}
