package tutor

import tutor.utils.FileUtil._
import tutor.utils.FileUtil

case class SourceCodeInfo(path: String, localPath: String, count: Int)
object SourceCodeInfo{
  implicit object SourceCodeInfoOrdering extends Ordering[SourceCodeInfo] {
    override def compare(x: SourceCodeInfo, y: SourceCodeInfo): Int = x.count compare y.count
  }
}

trait SourceCodeAnalyzer {
  def processFile(path: Path): SourceCodeInfo = {
    import scala.io._

    val source = Source.fromFile(path)
    val lines = source.getLines.toList
    SourceCodeInfo(path, FileUtil.extractLocalPath(path), lines.length)
  }

}