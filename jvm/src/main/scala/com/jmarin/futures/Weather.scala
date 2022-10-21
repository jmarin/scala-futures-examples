package com.jmarin.futures

import com.jmarin.futures.weather.WeatherApi
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

object Weather extends WeatherApi:
  def sleep[T](f: => Future[T], milliseconds: Int)(using
      ExecutionContext
  ): Future[T] =
    Thread.sleep(milliseconds)
    f
