package com.jmarin

import com.jmarin.futures.Weather
import scala.concurrent.Await
import scala.concurrent.duration.*
import scala.util.Failure
import scala.util.Success
import com.jmarin.actor.SimpleActor
import com.jmarin.futures.FutureExample

object FuturesMain:

  def main(args: Array[String]): Unit =
    println("Hello Scala JVM Futures!")

    // Running Futures
    FutureExample.run()

    // Interacting with Actors
    SimpleActor.run()
