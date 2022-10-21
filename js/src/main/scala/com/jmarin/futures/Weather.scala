package com.jmarin.futures

import com.jmarin.futures.weather.WeatherApi
import scala.concurrent.Future
import scalajs.js
import scala.concurrent.Promise
import scala.util.Success
import scala.util.Failure
import scala.concurrent.ExecutionContext

object Weather extends WeatherApi:

  def sleep[T](f: => Future[T], milliseconds: Int)(using
      ExecutionContext
  ): Future[T] =
    val p = Promise[T]
    js.timers.setTimeout(milliseconds) {
      f.onComplete {
        case Success(value)     => p.success(value)
        case Failure(exception) => p.failure(exception)
      }
    }
    p.future
