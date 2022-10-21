package com.jmarin.futures.weather

case class WeatherConditions(
    city: String,
    temp: Double,
    pressure: Int,
    precipProbability: Int
)
