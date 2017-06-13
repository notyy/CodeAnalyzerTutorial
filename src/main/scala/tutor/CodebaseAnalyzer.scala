package tutor

import tutor.utils.{BenchmarkUtil, FileUtil}
import tutor.utils.FileUtil._

object CodebaseInfo {
  def empty:CodebaseInfo = new CodebaseInfo(Map.empty[String,Int],0,0,null,Seq.empty[SourceCodeInfo])
}

case class CodebaseInfo(fileTypeNums: Map[String, Int], totalLineCount: Int, avgLineCount: Double,
                        longestFileInfo: Option[SourceCodeInfo],
                        top10Files: Seq[SourceCodeInfo]
                       )

trait CodebaseAnalyzer extends CodebaseAnalyzerInterface {
  this: DirectoryScanner with SourceCodeAnalyzer with CodebaseAnalyzeAggregator =>

  override def analyze(path: Path, knownFileTypes: Set[String], ignoreFolders: Set[String]): Option[CodebaseInfo] = {
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
