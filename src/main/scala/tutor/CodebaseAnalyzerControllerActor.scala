package tutor

import akka.actor.{Actor, Props}
import tutor.CodebaseAnalyzeAggregatorActor.{AnalyzeDirectory, Report}

object CodebaseAnalyzerControllerActor {
  def props(): Props = Props(new CodebaseAnalyzerControllerActor)
}

class CodebaseAnalyzerControllerActor extends Actor with ReportFormatter {
  override def receive: Receive = {
    case AnalyzeDirectory(path) => {
      context.actorOf(CodebaseAnalyzeAggregatorActor.props()) ! AnalyzeDirectory(path)
    }
    case Report(content) => {
      println(format(content))
    }
  }
}
