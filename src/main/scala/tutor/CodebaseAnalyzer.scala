package tutor

import tutor.utils.FileUtil
import tutor.utils.FileUtil._


trait CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer =>

  type FileType = String

  def countFileNum(path: Path): Map[FileType, Int] = {
    scan(path).groupBy(FileUtil.extractExtFileName).mapValues(_.length)
  }
}
