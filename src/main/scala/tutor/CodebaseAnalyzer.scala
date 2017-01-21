package tutor

import tutor.utils.FileUtil
import tutor.utils.FileUtil._

case class CodebaseInfo(fileTypeNums: Map[String, Int], totalLineCount: Int, avgLineCount: Double,
                        longestFileInfo: SourceCodeInfo,
                        top10Files: Seq[SourceCodeInfo]
                       )

trait CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer =>

  def analyze(path: Path, knownFileTypes: Set[String]): CodebaseInfo = {
    val files = scan(path,knownFileTypes)
    val sourceCodeInfos: Seq[SourceCodeInfo] = files.map(processFile)
    val avgLineCount = sourceCodeInfos.map(_.count).sum.toDouble / files.length
    CodebaseInfo(countFileTypeNum(files), totalLineCount(sourceCodeInfos),avgLineCount, longestFile(sourceCodeInfos), top10Files(sourceCodeInfos))
  }

  private[tutor] def countFileTypeNum(files: Seq[Path]): Map[String, Int] = {
    files.groupBy(FileUtil.extractExtFileName).mapValues(_.length)
  }

  private[tutor] def longestFile(sourceCodeInfos: Seq[SourceCodeInfo]): SourceCodeInfo = {
    sourceCodeInfos.sorted.last
  }

  private[tutor] def top10Files(sourceCodeInfos: Seq[SourceCodeInfo]): Seq[SourceCodeInfo] = {
    sourceCodeInfos.sortBy(_.count).reverse.take(10)
  }

  private[tutor] def totalLineCount(sourceCodeInfos: Seq[SourceCodeInfo]): Int = {
    sourceCodeInfos.map(_.count).sum
  }
}
