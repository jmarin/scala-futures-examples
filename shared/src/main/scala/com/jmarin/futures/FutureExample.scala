package com.jmarin.futures

import scala.util.Failure
import scala.util.Success

object FutureExample:
  def run(): Unit =
    import scala.concurrent.ExecutionContext.Implicits.global

    (for
      fs <- Weather.weatherSequential("MAD")
      fp <- Weather.weatherParallel("MAD")
    yield (fs, fp)).onComplete {
      case Failure(exception) =>
        println(s"Something failed ${exception.getLocalizedMessage()}")
      case Success(value) =>
        println(
          s"Prediction from sequential futures: ${value._1}\nPrediction from parallel futures: ${value._2}"
        )
    }
