package tutor

import com.typesafe.scalalogging.slf4j.StrictLogging
import tutor.utils.FileUtil._
import tutor.utils.FileUtil

import scala.util.Try

case class SourceCodeInfo(path: String, localPath: String, count: Int)

object SourceCodeInfo {

  implicit object SourceCodeInfoOrdering extends Ordering[SourceCodeInfo] {
    override def compare(x: SourceCodeInfo, y: SourceCodeInfo): Int = x.count compare y.count
  }

}

trait SourceCodeAnalyzer extends StrictLogging {
  def processFile(path: Path): Try[SourceCodeInfo] = {
    import scala.io._
    logger.info(s"processing $path")
    Try {
      val source = Source.fromFile(path)
      val lines = source.getLines.toList
      SourceCodeInfo(path, FileUtil.extractLocalPath(path), lines.length)
    }
  }
}