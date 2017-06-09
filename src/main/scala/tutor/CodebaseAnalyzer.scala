package tutor

import tutor.utils.{BenchmarkUtil, FileUtil}
import tutor.utils.FileUtil._

case class CodebaseInfo(fileTypeNums: Map[String, Int], totalLineCount: Int, avgLineCount: Double,
                        longestFileInfo: SourceCodeInfo,
                        top10Files: Seq[SourceCodeInfo]
                       )

trait CodebaseAnalyzer {
  this: DirectoryScanner with SourceCodeAnalyzer with CodebaseAnalyzeAggregator =>

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
        val avgLineCount: Double = avgLines(files, sourceCodeInfos)
        Some(CodebaseInfo(countFileTypeNum(files), totalLineCount(sourceCodeInfos), avgLineCount, longestFile(sourceCodeInfos), top10Files(sourceCodeInfos)))
      }
    }
  }

  protected def processSourceFiles(files: Seq[Path]): Seq[SourceCodeInfo]

}
