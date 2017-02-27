package tutor

import tutor.utils.{BenchmarkUtil, FileUtil}
import tutor.utils.FileUtil._

case class CodebaseInfo(fileTypeNums: Map[String, Int], totalLineCount: Int, avgLineCount: Double,
                        longestFileInfo: SourceCodeInfo,
                        top10Files: Seq[SourceCodeInfo]
                       )

trait CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer =>

  def analyze(path: Path, knownFileTypes: Set[String], ignoreFolders: Set[String]): Option[CodebaseInfo] = {
    val files = BenchmarkUtil.record("scan folders") {
      scan(path, knownFileTypes, ignoreFolders)
    }
    if (files.isEmpty) {
      None
    } else {
      val sourceCodeInfos: Seq[SourceCodeInfo] = BenchmarkUtil.record("processing each file") {
        processSourceFiles(files)
      }
      BenchmarkUtil.record("make last result ##") {
        val avgLineCount = sourceCodeInfos.map(_.count).sum.toDouble / files.length
        Some(CodebaseInfo(countFileTypeNum(files), totalLineCount(sourceCodeInfos), avgLineCount, longestFile(sourceCodeInfos), top10Files(sourceCodeInfos)))
      }
    }
  }


  protected def processSourceFiles(files: Seq[Path]): Seq[SourceCodeInfo]

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
