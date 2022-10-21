package com.jmarin.futures.weather

import scala.concurrent.Future
import com.jmarin.futures.weather.cities.*
import scala.concurrent.ExecutionContext
import com.jmarin.futures.FutureUtils

trait WeatherApi extends FutureUtils:

  val rand = new scala.util.Random

  def currentTime = System.currentTimeMillis()
  def timeSpent(t: Long) = currentTime - t

  def pressure(city: String)(using ExecutionContext): Future[Int] =
    val f =
      if !cityList.map(_.abbr).contains(city) then
        Future.failed(throw new Exception("City not found"))
      else
        val pressure = city match
          case "AMS"  => rand.between(980, 1013)
          case "LON"  => rand.between(990, 1020)
          case "MAD"  => rand.between(1013, 1030)
          case "ROM"  => rand.between(1000, 1025)
          case "STOC" => rand.between(980, 1000)
        Future(pressure)

    sleep(f, rand.nextInt(2000))

  def temp(city: String)(using ExecutionContext): Future[Double] =
    val f =
      if !cityList.map(_.abbr).contains(city) then
        Future.failed(throw new Exception("City not found"))
      else
        val temp = city match
          case "AMS"  => rand.between(-2.0, 15.0)
          case "LON"  => rand.between(0.5, 11.5)
          case "MAD"  => rand.between(-1.2, 20.6)
          case "ROM"  => rand.between(0.1, 16.3)
          case "STOC" => rand.between(-5.2, 8.5)
        Future(temp)
    sleep(f, rand.nextInt(2000))

  def precipProbability(city: String)(using ExecutionContext): Future[Int] =
    val f =
      if !cityList.map(_.abbr).contains(city) then
        Future.failed(throw new Exception("City not found"))
      else
        val prob = city match
          case "AMS"  => rand.between(80, 100)
          case "LON"  => rand.between(20, 100)
          case "MAD"  => rand.between(0, 60)
          case "ROM"  => rand.between(5, 80)
          case "STOC" => rand.between(60, 100)
        Future(prob)
    sleep(f, rand.nextInt(2000))

  def weatherSequential(
      city: String
  )(using ExecutionContext): Future[WeatherConditions] =
    if !cityList.map(_.abbr).contains(city) then
      Future.failed(throw new Exception("City not found"))
    else
      time(for
        temp <- temp(city)
        pressure <- pressure(city)
        precipProbability <- precipProbability(city)
      yield WeatherConditions(city, temp, pressure, precipProbability))

  def weatherParallel(
      city: String
  )(using ExecutionContext): Future[WeatherConditions] =
    if !cityList.map(_.abbr).contains(city) then
      Future.failed(throw new Exception("City not found"))
    else
      val t = temp(city)
      val p = pressure(city)
      val prob = precipProbability(city)
      time(for
        temp <- t
        pressure <- p
        precipProbability <- prob
      yield WeatherConditions(city, temp, pressure, precipProbability))
