package com.jmarin.actor

import akka.actor.typed.Behavior
import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import akka.util.Timeout
import akka.actor.typed.Scheduler
import scala.concurrent.Future
import scala.concurrent.duration.*
import akka.actor.typed.scaladsl.AskPattern.*
import scala.util.Failure
import scala.util.Success
import scala.concurrent.ExecutionContext

object SimpleActor:

  final case class Ping(caller: String, replyTo: ActorRef[Pong])
  final case class Pong(receiver: String, from: ActorRef[Ping])

  def apply(): Behavior[Ping] = Behaviors.receive { (context, message) =>
    context.log.info("Received Ping {}", message.caller)
    message.replyTo ! Pong(message.caller, context.self)
    Behaviors.same
  }

  // *****************************//
  // Interacting with Actors //

  def run(): Unit =
    val system: ActorSystem[Ping] =
      ActorSystem(SimpleActor(), "ping-jvm")

    given timeout: Timeout = Timeout(5.seconds)
    given scheduler: Scheduler = system.scheduler
    given ec: ExecutionContext = system.executionContext

    val f: Future[Pong] = system.ask(ref => Ping("pinger", ref))

    f.onComplete {
      case Failure(exception) => println("I can't talk to this Actor")
      case Success(value) =>
        println(
          s"Pong received message from Ping with payload: ${value}"
        )
    }
