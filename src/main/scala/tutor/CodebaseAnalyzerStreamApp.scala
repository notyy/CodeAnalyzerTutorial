package tutor

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import com.typesafe.scalalogging.slf4j.StrictLogging
import tutor.utils.BenchmarkUtil

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.util.{Failure, Success}

object CodebaseAnalyzerStreamApp extends App with DirectoryScanner with SourceCodeAnalyzer with ReportFormatter with StrictLogging {

  implicit val system = ActorSystem("CodebaseAnalyzer")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val path = args(0)
  val beginTime = BenchmarkUtil.recordStart(s"analyze $path with akka stream")
  val files = scan(path, PresetFilters.knownFileTypes, PresetFilters.ignoreFolders).iterator
  var errorProcessingFiles: ArrayBuffer[Throwable] = ArrayBuffer.empty

  val done = Source.fromIterator(() => files).mapAsync(8)(path => Future {
    processFile(path)
  }).fold(CodebaseInfo.empty) {
    (acc, trySourceCodeInfo) =>
      trySourceCodeInfo match {
        case Success(sourceCodeInfo) => acc + sourceCodeInfo
        case Failure(e) => {
          errorProcessingFiles += e
          acc
        }
      }
  }.runForeach(codebaseInfo => {
    println(format(codebaseInfo))
    println(s"there are ${errorProcessingFiles.size} files failed to process.")
  })
  done.onComplete { _ =>
    BenchmarkUtil.recordElapse(s"analyze $path with akka stream", beginTime)
    system.terminate()
  }
}
