package tutor
import tutor.utils.FileUtil
import tutor.utils.FileUtil.Path

trait CodebaseAnalyzeAggregator {

  private[tutor] def countFileTypeNum(files: Seq[Path]): Map[String, Int] = {
    files.groupBy(FileUtil.extractExtFileName).mapValues(_.length)
  }

  private[tutor] def longestFile(sourceCodeInfos: Seq[SourceCodeInfo]): SourceCodeInfo = {
    sourceCodeInfos.max
  }

  private[tutor] def top10Files(sourceCodeInfos: Seq[SourceCodeInfo]): Seq[SourceCodeInfo] = {
    sourceCodeInfos.sortBy(_.count).reverse.take(10)
  }

  private[tutor] def totalLineCount(sourceCodeInfos: Seq[SourceCodeInfo]): Int = {
    sourceCodeInfos.map(_.count).sum
  }

  def avgLines(files: Seq[Path], sourceCodeInfos: Seq[SourceCodeInfo]): Double = {
    val avgLineCount = sourceCodeInfos.map(_.count).sum.toDouble / files.length
    avgLineCount
  }
}
