package com.jmarin.futures.weather

final case class City(name: String, abbr: String)

object cities:
  val cityList = List(
    City("Amsterdam", "AMS"),
    City("London", "LON"),
    City("Madrid", "MAD"),
    City("Rome", "ROM"),
    City("Stockholm", "STOC")
  )
