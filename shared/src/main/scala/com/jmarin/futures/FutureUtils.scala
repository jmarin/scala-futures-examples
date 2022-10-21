package com.jmarin.futures

import scala.concurrent.Future
import java.time.Instant
import scala.concurrent.ExecutionContext

trait FutureUtils:
  def time[T](f: => Future[T])(using ExecutionContext): Future[T] =
    val start = Instant.now().toEpochMilli()
    f.andThen { case _ =>
      val end = Instant.now().toEpochMilli()
      val duration = end - start
      println(s"Execution Time: $duration")
    }

  def sleep[T](f: => Future[T], milliseconds: Int)(using
      ExecutionContext
  ): Future[T]
