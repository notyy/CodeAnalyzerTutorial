package tutor

import tutor.utils.FileUtil
import tutor.utils.FileUtil._

case class CodebaseInfo(fileTypeNums: Map[String, Int])

trait CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer =>

  def countFileNum(path: Path): CodebaseInfo = {
    countFileTypeNum(scan(path))
  }

  private[tutor] def countFileTypeNum(files: Seq[Path]): CodebaseInfo = {
    CodebaseInfo(files.groupBy(FileUtil.extractExtFileName).mapValues(_.length))
  }
}
