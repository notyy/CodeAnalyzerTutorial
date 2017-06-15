package tutor

import akka.actor.{Actor, ActorLogging, Props}
import tutor.CodebaseAnalyzeAggregatorActor.Complete
import tutor.SourceCodeAnalyzerActor.NewFile


object SourceCodeAnalyzerActor {
  def props(): Props = Props(new SourceCodeAnalyzerActor)

  final case class NewFile(path: String)

}

class SourceCodeAnalyzerActor extends Actor with ActorLogging with SourceCodeAnalyzer {
  override def receive: Receive = {
    case NewFile(path) => {
      val sourceCodeInfo = processFile(path)
      sender() ! Complete(sourceCodeInfo)
    }
  }
}
