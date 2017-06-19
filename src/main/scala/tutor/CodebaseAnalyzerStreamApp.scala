package tutor

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import com.typesafe.scalalogging.slf4j.StrictLogging
import tutor.utils.BenchmarkUtil

import scala.util.{Failure, Success}

object CodebaseAnalyzerStreamApp extends App with DirectoryScanner with SourceCodeAnalyzer with ReportFormatter with StrictLogging {

  implicit val system = ActorSystem("CodebaseAnalyzer")
  implicit val materializer = ActorMaterializer()

  val path = args(0)
  val beginTime = BenchmarkUtil.recordStart(s"analyze $path with akka stream")
  val files = scan(path, PresetFilters.knownFileTypes, PresetFilters.ignoreFolders).iterator
  val done = Source.fromIterator(() => files).map(processFile).fold(CodebaseInfo.empty) {
    (acc, trySourceCodeInfo) =>
      trySourceCodeInfo match {
        case Success(sourceCodeInfo) => acc + sourceCodeInfo
        case Failure(e) => {
          logger.warn("error processing file", e)
          acc
        }
      }
  }.runForeach(codebaseInfo => println(format(codebaseInfo)))
  implicit val ec = system.dispatcher
  done.onComplete { _ =>
    BenchmarkUtil.recordElapse(s"analyze $path with akka stream", beginTime)
    system.terminate()
  }
}
