package tutor

import tutor.utils.FileUtil
import tutor.utils.FileUtil._

case class CodebaseInfo(fileTypeNums: Map[String, Int], avgLineCount: Double)

trait CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer =>

  def analyze(path: Path): CodebaseInfo = {
    val files = scan(path)
    val avgLineCount = files.map(processFile).map(_.count).sum.toDouble / files.length
    CodebaseInfo(countFileTypeNum(files), avgLineCount)
  }

  private[tutor] def countFileTypeNum(files: Seq[Path]): Map[String, Int] = {
    files.groupBy(FileUtil.extractExtFileName).mapValues(_.length)
  }

}
