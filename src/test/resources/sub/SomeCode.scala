package tutor

class SomeCode(val path: String, val name: String, private val lines: List[String]) {
  def count: Int = lines.length
}

object SomeCode {
  def fromFile(path: Path): SourceCodeInfo = {
    import scala.io._

    val source = Source.fromFile(path)
    val lines = source.getLines.toList
    new SourceCodeInfo(path, extractLocalPath(path), lines)
  }

}