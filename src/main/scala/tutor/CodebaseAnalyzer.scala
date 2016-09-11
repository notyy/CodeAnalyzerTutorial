package tutor

import tutor.utils.FileUtil
import tutor.utils.FileUtil._


trait CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer =>

  type FileType = String

  def countFileNum(path: Path): Map[FileType, Int] = {
    countFileTypeNum(scan(path))
  }

  private[tutor] def countFileTypeNum(files: Seq[Path]): Map[String, Int] = {
    files.groupBy(FileUtil.extractExtFileName).mapValues(_.length)
  }
}
