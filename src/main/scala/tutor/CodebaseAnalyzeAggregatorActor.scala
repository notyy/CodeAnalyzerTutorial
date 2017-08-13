package tutor

import java.util.Date

import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}
import tutor.CodebaseAnalyzeAggregatorActor.{AnalyzeDirectory, Complete, Report, Timeout}
import tutor.SourceCodeAnalyzerActor.NewFile
import tutor.utils.BenchmarkUtil

import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

object CodebaseAnalyzeAggregatorActor {
  def props(): Props = Props(new CodebaseAnalyzeAggregatorActor)

  final case class AnalyzeDirectory(path: String)

  final case class Complete(result: Try[SourceCodeInfo])

  final case object Timeout

  final case class Report(codebaseInfo: CodebaseInfo)

}

class CodebaseAnalyzeAggregatorActor extends Actor with ActorLogging with DirectoryScanner with ReportFormatter {
  var controller: ActorRef = _
  var currentPath: String = _
  var beginTime: Date = _
  var fileCount = 0
  var completeCount = 0
  var failCount = 0
  var result: CodebaseInfo = CodebaseInfo.empty
  var timeoutTimer: Cancellable = _

  var router: Router = {
    val routees = Vector.fill(8) {
      val r = context.actorOf(SourceCodeAnalyzerActor.props())
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  override def receive: Receive = {
    case AnalyzeDirectory(path) => {
      controller = sender()
      currentPath = path
      beginTime = BenchmarkUtil.recordStart(s"analyze folder $currentPath")
      foreachFile(path, PresetFilters.knownFileTypes, PresetFilters.ignoreFolders) { file =>
        fileCount += 1
        router.route(NewFile(file.getAbsolutePath), context.self)
      }
      import context.dispatcher
      timeoutTimer = context.system.scheduler.scheduleOnce((fileCount / 1000).seconds, context.self, Timeout)
    }
    case Complete(Success(sourceCodeInfo: SourceCodeInfo)) => {
      completeCount += 1
      result = result + sourceCodeInfo
      finishIfAllComplete()
    }
    case Complete(Failure(exception)) => {
      completeCount += 1
      failCount += 1
      log.warning("processing file failed {}", exception)
      finishIfAllComplete()
    }
    case Timeout => {
      println(s"${result.totalFileNums} of $fileCount files processed before timeout")
      controller ! Report(result)
      BenchmarkUtil.recordElapse(s"analyze folder $currentPath", beginTime)
    }
    case Terminated(a) =>
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[SourceCodeAnalyzerActor])
      context watch r
      router = router.addRoutee(r)
    case x@_ => log.error(s"receive unknown message $x")
  }

  def finishIfAllComplete(): Unit = {
    if (completeCount == fileCount) {
      timeoutTimer.cancel()
      controller ! Report(result)
      BenchmarkUtil.recordElapse(s"analyze folder $currentPath", beginTime)
      context.stop(self)
    }
  }
}
