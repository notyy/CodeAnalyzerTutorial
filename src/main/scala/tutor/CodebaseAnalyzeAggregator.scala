package tutor

import tutor.utils.FileUtil
import tutor.utils.FileUtil.Path

trait CodebaseAnalyzeAggregator {

  private[tutor] def countFileTypeNum(files: Seq[Path]): Map[String, Int] = {
    files.groupBy(FileUtil.extractExtFileName).mapValues(_.length)
  }

  private[tutor] def longestFile(sourceCodeInfos: Seq[SourceCodeInfo]): Option[SourceCodeInfo] = {
    if (sourceCodeInfos.isEmpty) None
    else Some(sourceCodeInfos.max)
  }

  private[tutor] def top10Files(sourceCodeInfos: Seq[SourceCodeInfo]): Seq[SourceCodeInfo] = {
    sourceCodeInfos.sortBy(_.lineCount).reverse.take(10)
  }

  private[tutor] def totalLineCount(sourceCodeInfos: Seq[SourceCodeInfo]): Int = {
    sourceCodeInfos.map(_.lineCount).sum
  }

  private[tutor] def avgLines(files: Seq[Path], sourceCodeInfos: Seq[SourceCodeInfo]): Double = {
    val avgLineCount = sourceCodeInfos.map(_.lineCount).sum.toDouble / files.length
    avgLineCount
  }

  private[tutor] def aggregate(files: Seq[Path], sourceCodeInfos: Seq[SourceCodeInfo]): Option[CodebaseInfo] = {
    val avgLineCount: Double = avgLines(files, sourceCodeInfos)
    Some(CodebaseInfo(countFileTypeNum(files), totalLineCount(sourceCodeInfos), avgLineCount, longestFile(sourceCodeInfos), top10Files(sourceCodeInfos)))
  }
}
