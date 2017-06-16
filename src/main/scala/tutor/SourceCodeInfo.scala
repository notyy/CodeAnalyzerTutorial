package tutor

import com.typesafe.scalalogging.slf4j.StrictLogging
import tutor.utils.FileUtil._
import tutor.utils.FileUtil

import scala.util.Try

final case class SourceCodeInfo(path: String, localPath: String, lineCount: Int)

object SourceCodeInfo {

  implicit object SourceCodeInfoOrdering extends Ordering[SourceCodeInfo] {
    override def compare(x: SourceCodeInfo, y: SourceCodeInfo): Int = x.lineCount compare y.lineCount
  }

}

trait SourceCodeAnalyzer extends StrictLogging {
  def processFile(path: Path): Try[SourceCodeInfo] = {
    import scala.io._
    Try {
      val source = Source.fromFile(path)
      try {
        val lines = source.getLines.toList
        SourceCodeInfo(path, FileUtil.extractLocalPath(path), lines.length)
      } catch {
        case e => throw new IllegalArgumentException(s"error processing file $path", e)
      } finally {
        source.close()
      }
    }
  }
}