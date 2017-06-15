package tutor

import akka.actor.{ActorRef, ActorSystem}
import tutor.CodebaseAnalyzeAggregatorActor.AnalyzeDirectory

import scala.io.StdIn

object CodebaseAnalyzerAkkaApp extends App {

  val system = ActorSystem("CodebaseAnalyzer")
  val codebaseAnalyzerControllerActor: ActorRef = system.actorOf(CodebaseAnalyzerControllerActor.props())

  var shouldContinue = true
  try {
    while (shouldContinue) {
      println("please input source file folder or :q to quit")
      val input = StdIn.readLine()
      if (input == ":q") {
        shouldContinue = false
      } else {
        codebaseAnalyzerControllerActor ! AnalyzeDirectory(input)
      }
    }
  } finally {
    println("good bye!")
    system.terminate()
  }
}
